package serializable.chapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableDemo {

	// 序列化 第一次比较耗时，后面几次很快，因为存在缓存里 了
	public static void main(String[] args) throws IOException, Exception {
		for (int i = 0; i < 10; i++) {
			long beginTime = System.currentTimeMillis();
			AObject object = new AObject();
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
			objectOutput.writeObject(object);
			objectOutput.close();
			byteOutput.close();
		    byte[] bytes = byteOutput.toByteArray();
		    System.out.println("java 序列化耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
		    System.out.println("java 序列化的字节大小为：" + bytes.length);
		    
		    beginTime = System.currentTimeMillis();
		    ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
		    ObjectInputStream objectInput = new ObjectInputStream(byteIn);
		    objectInput.readObject();
		    objectInput.close();
		    byteIn.close();
		    System.out.println("java 序列化耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
		    
		}

	}

}
