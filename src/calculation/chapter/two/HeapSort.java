package calculation.chapter.two;

import calculation.chapter.CommonCalc;

import java.util.Arrays;

/**
 * 堆排序
 * 推：一个二叉树，堆中每个节点的值都必须大于等于（或小于等于）其子树中每个节点的值
 * 1.构建一个堆，取长度-2 / 2 值开始 记为下标A，递减，
 * 2.记下标A的为parentIndex，  记录父节点temp：取parentIndex的值，取parentIndex的2倍+1的值为，下标B。判断下标B是否小于长度进行循环，
 * 3.找到右孩子，判断下标B+1是否小于长度，下标B+1 的值》下标B的值，true：右孩子下标++
 * 4.如果父节点》所有节点；跳出循环
 * 5.把右孩子放到parentInddex的位置，记录新的父节点，childIndex = 2 * childIndex + 1;
 * 6.父节点放temp
 * 7.for 把最后的数和第一个数互调，取0 循环2-6方法。
 * @author: xuxianbei
 * Date: 2019/12/17
 * Time: 14:33
 * Version:V1.0
 */
public class HeapSort extends CommonCalc {


    public HeapSort(int[] array) {
        this.array = array;
    }

    public void sort() {
        doSort(array);
    }

    private void doSort(int[] array) {
        // 1.把无序数组构建成二叉堆。
        for (int i = (array.length - 2) /
                2; i >= 0; i--) {
            downAdjust(array, i, array.length);
        }
        System.out.println(Arrays.toString(array));
        // 2.循环删除堆顶元素，移到集合尾部，调节堆产生新的堆顶。
        for (int i = array.length - 1; i > 0; i--) {
            // 最后一个元素和第一元素进行交换
            int temp = array[i];
            array[i] = array[0];
            array[0] = temp;
            // 下沉调整最大堆
            downAdjust(array, 0, i);
        }

    }

    private void downAdjust(int[] array, int parentIndex, int length) {
        // temp保存父节点值，用于最后的赋值
        int temp = array[parentIndex];
        int childIndex = 2 * parentIndex + 1;
        while (childIndex < length) {
            // 如果有右孩子，且右孩子大于左孩子的值，则定位到右孩子
            if (childIndex + 1 < length && array[childIndex + 1] > array[childIndex]) {
                childIndex++;
            }
            // 如果父节点小于任何一个孩子的值，直接跳出
            if (temp >= array[childIndex]) break;
            //无需真正交换，单向赋值即可
            array[parentIndex] = array[childIndex];
            parentIndex = childIndex;
            childIndex = 2 * childIndex + 1;
        }
        array[parentIndex] = temp;
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 6, 8, 9, 12, 1, 5, 2};
        HeapSort heapSort = new HeapSort(array);
        heapSort.sort();
        heapSort.display();
    }

}
