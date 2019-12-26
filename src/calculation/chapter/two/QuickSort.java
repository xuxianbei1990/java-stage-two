package calculation.chapter.two;

import calculation.chapter.CommonCalc;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 快速排序；
 * 冒泡算法大改进 时间复杂度O(logN)
 *
 * @author: xuxianbei
 * Date: 2019/12/17
 * Time: 11:30
 * Version:V1.0
 */
public class QuickSort extends CommonCalc {

    private int startIndex;
    private int endIndex;

    public QuickSort(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }


    public void sort() {
        doSort(array, startIndex, endIndex);
    }

    public void sortEx() {
        doSort(array, startIndex, endIndex);
    }

    public void sortEx2() {
        partitionStack(array, startIndex, endIndex);
    }

    private void doSortEx(int[] array, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        int pivotIndex = partitionEx(array, startIndex, endIndex);
        doSort(array, startIndex, pivotIndex - 1);
        doSort(array, pivotIndex + 1, endIndex);
    }

    private void doSort(int[] array, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        int pivotIndex = partition(array, startIndex, endIndex);
        doSort(array, startIndex, pivotIndex - 1);
        doSort(array, pivotIndex + 1, endIndex);
    }

    //挖坑法
    private int partition(int[] array, int startIndex, int endIndex) {
        int pivot = array[startIndex];
        int left = startIndex;
        int right = endIndex;
        // 坑的位置，初始等于pivot的位置
        int index = startIndex;
        //大循环在左右指针重合或者交错时结束
        while (right >= left) {
            //right指针从右向左进行比较
            while (right >= left) {
                if (array[right] < pivot) {
                    array[left] = array[right];
                    index = right;
                    left++;
                    break;
                }
                right--;
            }
            //left指针从左向右进行比较
            while (right >= left) {
                if (array[left] > pivot) {
                    array[right] = array[left];
                    index = left;
                    right--;
                    break;
                }
                left++;
            }
        }
        array[index] = pivot;
        return index;
    }

    //指针交换
    private int partitionEx(int[] arr, int startIndex, int endIndex) {
        // 取第一个位置的元素作为基准元素
        int pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;
        while (left != right) {
            //控制right指针比较并左移
            while (left < right && arr[right] > pivot) {
                right--;
            }
            //控制right指针比较并右移
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            //交换left和right指向的元素
            if (left < right) {
                int p = arr[left];
                arr[left] = arr[right];
                arr[right] = p;
            }
        }
        //pivot和指针重合点交换
        int p = arr[left];
        arr[left] = arr[startIndex];
        arr[startIndex] = p;
        return left;
    }

    //非递归实现
    private void partitionStack(int[] arr, int startIndex, int endIndex) {
        // 用一个集合栈来代替递归的函数栈
        Stack<Map<String, Integer>> quickSortStack = new Stack<Map<String, Integer>>();
        // 整个数列的起止下标，以哈希的形式入栈
        Map rootParam = new HashMap();
        rootParam.put("startIndex", startIndex);
        rootParam.put("endIndex", endIndex);
        quickSortStack.push(rootParam);
        // 循环结束条件：栈为空时结束
        while (!quickSortStack.isEmpty()) {
            // 栈顶元素出栈，得到起止下标
            Map<String, Integer> param = quickSortStack.pop();
            // 得到基准元素位置
            int pivotIndex = partitionEx(arr, param.get("startIndex"), param.get("endIndex"));
            // 根据基准元素分成两部分, 把每一部分的起止下标入栈
            if (param.get("startIndex") < pivotIndex - 1) {
                Map<String, Integer> leftParam = new HashMap<String, Integer>();
                leftParam.put("startIndex", param.get("startIndex"));
                leftParam.put("endIndex", pivotIndex - 1);
                quickSortStack.push(leftParam);
            }

            if (pivotIndex + 1 < param.get("endIndex")) {
                Map<String, Integer> rightParam = new HashMap<String, Integer>();
                rightParam.put("startIndex", pivotIndex + 1);
                rightParam.put("endIndex", param.get("endIndex"));
                quickSortStack.push(rightParam);
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 6, 8, 9, 12, 1, 5, 2};
        QuickSort quickSort = new QuickSort(array, 0, array.length - 1);
        quickSort.sort();
        quickSort.display();
        quickSort.sortEx();
        quickSort.display();
        quickSort.sortEx2();
        quickSort.display();
    }
}
