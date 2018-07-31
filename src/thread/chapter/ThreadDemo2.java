package thread.chapter;

class Babble extends Thread {
	static boolean doYield;
	static int howOften;
	private char[] cs;

	public Babble(String whatToSay) {
		// TODO Auto-generated constructor stub
		cs = whatToSay.toCharArray();
	}

	public void run() {
			for (char c : cs) {
				System.out.print(this.getName() + ":" + c);
				if (doYield)
					Thread.yield();// 重新让自己处于（就绪状态） 让步
			}
		
	}
}

public class ThreadDemo2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub true 2 Did DidNot
		String[] strs = { "abc", "DidNot" };
		Babble.doYield = new Boolean("true").booleanValue();
		Babble.howOften = Integer.parseInt(args[1]);
		for (int i = 2; i < 4; i++)
			new Babble(strs[i % 2]).start();

	}

}
