package com.afeiluo.cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Cache<K, V> {

    private LinkedHashMap<K, V> innerMap;
    private int maxSize;
    private long expireTime;
    private final Map<K, ExpiringKey<K>> expiringKeys = new HashMap<K, ExpiringKey<K>>();;
    private final DelayQueue<ExpiringKey> delayQueue = new DelayQueue<ExpiringKey>();

    public <K, V> Cache(CacheBuilder<K, V> builder) {
        innerMap = new LinkedHashMap<>(builder.getMaxSize().intValue(), 0.75f);
        maxSize = builder.getMaxSize().intValue();
        expireTime = builder.getExpireAfterWriteNanos();
    }

    public V get(K k) {
        cleanup();
        return innerMap.get((K) k);
    }

    public V set(K k, V v) {
        // 检查大小
        cleanup();
        ExpiringKey<K> delayedKey = new ExpiringKey<K>(k, expireTime);
        ExpiringKey<K> oldKey = expiringKeys.put(k, delayedKey);
        if (oldKey != null) {// 之前的key还没有过期
            expireKey(oldKey);
            expiringKeys.put(k, delayedKey);
        }
        delayQueue.offer(delayedKey);
        return innerMap.put(k, v);
    }

    private void expireKey(ExpiringKey<K> delayedKey) {
        if (delayedKey != null) {
            delayedKey.expire();
            cleanup();
        }
    }

    public void clear() {
        delayQueue.clear();
        expiringKeys.clear();
        innerMap.clear();
    }

    /**
     * 清除掉过期的key
     */
    private void cleanup() {
        ExpiringKey<K> delayedKey = delayQueue.poll();
        while (delayedKey != null) {
            innerMap.remove(delayedKey.getKey());
            expiringKeys.remove(delayedKey.getKey());
            delayedKey = delayQueue.poll();
        }
        checkState(innerMap.size() < maxSize, "over max cache size");
    }

    public static void checkState(boolean expression, String errorMessageTemplate) {
        if (!expression) {
            throw new IllegalStateException(errorMessageTemplate);
        }
    }

    public static void main(String[] args) throws Exception {
        Cache<String, String> cache = CacheBuilder.newBuilder().maxSize(10).expireAfterWrite(1, TimeUnit.SECONDS).build();
        cache.set("hello", "world");
        Thread.sleep(800);
        System.out.println("Hello:" + cache.get("hello"));
        Thread.sleep(300);
        System.out.println("Hello:" + cache.get("hello"));
        for (int i = 0; i < 11; i++) {
            cache.set("key" + i, i + "");
        }
    }
}

class ExpiringKey<K> implements Delayed {

    private long startTime = System.nanoTime();
    private final long maxLifeTimeNanos;
    private final K key;

    public ExpiringKey(K key, long maxLifeTimeNanos) {
        this.maxLifeTimeNanos = maxLifeTimeNanos;
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExpiringKey<K> other = (ExpiringKey<K>) obj;
        if (this.key != other.key && (this.key == null || !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.key != null ? this.key.hashCode() : 0);
        return hash;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(getDelayNanos(), TimeUnit.NANOSECONDS);
    }

    private long getDelayNanos() {
        return (startTime + maxLifeTimeNanos) - System.nanoTime();
    }

    public void renew() {
        startTime = System.nanoTime();
    }

    public void expire() {
        startTime = System.nanoTime() - maxLifeTimeNanos - 1;
    }

    @Override
    public int compareTo(Delayed that) {
        return Long.compare(this.getDelayNanos(), ((ExpiringKey) that).getDelayNanos());
    }
}
