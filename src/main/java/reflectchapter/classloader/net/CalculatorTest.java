package reflectchapter.classloader.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CalculatorTest {

	private static String classNameToPath(String className) {  
        // 得到类文件的URL  
        return "http://localhost:8080/ClassloaderTest/classes" + "/"  
                + className.replace('.', '/') + ".class";  
    }
	
	public static void main(String[] args) throws IOException {
		String url = "http://localhost:8080/ClassloaderTest/classes";
		NetworkClassLoader ncl = new NetworkClassLoader(url);  
        String basicClassName = "reflectchapter.classloader.net.CalculatorBasic"; 
        URL urls = new URL(classNameToPath(basicClassName));  
        InputStream ins = urls.openStream();  
        String advancedClassName = "reflectchapter.classloader.net.CalculatorAdvanced";  
        try {  
            Class<?> clazz = ncl.loadClass(basicClassName);  // 加载一个版本的类  
            ICalculator calculator = (ICalculator) clazz.newInstance();  // 创建对象  
            System.out.println(calculator.getVersion());
            
            
            clazz = ncl.loadClass(advancedClassName);  // 加载另一个版本的类  
            calculator = (ICalculator) clazz.newInstance();  
            System.out.println(calculator.getVersion());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}

}
