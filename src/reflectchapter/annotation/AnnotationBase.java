package reflectchapter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//280

/* 注解加反射是实现Spring 3之后的技术关键
 * 元注解：
 * java提供了4种元注解用于注解其他注解，所有的注解都是基于这四种注解来定义的。
 * @Target @Retention @Documented @Inherited
 */

//@Retention：描述注解的生命周期，即注解的生效范围。
//取值范围（RetentionPolicy）：，。
// 1.SOURCE：在源文件中生效，仅存在java文件中，class文件将会去除注解。
// 2.CLASS：在class文件中生效，仅保留在class文件中，运行时无法获取注解。
// 3.RUNTIME:在运行时生效，保留在class文件中且运行时可通过反射机制获取。

//@Documented：用于指定javadoc生成API时显示该注解信息。

//@Inherited：标明该注解可以由子类继承，及子类可以继承父类的注解。而默认情况下，子类是不继承父类注解的。

//@Target注解：用于描述注解的使用范围，超出范围时编译失败
//1.CONSTRUCTOR:用于描述构造器
//2.FIELD:用于描述域（成员变量）
//3.LOCAL_VARIABLE:用于描述局部变量
//4.METHOD:用于描述方法
//5.PACKAGE:用于描述包
//6.PARAMETER:用于描述参数
//7.TYPE:用于描述类、接口(包括注解类型) 或enum声明


//自定义注解的格式：
//public @interface 注解名｛定义体｝
//
//注解参数可支持的类型：
//  1.所有基本数据类型（int,float,boolean,byte,double,char,long,short)
//  2.String类型
//  3.Class类型
//  4.enum类型
//  5.Annotation类型
//  6.以上所有类型的数组
//
//注解参数的定义规则：
//  a.只能使用public或默认2种访问修饰，例如：String getName();这里getName()就是使用了默认访问权限。
//  b.参数类型只能使用上面提到的6种情况
//  c.如果只有一个参数成员，最好将参数名定义为：value()。
//  d.注解元素必须有确定值，要么在定义的时候设置默认值，要么在使用注解的时候设置参数值。

@Target(ElementType.FIELD) //字段注解  
@Retention(RetentionPolicy.RUNTIME) //在运行期保留注解信息  
@Documented     //在生成javac时显示该注解的信息  
@Inherited      //标明MyAnnotation1注解可以被使用它的子类继承  
@interface MyAnnotation {  
    String name() default "fieldName";            
    String getFieldValue() default "getField";    
    String setFieldValue() default "setField";   
    public enum FieldValue{MYTEST,MYFIELD,MYVALUE};  
    FieldValue  realVale() default FieldValue.MYFIELD;  
}  


//表示下面这个注解只能注解属性  Target：作用域
@Target(ElementType.FIELD)
@interface FieldAnno{
	String name() default "sky";
}
	
//表示下面这个注解只能注解类，接口， 枚举，  Target：作用域
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) //在运行期保留注解信息 
@interface ClassInfo {
	String crated();

	String createdBy();

	String lastModified();

	String lastModifiedBy();

	int revision();
}

@interface BugsFixed {
	String[] bugIds();
}


/**
 * @author xuxb
 *
 */
@ClassInfo(crated = "dd", createdBy = "xx", 
  lastModified = "rr", lastModifiedBy = "zz", revision = 1)
public class AnnotationBase {
	//例子
	@FieldAnno()
	private String name;
	
	@MyAnnotation()
	private String sky;
	
	//方法弃用
	@Deprecated()
	public void testA(){
		
	}
	
	@BugsFixed(bugIds = { "cc" })
	public void testB(){
		
	}
	
	public static void main(String[] args){
		
	}

}
