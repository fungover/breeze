package org.fungover.breeze.collection;

import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FTree} interface represents a functional, immutable binary tree.
 * It provides operations to work with the tree structure in a persistent way.
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
     * Inserts a new value into the tree, returning a new tree with the value inserted.
     * The tree remains immutable.
     *
     * @param value the value to insert.
     * @return a new tree with the value inserted.
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
     * with the results of applying the function. The original tree structure is preserved.
     *
     * @param f   the function to apply to each value in the tree.
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
        return new EmptyTree<T>();
    }

    /**
     * Applies the given function to each value in the tree and rebuilds a new tree
     * using the natural ordering of the mapped values. This ensures that the BST invariant
     * holds even if the mapping function is not order preserving.
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
 * {@code EmptyTree} is an implementation of the {@code FTree} interface representing
 * an empty binary tree. It provides default behavior for an empty tree.
 *
 * @param <T> the type of elements stored in the tree.
 */
class EmptyTree<T extends Comparable<T>> implements FTree<T> {

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always thrown since the tree is empty.
     */
    @Override
    public T value() {
        throw new UnsupportedOperationException("Empty tree has no value");
    }

    /**
     * Returns the left subtree, which is the empty tree itself.
     *
     * @return this empty tree.
     */
    @Override
    public FTree<T> left() {
        return this;
    }

    /**
     * Returns the right subtree, which is the empty tree itself.
     *
     * @return this empty tree.
     */
    @Override
    public FTree<T> right() {
        return this;
    }

    /**
     * Inserts a new value into the empty tree, resulting in a new non-empty tree.
     *
     * @param value the value to insert.
     * @return a new {@link NonEmptyTree} with the given value.
     */
    @Override
    public FTree<T> insert(T value) {
        return new NonEmptyTree<>(value, this, this);
    }

    /**
     * Always returns {@code false} since an empty tree does not contain any values.
     *
     * @param value the value to search for.
     * @return {@code false}.
     */
    @Override
    public boolean contains(T value) {
        return false;
    }

    /**
     * Applies the mapping function to an empty tree, resulting in another empty tree.
     *
     * @param f   the function to apply to each value.
     * @param <R> the type of the values in the new tree.
     * @return an empty tree.
     */
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
     * Inserts a new value into the tree. The new value is placed in the left subtree if it is
     * less than the current node's value; otherwise, it is placed in the right subtree.
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
        return new NonEmptyTree<>(
                f.apply(value),
                left.map(f),
                right.map(f)
        );
    }
}
