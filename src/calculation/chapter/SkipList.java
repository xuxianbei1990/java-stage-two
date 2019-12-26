package calculation.chapter;

import lombok.Data;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 调表排序
 * User: xuxianbei
 * Date: 2019/11/25
 * Time: 14:02
 * Version:V1.0
 * @author xuxianbei
 */
public class SkipList<K extends Comparable<K>, V> {

    @Data
    protected static class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private int level;
        private Node<K, V> up, down, next, previous;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.level = level;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", level=" + level +
                    ", up=" + up +
                    ", down=" + down +
                    ", next=" + next +
                    ", previous=" + previous +
                    '}';
        }
    }

    /**
     * 一个随机数生成器
     */
    protected static final Random randomGenerator = new Random();

    /**
     * 默认的概率
     */
    protected static final double DEFAULT_PROBABILITY = 0.5;

    private Node<K, V> head;

    private double probability;

    /**
     * SkipList中的元素数量（不计算多个层级中的冗余元素）
     */
    private int size;

    public SkipList() {
        this(DEFAULT_PROBABILITY);
    }

    public SkipList(double probability) {
        this.head = new Node<K, V>(null, null, 0);
        this.probability = probability;
        this.size = 0;
    }

    protected void checkKeyValidity(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must be not null");
        }
    }

    protected boolean lessThanOrEqual(K a, K b) {
        return a.compareTo(b) <= 0;
    }

    //可以限制层数 比如12层
    protected boolean isBuildLevel() {
        return randomGenerator.nextDouble() < probability;
    }

    protected void horizontalInsert(Node<K, V> x, Node<K, V> y) {
        y.setPrevious(x);
        y.setNext(x.getNext());
        if (x.getNext() != null) {
            x.getNext().setPrevious(y);
        }
        x.setNext(y);
    }

    protected void verticalLink(Node<K, V> x, Node<K, V> y) {
        x.setDown(y);
        y.setUp(x);
    }

    protected Node<K, V> findNode(K key) {
        Node<K, V> node = head;
        Node<K, V> next = null;
        Node<K, V> down = null;
        K nodeKey = null;
        while (true) {
            // 不断遍历直到遇见大于目标元素的节点
            next = node.getNext();
            while (next != null && lessThanOrEqual(next.getKey(), key)) {
                node = next;
                next = node.getNext();
            }
            // 当前元素等于目标元素，中断循环
            nodeKey = node.getKey();
            if (nodeKey != null && nodeKey.compareTo(key) == 0) {
                break;
            }
            // 否则，跳跃到下一层级
            down = node.getDown();
            if (down != null) {
                node = down;
            } else {
                break;
            }
        }
        return node;
    }

    public V get(K key) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node.getKey().compareTo(key) == 0) {
            return node.getValue();
        } else {
            return null;
        }
    }

    public void add(K key, V value) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node.getKey() != null && node.getKey().compareTo(key) == 0) {
            node.setValue(value);
            return;
        }
        //newNode水平插入node之后
        Node<K, V> newNode = new Node<K, V>(key, value, node.getLevel());
        horizontalInsert(node, newNode);
        int currentLevel = node.getLevel();
        int headLevel = head.getLevel();
        while (isBuildLevel()) {
            // 如果当前层级已经到达或超越顶层
            // 那么需要构建一个新的顶层
            if (currentLevel >= headLevel) {
                Node<K, V> newHead = new Node<K, V>(null, null, headLevel + 1);
                verticalLink(newHead, head);
                head = newHead;
                headLevel = head.getLevel();
            }

            // 找到node对应的上一层节点
            while (node.getUp() == null) {
                node = node.getPrevious();
            }
            node = node.getUp();

            Node<K, V> tmp = new Node<K, V>(key, value, node.getLevel());
            horizontalInsert(node, tmp);
            verticalLink(tmp, newNode);
            newNode = tmp;
            currentLevel++;
        }
        size++;
    }

    /**
     * 注定删除效率比较低下
     *
     * @param key
     */
    public void remove(K key) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node == null || node.getKey().compareTo(key) != 0) {
            throw new NoSuchElementException("the key is not exist!");
        }
        //删除要从底层开始删除
        node = getLowestDownNode(node);
        Node<K, V> prev = null;
        Node<K, V> next = null;
        for (; node != null; ) {
            prev = node.getPrevious();
            next = node.getNext();
            if (prev != null) {
                prev.setNext(next);
            }
            if (next != null) {
                next.setPrevious(prev);
            }
            node = node.getUp();
            node.setDown(null);
        }
        //如果当层只有一个，那么就删除当层；
        while (head.getNext() == null && head.getDown() != null) {
            head = head.getDown();
            head.setUp(null);
        }
        size--;
    }

    /**
     * 因为只有底层才有完整的数据，只需要遍历底层即可
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node<K, V> node = head;

        node = getLowestDownNode(node);

        node = getForemostNode(node);

        if (node.getNext() != null) {
            node = node.getNext();
        }

        while (node != null) {
            stringBuilder.append(node.toString()).append("\n");
            node = node.getNext();
        }
        return stringBuilder.toString();
    }

    //最前面的
    private Node<K, V> getForemostNode(Node<K, V> node) {
        while (node.getPrevious() != null) {
            node = node.getPrevious();
        }
        return node;
    }

    //最下面的
    private Node<K, V> getLowestDownNode(Node<K, V> node) {
        while (node.getDown() != null) {
            node = node.getDown();
        }
        return node;
    }

    public Iterator<K> iterator() {
        return new SkipListIterator<K, V>(head);
    }

    protected static class SkipListIterator<K extends Comparable<K>, V> implements Iterator<K> {
        private Node<K, V> node;

        public SkipListIterator(Node<K, V> node) {
            while (node.getDown() != null) {
                node = node.getDown();
            }
            while (node.getPrevious() != null) {
                node = node.getPrevious();
            }
            if (node.getNext() != null) {
                node = node.getNext();
            }
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return this.node != null;
        }

        @Override
        public K next() {
            K result = node.getKey();
            node = node.getNext();
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        SkipList<Integer, String> skipList = new SkipList();
        for (int i = 0; i < 10; i++) {
            skipList.add(i, String.valueOf(i));
        }
        System.out.println(skipList.head.level);
    }

}
