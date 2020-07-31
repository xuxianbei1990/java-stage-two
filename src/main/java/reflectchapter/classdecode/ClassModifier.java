package reflectchapter.classdecode;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

//非注解修饰符 299
public class ClassModifier {
	private static void printModifierContext(Class cla){
		Modifier.isInterface(cla.getModifiers());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class cla = Class.forName(args[0]);
			printModifierContext(cla);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
