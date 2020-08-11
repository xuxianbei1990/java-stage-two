package optimization.chapter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 合理的缓存失效算法 FIFO LRU策略
 */

class ObjectCachePool<K, V> {
    public static final int FIFO_POLICY = 1;
    public static final int LRU_POLICY = 2;
    private static final int DEFAULT_SIZE = 10;
    private Map<K, V> cacheObjects;

    public ObjectCachePool() {
        this(DEFAULT_SIZE);
    }

    public ObjectCachePool(final int size) {
        this(size, FIFO_POLICY);
    }

    public ObjectCachePool(final int size, final int policy) {
        switch (policy) {
            case FIFO_POLICY:
                cacheObjects = new LinkedHashMap<K, V>(size) {
                    private static final long serialVersionUID = 1L;

                    // LinkedHashMap有一个removeEldestEntry(Map.Entry eldest)方法，
                    // 通过覆盖这个方法，加入一定的条件，满足条件返回true。当put进新的值方法返回true时，
                    // 便移除该map中最老的键和值。
                    @Override
                    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                        return size() > size;
                    }
                };
                break;
            case LRU_POLICY: {
                cacheObjects = new LinkedHashMap<K, V>(size, 0.75f, true) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                        return size() > size;
                    }
                };
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown policy: " + policy);
        }
    }

    public void put(K key, V value) {
        cacheObjects.put(key, value);
    }

    public V get(K key) {
        return cacheObjects.remove(key);
    }

    public void remove(K key) {
        cacheObjects.remove(key);
    }

    public void clear() {
        cacheObjects.clear();
    }
}

/**
 * 分布式231
 */
public class ObjectPoolDemo {

    private static int executeTimes = 10;
    private static int maxFactor = 10;
    private static int threadCount = 100;
    private static final int NOTUSE_OBJECTPOOL = 1;
    private static final int USE_OBJECTPOOL = 2;
    private static int runMode = NOTUSE_OBJECTPOOL;
    private static CountDownLatch latch = null;

    /**
     * -Xms128M -Xmx128M -Xmn64M
     */
    public static void main(String[] args) throws Exception {
        Thread.sleep(20000);
        switch (args.length) {
            case 1:
                runMode = Integer.parseInt(args[0]);
                break;
            case 2: {
                runMode = Integer.parseInt(args[0]);
                executeTimes = Integer.parseInt(args[1]);
                break;
            }
            case 3: {
                runMode = Integer.parseInt(args[0]);
                executeTimes = Integer.parseInt(args[1]);
                maxFactor = Integer.parseInt(args[2]);
                break;
            }
            case 4: {
                runMode = Integer.parseInt(args[0]);
                executeTimes = Integer.parseInt(args[1]);
                maxFactor = Integer.parseInt(args[2]);
                threadCount = Integer.parseInt(args[3]);
                break;
            }
        }
        long beginTime = System.currentTimeMillis();
        Task task = new Task();
        for (int i = 0; i < executeTimes; i++) {
            System.out.println("Round: " + (i + 1));
            latch = new CountDownLatch(threadCount);
            for (int j = 0; j < threadCount; j++) {
                new Thread(task).start();
            }
            latch.await();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execute summary: Round(" + executeTimes + ")" + "Thread PerRound( " + threadCount + " ) Object Factor( "
                + maxFactor + ") Execute Time (" + (endTime - beginTime) + "ms)");

    }

    static class Task implements Runnable {

        @Override
        public void run() {
            /*
             * 创建大量临时对象会导致频繁的GC。所以用map保存起来提升性能
             */
            for (int j = 0; j < maxFactor; j++) {
                if (runMode == USE_OBJECTPOOL) {
                    BigObjectPool.getInstance().getBigObject(j);
                } else {
                    new BigObject(j);
                }
            }
            latch.countDown();
        }
    }

    static class BigObjectPool {

        private static final BigObjectPool self = new BigObjectPool();

        private final Map<Integer, BigObject> cacheObjects = new HashMap<Integer, BigObject>();

        public static BigObjectPool getInstance() {
            return self;
        }

        public BigObject getBigObject(int factor) {
            if (cacheObjects.containsKey(factor)) {
                return cacheObjects.get(factor);
            } else {
                BigObject object = new BigObject(factor);
                cacheObjects.put(factor, object);
                return object;
            }
        }
    }

    static class BigObject {
        private byte[] bytes = null;

        public BigObject(int factor) {
            bytes = new byte[(factor + 1) * 1024 * 1024];
        }

        public byte[] getBytes() {
            return bytes;
        }
    }
}
