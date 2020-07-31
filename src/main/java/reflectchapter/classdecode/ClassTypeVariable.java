package reflectchapter.classdecode;

import java.lang.reflect.*;
import java.util.Arrays;

class GenericClass<T, S> {
	public <C, V> GenericClass(C c, V v) {
		// ...
	}

	public <E, F> T genericMethod(E e, F f, T t, S s) {
		// ...
		return t;
	}
}

// 303
public class ClassTypeVariable {
	private static void printType(Class cla) {
		String printStr;
		TypeVariable[] tvs = cla.getTypeParameters();
		for (TypeVariable tv : tvs) {
			printStr = tv.getName();
			// tv.getGenericDeclaration()
			// tv.getBounds()
		}
	}

	public static void analyzeTypeVariableParts(TypeVariable<?>[] genericParameterTypes)
			throws ClassNotFoundException, NoSuchMethodException {
		TypeVariable tVariable = null;
		Type[] bounds = null;
		GenericDeclaration genericDeclaration = null;
		String typeVariableName = null;

		for (int i = 0; i < genericParameterTypes.length; i++) {

			tVariable = (TypeVariable) genericParameterTypes[i];

			bounds = tVariable.getBounds();
			genericDeclaration = tVariable.getGenericDeclaration();
			typeVariableName = tVariable.getName();

			System.out.println("类型变量是：" + typeVariableName);
			System.out.println("父边界是：" + Arrays.asList(bounds));
			System.out.println("类型变量声明的位置：" + genericDeclaration);
		}
	}

	public static void main(String[] args) {
		try {
			// 获取Class类对象
			Class clazz = Class.forName("GenericClass");

			// 获取Constructor类对象
			Constructor cons = clazz.getConstructor(Object.class, Object.class);

			// 获取Method类对象
			Method method = clazz.getMethod("genericMethod", Object.class, Object.class, Object.class, Object.class);

			TypeVariable<?>[] classTypeVariables = clazz.getTypeParameters();
			TypeVariable<?>[] constructorTypeVariables = cons.getTypeParameters();
			TypeVariable<?>[] methodTypeVariables = method.getTypeParameters();

			System.out.println();
			System.out.println("*******Class TypeVariables*********");
			analyzeTypeVariableParts(classTypeVariables);

			System.out.println();
			System.out.println("*******Constructor TypeVariables*********");
			analyzeTypeVariableParts(constructorTypeVariables);

			System.out.println();
			System.out.println("*******Method TypeVariables*********");
			analyzeTypeVariableParts(methodTypeVariables);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

}
