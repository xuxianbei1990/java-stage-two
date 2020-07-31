package reflectchapter.classdecode;
//287
/*
 * 遍历某个类，集成和实现了那些的类或者接口
 */
import java.lang.reflect.*;

public class ReflectTypeDesc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReflectTypeDesc desc = new ReflectTypeDesc();
		for (String name: args){
			try{
				Class<?> startClass = Class.forName(name);
				desc.printType(startClass, 0, basic);
			}catch(ClassNotFoundException e){
				System.err.println(e);
			}
		}

	}
	//by default print on standard output
	private java.io.PrintStream out = System.out;
	
	//used in printType() for labeling type names
	private static String[]
			basic = {"class", "interface",
		             "enum", "annotation"},
		    supercl = {"extends", "implements"},
		    iFace = {null, "extends"};
	private void printType(
			Type type, int depth, String[] lables)
	{
		if (type == null)
			return;
		
		//turn the type into a Class object
		Class<?> cls = null;
		if (type instanceof Class<?>)
			cls = (Class<?>)type;
		else if (type instanceof ParameterizedType)
			cls = (Class<?>)
			((ParameterizedType)type).getRawType();
		else
			throw new Error("Unexpected non - class type");
		
		//print this type
		for (int i = 0; i < depth; i++)
			out.print("  ");
		//注解类型 
		int kind = cls.isAnnotation() ? 3:
			//表示 枚举
			cls.isEnum() ? 2:
			cls.isInterface() ? 1: 0;
		out.print(lables[kind] + "  ");
		out.print(cls.getCanonicalName());
		
		//print generic type parameters if present
		TypeVariable<?>[] params = cls.getTypeParameters();
		if (params.length > 0){
			out.print("<");
			for (TypeVariable<?> param: params){
				out.print(param.getName());
				out.print(", ");
			}
			out.println("\b\b>");
		}
		else
			out.println();
		
		//print out all interface this class implements
		Type[] interfaces = cls.getGenericInterfaces();
		
		for (Type iface: interfaces){
			printType(iface, depth + 1,
					cls.isInterface() ? iFace: supercl);
		}
		//recurse on the superclass
		printType(cls.getGenericSuperclass(), depth + 1, supercl);
	}

}
