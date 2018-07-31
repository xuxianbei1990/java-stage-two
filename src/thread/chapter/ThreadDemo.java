package thread.chapter;

class SynchronizedStatic implements Runnable {
	static boolean staticFlag = true;

	// 静态方法同步监视器 是类本身
	public static synchronized void test0() {
		for (int i = 0; i < 100; i++) {
			System.out.println("test0:" + Thread.currentThread().getName() + " " + i);
		}
	}

	public void test1() {
		/*
		 * 同步监视器 是类对象 所以可以和 test0同时进行 如果换成SynchronizedStatic.class 那么必须等待test0完成
		 */
		synchronized (this) {
			for (int i = 0; i < 100; i++) {
				System.out.println("test1:" + Thread.currentThread().getName() + " " + i);
			}
		}
	}

	@Override
	public void run() {
		if (staticFlag) {
			staticFlag = false;
			test0();
		} else {
			staticFlag = true;
			test1();
		}
	}
}

// 线程测试 Stringbuffer 是线程安全的。
class StringRun implements Runnable {
	StringBuilder builder = new StringBuilder();
	StringBuffer buffer = new StringBuffer();

	public void run() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		builder.append(1);
		buffer.append(1);
	}

	public static void testSynchronized() throws InterruptedException {
		ThreadGroup group = new ThreadGroup("testgroup");
		StringRun t = new StringRun();
		for (int i = 0; i < 100000; i++) {
			Thread th = new Thread(group, t, String.valueOf(i));
			th.start();
		}

		while (group.activeCount() > 0) {
			Thread.sleep(10);
		}
		// 如果长度为100000就是安全的
		System.out.println(t.builder.length());
		System.out.println(t.buffer.length());
	}
}

// 死锁
/*
 * 突破程序员基本功的16课 170 初始化过程 1. 为该类所有静态field分配内存； 2. 调用静态初始化块的代码执行初始化。
 */
class StaticThreadInit {
	static {
		Thread t = new Thread() {
			public void run() {
				System.out.println("进入run方法");
				// 在调用这个方式时候一定会等待类初始化完成
				System.out.println(website);
				website = "www.baidu.com";
				System.out.println("退出run方法");
			}
		};
		t.start();
		try {
			// 让该其他线程等待这个线程执行完毕
			t.join();

			Thread.sleep(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	static String website = "www.lukaer.com";
}

class Account {
	private String accountNo;

	private double balance;

	public Account(String accountNo, double balance) {
		this.accountNo = accountNo;
		this.balance = balance;
	}

	// 有无同步结果
	public /* synchronized */ double getBalance() {
		return this.balance;
	}

	public /* synchronized */ void draw(double drawAmount) {
		if (balance >= drawAmount) {
			System.out.println(Thread.currentThread().getName() + "取钱成功！" + drawAmount);

			balance -= drawAmount;

			System.out.println("\t余额为：" + balance);

		} else {
			System.out.println(Thread.currentThread().getName() + "取钱失败，余额不足");
		}

	}

	public int hashCode() {
		return accountNo.hashCode();
	}

}

class DrawThread extends Thread {
	private Account account;

	private double drawAmount;

	public DrawThread(String name, Account account, double drawAmout) {
		super(name);
		this.account = account;
		this.drawAmount = drawAmout;
	}

	public void run() {
		account.draw(drawAmount);
	}
}

public class ThreadDemo {

	private void testSynchronizeStatic() {
		SynchronizedStatic ss = new SynchronizedStatic();
		new Thread(ss).start();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(ss).start();
	}

	private void testAccount() {
		Account acct = new Account("1234567", 1000);
		for (int i = 0; i < 5; i++) {
			new DrawThread(String.valueOf(i), acct, 800).start();
		}
	}

	public static void main(String args[]) throws InterruptedException {
		// 测试同步监视器
		StringRun.testSynchronized();

		// 测试Stringbuffer 线程安全
		// ThreadDemo td = new ThreadDemo();
		// td.testSynchronizeStatic();

		// 测试同步
		// ThreadDemo td = new ThreadDemo();
		// td.testAccount();

		// 测试死锁
		// System.out.println(StaticThreadInit.website);
	}
}
