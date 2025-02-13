package org.fungover.breeze.fmultimap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FSet<T> {
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
        Set<T> newSet = new HashSet<>(set);
        newSet.add(element);
        return new FSet<>(newSet);
    }

    public FSet<T> remove(T element) {
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
}
