package jvm.chapter;

public class ThreadStack {

	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run(){
				loop(0);
			}

			private void loop(int i) {
				if (i != 100000) {
					i++;
					loop(i);
				} else
					return;
			}

		}).start();
	}

}
