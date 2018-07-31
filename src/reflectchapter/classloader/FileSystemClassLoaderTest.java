package reflectchapter.classloader;

import java.lang.reflect.Method;

public class FileSystemClassLoaderTest {

	public static void main(String[] args) {
		String classDataRootPath = "E:\\税友\\01历年Demo\\Workspaces\\MyEclipse 10\\java-stage-two\\src\\reflectchapter\\classloader";
		FileSystemClassLoader fscl1 = new FileSystemClassLoader(classDataRootPath);
		FileSystemClassLoader fscl2 = new FileSystemClassLoader(classDataRootPath);
		String className = "reflectchapter.classloader.Sample";
		
		try {
			// 加载Sample类
			Class<?> class1 = fscl1.loadClass(className);
			Object obj1 = class1.newInstance();  // 创建对象  
			Class<?> class2 = fscl2.loadClass(className);  
            Object obj2 = class2.newInstance();
            Method setSampleMethod = class1.getMethod("setSample", java.lang.Object.class);  
            setSampleMethod.invoke(obj1, obj2);  
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

}
