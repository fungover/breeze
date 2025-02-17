package org.fungover.breeze.collection;

import java.util.function.Function;

public interface FTree<T> {

    T value();

    FTree<T> left();

    FTree<T> right();

    FTree<T> insert(T value);

    boolean contains(T value);

    <R> FTree<R> map(Function<T, R> f);

    static <T> FTree<T> empty() {
        return new EmptyTree<>();

    }
}
