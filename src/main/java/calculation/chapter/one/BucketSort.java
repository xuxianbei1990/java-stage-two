package calculation.chapter.one;

import calculation.chapter.CommonCalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 桶排序
 * 时间复杂度： O(n+m+n(logn-logm)）
 * 空间复杂度：O（m+n）
 * 缺点：桶的元素分布均匀：时间复杂度O（n）;极不均匀 例如第一桶n -1 个元素
 * 最后一个桶 1个元素： O（nlogn）  例如 4.5,0.84,3.25,10000000.0,0.5
 *
 * @author: xuxianbei
 * Date: 2019/12/17
 * Time: 15:12
 * Version:V1.0
 */
public class BucketSort extends CommonCalc {

    protected double[] array;

    public BucketSort(double[] array) {
        this.array = array;
    }

    public void sort() {
//1.得到数列的最大值和最小值，并算出差值d
        double max = array[0];
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        double d = max - min;
        //2.初始化桶
        int bucketNum = array.length;
        ArrayList<LinkedList<Double>> bucketList = new ArrayList<LinkedList<Double>>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            bucketList.add(new LinkedList<Double>());
        }
        //3.遍历原始数组，将每个元素放入桶中
        for (int i = 0; i < array.length; i++) {
            int num = (int) ((array[i] - min) * (bucketNum - 1) / d);
            bucketList.get(num).add(array[i]);
        }
        //4.对每个通内部进行排序
        for (int i = 0; i < bucketList.size(); i++) {
            //JDK底层采用了归并排序或归并的优化版本
            Collections.sort(bucketList.get(i));
        }
        //5.输出全部元素
        double[] sortedArray = new double[array.length];
        int index = 0;
        for (LinkedList<Double> list : bucketList) {
            for (double element : list) {
                sortedArray[index] = element;
                index++;
            }
        }
        array = sortedArray;
    }

    public void display() {
        System.out.println(Arrays.toString(array));
    }

    public static void main(String[] args) {
        double[] array = {4.12, 6.421, 0.0023, 3.0, 2.123, 8.122, 4.12, 10.09};
        BucketSort bucketSort = new BucketSort(array);
        bucketSort.sort();
        bucketSort.display();
    }
}
