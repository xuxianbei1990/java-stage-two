package reflectchapter.classdecode;
//292 遍历一个类的所有方法，成员，
import java.lang.reflect.*;


public class ReflectClassContents {
	private int age;
	
	private static void printMembers(Member[] mems){
		for (Member m: mems){
			if (m.getDeclaringClass() == Object.class)
				continue;
			String decl = m.toString();
			printSpace();
			System.out.println(strip(decl, "java.lang. "));
		}
	}
	private static void printSpace() {
		System.out.print("  ");
	}
	public static String strip(String aValue, String  delte){
		return aValue.replaceAll(delte, "");
	}
	public static void main(String[] args){
		try{
			Class<?> c = Class.forName(args[0]);
			System.out.println(c);
			printMembers(c.getFields());
			printMembers(c.getConstructors());
			printMembers(c.getMethods());
		}catch(ClassNotFoundException e){
			System.out.println("unkonwn class: " + args[0]);
		}
	}
	int getAge() {
		return age;
	}
	void setAge(int age) {
		this.age = age;
	}

}
