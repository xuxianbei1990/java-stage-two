package jvm.chapter;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/*
 * 内存回收如果是不可以找到引用，则释放。
 * 
 * 局部函数：定义变量不需要在结尾时候赋值null。多此一举。但是如果是局部函数
 * 下一步执行费时间的函数的话，可以赋值为null
 * 
 * 垃圾回收机制
 * 串行回收（Serial）和并行回收（Parallel）：串行：有一个cpu专门负责。并行：多个cpu并行执行。缺点：复杂度，内存碎片
 * 并发执行（Concurrent）和应用程序停止（Stop the world）： stop the world ：坏处，应用程序停止。Concurrent：需要解决
 * 垃圾回收和应用程序冲突，回收垃圾开销会比较大
 * 压缩（Compacting）和不压缩(Non-compacting)和复制（Copying）:
 * 主要针对内存碎片的问题，压缩把活对象搬迁到一起，不压缩：直接回收，导致内存碎片，下次分配内存更慢。优点，快。
 * 复制（Copying）:活动的对象拷贝到另一个相同的内存块，优点：没有碎片，缺点：复制数据和额外内存开销。
 * 几种具体的方式
 * copying：因为只对可达的对象遍历。所以遍历空间的成本较小，但需要巨大的复制成本和较多的内存。
 * 标记清除（mark-sweep）:不压缩回收方式。无需大规模复制操作，内存利用率高，需两次遍历堆内空间，遍历的成本较大，
 * 应用程序暂停的时间随堆空间大小线性增大，内存不连续，碎片很多
 * 标记压缩（mark-sweep-compact）:压缩方式。可达对象，搬到一起，然后回收不可达对象。
 * 
 * 
 */


/*
 * 弱引用 主要使用的是WeakHashMap；
 */
class CrazyKey {
	String name;
	public CrazyKey(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj != null && obj.getClass() == CrazyKey.class) {
			return name.equals(((CrazyKey)obj).name);
		}
		return false;
	}
	
	public String toString() {
		return "CrazyKey[name=" + name + "]";
	}
}


class Person {
	String name;
	int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String toString() {
		return "Person[name=" + name + ", age=" + age + "]";
	}
}

// 内存回收
public class MemoryCollect {

	void testStrongReference() {
		Person[] people = new Person[10000];

		for (int i = 0; i < people.length; i++) {
			people[i] = new Person("姓名" + i, (i + 1) * 4 % 100);
		}

		System.out.println(people[2]);
		System.out.println(people[4]);
		System.gc();
		System.runFinalization();
		System.out.println(people[2]);
		System.out.println(people[4]);
	}

	/*
	 * 软应用：当内存充足时候和强应用一样，但是内存不足时候，弱应用会被释放掉
	 */
	void testSoftReference() {

		SoftReference<Person>[] people = new SoftReference[10000];

		for (int i = 0; i < people.length; i++) {
			people[i] = new SoftReference<Person>(new Person("姓名" + i, (i + 1) * 4 % 100));
		}

		System.out.println(people[2].get());
		System.out.println(people[4].get());
		System.gc();
		System.runFinalization();
		System.out.println(people[2].get());
		System.out.println(people[4].get());
	}
	
	/*
	 * 弱应用：
	 */
	void testWeakReference() {
		String str = new String("疯狂java定义");
		
		WeakReference<String> wr = new WeakReference<String>(str);
		
		str = null;
		System.out.println(wr.get());
		System.gc();
		System.runFinalization();
		System.out.println(wr.get());
		
	}
	
	/*
	 * 测试弱引用WeakHashMap
	 */
	void testWeakHashMap() throws InterruptedException {
		WeakHashMap<CrazyKey, String> map = new WeakHashMap<CrazyKey, String>();
		
		for (int i = 0; i < 10; i++) {
			map.put(new CrazyKey(i + 1 + ""), "value" + (i + 11));
		}
		System.out.println(map);
		System.out.println(map.get(new CrazyKey("2")));
		System.gc();
		Thread.sleep(50);
		System.out.println(map);
		System.out.println(map.get(new CrazyKey("2")));
	}
	
	/*
	 * 虚引用: 用于跟踪对象被垃圾回收
	 */
	void testReferenceQueue() {
		String str = new String("lukaer");
		
		//用于保存系统回收对象的引用
		ReferenceQueue<String> rq = new ReferenceQueue<String>();
		
		PhantomReference<String> pr = new PhantomReference<String>(str, rq);
		str = null;
		
		System.out.println(pr.get());
		System.gc();
		System.runFinalization();
		
		System.out.println(rq.poll() == pr);
	}

	public static void main(String args[]) throws InterruptedException {
		// for (String str : args)
		// System.out.println(str);
		System.out.println(String.format("%02d", 1));
//		MemoryCollect mc = new MemoryCollect();
//		mc.testReferenceQueue();
	}

}
