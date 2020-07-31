package calculation.chapter.three;

import java.util.Arrays;

/**
 * 冒泡排序
 * 算法：取第一个数与相邻的数比较，如果比他小就互换位置。直到把最大的数放到末尾。
 * 复杂度是O（N^2）
 * <p>
 * https://mp.weixin.qq.com/s/wO11PDZSM5pQ0DfbQjKRQA
 *
 * @author: xuxianbei
 * Date: 2019/12/17
 * Time: 10:01
 * Version:V1.0
 */
public class BubbleSort {
    private int[] array;
    private int length;

    public BubbleSort(int[] array) {
        this.array = array;
        this.length = array.length;
    }

    public void sort() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 改进 到后期数据都是有序的，不用进行冒泡排序了
     */
    public void sortEx() {
        for (int i = 0; i < array.length; i++) {
            // 有序标记
            boolean isSorted = true;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    //有元素交换
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    /**
     * 再一次改进
     */
    public void sortEx2() {
        int tmp = 0;
        int lastExchangeIndex = 0;
        int sortBorder = array.length - 1;
        for(int i = 0; i < array.length; i++) {
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                if (array[j] > array[j+1]){
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    isSorted = false;
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if (isSorted) {
                break;
            }
        }
    }

    public void display() {
        System.out.println(Arrays.toString(array));
    }

    public static void main(String[] args) {
        int[] array = {100, 45, 36, 21, 17, 13, 7};
        BubbleSort bubbleSort = new BubbleSort(array);
        bubbleSort.sort();
        bubbleSort.display();
        bubbleSort.sortEx();
        bubbleSort.display();
        bubbleSort.sortEx2();
        bubbleSort.display();
    }
}
