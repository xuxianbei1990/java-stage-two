package thread.chapter;
//251 j线程使用场景之一，用于界面的数据实时交互
import java.applet.Applet;
import java.awt.List;
import java.util.ArrayList;

class Body {
	int t;
	Body(){
		synchronized (Body.class) {
			
		}
	}	
	synchronized void Body2(){
		synchronized (Body.this){
			
		}
	}
	synchronized void changeCondition(){
		// change some value used in a  condition test
		notifyAll();
	}
	synchronized void doWhenCondition() throws InterruptedException{
		while (t!=1)
			wait();
	}
	 
}


class PingPong extends Thread{
	private String word;
	private int delay;
	
	public PingPong(String whatToSay, int delayTime){
		word = whatToSay;
		delay = delayTime;
	}
	public void run(){
		try{
		  for (;;){
			System.out.print(word + " ");
			Thread.sleep(delay);
		 }
		}catch(InterruptedException e){
			return ;
		}
	}
	
}

class RunPingPong implements Runnable{
	private String word;
	private int delay;
	
	RunPingPong(String whatTosay, int dealyTime){
		word = whatTosay;
		delay = dealyTime;
	}
	
	public void run(){
		try{
			for (;;) {
				System.out.print(word + "");
				Thread.sleep(delay);
			}
		}catch (InterruptedException e){
			return;
		}
	}
}

class PrintQueue extends ArrayList{
	public void remove(){
		
	}
	
}

class Printjob{
	
}

class PrintServer implements Runnable{
	private final PrintQueue requests = new PrintQueue();
	public void run(){
		for(;;){
			realPrint(requests);
		}
	}
	void realPrint(PrintQueue pq){
		System.out.print("打印些什么");
		
	}
}

class PrintServer2{
	private final PrintQueue requests = new PrintQueue();
	public PrintServer2(){
		Runnable service =  new Runnable(){
			public void run(){
				for (;;)
					realPrint(requests);
			}
		};
		new Thread(service).start();
	}

	public void print(Printjob Job){
		requests.add(Job);
	}
	
	void realPrint(PrintQueue pq){
		System.out.print("打印些什么");
		
	}
}



public class ThreadDemo1 {
	int[] values = {1,2};
	public  void arrAdd(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				synchronized (values) {
					for (int j:values){
						System.out.println("Thread.holdsLock:" + j + Thread.holdsLock(values));
					}
				}
			}
		}).start();
		
	}
	
	public static void main(String[] args){
		ThreadDemo1 td = new ThreadDemo1();
		td.arrAdd();
//		new PingPong("ping", 33).start();	
//		RunPingPong rpp = new RunPingPong("战", 500);
//		Thread thread1 = new Thread(rpp);
//		thread1.start();
//		Thread.holdsLock(thread1);
	}
	
	

}
