package thread.chapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

class ReentrantLockDemo {
	private static ReentrantLock lock = new ReentrantLock();
	private static Map<String, String> maps = new HashMap<String, String>();
	private static CountDownLatch latch = new CountDownLatch(102);
	private static CyclicBarrier barrier = new CyclicBarrier(102);

	public static void main() throws Exception {
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			new Thread(new ReadThread()).start();
		}
		for (int i = 0; i < 2; i++) {
			new Thread(new WriteThread()).start();
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("Consume Time is:" + (endTime - beginTime) + " ms");
	}

	static class WriteThread implements Runnable {

		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				lock.lock();
				maps.put("1", "2");
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			latch.countDown();
		}
	}
	
	static class ReadThread implements Runnable {

		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				lock.lock();
				maps.get("1");
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			latch.countDown();
		}
		
	}
}


// 分布式 187
// 如果 针对读多写少的程序 ReentrantReadWriteLock 比 ReentrantLock 高效 大约30倍
public class ReentrantReadWriteLockDemo {
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static ReadLock readLock = lock.readLock();
	private static WriteLock writeLock = lock.writeLock();
	private static Map<String, String> maps = new HashMap<String, String>();
	private static CountDownLatch latch = new CountDownLatch(102);
	private static CyclicBarrier barrier = new CyclicBarrier(102);

	public static void main(String[] args) throws Exception {
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			new Thread(new ReadThread()).start();
		}
		for (int i = 0; i < 2; i++) {
			new Thread(new WriteThread()).start();
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("Consume Time is:" + (endTime - beginTime) + " ms");
		ReentrantLockDemo.main();
	}

	static class WriteThread implements Runnable {

		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				writeLock.lock();
				maps.put("1", "2");
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				writeLock.unlock();
			}
			latch.countDown();
		}
	}
	
	static class ReadThread implements Runnable {

		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				readLock.lock();
				maps.get("1");
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				readLock.unlock();
			}
			latch.countDown();
		}
		
	}
}
