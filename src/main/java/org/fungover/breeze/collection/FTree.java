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

class EmptyTree<T> implements FTree<T> {

    @Override

    public T value() {

        throw new UnsupportedOperationException("Empty tree has no value");

    }

    @Override
    public FTree<T> left() {
        return this;
    }

    @Override
    public FTree<T> right() {
        return this;
    }

    @Override
    public FTree<T> insert(T value) {
        return new NonEmptyTree<>(value, this, this);
    }

    @Override
    public boolean contains(T value) {
        return false;
    }

    @Override
    public <R> FTree<R> map(Function<T, R> f) {
        return new EmptyTree<>();
    }
}


