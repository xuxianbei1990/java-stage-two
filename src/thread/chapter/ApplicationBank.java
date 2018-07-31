package thread.chapter;

import java.util.concurrent.CountDownLatch;

class Bank {

	private static int money = 10000;

	public int getMoney() {
		synchronized (this) {
			return money;
		}
	}

	// 柜台取钱
	public void Counter(int money) {
		synchronized (this) {
			Bank.money -= money;
			System.out.println("取走了" + money + "还剩下" + getMoney());
		}

	}

	public void ATM(int money) {
		synchronized (this) {
			Bank.money -= money;
			System.out.println("取走了" + money + "还剩下" + getMoney());
		}
	}
}

// 柜台取钱
class PersonA extends Thread {

	private Bank bank;

	private CountDownLatch countDownLatch;

	public PersonA(Bank bank, CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		this.bank = bank;
	}

	@Override
	public void run() {
		while (bank.getMoney() >= 100) {
			bank.Counter(100);
		}
		countDownLatch.countDown();
	}
}

class PersonB extends Thread {

	private Bank bank;

	private CountDownLatch countDownLatch;

	public PersonB(Bank bank, CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		this.bank = bank;
	}

	@Override
	public void run() {
		while (bank.getMoney() >= 200) {
			bank.ATM(200);
		}
		countDownLatch.countDown();
	}
}

public class ApplicationBank {
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(2);
		// 实力化一个银行对象
		Bank bank = new Bank();
		// 实例化两个人，传入同一个银行的对象
		PersonA pA = new PersonA(bank, countDownLatch);
		PersonB pB = new PersonB(bank, countDownLatch);
		// 两个人开始取钱
		pA.start();
		pB.start();
		countDownLatch.await();
		System.out.println("last:" + bank.getMoney());
	}
}
