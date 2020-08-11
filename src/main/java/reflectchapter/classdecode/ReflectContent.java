package reflectchapter.classdecode;
import static java.lang.System.out;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

//296
//Modifier类 修饰符类 public final 等

public class ReflectContent {
	void test1(){
		int m = 0;
		AccessibleObject dd;
		if (Modifier.PUBLIC == m){
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class tt;
		Annotation[] dd;
		
		tt = ReflectContent.class;
		Annotation sd = 
				tt.getAnnotation(tt);
		dd = tt.getDeclaredAnnotations();
		int i = tt.getModifiers();
		tt.getDeclaringClass();
		
			
		}


}
