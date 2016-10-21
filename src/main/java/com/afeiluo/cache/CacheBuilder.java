package com.afeiluo.cache;

import java.util.concurrent.TimeUnit;

public class CacheBuilder<K, V> {

    private Long maxSize;
    private Long expireAfterWriteNanos;

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<Object, Object>();
    }

    CacheBuilder() {
    }

    public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
        return new Cache<K1, V1>(this);
    }

    public CacheBuilder<K, V> maxSize(long maxsize) {
        this.maxSize = maxsize;
        return this;
    }

    public CacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit) {
        this.expireAfterWriteNanos = unit.toNanos(duration);
        return this;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public Long getExpireAfterWriteNanos() {
        return expireAfterWriteNanos;
    }
}
