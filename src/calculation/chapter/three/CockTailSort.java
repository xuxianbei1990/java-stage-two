package calculation.chapter.three;

import java.util.Arrays;

/**
 * 鸡尾酒排序  冒泡排序的变种
 *O（n^2）
 * @author: xuxianbei
 * Date: 2019/12/16
 * Time: 10:12
 * Version:V1.0
 * 适用场景：大部分是有序的数据
 * 数组中的数字本是无规律的排放，先找到最小的数字，把他放到第一位，
 * 然后找到最大的数字放到最后一位。然后再找到第二小的数字放到第二位，
 * 再找到第二大的数字放到倒数第二位
 * <p>
 * 优化思路：
 * 原始的冒泡排序，有序区的长度和排序的轮数是相等的。
 * 比如第一轮排序过后的有序区长度是1，第二轮排序过后的有序区长度是2 ......
 * <p>
 * 要想优化，我们可以在每一轮排序的最后，记录下最后一次元素交换的位置，
 * 那个位置也就是无序数列的边界，再往后就是有序区了。
 */
public class CockTailSort {
    private int[] array;
    private int length;

    public CockTailSort(int[] array) {
        this.array = array;
        this.length = array.length;
    }

    public void display() {
        System.out.println(Arrays.toString(array));
    }

    /**
     * 大循环内包含两个小循环，第一个循环从左向右比较并交换元素，第二个循环从右向左比较并交换元素。
     */
    public void sort() {
        int temp = 0;
        for (int i = 0; i < array.length / 2; i++) {
            //有序标记，每一轮的初始是true
            boolean isSorted = true;
            //奇数轮，从左向右比较和交换
            for (int j = i; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            isSorted = true;
            //偶数轮，从右向左比较和交换
            for (int j = array.length - i - 1; j > i; j--) {
                if (array[j] < array[j - 1]) {
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                    //有元素交换，所以不是有序，标记变为false
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    //优化算法
    public void sortEx() {
        int tmp = 0;
        //记录右侧最后一次交换的位置
        int lastRightExchangeIndex = 0;
        //记录左侧最后一次交换的位置
        int lastLeftExchangeIndex = 0;
        //无序数列的右边界，每次比较只需要比到这里为止
        int rightSortBorder = array.length - 1;
        //无序数列的左边界，每次比较只需要比到这里为止
        int leftSortBorder = 0;
        for (int i = 0; i < array.length / 2; i++) {
            //有序标记，每一轮的初始是true
            boolean isSorted = true;
            //奇数轮，从左向右比较和交换
            for (int j = leftSortBorder; j < rightSortBorder; j++) {
                if (array[j] > array[j + 1]) {
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    //有元素交换，所以不是有序，标记变为false
                    isSorted = false;
                    lastRightExchangeIndex = j;
                }
            }
            rightSortBorder = lastRightExchangeIndex;

            if (isSorted) {
                break;
            }
            //偶数轮之前，重新标记为true
            isSorted = true;
            //偶数轮，从右向左比较和交换
            for (int j = rightSortBorder; j > leftSortBorder; j--) {

                if (array[j] < array[j - 1]) {
                    tmp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = tmp;
                    //有元素交换，所以不是有序，标记变为false
                    isSorted = false;
                    lastLeftExchangeIndex = j;
                }
            }
            leftSortBorder = lastLeftExchangeIndex;
            if (isSorted) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 6, 8, 9, 12, 1, 5, 2};
        CockTailSort cockTailSort = new CockTailSort(array);
        cockTailSort.sort();
        cockTailSort.display();
    }
}
