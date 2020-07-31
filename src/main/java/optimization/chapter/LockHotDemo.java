package optimization.chapter;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


// Treiber 优于 Stack  先进后出 LIFO
class ConcurrentStack<E> {
	AtomicReference<Node<E>> head = new AtomicReference<Node<E>>();

	public void push(E item) {
		Node<E> newHead = new Node<E>(item);
		Node<E> oldHead;
		do {
			oldHead = head.get();
			newHead.next = oldHead;
		} while (!head.compareAndSet(oldHead, newHead));
	}
	
	public E pop() {
		Node<E> oldHead;
		Node<E> newHead;
		do {
			oldHead = head.get();
			if (oldHead == null)
				return null;
			newHead = oldHead.next;
		} while (!head.compareAndSet(oldHead, newHead));
		return oldHead.item;
	}
	
	static class Node<E> {
		final E item;
		Node<E> next;
		public Node(E item) {
			
			this.item = item;
		}
	}
}

/*
 * 分布式 235
 */
public class LockHotDemo {

	private static CountDownLatch latch;

	private static int threadCount = Runtime.getRuntime().availableProcessors() * 100;

	private static int executeTimes = 10;

	public static void main(String[] args) throws Exception {
		if ((args.length == 1) || args.length == 2)
			threadCount = Integer.parseInt(args[0]);
		if (args.length == 2)
			executeTimes = Integer.parseInt(args[1]);
		HandleTask task = new HandleTask();
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < executeTimes; i++) {
			System.out.println("Round: " + (i + 1));
			latch = new CountDownLatch(threadCount);
			for (int j = 0; j < threadCount; j++) {
				new Thread(task).start();
			}
			latch.await();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Execute summary: Round(" + executeTimes + ")" + "Thread Count( " + threadCount + " )"
				+ " Execute Time (" + (endTime - beginTime) + "ms)");
	}

	static class HandleTask implements Runnable {

		private final Random random = new Random();
		
		@Override
		public void run() {
			Handler.getInstance().handle(random.nextInt(10000));
			latch.countDown();
		}

	}

	static class Handler {
		private static final Handler self = new Handler();

		private final Random random = new Random();

		private final Lock lock = new ReentrantLock();

		public static Handler getInstance() {
			return self;
		}

		public void handle(int id) {
			try {
				//如果把锁去掉，性能会提升很多。
				lock.lock();
				try {
					Thread.sleep(random.nextInt(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				lock.unlock();
			}
		}
	}
}
