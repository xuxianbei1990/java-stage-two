package calculation.chapter.two;

import calculation.chapter.CommonCalc;

/** 归并排序
 * 母亲庆生；
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O（n)
 * @author: xuxianbei
 * Date: 2019/12/17
 * Time: 14:20
 * Version:V1.0
 */
public class MergeSort extends CommonCalc {

    private int start;
    private int end;

    public MergeSort(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public void sort() {
        doSort(array, start, end);
    }

    private void doSort(int[] array, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            doSort(array, start, mid);
            doSort(array, mid + 1, end);
            doMerge(array, start, mid, end);
        }
    }

    private void doMerge(int[] array, int start, int mid, int end) {
        //开辟额外大集合，设置指针
        int[] tempArray = new int[end - start + 1];
        int p1 = start, p2 = mid + 1, p = 0;
        //比较两个小集合的元素，依次放入大集合
        while (p1 <= mid && p2 <= end) {
            if (array[p1] <= array[p2])
                tempArray[p++] = array[p1++];
            else
                tempArray[p++] = array[p2++];
        }
        //左侧小集合还有剩余，依次放入大集合尾部
        while (p1 <= mid) tempArray[p++] = array[p1++];
        //右侧小集合还有剩余，依次放入大集合尾部
        while (p2 <= end) tempArray[p++] = array[p2++];
        //把大集合的元素复制回原数组
        for (int i = 0; i < tempArray.length; i++) array[i + start] = tempArray[i];
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 6, 8, 9, 12, 1, 5, 2};
        MergeSort mergeSort = new MergeSort(array, 0, array.length - 1);
        mergeSort.sort();
        mergeSort.display();
    }
}
