package thread.chapter;
/*
 * 程序分析：1.票数要使用同一个静态值
 * 2.为保证不会出现卖出同一个票数，要java多线程同步锁。
 * 设计思路：1.创建一个站台类Station，继承Thread，重写run方法，在run方法里面执行售票操作！售票要使用同步锁：即有一个站台卖这张票时，其他站台要等这张票卖完！
 * 
 */

class Station extends Thread {

	public Station(String name) {
		super(name);
	}

	static int tick = 20;

	// 创建一个静态钥匙 值随意
	static Object ob = "aa";

	@Override
	public void run() {
		while (tick > 0) {
			synchronized (ob) {

				if (tick > 0) {
					System.out.println(getName() + "卖出了第" + tick + "张票");
					tick--;
				} else {
					System.out.println("票卖完了");
				}
			}
			try {
				sleep(1000);// 休息一秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class ApplicationStation {
	public static void main(String[] args) {
		Station station1 = new Station("窗口1");
		Station station2 = new Station("窗口2");
		Station station3 = new Station("窗口3");
		station1.start();
		station2.start();
		station3.start();
	}
}
