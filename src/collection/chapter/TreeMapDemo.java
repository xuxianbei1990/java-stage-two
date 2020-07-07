package collection.chapter;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 红黑树
 * 特性：
 * 1.根节点是黑色
 * 2.叶节点是黑色的
 * 3.节点不是黑色就是红色
 * 4.节点到各个叶节点是一样的
 * 5.一个节点是红色那么他的叶子一定是黑色的
 * <p>
 * java实现：
 * 1.创建根节点.
 * 2.找到父节点
 * 3.构建二叉树
 * 4.平衡红黑树（）
 * Name
 *
 * @author xxb
 * Date 2020/6/28
 * VersionV1.0
 * @description
 */
public class TreeMapDemo<K, V> {

    Comparator<? super K> comparator;
    Entry<K, V> root;
    Integer size;
    transient int modCount = 0;

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    static class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> parent;
        boolean color = BLACK;

        Entry(K key, V value, Entry<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    public V put(K key, V value) {
        Entry<K, V> t = root;
        if (Objects.isNull(t)) {
            root = new Entry<>(key, value, null);
            size = 1;
            modCount++;
            return null;
        }
        int cmp;
        Entry<K, V> parent;
        //如果自定义比较器  Comparator<? super K> cpr = comparator;
        if (key == null) {
            throw new NullPointerException();
        }
        Comparable<? super K> k = (Comparable<? super K>) key;
        do {
            cmp = k.compareTo(t.getKey());
            parent = t;
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else
                return t.setValue(value);
        } while (t != null);
        //构建二叉树
        Entry<K, V> e = new Entry<>(key, value, parent);
        if (cmp < 0) {
            parent.left = e;
        } else {
            parent.right = e;
        }
        fixAfterInsertion(e);
        size++;
        modCount++;
        return null;
    }

    //修复红黑树
    private void fixAfterInsertion(Entry<K, V> x) {
        x.color = RED;

        /**
         * 一共四种情况：
         * 父节点左添加时候：
         * 获取右叔叔节点
         * 1.父节点和叔叔节点都是红色：设置父节点，叔叔节点为黑色，设置祖父节点红色，祖父节点赋值给当前节点
         * 2.父节点红色，叔叔节点黑色:
         *    如果是右加孩子，把当前节点改为父节点, 左旋当前节点; 父节点置为黑色，祖父节点红色，右旋祖父节点
         * 父节点右添加时候：
         * 获取左叔叔节点
         * 1.父节点和叔叔节点都是红色：设置父节点，叔叔节点黑色，设置祖父节点为红色，祖父节点赋值给当前节点
         * 2.父节点红色，叔叔节点黑色：
         *    左孩子添加，把当前节点改为父节点, 右旋当前节点；父亲节置为黑色，祖父节点红色，左旋祖父节点
         *
         *
         */
        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                Entry<K, V> y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                Entry<K, V> y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                }

            }
        }
        root.color = BLACK;
    }

    private void rotateRight(Entry<K, V> p) {

    }

    private void rotateLeft(Entry<K, V> p) {

    }

    private void setColor(Entry<K, V> p, boolean black) {
        if (p != null) {
            p.color = black;
        }
    }

    private boolean colorOf(Entry<K, V> y) {
        return y == null ? BLACK : y.color;
    }

    private Entry<K, V> rightOf(Entry<K, V> p) {
        return p == null ? null : p.right;
    }

    private Entry<K, V> leftOf(Entry<K, V> p) {
        return (p == null) ? null : p.left;
    }

    private <K, V> Entry<K, V> parentOf(Entry<K, V> p) {
        return (p == null ? null : p.parent);
    }

    public static void main(String[] args) {
        TreeMap treeMap = new TreeMap();
        treeMap.clear();
    }
}
