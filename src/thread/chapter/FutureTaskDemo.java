package thread.chapter;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

// FutureTask执行多任务计算的使用场景
class FutureTaskForMultiCompute {
	static void mainTest() {
		FutureTaskForMultiCompute inst = new FutureTaskForMultiCompute();
		// 创建任务集合
		List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
		// 创建线程池
		ExecutorService exec = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 10; i++) {
			FutureTask<Integer> ft = new FutureTask<Integer>(inst.new ComputeTask(i, "" + i));
			taskList.add(ft);
			// 提交给线程池执行任务， 也可以通过exec.invokeAll(taskList)一次性提交所有任务;
			exec.submit(ft);
		}

		System.out.println("所有计算任务提交完毕， 主线程接着干其他事情!");
		
		// 开始统计各计算线程计算结果
		Integer totalResult = 0;
		for (FutureTask<Integer> ft: taskList) {
			try {
				//FutureTask的get方法会自动阻塞,直到获取计算结果为止  
                totalResult = totalResult + ft.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		// 关闭线程池
		exec.shutdown();
		System.out.println("多任务计算后的总结果是：" + totalResult);
	}

	/*
	 * Callable 和 Runnable 都可以作为任务去执行,异同点主要表现在以下几点
1.Callable是一个接口需要实现call()方法,Runnable也是一个接口需要是实现run() 方法
2.call()方法可以抛出异常,run()方法无法抛出异常,只能自行处理
3.Callable类有泛型,在创建的时候可以传递进去,在实现call()方法的时候可以返回
4.Callable执行任务的时候可以通过FutureTask知晓任务执行的状态以及结果(FutureTask的get()方法),Runnable无法知晓任务执行的状态以及结果.
5.Runnable实例对象需要Thread包装启动,Callable实例对象需要先通过FutureTask包装再丢给Thread包装执行(FutrueTask对象本质还是Runnable),
当然在线程池里不需要那么复杂
	 */
	private class ComputeTask implements Callable<Integer> {

		private Integer result = 0;

		private String taskName = "";

		public ComputeTask(Integer iniResult, String taskName) {
			result = iniResult;
			this.taskName = taskName;
			System.out.println("生成子线程计算任务:" + taskName);
		}

		public String getTaskName() {
			return this.taskName;
		}

		@Override
		public Integer call() throws Exception {
			for (int i = 0; i < 100; i++) {
				result = +i;
			}
			// 休眠5秒钟，观察主线程行为，预期的结果是主线程会继续执行，
			// 到要取得FutureTask的结果是等待直至完成。
			Thread.sleep(5000);
			return result;
		}

	}
}

/*
 * FutureTask在高并发环境下确保任务只执行一次
在很多高并发的环境下，往往我们只需要某些任务只执行一次。
这种使用情景FutureTask的特性恰能胜任
举一个例子，假设有一个带key的连接池，当key存在时，即直接返回key对应的对象；
当key不存在时，则创建连接。对于这样的应用场景，通常采用的方法为使用一个Map
对象来存储key和连接池对应的对应关系，典型的代码如下面所示：
 */
class OldConnection {
	private Map<String, Connection> connectionPool = new HashMap<String, Connection>();
	private ReentrantLock lock = new ReentrantLock();
	
	public Connection getConnection(String key) {
		try {
			lock.lock();
			if (connectionPool.containsKey(key)) {
				return connectionPool.get(key);
			} else {
				//创建 Connection  
	            Connection conn = createConnection();  
	            connectionPool.put(key, conn);
	            return conn;
			} 
		} finally {
			lock.unlock();
		}
	}
	
	private Connection createConnection() {
		return null;
	}
}

/*
 * 我们通过加锁确保高并发环境下的线程安全，也确保了connection只创建一次，然而确牺牲了性能。
 * 改用ConcurrentHash的情况下，几乎可以避免加锁的操作，性能大大提高，但是在高并发的情况下有
 * 可能出现Connection被创建多次的现象。这时最需要解决的问题就是当key不存在时，创建Connection
 * 的动作能放在connectionPool之后执行，这正是FutureTask发挥作用的时机，
 * 基于ConcurrentHashMap和FutureTask的改造代码如下：
 */
class NewConnection {
	private ConcurrentHashMap<String, FutureTask<Connection>> connectionPool = new ConcurrentHashMap<String, FutureTask<Connection>>();
	
	public Connection getConnection(String key) throws Exception {
		FutureTask<Connection> connectionTask = connectionPool.get(key);
		if (connectionTask != null) {
			return connectionTask.get();
		} else {
			Callable<Connection> callable = new Callable<Connection>() {
				@Override
				public Connection call() throws Exception {
					return createConnection();
				}
				
			};
			FutureTask<Connection> newTask = new FutureTask<Connection>(callable);
			newTask = connectionPool.putIfAbsent(key, newTask);
			if (connectionTask == null) {
				connectionTask = newTask;  
	            connectionTask.run();
			}
			return connectionTask.get(); 
		}
	}

	protected Connection createConnection() {
		return null;
	}
}

public class FutureTaskDemo {

}
