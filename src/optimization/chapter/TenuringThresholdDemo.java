package optimization.chapter;

import java.util.ArrayList;
import java.util.List;

class DataObject {
	byte[] bytes = null;
	
	public DataObject(int factor) {
		bytes = new byte[factor * 1024];
	}
}

// 设置生存周期
public class TenuringThresholdDemo {
	
	// -Xms150M -Xmx150M -Xmn30M -XX:+UseSerialGC
	// -XX:MaxTenuringThreshold=16
	public static void main (String[] args) throws Exception {
		System.out.println("wait jstat");
		Thread.sleep(10000);
		List<DataObject> objects = new ArrayList<DataObject>();
		for (int i = 0; i < 52100; i++) {
			// 50M
			objects.add(new DataObject(1));
		}
		// 40M
		List<DataObject> tmpobejcts = new ArrayList<DataObject>();
		for (int i = 0; i < 10240; i++) {
			tmpobejcts.add(new DataObject(4));
		}
		System.gc();
		Thread.sleep(1000);
		tmpobejcts.size();
		tmpobejcts = null;
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 30; i++) {
			DataObject toOldObject = new DataObject(1024);
			for (int j = 0; j < 14; j++) {
				for (int m = 0; m < 23; m++) {
					new DataObject(1024);
				}
			}
			toOldObject.toString();
			toOldObject = null;
		}
		objects.size();
		long endTime = System.currentTimeMillis();
		System.out.println("Execute Summary:" + (endTime - beginTime) + "ms");
		Thread.sleep(10000);
	}
}
