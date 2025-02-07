package org.fungover.breeze.fmultimap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FSet<V> {
    private final Set<V> set;

    private FSet(Set<V> set) {
        this.set = Collections.unmodifiableSet(set);
    }

    public static <V> FSet<V> empty() {
        return new FSet<>(new HashSet<>());
    }

    public FSet<V> add(V value) {
        Set<V> newSet = new HashSet<>(set);
        newSet.add(value);
        return new FSet<>(newSet);
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public boolean contains(V value) {
        return set.contains(value);
    }

    @Override
    public String toString() {
        return set.toString();
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
}
