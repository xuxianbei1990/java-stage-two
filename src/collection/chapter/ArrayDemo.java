package collection.chapter;

/*
 * 泛型好处，可以给让类型 参数化
 * 同时约束代码的类型限制，代码更加好读些。
 */
public class ArrayDemo {
	public static void main(String args[]) {
		// 定义二维数组
		// 所以严格意义上讲。java是没有2维数组的概念。因为
		/*
		 * 2维数组其实就是1维数组里每个子项存储了一维数组，且是每个子项存储同样一维数组
		 * */
		/*
		 * Arrays工具类
		 * 1.排序 sort;2.数组转换list  asList；
		 * {数组变成集合后，不可以使用增删等改变数组长度的方法。}
		 * 
		 */
		int[][] arr = { { 1, 2, 3 }, { 4, 5, 6 } };
		// 静态初始化

		// 打印出二维数组
		System.out.println(arr.length);
		System.out.println(arr[0].length);
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			// 输出一列后就回车空格
			System.out.println();
		}

	}

}
