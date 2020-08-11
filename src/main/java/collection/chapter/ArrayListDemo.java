package collection.chapter;

import java.util.ArrayList;
import java.util.Iterator;

/*
 *
 */
public class ArrayListDemo<E> extends ArrayList<E> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*
     * 本质上是一个数组。
     */
    ArrayListDemo(int initialCapacity) {
        super(initialCapacity);
    }

    /*
     * 每次扩充原数据的一倍减1位，用
     * Arrays.copyOf(elementData, newCapacity) 拷贝
     * (non-Javadoc)
     * @see java.util.ArrayList#add(java.lang.Object)
     */
    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    @Override
    public void add(int index, E element) {
        super.add(index, element);
    }

    void testIterator() {
        Iterator<E> it = super.iterator();
        it.hasNext();
    }

    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("222");
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if ("222".equals(str)) {
                iterator.remove();
            }
        }
        System.out.println(arrayList.toString());
    }
}
