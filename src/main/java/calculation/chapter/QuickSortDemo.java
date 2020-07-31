package calculation.chapter;

import java.util.Arrays;

/**
 * @author EDZ
 *
 * 快速排序； 冒泡大改进
 *  * 以数组{49,38,65,97,76,13,27,49}为例，
 *  * 选择第一个元素49为基准,把比这个数大的放到
 *  * 放到右边，小的放到左边。以此类推。
 *  * 初始化关键字： [49,38,65,97,76,13,27,49]
 */
public class QuickSortDemo {

    public static void sort(int a[], int low, int hight) {
        int i, j, index;
        if (low > hight) {
            return;
        }
        i = low;
        j = hight;
        index = a[i];
        while (i < j) {
            while (i < j && a[j] >= index) {
                j--;
            }
            if (i < j) {
                a[i++] = a[j];
            }
            while (i < j && a[i] < index) {
                i++;
            }
            if (i < j) {
                a[j--] = a[i];
            }
        }
        a[i] = index;
        sort(a, low, i - 1);
        sort(a, i + 1, hight);
    }

    public static void quickSort(int a[]) {
        sort(a, 0, a.length - 1);
    }

    public static void main(String[] args) {
        int a[] = {49, 38, 65, 97, 76, 13, 27, 49};
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }
}
