package org.fungover.breeze.collection;

import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FTree} interface represents a functional, immutable binary search tree (BST).
 * It supports insertion, searching, and transformation of elements in a persistent manner.
 *
 * @param <T> the type of elements stored in the tree, which must implement {@link Comparable}.
 */
public interface FTree<T extends Comparable<T>> {

    /**
     * Returns the value stored at the current node.
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
     * Inserts a new value into the tree while maintaining the BST ordering.
     * This operation does not modify the existing tree but returns a new one.
     *
     * @param value the value to insert.
     * @return a new tree with the value inserted.
     */
    FTree<T> insert(T value);

    /**
     * Checks whether the tree contains the specified value.
     *
     * @param value the value to search for.
     * @return {@code true} if the tree contains the value, {@code false} otherwise.
     */
    boolean contains(T value);

    /**
     * Applies the given function to each value in the tree and returns a new tree
     * with the transformed values. The structure of the tree is preserved.
     *
     * @param f   the function to apply to each value.
     * @param <R> the type of the values in the new tree.
     * @return a new tree with the function applied to each value.
     */
    <R extends Comparable<R>> FTree<R> map(Function<T, R> f);

    /**
     * Returns a singleton instance of an empty tree.
     *
     * @param <T> the type of elements in the tree.
     * @return a singleton instance of an empty tree.
     */
    static <T extends Comparable<T>> FTree<T> empty() {
        return EmptyTree.getInstance();
    }

    /**
     * Applies the given function to each value in the tree and rebuilds a new tree
     * using the natural ordering of the mapped values. This ensures that the BST invariant
     * holds even if the mapping function is not order-preserving.
     *
     * @param f   the function to apply to each value.
     * @param <R> the type of the mapped values.
     * @return a new tree built from the mapped values.
     */
    default <R extends Comparable<R>> FTree<R> mapAndRebuild(Function<T, R> f) {
        List<R> mappedValues = new ArrayList<>();
        inOrderTraversal(this, f, mappedValues);
        FTree<R> newTree = FTree.empty();
        for (R value : mappedValues) {
            newTree = newTree.insert(value);
        }
        return newTree;
    }

    /**
     * Performs an in-order traversal of the tree, applying the given function to each node's value,
     * and collects the results in the provided list.
     *
     * @param tree the tree to traverse.
     * @param f    the function to apply to each node's value.
     * @param list the list to collect the mapped values.
     * @param <T>  the type of the elements in the original tree.
     * @param <R>  the type of the mapped values.
     */
    static <T extends Comparable<T>, R extends Comparable<R>> void inOrderTraversal(
            FTree<T> tree, Function<T, R> f, List<R> list) {
        if (tree instanceof EmptyTree) {
            return;
        }
        inOrderTraversal(tree.left(), f, list);
        list.add(f.apply(tree.value()));
        inOrderTraversal(tree.right(), f, list);
    }
}

/**
 * Singleton implementation of {@code FTree} representing an empty binary tree.
 *
 * @param <T> the type of elements stored in the tree.
 */
class EmptyTree<T extends Comparable<T>> implements FTree<T> {

    /**
     * Singleton instance of EmptyTree.
     */
    private static final EmptyTree<?> INSTANCE = new EmptyTree<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private EmptyTree() {
    }

    /**
     * Returns the singleton instance of {@code EmptyTree}.
     *
     * @param <T> the type of elements in the tree.
     * @return the singleton instance of {@code EmptyTree}.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> EmptyTree<T> getInstance() {
        return (EmptyTree<T>) INSTANCE;
    }

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
        return getInstance();
    }
}

/**
 * Immutable implementation of {@code FTree} representing a non-empty binary tree node.
 *
 * @param <T> the type of elements stored in the tree, which must implement {@link Comparable}.
 */
record NonEmptyTree<T extends Comparable<T>>(T value, FTree<T> left, FTree<T> right) implements FTree<T> {

    /**
     * Inserts a new value into the tree while maintaining the BST ordering.
     * This operation does not modify the existing tree but returns a new one.
     *
     * @param newValue the value to insert.
     * @return a new tree with the value inserted.
     */
    @Override
    public FTree<T> insert(T newValue) {
        if (newValue.compareTo(value) < 0) {
            return new NonEmptyTree<>(value, left.insert(newValue), right);
        } else if (newValue.compareTo(value) > 0) {
            return new NonEmptyTree<>(value, left, right.insert(newValue));
        } else {
            // Choose left or right based on tree balance, or
            // document that duplicates go to right subtree
            return new NonEmptyTree<>(value, left, right.insert(newValue));
        }
    }

    /**
     * Checks whether the tree contains the specified value.
     *
     * @param searchValue the value to search for.
     * @return {@code true} if the value is found in the tree, {@code false} otherwise.
     */
    @Override
    public boolean contains(T searchValue) {
        int cmp = searchValue.compareTo(value);
        if (cmp == 0) return true;
        return (cmp < 0) ? left.contains(searchValue) : right.contains(searchValue);
    }

    /**
     * Applies the given function to each value in the tree, preserving the original tree structure,
     * and returns a new tree with the transformed values.
     *
     * @param f   the function to apply to each value.
     * @param <R> the type of the values in the new tree.
     * @return a new tree with the function applied to each value.
     */
    @Override
    public <R extends Comparable<R>> FTree<R> map(Function<T, R> f) {
        return new NonEmptyTree<>(f.apply(value), left.map(f), right.map(f));
    }
}
