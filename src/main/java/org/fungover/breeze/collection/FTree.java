package org.fungover.breeze.collection;

import java.util.function.Function;

public interface FTree<T extends Comparable<T>> {

    T value();

    FTree<T> left();

    FTree<T> right();

    FTree<T> insert(T value);

    boolean contains(T value);

    <R extends Comparable<R>> FTree<R> map(Function<T, R> f);

    static <T extends Comparable<T>> FTree<T> empty() {
        return new EmptyTree<>();
    }
}

class EmptyTree<T extends Comparable<T>> implements FTree<T> {

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
    public <R extends Comparable<R>> FTree<R> map(Function<T, R> f) {
        return new EmptyTree<>();
    }
}

class NonEmptyTree<T extends Comparable<T>> implements FTree<T> {

    private final T value;
    private final FTree<T> left;
    private final FTree<T> right;


    public NonEmptyTree(T value, FTree<T> left, FTree<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }


    @Override
    public T value() {
        return value;
    }

    @Override
    public FTree<T> left() {
        return left;
    }

    @Override
    public FTree<T> right() {
        return right;
    }

    @Override
    public FTree<T> insert(T newValue) {
        if (newValue.compareTo(value) < 0) {
            return new NonEmptyTree<>(value, left.insert(newValue), right);
        } else {
            return new NonEmptyTree<>(value, left, right.insert(newValue));
        }
    }

    @Override
    public boolean contains(T searchValue) {
        int cmp = searchValue.compareTo(value);
        if (cmp == 0) return true;
        return (cmp < 0) ? left.contains(searchValue) : right.contains(searchValue);
    }

    @Override
    public <R extends Comparable<R>> FTree<R> map(Function<T, R> f) {
        return new NonEmptyTree<>(
                f.apply(value),
                left.map(f),
                right.map(f)
        );
    }
}



