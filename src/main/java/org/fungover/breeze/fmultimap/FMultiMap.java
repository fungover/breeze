package org.fungover.breeze.fmultimap;

import java.util.*;

/**
 * A generic, immutable multimap implementation that allows storing multiple values for a single key.
 * It supports key-value associations where each key can map to multiple values, using a custom {@code FSet} collection.
 *
 * @param <K> the type of keys maintained by this multimap
 * @param <V> the type of values associated with keys in this multimap
 */
public class FMultiMap<K, V> {
    private final Map<K, FSet<V>> map;

    /**
     * Constructs an empty {@code FMultiMap}.
     */
    FMultiMap() {
        this.map = new HashMap<>();
    }

    /**
     * Constructs an {@code FMultiMap} with the given backing map.
     *
     * @param map the map to be used as the backing map for this {@code FMultiMap}
     */
    private FMultiMap(Map<K, FSet<V>> map) {
        this.map = map;
    }

    /**
     * Returns a new empty {@code FMultiMap}.
     *
     * @param <K> the type of keys
     * @param <V> the type of values
     * @return a new empty {@code FMultiMap}
     */
    public static <K, V> FMultiMap<K, V> empty() {
        return new FMultiMap<>();
    }

    /**
     * Adds the given key-value pair to the multimap. If the key already exists,
     * the value will be added to the set of values associated with that key.
     *
     * Returns a new {@code FMultiMap} instance with the updated mapping.
     *
     * @param key   the key to be added
     * @param value the value to be added
     * @return a new {@code FMultiMap} containing the updated key-value mapping
     * @throws IllegalArgumentException if either the key or the value is {@code null}
     */
    public FMultiMap<K, V> put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        Map<K, FSet<V>> newMap = new HashMap<>(map);
        FSet<V> currentSet = newMap.getOrDefault(key, FSet.empty());
        newMap.put(key, currentSet.add(value));
        return new FMultiMap<>(newMap);
    }

    /**
     * Removes the given key-value pair from the multimap.
     *
     * If the value is the last one associated with the key, the key will be removed as well.
     * Returns a new {@code FMultiMap} instance with the updated mapping.
     *
     * @param key   the key whose mapping is to be removed
     * @param value the value to be removed from the set associated with the key
     * @return a new {@code FMultiMap} without the specified key-value pair
     * @throws IllegalArgumentException if either the key or the value is {@code null}
     */
    public FMultiMap<K, V> remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

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

    /**
     * Returns the set of values associated with the specified key.
     * If the key is not present, returns an empty set.
     *
     * @param key the key whose associated values are to be returned
     * @return a set of values associated with the specified key, or an empty set if the key is not present
     * @throws IllegalArgumentException if the key is {@code null}
     */
    public Set<V> get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return map.getOrDefault(key, FSet.<V>empty()).toSet();
    }

    /**
     * Checks if the multimap contains the specified key.
     *
     * @param key the key whose presence in the multimap is to be tested
     * @return {@code true} if the multimap contains the specified key, otherwise {@code false}
     * @throws IllegalArgumentException if the key is {@code null}
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return map.containsKey(key);
    }

    /**
     * Checks if the multimap contains the specified key-value pair.
     *
     * @param key   the key to check for
     * @param value the value associated with the key to check for
     * @return {@code true} if the multimap contains the key-value pair, otherwise {@code false}
     * @throws IllegalArgumentException if either the key or the value is {@code null}
     */
    public boolean containsEntry(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        return map.containsKey(key) && map.get(key).contains(value);
    }

    /**
     * Returns the number of keys in this multimap.
     *
     * @return the number of keys in this multimap
     */
    public int getKeyCount() {
        return map.size();
    }

    /**
     * Compares this multimap with another object for equality.
     * Two {@code FMultiMap} objects are considered equal if they have the same mappings.
     *
     * @param o the object to be compared with this multimap
     * @return {@code true} if the specified object is equal to this multimap, otherwise {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FMultiMap<?, ?> that = (FMultiMap<?, ?>) o;
        return map.equals(that.map);
    }

    /**
     * Returns the hash code value for this multimap.
     *
     * @return the hash code value for this multimap
     */
    @Override
    public int hashCode() {
        return map.entrySet().stream()
                .mapToInt(entry -> Objects.hash(entry.getKey(), entry.getValue()))
                .sum();
    }

    /**
     * Returns a string representation of this multimap. The string representation consists of the map's entries.
     *
     * @return a string representation of the multimap
     */
    @Override
    public String toString() {
        return map.toString();
    }
}

class FSet<T> {
    private final Set<T> set;

    private FSet() {
        this.set = Collections.emptySet();
    }

    private FSet(Set<T> set) {
        this.set = Collections.unmodifiableSet(set);
    }

    public static <T> FSet<T> empty() {
        return new FSet<>();
    }

    public FSet<T> add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }

        Set<T> newSet = new HashSet<>(set);
        newSet.add(element);
        return new FSet<>(newSet);
    }

    public FSet<T> remove(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }

        if (!set.contains(element)) {
            return this;
        }

        Set<T> newSet = new HashSet<>(set);
        newSet.remove(element);
        return new FSet<>(newSet);
    }

    public boolean contains(T element) {
        return set.contains(element);
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public Set<T> toSet() {
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FSet<?> fSet = (FSet<?>) o;
        return set.equals(fSet.set);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public String toString() {
        return set.toString();
    }
}
