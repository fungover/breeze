package org.fungover.breeze.fmultimap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FMultiMap<K, V> {
    private final Map<K, FSet<V>> map;

    private FMultiMap() {
        this.map = new HashMap<>();
    }

    private FMultiMap(Map<K, FSet<V>> map) {
        this.map = map;
    }

    public static <K, V> FMultiMap<K, V> empty() {
        return new FMultiMap<>();
    }

    public FMultiMap<K, V> put(K key, V value) {
        Map<K, FSet<V>> newMap = new HashMap<>(map);
        FSet<V> currentSet = newMap.getOrDefault(key, FSet.empty());
        newMap.put(key, currentSet.add(value));
        return new FMultiMap<>(newMap);
    }

    public FMultiMap<K, V> remove(K key, V value) {
        Map<K, FSet<V>> newMap = new HashMap<>(map);
        FSet<V> currentSet = newMap.get(key);
        if (currentSet == null || !currentSet.contains(value)) {
            return this;
        }

        FSet<V> updatedSet = currentSet.remove(value);
        if (updatedSet.isEmpty()) {
            newMap.remove(key);
        } else {
            newMap.put(key, updatedSet);
        }

        return new FMultiMap<>(newMap);
    }

    public Optional<FSet<V>> get(K key) {
        return Optional.ofNullable(map.get(key));
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public boolean containsEntry(K key, V value) {
        return map.containsKey(key) && map.get(key).contains(value);
    }

    public int getKeyCount() {
        return map.size();
    }
}
