package thread.chapter;

// 本文重点：
//
//1. 你可以使用wait和notify函数来实现线程间通信。你可以用它们来实现多线程（>3）之间的通信。
//
//2. 永远在synchronized的函数或对象里使用wait、notify和notifyAll，不然Java虚拟机会生成
//IllegalMonitorStateException。
//
//3. 永远在while循环里而不是if语句下使用wait。这样，循环会在线程睡眠前后都检查wait的条件，
//并在条件实际上并未改变的情况下处理唤醒通知。
//
//4. 永远在多线程间共享的对象（在生产者消费者模型里即缓冲区队列）上使用wait


import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Consumer implements Runnable {

	private Queue queue;

	Consumer(Queue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (queue) {
				while (queue.isEmpty()) {
					System.out.println("Queue is Empty Consumer thread is waiting");
					try {
						queue.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				System.out.println("Consuming value" + queue.remove());
				queue.notifyAll();
			}
		}

	}

}

class Producer implements Runnable {

	private Queue queue;
	private int maxSize;

	Producer(Queue queue, int maxSize) {
		this.queue = queue;
		this.maxSize = maxSize;
	}

	@Override
	public void run() {
		System.out.println("xxxxx");
		while (true) {
			synchronized (queue) {
				while (queue.size() == maxSize) {
					try {
						System.out.println("Produce 等待");
						queue.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				Random random = new Random();
				int i = random.nextInt();
				System.out.println("Producing value : " + i);
				queue.add(i);
				queue.notifyAll();

			}
		}
	}

}

public class ThreadWaitNotifyDemo2 {
	public static void main(String[] args) {
		System.out.println("Customer AND Solving");
		Queue<Integer> buffer = new LinkedList<Integer>();
		Thread consumer = new Thread(new Consumer(buffer));
		consumer.setName("Consumer");
		consumer.start();

		Thread producer = new Thread(new Producer(buffer, 10));
		producer.setName("Producer");
		producer.start();
	}

}
