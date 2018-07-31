package optimization.chapter;

import java.util.ArrayList;
import java.util.List;

/*
 * GC 策略 调优 
 */
class GCPolicyDataObject {

	byte[] bytes = null;

	GCPolicyRefObject objcet = null;

	public GCPolicyDataObject(int facotr) {
		bytes = new byte[facotr * 124];
		objcet = new GCPolicyRefObject();
	}

}

class GCPolicyRefChildObject {

	public GCPolicyRefChildObject() {
		;
	}
}

class GCPolicyRefObject {
	GCPolicyRefChildObject object;

	public GCPolicyRefObject() {
		object = new GCPolicyRefChildObject();
	}
}

public class GCPolicyDemo {

	/*
	 * -Xms680M -Xmx680M -Xmn80M -XX:+UseConcMarkSweepGC -XX:CMSMaxAbortablePrecleanTime=5
	 * -XX:+PrintGCApplicationStoppedTime
	 * 
	 * 
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("ready to start");
		Thread.sleep(1000);
		List<GCPolicyDataObject> cacheObjects = new ArrayList<GCPolicyDataObject>();
		for (int i = 0; i < 20148; i++) {
			cacheObjects.add(new GCPolicyDataObject(100));
		}
		System.gc();
		Thread.sleep(1000);
		for (int i = 0; i < 1; i++) {
			System.out.println("Round:" + (i + 1));
			System.out.println("put 64M objects");
			List<GCPolicyDataObject> tmpObjects = new ArrayList<GCPolicyDataObject>();
			for (int m = 0; m < 1; m++) {
				tmpObjects.add(new GCPolicyDataObject(1));
			}
			tmpObjects = null;
		}
		cacheObjects.size();
		cacheObjects = null;
	}

}
