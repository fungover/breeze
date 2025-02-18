package org.fungover.breeze.cache;

import java.security.Key;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCache<K, V> {
    private final ConcurrentHashMap<K, V> cache;

    public ConcurrentCache(ConcurrentHashMap<K, V> cache) {
        this.cache = cache;
    }

    public V get (K key) {
        return cache.get(key);
    }

    public void put (K key, V value) {
        cache.put(key, value);
    }

    public void remove (K key) {
        cache.remove(key);
    }


}
