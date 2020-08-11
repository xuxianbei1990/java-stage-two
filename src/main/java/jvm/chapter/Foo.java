package jvm.chapter;

/*
 * javac -g 编译生成class文件
 * 通过以下命令来查看class文件
 * javap -c -s -l -verbose "E:\税友\01历年Demo\Workspaces\My
Eclipse 10\java-stage-two\src\jvm\chapter\Foo.class"
 * 
 */
public class Foo {
	private static final int MAX_COUNT = 1000;

	private static int count = 0;

	public static int cc = 1;

	static  {
		cc = -1;
	}
	Foo() {
		cc = 3;
	}

	public int bar() throws Exception {
		if (++count >= MAX_COUNT) {
			cc++;
			count = 0;
			throw new Exception("counnt overflow");
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.print(Foo.cc);
	}

}
