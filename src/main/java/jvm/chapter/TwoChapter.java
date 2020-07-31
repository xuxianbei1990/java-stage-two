package jvm.chapter;

/*
 * final 变量被初始化之后 ，不能对它重新赋值  定义final static 的级别高于static 级别
 * final 方法,不能被重写
 * final 类，不能派生子类。
 * 
 */

class FinalDemo {
	final int var1 = "Simple".length();
	final int var2;
	final int var3;
	final static int var4;
	{
		var2 = "Simple".length();
	}

	static {
		var4 = "Simple".length();
	}

	FinalDemo() {
		this.var3 = "Simple".length();
	}

}

public class TwoChapter {
	public static void main(String args[]) {
		FinalDemo fi = new FinalDemo();
		System.out.println("var1 " + fi.var1 + ";var2 " + fi.var2 
				 + ";var3 " + fi.var3 + ";var4 " + fi.var4);
	}

}
