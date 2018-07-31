package optimization.chapter;

import java.util.ArrayList;
import java.util.List;

class RefChildObject {

	public RefChildObject() {
		;
	}
}

class RefObject {
	RefChildObject object;
	
	public RefObject() {
		object = new RefChildObject();
	}
}

class GCDataObject {
	byte[] bytes = null;
	
	RefObject object = null;
	
	public GCDataObject(int facotr) {
		bytes = new byte[facotr * 1024];
		object =new RefObject();
	}
}

public class GCDemo {

	
	/*
	 * 分布式213
	 * 
	 * Usage: jstat -help|-options
       jstat -<option> [-t] [-h<lines>] <vmid> [<interval> [<count>]]
 参数解释：
Options — 选项，我们一般使用 -gcutil 查看gc情况
vmid    — VM的进程号，即当前运行的java进程号
interval– 间隔时间，单位为秒或者毫秒
count   — 打印次数，如果缺省则打印无数次
	 * -Xms135M -Xmx135M -Xmn20M -XX:+UseSerialGC
	 * jstat -gcutil 9256 1000 5
	 * http://blog.csdn.net/zhaozheng7758/article/details/8623549
	 * 具体参数查看
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("ready to start");
		Thread.sleep(10000);
		List<GCDataObject> oldGenObjects = new ArrayList<GCDataObject>();
		for (int i = 0 ; i < 51200; i ++) {
			oldGenObjects.add(new GCDataObject(2));
		}
		System.gc();
		oldGenObjects.size();
		oldGenObjects = null;
		Thread.sleep(5000);
		List<GCDataObject> tmpObjects = new ArrayList<GCDataObject>();
		for (int i = 0; i < 3200; i++) {
			tmpObjects.add(new GCDataObject(5));
		}
		tmpObjects.size();
		tmpObjects = null;
		Thread.sleep(10000);
	}

}
