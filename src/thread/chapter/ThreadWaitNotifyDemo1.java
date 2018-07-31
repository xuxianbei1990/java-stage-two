package thread.chapter;

import java.math.BigDecimal;
//http://blog.csdn.net/ghsau/article/details/7433673
class BankAccount {
	String name;
	BigDecimal money = new BigDecimal(10);

	void testWaitMoney() throws InterruptedException {
		System.out.println("正在等待");
		synchronized (money) {
			money.wait();
			money = money.add(new BigDecimal(5));
			System.out.println("正在等待" + money.toString());
		}
		System.out.println("等到你了");
	}

	synchronized void testNotifyMoney() throws InterruptedException {
		System.out.println("执行等待");
		Thread.sleep(1000);
		synchronized (money) {
			money.notify();
		}
		System.out.println("通知结束");
	}
}

class ThreadWaitRun implements Runnable {
	private BankAccount bankAccout;

	ThreadWaitRun(BankAccount bankAccout) {
		this.bankAccout = bankAccout;
	}

	@Override
	public void run() {
		try {
			bankAccout.testWaitMoney();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadNofifyRun implements Runnable {
	private BankAccount bankAccout;

	ThreadNofifyRun(BankAccount bankAccout) {
		this.bankAccout = bankAccout;
	}

	@Override
	public void run() {
		try {
			bankAccout.testNotifyMoney();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class ThreadWaitNotifyDemo1 {
	public static void main(String args[]) {
		BankAccount bankAccout = new BankAccount();
		new Thread(new ThreadWaitRun(bankAccout)).start();
		new Thread(new ThreadNofifyRun(bankAccout)).start();
	}

}
