package collection.chapter;

import java.util.*;

/*
 * 75
 * Set:无序，不可重复集合；
 * Map:像是Set一种扩展，类继承体系很类似。
 *
 * HashMap：可以排序
 * initialCapacity：最好是2的N次方  loadFactor：值约小，查询时间越短，但是占用空间越大
 * 实际存储时候是一个数据加一个链表
 * new HashMap(int initialCapacity, float loadFactor);
 *
 * 如果使用HashSet来存储键值的时候：需要重写equals 和 hashcode 函数。而HashSet实际上对HashMap的分装。
 *
 * TreeMap 的实现使用了红黑树数据结构 有序的
 *
 * Map和List差异：
 * Map和List：用法相似，但是实际实现方法并没有什么关联。
 *
 * ArrayList底层封装就是数组
 * List：ArrayList和Vector  Vector：线程安全。比较老;其中两个类，功能类似
 * 其中 Collections; 提供synchronizedList可以将ArrayList转换成安全的线程ArrayList
 *
 * ArrayList 和 LinkedList差异：
 * ArrayList： 数组  查找快；LinkedList实际上是双向链表 更新快
 *
 */

class SimpleEntry<K, V> implements Map.Entry<K, V>, java.io.Serializable {
    private final K key;

    private V value;

    public SimpleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public SimpleEntry(Map.Entry<? extends K, ? extends V> entry) {
        this.key = entry.getKey();
        this.value = entry.getValue();
    }

    @Override
    public K getKey() {
        // TODO Auto-generated method stub
        return key;
    }

    @Override
    public V getValue() {
        // TODO Auto-generated method stub
        return value;
    }

    @Override
    public V setValue(V value) {
        // TODO Auto-generated method stub
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o.getClass() == SimpleEntry.class) {
            SimpleEntry se = (SimpleEntry) o;
            return se.getKey().equals(getKey());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key == null ? 0 : key.hashCode();
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

}

class MySetMap<K, V> extends HashSet<SimpleEntry<K, V>> {

    @Override
    public void clear() {
        super.clear();
    }

    public boolean containsKey(Object key) {
        return super.contains(new SimpleEntry<K, V>((K) key, null));
    }

    boolean containsValue(Object value) {
        for (SimpleEntry<K, V> se : this) {
            if (se.getValue().equals(value)) {
                return true;
            }

        }
        return false;
    }

    public V get(Object key) {
        for (SimpleEntry<K, V> se : this) {
            if (se.getKey().equals(key)) {
                return se.getValue();
            }
        }
        return null;
    }

    public V put(K key, V value) {
        add(new SimpleEntry<K, V>(key, value));
        return value;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            add(new SimpleEntry<K, V>(key, m.get(key)));
        }
    }

    public V removeEntry(Object key) {
        for (Iterator<SimpleEntry<K, V>> it = this.iterator(); it.hasNext(); ) {
            SimpleEntry<K, V> en = (SimpleEntry<K, V>) it.next();
            if (en.getKey().equals(key)) {
                V v = en.getValue();
                it.remove();
                return v;
            }
        }

        return null;
    }
}

public class CollectionDemo {

    // 测试用Set改造的map
    void testMySetMap() {

        Map map = new HashMap<Object, Object>();
        MySetMap<String, Integer> sm = new MySetMap<String, Integer>();
        sm.put("语文", 89);
        sm.put("数学", 83);
        sm.put("英文", 80);

        System.out.println(sm);
        System.out.println(sm.size());
        sm.removeEntry("数学");
        System.out.println("删除key为\"数学\"的Entry之后：" + sm);
        System.out.println("语文成绩:" + sm.get("语文"));
        System.out.println("是否包含\"英文\"key:" + sm.containsKey("英文"));
        System.out.println("是否包含 82 value:" + sm.containsValue(82));
        sm.clear();
        System.out.println("执行clear（）方法之后的集合：" + sm);
    }

    class Name {
        protected String firstName;
        protected String lastName;

        Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            // 判断Name是 o的父类
            if (Name.class.isAssignableFrom(o.getClass())) {
                Name n = (Name) o;
                return n.firstName.equals(firstName) && n.lastName.equals(lastName);
            }
            return false;
        }
    }

    class HashName extends Name {

        HashName(String firstName, String lastName) {
            super(firstName, lastName);
        }

        @Override
        public boolean equals(Object o) {
            return super.equals(o);
        }

        @Override
        public int hashCode() {
            return firstName.hashCode();
        }

        @Override
        public String toString() {
            return "Name[first=" + firstName + ", last=" + lastName + "]";
        }

    }

    void testHashSet() {
        Set<Name> s = new HashSet<Name>();
        s.add(new Name("abc", "123"));
        System.out.println(s.contains(new Name("abc", "123")));
        s.add(new HashName("abc", "123"));
        System.out.println(s.contains(new HashName("abc", "123")));

        // 左移动4位
        System.out.println(1 << 4);
        HashName hashName1 = new HashName("admin", "123");
        HashName hashName2 = new HashName("admin", "123");
        System.out.println(hashName1.hashCode());
        System.out.println(hashName2.hashCode());
        System.out.println(hashName2.equals(hashName1));
    }

    // 测试HashMap
    void testHashMap() {
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        hm.values();
    }

    enum Gender {
        MALE, FEMALE;
    }

    // 测试迭代器 (Iterator迭代器 迭代器模式。只是用来做循环的。)
    void testIteratorTest() {
        HashSet<String> hs = new HashSet<String>();
        System.out.println("HashSet 的 Iterator:" + hs.iterator());

        LinkedHashSet<String> lhs = new LinkedHashSet<String>();
        System.out.println("LinkedHashSet 的 Iterator:" + lhs.iterator());

        TreeSet<String> ts = new TreeSet<String>();
        System.out.println("TreeSet 的 Iterator:" + ts.iterator());

        EnumSet<Gender> es = EnumSet.allOf(Gender.class);
        System.out.println("EnumSet 的 Iterator:" + es.iterator());

        ArrayList<String> al = new ArrayList<String>();
        System.out.println("ArrayList 的 Iterator:" + al.iterator());

        Vector<String> vector = new Vector<String>();
        System.out.println("Vector 的 Iterator:" + vector.iterator());

        LinkedList<String> ll = new LinkedList<String>();
        System.out.println("LinkedList 的 Iterator:" + ll.iterator());

        ArrayDeque<String> ad = new ArrayDeque<String>();
        System.out.println("ArrayDeque 的 Iterator:" + ad.iterator());
    }

    //虽然是无序排序但是如果是字母的话，还是会按照字母顺序排序
    public void testHashMapSort() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put("SFGY", null);
        result.put("SFGD", null);
        result.put("SFCJLSGL", null);
        result.put("SFTDHY", null);
        result.put("SFJWRY", null);
        System.out.println(result);
        String[] nsrlxs = {"SFJWRY", "SFGY", "SFTDHY", "SFCJLSGL"};
        for (String str : nsrlxs) {
            if (result.containsKey(str)) {
                result.put(str, 1);
            }
        }
        System.out.println(result);
    }

    public static void main(String args[]) {
        CollectionDemo cd = new CollectionDemo();
        cd.testIteratorTest();
    }

}
