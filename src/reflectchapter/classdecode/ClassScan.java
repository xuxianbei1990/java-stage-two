//283
package reflectchapter.classdecode;

//遍历一个类所有方法，属性，
import java.lang.reflect.*;
import java.util.*;

import static java.lang.System.out;
import static java.lang.System.err;

public class ClassScan {

	// 返回改类所有的方法
	LinkedList<String> ListClassFunc(Class<?> objclass) {
		LinkedList<String> ll = new LinkedList<String>();
		if (objclass == null)
			return null;
		ll.add(objclass.getCanonicalName());
		// 返回所有声明方法
		Method[] methods = objclass.getDeclaredMethods();
		for (Method m : methods) {
			// 如果是Public方法的话
			// if (Modifier.isPublic(m.getModifiers()))
			ll.add(m.toString());
		}
		return ll;
	}

	// 297对field函数的解析
	void decodeFields(Class<?> objclass, Object obj) {
		/* 获取类的申明的方法 和 objclass.getFields(
		 * 
		 */
		 
		Field[] fields = objclass.getDeclaredFields();

		try {
			for (Field field : fields) {
				if (field.getName() == "name") {
					field.set(obj, "value");
				}
				if (field.getType() == String.class) {
					field.set(obj, "value");
				}
			}
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e2) {
			err.print(e2.toString());
		}
	}

	// 290
	LinkedList<String> ListClassType(Class<?> Objclass) {
		LinkedList<String> ll = new LinkedList<String>();
		if (Objclass == null)
			return null;
		ll.add(Objclass.getCanonicalName());
		// 返回泛型
		TypeVariable<?>[] tv = Objclass.getTypeParameters();
		for (TypeVariable<?> m : tv) {
			ll.add(m.getName());
		}
		return ll;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassScan cs = new ClassScan();
		ClassTest ct = new ClassTest();
		Class<?> cla = ct.getClass();
		
		LinkedList<String> ll = cs.ListClassFunc(cla);
		String[] obs = ll.toArray(new String[0]);
		for (String ob : obs) {
			out.println(ob);
		};

		cs.decodeFields(cla, ct);
		out.print(ct.name);

	}

}
