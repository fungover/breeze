package org.fungover.breeze.collection;

import java.util.function.Function;

/**
 * The {@code FTree} interface represents a functional, immutable binary tree.
 * It provides operations to work with the tree structure in a persistent way.
 *
 * @param <T> the type of elements stored in the tree, which must implement {@link Comparable}.
 */

public interface FTree<T extends Comparable<T>> {

    /**
     * Returns the value stored at the node.
     *
     * @return the value at the current node.
     * @throws UnsupportedOperationException if the tree is empty.
     */
    T value();

    /**
     * Returns the left subtree of the current node.
     *
     * @return the left subtree.
     */
    FTree<T> left();

    /**
     * Returns the right subtree of the current node.
     *
     * @return the right subtree.
     */
    FTree<T> right();

    /**
     * Inserts a new value into the tree, returning a new tree with the value inserted.
     * The tree structure remains immutable.
     *
     * @param value the value to insert.
     * @return a new tree with the inserted value.
     */
    FTree<T> insert(T value);

    /**
     * Checks if the tree contains the specified value.
     *
     * @param value the value to search for.
     * @return {@code true} if the tree contains the value, {@code false} otherwise.
     */
    boolean contains(T value);

    /**
     * Applies the given function to each value in the tree and returns a new tree
     * with the results of applying the function.
     *
     * @param f the function to apply to each value in the tree.
     * @param <R> the type of the values in the new tree.
     * @return a new tree with the function applied to all values.
     */
    <R extends Comparable<R>> FTree<R> map(Function<T, R> f);

    /**
     * Returns an empty tree.
     *
     * @param <T> the type of elements in the tree.
     * @return an empty tree.
     */
    static <T extends Comparable<T>> FTree<T> empty() {
        return new EmptyTree<>();
    }
}

/**
 * {@code EmptyTree} is an implementation of the {@code FTree} interface representing
 * an empty binary tree. It provides default behavior for the empty tree.
 *
 * @param <T> the type of elements stored in the tree.
 */
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

/**
 * {@code NonEmptyTree} is an implementation of the {@code FTree} interface representing
 * a non-empty binary tree. It contains a value, a left subtree, and a right subtree.
 *
 * @param <T> the type of elements stored in the tree, which must implement {@link Comparable}.
 */
record NonEmptyTree<T extends Comparable<T>>(T value, FTree<T> left, FTree<T> right) implements FTree<T> {

    /**
     * Inserts a new value into the tree and returns a new tree with the inserted value.
     * If the new value is less than the current node's value, it will be inserted into
     * the left subtree, otherwise into the right subtree.
     *
     * @param newValue the value to insert.
     * @return a new tree with the value inserted.
     */
    @Override
    public FTree<T> insert(T newValue) {
        if (newValue.compareTo(value) < 0) {
            return new NonEmptyTree<>(value, left.insert(newValue), right);
        } else {
            return new NonEmptyTree<>(value, left, right.insert(newValue));
        }
    }

    /**
     * Checks if the tree contains the specified value.
     * If the value is found at the current node, it returns {@code true}.
     * Otherwise, it checks the left or right subtree based on the comparison.
     *
     * @param searchValue the value to search for.
     * @return {@code true} if the value is found, {@code false} otherwise.
     */
    @Override
    public boolean contains(T searchValue) {
        int cmp = searchValue.compareTo(value);
        if (cmp == 0) return true;
        return (cmp < 0) ? left.contains(searchValue) : right.contains(searchValue);
    }

    /**
     * Maps the function over the current tree, returning a new tree with the transformed values.
     *
     * @param f the function to apply to each value in the tree.
     * @param <R> the type of the new tree's values.
     * @return a new tree with the function applied to all values.
     */
    @Override
    public <R extends Comparable<R>> FTree<R> map(Function<T, R> f) {
        return new NonEmptyTree<>(
                f.apply(value),
                left.map(f),
                right.map(f)
        );
    }
}



