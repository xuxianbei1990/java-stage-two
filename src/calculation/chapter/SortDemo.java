package calculation.chapter;

//88

/**
 * 冒泡排序算法
 * 算法：取第一个数与相邻的数比较，如果比他小就互换位置。直到把最大的数放到末尾。
 * <p>
 * O（N^2）
 */
final class SortMetrics extends Object implements Cloneable {
    public long probeCnt, compareCnt, swapCnt;

    public void init() {
        probeCnt = swapCnt = compareCnt = 0;
    }

    @Override
    public String toString() {
        return probeCnt + "probes" + compareCnt + "compares" + swapCnt + "swaps";
    }

    @Override
    public SortMetrics clone() {
        try {
            return (SortMetrics) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }
}

abstract class SortDouble {
    private double[] values;
    private final SortMetrics curMetrics = new SortMetrics();

    public final SortMetrics sort(double[] data) {
        values = data;
        curMetrics.init();
        doSort();
        return getMetrics();
    }

    public final SortMetrics getMetrics() {
        return curMetrics.clone();
    }

    protected final int getDataLength() {
        return values.length;
    }

    protected final double probe(int i) {
        curMetrics.probeCnt++;
        return values[i];
    }

    protected final int compare(int i, int j) {
        curMetrics.compareCnt++;
        double d1 = values[i];
        double d2 = values[j];
        if (d1 == d2) {
            return 0;
        } else {
            return (d1 < d2 ? -1 : 1);
        }
    }

    protected final void swap(int i, int j) {
        curMetrics.swapCnt++;
        double tmp = values[i];
        values[i] = values[j];
        values[j] = tmp;
    }

    protected abstract void doSort();
}

class SimpleSortDouble extends SortDouble {
    @Override
    protected void doSort() {
        for (int i = 0; i < getDataLength(); i++) {
            for (int j = i + 1; j < getDataLength(); j++) {
                if (compare(i, j) > 0) {
                    swap(i, j);
                }
            }
        }
    }
}

public class SortDemo {
    static double[] testData = {0.3, 1.3e-1, 7.9, 3.17};

    /**
     * @param args
     */
    public static void main(String[] args) {
        SortDouble bsort = new SimpleSortDouble();
        SortMetrics metrics = bsort.sort(testData);
        System.out.println("Metrics:" + metrics);
        for (int i = 0; i < testData.length; i++)
            System.out.println("\t" + testData[i]);
    }

}
