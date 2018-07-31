package collection.chapter;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueDemo {
	// 线程安全的 优先级队列
	public static PriorityBlockingQueue<User> queue = new PriorityBlockingQueue<User>();

	public static void main(String[] args) throws InterruptedException {
		queue.add(new User(1,"wu"));  
	    queue.add(new User(5,"wu5"));  
	    queue.add(new User(23,"wu23"));  
	    queue.add(new User(55,"wu55"));  
	    queue.add(new User(9,"wu9"));  
	    queue.add(new User(3,"wu3"));  
	    for (User user : queue) {  
	        System.out.println(queue.peek().name);  
	    }  
	}

	static class User implements Comparable<User> {
		int age;
		String name;

		public User(int age, String name) {
			this.age = age;
			this.name = name;
		}

		@Override
		public int compareTo(User o) {
			return this.age > o.age ? 1: -1;
		}

	}

}
