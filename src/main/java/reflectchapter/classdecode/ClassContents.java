package reflectchapter.classdecode;

import java.lang.reflect.*;

public class ClassContents {
	private static void printMembers(Member[] mems){
		for (Member m: mems){
			//剥除掉从Object继承而来的成员
			if (m.getDeclaringClass() == Object.class)
				continue;
			String decl = m.toString();
			System.out.print("  ");
			System.out.println(strip(decl, "java.lang."));
		}
	}

	private static String strip(String decl, String string) {
		// TODO Auto-generated method stub
		decl.replace(string, "");
		return decl;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Class<?> c = Class.forName(args[0]);
			//简称 例如java.lang.Object 简称Object
			c.getSimpleName();
			//全称 例如java.lang.Object
			c.getCanonicalName();
//	把obj对象强制转换成 c类	    c.cast(obj)
			System.out.println(c);
			printMembers(c.getFields());
			printMembers(c.getConstructors());
			printMembers(c.getMethods());
		}catch(ClassNotFoundException e){
			System.out.println("unknown class:" + args[0]);
		}

	}

}
