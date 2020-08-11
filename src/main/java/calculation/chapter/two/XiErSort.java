package calculation.chapter.two;

import java.util.Arrays;

/**
 * 希尔排序  插入排序优化  O（n^1.3）
 * 请假：请假对面超市月饼打折，抢月饼
 * https://mp.weixin.qq.com/s/b9-dkpAhWJYshuSs5cwnOw
 *
 * @author: xuxianbei
 * Date: 2019/12/16
 * Time: 18:17
 * Version:V1.0
 */
public class XiErSort {
    private int[] array;
    private int length;

    public XiErSort(int[] array) {
        this.array = array;
        this.length = array.length;
    }

    public void display() {
        System.out.println(Arrays.toString(array));
    }

    public void sort() {
        //希尔排序增量
        int d = array.length;
        while (d > 1) {
            //使用希尔增量的方式，即每次折半
            d = d / 2;
            for (int x = 0; x < d; x++) {
                for (int i = x + d; i < array.length; i = i + d) {
                    int temp = array[i];
                    int j;
                    for (j = i - d; j >= 0 && array[j] > temp; j = j - d) {
                        array[j + d] = array[j];
                    }
                    array[j + d] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {5, 21, 1, 2, 8, 12, 34, 6, 3, 11, 3, 6, 8, 10};
        XiErSort xiErSort = new XiErSort(array);
        xiErSort.sort();
        xiErSort.display();
    }
}
