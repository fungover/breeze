package org.fungover.breeze.fmultimap;

import java.util.HashMap;
import java.util.Map;

public class FMultiMap<K, V> {
    private final Map<K, FSet<V>> map;

    private FMultiMap(Map<K, FSet<V>> map) {
        this.map = new HashMap<>(map);
    }

    public static <K, V> FMultiMap<K, V> empty() {
        return new FMultiMap<>(new HashMap<>());
    }

    public FMultiMap<K, V> put(K key, V value) {
        FSet<V> currentValues = map.getOrDefault(key, FSet.empty());
        FSet<V> newValues = currentValues.add(value);

        Map<K, FSet<V>> newMap = new HashMap<>(map);
        newMap.put(key, newValues);

        return new FMultiMap<>(newMap);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public boolean containsEntry(K key, V value) {
        return map.containsKey(key) && map.get(key).contains(value);
    }

    public FSet<V> get(K key) {
        return map.getOrDefault(key, FSet.empty());
    }

    public int getKeyCount() {
        return map.size();
    }
}
