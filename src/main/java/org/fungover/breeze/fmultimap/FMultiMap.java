package org.fungover.breeze.fmultimap;

import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class FMultiMap<K, V> {
    private final Map<K, Set<V>> map;
    private FMultiMap(Map<K, Set<V>> map) {
        this.map = map;
    }

    public static <K, V> FMultiMap<K, V> empty() {
        return new FMultiMap<>(new HashMap<>());
    }

    public FMultiMap<K, V> put(K key, V value) {
        Map<K, Set<V>> newMap = new HashMap<>(map);
        newMap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        return new FMultiMap<>(newMap);
    }

    public Set<V> get(K key) {
        return map.getOrDefault(key, new HashSet<>());
    }

    public int getKeyCount() {
        return map.size();
    }
}
