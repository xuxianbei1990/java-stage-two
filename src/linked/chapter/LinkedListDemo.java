package linked.chapter;

import java.util.Arrays;

/**
 * @author xuxb 246 从数据的逻辑结构来分： 
 * 1.集合：元素之间只有“同属于一个集合”关系。
 * 2.线性结构：数据元素之间存在一对一的关系。用于相同的结构 示例：1，2,3,4,5 
 * 3.树形机构：数据元素之间存在一对多的关系。
 * 4.网状或图状结构：数据元素之间存在多对多的关系。
 * 
 * ArrayList: 线性表的数组实现
 *    顺序表： 顺序表存储空间是静态分布的，
 *    需要一定长度固定 的数组，因此总有部分数组元素被浪费。
 *    顺序表中元素的逻辑顺序与物理存储顺序保持一致，而且支持随机存取
 *    因此顺序表在查找、读取时性能很好。
 * LinkedList：线性表的链式实现
 *    双向链表：不会浪费空间，插入。删除元素性能更好。
 *    
 *    272
 *    栈（LIFO）：先进后出。允许插入、删除操作的一段被称作：栈顶。
 *    从顶部压入数据 因为栈是不需要查询的，所以栈用链表实现更好
 *    顺序栈：是用数组实现的，在扩充时候比较麻烦
 *    
 *    顺序栈  
 *    java.util.Stack;
 *    
 *    双向链表
 *    java.util.LinkedList;
 *    
 */

/**
 * 顺序栈
 * 
 * @author xuxb
 * 
 * @param <T>
 */
class SequenceStack<T> {

	private int DEFAULT_SIZE = 10;

	// 保存数组的长度
	private int capacity;

	// 当底层数组容量不够时，程序每次增加的数组长度
	private int capacityIncrement = 0;

	// 定义一个数组用于保存顺序栈的元素
	private Object[] elementData;

	// 保存顺序栈中元素的当前个数
	private int size = 0;

	// 以默认数组长度常见空顺序栈
	public SequenceStack() {
		capacity = DEFAULT_SIZE;
		elementData = new Object[capacity];
	}

	// 以一个初始化元素创建顺序栈
	public SequenceStack(T element) {
		this();
		elementData[0] = element;
		size++;
	}

	/*
	 * 以指定长度的数组来创建顺序栈 element 指定顺序栈中第一个元素 initSize 指定顺序栈底层数组的长度
	 */
	public SequenceStack(T element, int initSize) {
		this.capacity = initSize;
		elementData = new Object[capacity];
		elementData[0] = element;
		size++;
	}

	/*
	 * 以指定长度的数组来创建顺序栈 element 指定顺序栈中第一个元素 initSize 顺序栈底层数组的长度 capacityIncrement
	 * 指定当顺序栈底层数组的长度不够时， 底层数组每次增加的长度
	 */
	public SequenceStack(T element, int initSize, int capacityIncrement) {
		this.capacity = initSize;
		this.capacityIncrement = capacityIncrement;
		elementData = new Object[capacity];
		elementData[0] = element;
		size++;
	}

	// 获取顺序栈的大小
	public int length() {
		return size;
	}

	// 入栈
	public void push(T element) {
		ensureCapacity(size + 1);
		elementData[size++] = element;
	}

	private void ensureCapacity(int minCapacity) {
		// 如果数组的原有长度小于目前所需的长度
		if (capacityIncrement > 0) {
			while (capacity < minCapacity) {
				capacity += capacityIncrement;
			}
		} else {
			while (capacity < minCapacity) {
				// 不断地将capacity * 2，直到capacity大于minCapacity 为止。
				capacity <<= 1;
			}
		}
		elementData = Arrays.copyOf(elementData, capacity);
	}

	// 出栈
	public T pop() {
		T oldValue = (T) elementData[size - 1];
		// 释放栈顶元素
		elementData[--size] = null;
		return oldValue;
	}

	// 返回栈顶元素，但不删除栈顶元素
	public T peek() {
		return (T) elementData[size - 1];
	}

	// 判断顺序栈是否为空栈
	public boolean empty() {
		return size == 0;
	}

	// 清空顺序栈
	public void clear() {
		Arrays.fill(elementData, null);
		size = 0;
	}

	public String toString() {
		if (size == 0) {
			return "[]";
		} else {
			StringBuilder sb = new StringBuilder("[");
			for (int i = size - 1; i > -1; i--) {
				sb.append(elementData[i].toString() + ", ");
			}
			int len = sb.length();
			return sb.delete(len - 2, len).append("]").toString();
		}
	}
}

// 链接栈
class LinkStack<T> {
	private class Node {
		// 保存节点的数据
		private T data;
		// 指向下个节点的引用
		private Node next;

		public Node() {

		}

		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	// 保存该链栈的栈顶元素
	private Node top;
	// 保存该链栈已包含的节点数
	private int size;

	// 创建空链栈
	public LinkStack() {
		// 空链栈，top的值为null
		top = null;
	}

	// 指定数据元素来创建链栈
	public LinkStack(T element) {
		top = new Node(element, null);
		size++;
	}

	// 返回链栈的长度
	public int length() {
		return size;
	}

	// 进栈
	public void push(T element) {
		top = new Node(element, top);
		size++;
	}

	// 出栈
	public T pop() {
		Node oldTop = top;
		top = oldTop.next;
		// 释放
		oldTop.next = null;
		size--;
		return oldTop.data;
	}

	// 访问栈顶元素
	public T peek() {
		return top.data;
	}

	public boolean empty() {
		return size == 0;
	}

	// 清空
	public void clear() {
		top = null;
		size = 0;
	}

	public String toString() {
		if (empty()) {
			return "[]";
		} else {
			StringBuilder sb = new StringBuilder("[");
			for (Node current = top; current != null; current = current.next) {
				sb.append(current.toString() + ", ");
			}
			int len = sb.length();
			return sb.delete(len - 2, len).append("]").toString();
		}
	}
}

public class LinkedListDemo {

	// 测试顺序栈
	void testSequenceStack() {
		SequenceStack<String> stack = new SequenceStack<String>();
		stack.push("aaa");
		stack.push("bbb");
		stack.push("ccc");
		stack.push("ddd");
		System.out.println(stack);
		// 访问栈顶元素
		System.out.println("访问栈顶元素:" + stack.peek());
		// 弹出一个元素
		System.out.println("第一次弹出栈顶元素:" + stack.pop());
		System.out.println("第二次弹出栈顶元素:" + stack.pop());
		System.out.println(stack);
	}

	public static void main(String[] args) {
		LinkedListDemo lld = new LinkedListDemo();
		lld.testSequenceStack();
	}

}
