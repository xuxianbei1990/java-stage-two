package jvm.chapter;
/*
 * 结论
 * 实例化情况下
 * 1.construct 地位低于  初始化值和初始化块
 * 继承：
 * 继承成员变量和继承方法的区别
 * 继承的方法是完全覆盖父类，但是变量不是；
 * 
 * 尽量不要在父类构造方法中调用子类可以重写的方法。
 * 调用子类的变量。
 * 
 */

//57页

class Cat {
	String name;
	int age;

	// 实例化执行顺序，系统会优先调用父类的无参构造函数
	// 构造函数 地位低于 初始化部分
	// 初始化部分 等价于 初始化变量
	// 那么就看谁定义在前面了。
	public Cat(String name, int age) {
		System.out.println("执行构造器");
		this.name = name;
		this.age = age;
	}

	public Cat() {
		System.out.println("无参构造器");
	}

	// 没有函数名称的只有{} 被称之为初始化块。依赖实例 等价于 局部定义的变量
	// 例如weight = 2.3;
	{
		System.out.println("执行非静态初始化块");
		weight = 2.0;
	}

	double weight = 2.3;

	public String toString() {
		return "name" + name + ";age" + age + ";weight" + weight;
	}
}

class Price {

	// 执行顺序： 从上往下
	// 第一执行：static
	// 第二执行：static声明的赋值
	// 如果实例化
	// 第三执行: 声明
	// 第四执行：声明的赋值

	final static Price INSTANCE = new Price(2.8);

	static double initPrice = 20;

	double currentPrice;

	public Price(double discount) {
		currentPrice = initPrice - discount;
	}

}

// 父类调用子类方法
class Base {
	private int i = 2;

	public Base() {
		this.display();
	}

	public void display() {
		System.out.println(i);
	}

}

class Driver extends Base {
	private int i = 22;

	public Driver() {
		i = 222;
	}

	public void display() {
		System.out.println(i);
	}

}

class Base2 {

	Base2() {
		System.out.println("构造函数");
	}

	public int count = 2;

	{
		System.out.println("初始化块" + count);
		count = 3;
		System.out.println("初始化块");
	}

	public void display2() {
		System.out.println(this.count);
	}

}

class Driver2 extends Base2 {
	public int count = 20;

	public void display2() {
		System.out.println(this.count);
	}

}

// 类的解释：
public class ClassConstructor {

	public static void main(String args[]) {
		System.out.println(new Cat("kitty", 2));
		System.out.println(new Cat("kitty", 2));
		
		System.out.println(Price.INSTANCE.currentPrice);
		System.out.println(new Price(2.8).currentPrice);

		new Driver();

		System.out.println("------------------");
		// 继承成员变量和继承方法的区别
		// 继承的方法是完全覆盖父类，但是变量不是
		Base2 b = new Base2();
		System.out.println(b.count);
		b.display2();

		Driver2 d = new Driver2();
		System.out.println(d.count);
		d.display2();

		Base2 bb = new Driver2();
		System.out.println(bb.count);
		bb.display2();

		Base2 d2 = d;
		System.out.println(d2.count);
		d2.display2();

	}

}
