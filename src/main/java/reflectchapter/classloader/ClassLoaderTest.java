package reflectchapter.classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderTest {

	// 查询某个类的类加载器
	public static void watchClassLoader() {
		try {
			// 查看系统类路径
			System.out.println(System.getProperty("java.class.path"));
			Class loaded = Class.forName("reflectchapter.classloader.TestBean");
			System.out.println(loaded.getClassLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 标准扩展类加载器能加载 哪些路径
	public static void standardPath() {
		try {  
            URL[] extURLs = ((URLClassLoader) ClassLoader.getSystemClassLoader().getParent()).getURLs();  
            for (int i = 0; i < extURLs.length; i++) {  
                System.out.println(extURLs[i]);  
            }  
        } catch (Exception e) {  
            //…  
        } 
	}
	
	public static void main(String[] args) {

	}
}
