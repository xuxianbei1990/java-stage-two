package thread.chapter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//https://www.cnblogs.com/dolphin0520/p/3920397.html
/*
 * 1）CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：

　　　　CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；

　　　　而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；

　　　　另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。

　　2）Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
 */

class SemaphoreTest {

	static void mainTest(String[] args) {
		int N = 8;            //工人数
        Semaphore semaphore = new Semaphore(5); //机器数目
        for(int i=0;i<N;i++)
            new Worker(i,semaphore).start();
	}
	static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }
         
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人"+this.num+"占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人"+this.num+"释放出机器");
                semaphore.release();           
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * https://www.cnblogs.com/dolphin0520/p/3920397.html
 * 
 * @author xuxb 高性能和缓冲队列 测试
 */
class ThreadInfo {
	final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10);

	/*
	 * corePoolSize ： 线程池维护线程的最少数量，哪怕是空闲的。 maximumPoolSize ：线程池维护线程的最大数量。
	 * keepAliveTime ： 线程池维护线程所允许的空闲时间。 unit ： 线程池维护线程所允许的空闲时间的单位。 workQueue ：
	 * 线程池所使用的缓冲队列，改缓冲队列的长度决定了能够缓冲的最大数量。
	 */
	final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 600, 30, TimeUnit.SECONDS, queue,
			Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
	final AtomicInteger completedTask = new AtomicInteger(0);
	final AtomicInteger rejectedTask = new AtomicInteger(0);

	static long beginTime;

	final int count = 1000;

	public void start() {
		CountDownLatch latch = new CountDownLatch(count);
		CyclicBarrier barrier = new CyclicBarrier(count);
		for (int i = 0; i < count; i++) {
			new Thread(new TestThread(latch, barrier)).start();
		}
		try {
			latch.await();
			executor.shutdownNow();
			System.err.println("被拒绝的任务数为：" + rejectedTask.get());
			System.err.println("任务数为：" + completedTask.get());
		} catch (InterruptedException e1) {
			e1.printStackTrace();

		}
	}

	class TestThread implements Runnable {
		private CountDownLatch latch;

		private CyclicBarrier barrier;

		public TestThread(CountDownLatch latch, CyclicBarrier barrier) {
			this.latch = latch;
			this.barrier = barrier;
		}

		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				executor.execute(new Task(latch));
			} catch (RejectedExecutionException exception) {
				latch.countDown();
				rejectedTask.incrementAndGet();
			}
		}

		class Task implements Runnable {
			private CountDownLatch latch;

			public Task(CountDownLatch latch) {
				this.latch = latch;
			}

			@Override
			public void run() {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				completedTask.incrementAndGet();
				System.out.println("任务耗时为：" + (System.currentTimeMillis() - beginTime) + "ms");
				latch.countDown();
			}
		}
	}
}

/**
 * @author xuxb
 *
 */
public class ThreadPoolDemo {

	void testThreadPoolExecutor() {
		// 拒绝任务：拒绝任务是指当线程池里面的线程数量达到 maximumPoolSize
		// 且 workQueue 队列已满的情况下被尝试添加进来的任务。
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(0, 0, 0, null, null);
		// 拒绝任务，
		// 这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
		CallerRunsPolicy crp = new CallerRunsPolicy();
		tpe.setRejectedExecutionHandler(crp);
		// 拒绝任务，直接异常
		AbortPolicy ap = new AbortPolicy();
		// 拒绝任务，不做任务动作
		DiscardPolicy dp = new DiscardPolicy();

		// 拒绝任务，对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
		DiscardOldestPolicy dop = new DiscardOldestPolicy();

		// 高性能 缓冲队列用
		SynchronousQueue sq = new SynchronousQueue();
		// 缓冲执行 ArrayBlockingQueue 或者 LinkedBlockingQueue
	}

	void testThreadPool(ExecutorService threadPool) {
		for (int i = 1; i < 5; i++) {
			final int taskID = i;
			threadPool.execute(new Runnable() {
				public void run() {
					for (int i = 1; i < 5; i++) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("第" + taskID + "次任务的第" + i + "次执行");
					}
				}
			});
		}
		threadPool.shutdown();
	}

	void testSchedulePool(ScheduledExecutorService schedulePool) {
		// 5秒之后执行
		// schedulePool.schedule(new Runnable(){
		// public void run(){
		// System.out.println("流星");
		// }
		// }, 5, TimeUnit.SECONDS);

		// 5秒之后执行，之后2秒执行一次
		// scheduleWithFixedDelay 类似lol一句结束 重开一局时间间隔固定。 优先保证任务执行的间隔。
		/*
		 * 假设他每玩一局休息10秒钟，然后再开始玩。每开新的一局，打完的时间不是固定的，但是间隔是固定的，
		 * 就是delay=10秒，表现在时间轴上就是【第一局260秒】【休息10秒】【第二局150秒】 【休息10秒】...
		 * 【第N局180秒】【休息10秒】。这个是优先保证任务执行的间隔。
		 */
		// scheduleAtFixedRate 类似飞机起飞， 优先保证任务执行的频率。
		schedulePool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out.println("陨落");
			}
		}, 5, 5, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {
		ThreadPoolDemo tpd = new ThreadPoolDemo();
		// 可重用固定线程集合的线程池，以共享的无界队列方式来运行这些线程
		// 创建可以容纳3个线程的线程池
		// tpd.testThreadPool(Executors.newFixedThreadPool(3));
		// 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们
		// 线程池的大小会根据执行的任务数动态分配
		// tpd.testThreadPool(Executors.newCachedThreadPool());
		// 创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程
		// 创建单个线程的线程池，如果当前线程在执行任务时突然中断，则会创建一个新的线程替
		// tpd.testThreadPool(Executors.newSingleThreadExecutor());
		// 创建一个可安排在给定延迟后运行命令或者定期地执行的线程池。
		// 效果类似于Timer定时器
		// tpd.testSchedulePool(Executors.newScheduledThreadPool(3));

//		ThreadInfo threadInfo = new ThreadInfo();
//		ThreadInfo.beginTime = System.currentTimeMillis();
//		threadInfo.start();
		
		SemaphoreTest.mainTest(args);
	}

}
