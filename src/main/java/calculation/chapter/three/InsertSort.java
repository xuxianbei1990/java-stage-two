package calculation.chapter.three;

import java.util.Arrays;

/**
 * 插入排序
 * 当原始数组接近有序时，插入排序性能最优
 *
 * 最近有点脱发，明天请假去医院看看
 *
 * O（n^2）
 * @author: xuxianbei
 * Date: 2019/12/16
 * Time: 9:42
 * Version:V1.0
 * 1、将指针指向某个元素，假设该元素左侧的元素全部有序，将该元素抽取出来，然后按照从右往左的顺序分别与其左边的元素比较，遇到比其大的元素便将元素右移，直到找到比该元素小的元素或者找到最左面发现其左侧的元素都比它大，停止；
 * 2、此时会出现一个空位，将该元素放入到空位中，此时该元素左侧的元素都比它小，右侧的元素都比它大；
 * 3、指针向后移动一位，重复上述过程。每操作一轮，左侧有序元素都增加一个，右侧无序元素都减少一个。
 */
public class InsertSort {

    private int[] array;
    private int length;

    public InsertSort(int[] array) {
        this.array = array;
        this.length = array.length;
    }

    public void display() {
        System.out.println(Arrays.toString(array));
    }

    public void sort() {
        for (int index = 1; index < length; index++) {
            int temp = array[index];
            int leftIndex = index - 1;
            while (leftIndex >= 0 && array[leftIndex] > temp) {
                array[leftIndex + 1] = array[leftIndex];
                leftIndex--;
            }
            array[leftIndex + 1] = temp;
        }
    }

    public static void main(String[] args) {
        int[] array = {38, 65, 97, 76, 13, 27, 49};
        InsertSort insertSort = new InsertSort(array);
        System.out.println("排序前的数据为：");
        insertSort.display();
        insertSort.sort();
        System.out.println("排序后的数据为：");
        insertSort.display();
    }
}
