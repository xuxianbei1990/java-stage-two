package collection.chapter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;

/*
 * 并发demo
 */
public class ConcurrencyCollectionDemo {

	// 基于数组、先进先出。线程安全的集合，特色：指定时间的阻塞读写。Qie
	// 容量是可限制的;
	void arrayBlockingQueueTest() throws InterruptedException {
		ArrayBlockingQueue queue = new ArrayBlockingQueue(0);
		// 尾部插入数据。数组满则等待
		queue.offer(new Integer(1));
		// 获取一个元素，如果没有则等待。
		queue.poll();
		// 获取一个元素，如果没有则等待1s。
		queue.poll(1000, TimeUnit.MILLISECONDS);
	}

	// 处理高并发读写比较好；put和offer 采用同一把锁；take和poll
	// 同一把锁，遍历删除时候会把两个锁都锁住
	// 单向链表实现的阻塞队列，先进先出的顺序
	// 构造的时候若没有指定大小，则默认大小为Integer.MAX_VALUE，
	// 当然也可以在构造函数的参数中指定大小。LinkedBlockingQueue不接受nu
	void linkedBlockingQueueTest() throws InterruptedException {
		LinkedBlockingQueue bgq = new LinkedBlockingQueue();
		// 只在尾部写入
		// 向队列尾部添加元素，队列已满的时候，阻塞等待。
		bgq.put("dd");
		// 向队列尾部添加元素，队列已满的时候，直接返回false。
		bgq.offer("dd");

		// take:若队列为空，发生阻塞，等待有元素。
		bgq.take();
		// poll: 若队列为空，返回null。
		bgq.poll();
	}

}
