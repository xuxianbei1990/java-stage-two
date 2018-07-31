package thread.chapter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class TestInputAccount extends Thread {
	ReentrantLock lock;
	Condition condition;

	TestInputAccount(ReentrantLock lock, Condition condition) {
		this.lock = lock;
		this.condition = condition;
	}

	public void run() {
		try {
			lock.lock();
			try {
				System.out.println("入锁");
				condition.await();// 等待
				System.out.println("入账了");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			lock.unlock();
		}

	}

}

class TestOutputAccout extends Thread {

	ReentrantLock lock;
	Condition condition;

	TestOutputAccout(ReentrantLock lock, Condition condition) {
		this.lock = lock;
		this.condition = condition;
	}

	public void run() {
		try {
			lock.lock();
			System.out.println("出锁");
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			condition.signal();// 通知
			System.out.println("出账");
		} finally {
			lock.unlock();
		}

	}
}

class testInterruptibly implements Runnable {

	ReentrantLock lock;
	int index;

	testInterruptibly(ReentrantLock lock, int index) {
		this.lock = lock;
		this.index = index;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		String str = "run";
		if (index == 1) {
			try {
				// 如果调用Interruptibly() ;那么该线程会立即中断
				lock.lockInterruptibly();
				Thread.currentThread().sleep(3000);
				str = "lockInterruptibly";
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		} else {
			try {
				// 无视Interrupt方法
				lock.lock();
				str = "lock";
				try {
					Thread.currentThread().sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				lock.unlock();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(str + index + ":" + (end - start));
	}

}

// 公平锁和非公平锁
class TestLock implements Runnable {

	private ReentrantLock lock;
	private int index;

	TestLock(ReentrantLock lock, int index) {
		this.lock = lock;
		this.index = index;
	}

	@Override
	public void run() {
		try {
			lock.lock();
			try {
				Thread.currentThread().sleep(100 * index);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.print("," + index);
		} finally {
			lock.unlock();
		}
	}

}

// 测试trylock
class TestTryLock implements Runnable {

	private ReentrantLock lock;
	private int timeInterval = 0;

	TestTryLock(ReentrantLock lock) {
		this.lock = lock;
	}

	TestTryLock(ReentrantLock lock, int timeInterval) {
		this.lock = lock;
		this.timeInterval = timeInterval;
	}

	@Override
	public void run() {
		if (timeInterval == 0) {
			testTryLock();
		} else {
			testTimeOutTryLock();
		}

	}

	private void testTimeOutTryLock() {
		long start = System.currentTimeMillis();
		try {
			// 如果已经被lock，尝试等待5s，看是否可以获得锁，如果5s后仍然无法获得锁则返回false继续执行
			if (lock.tryLock(timeInterval, TimeUnit.SECONDS)) {
				try {
					Thread.currentThread().sleep(1000);
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace(); // 当前线程被中断时(interrupt)，会抛InterruptedException
		}
		long end = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName() + ":" + (end - start));
	}

	public void testTryLock() {
		String str = "耐心等待";

		if (lock.tryLock()) {
			try {
				System.out.println("我被锁住了");
				str = "解锁";
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				lock.unlock();
			}
		}
		System.out.println(str);
	}

}

//测试线程的锁 测试线程安全与不安全问题
class TestThread extends Thread {
	static int safeCount, count;
	ReentrantLock lock;

	TestThread(ReentrantLock lock) {
		this.lock = lock;
	}

	void safe(){
		lock.lock();
		safeCount++;
		lock.unlock();
	}
	
	public void run() {
		count++;
		safe();
	}
}

public class ThreadLockDemo {

	public static void main(String args[]) {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		// for (int i = 0; i < 5; i++) {
		// new Thread(new TestTryLock(lock)).start();
		// }

		// 测试超时锁
		// for (int i = 0; i < 5; i++) {
		// new Thread(new TestTryLock(lock, 5)).start();
		// }
		// 测不出来公平锁和非公平锁区别
		// for (int j = 0; j < 5; j++) {
		// for (int i = 5; i > -1; i--) {
		// new Thread(new TestLock(lock, i)).start();
		// }
		// }

		// 测试 lockInterruptibly()
		// new Thread(new testInterruptibly(lock, 2)).start();
		// Thread t1 = new Thread(new testInterruptibly(lock, 0));
		// t1.start();
		// t1.interrupt();
		// Thread t2 = new Thread(new testInterruptibly(lock, 1));
		// t2.start();
		// t2.interrupt();

		// 测试唤醒
		// new testInputAccount(lock, condition).start();
		// new testOutputAccout(lock, condition).start();

		//测试线程的锁
		for (int i = 0; i < 100000; i++) {
			new TestThread(lock).start();
		}
		System.out.println(TestThread.count);
		System.out.println(TestThread.safeCount);
	}

}
