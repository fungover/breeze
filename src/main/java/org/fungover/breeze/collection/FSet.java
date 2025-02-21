package org.fungover.breeze.collection;

import java.util.Objects;

public final class FSet<T extends Comparable<T>> implements SetProcedural<T> {
    private final RedBlackTree<T> tree;



    public FSet() {
        this.tree = new RedBlackTree<>();
    }

    // Constructor for initializing with a RedBlackTree
    public FSet(RedBlackTree<T> tree) {
        this.tree = tree;
    }


    @Override
    public FSet<T> add(T element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to FSet");
        }

        RedBlackTree<T> newTree = new RedBlackTree<>();
        newTree.insertFromAnotherTree(this.tree);
        newTree.insert(element);

        return new FSet<>(newTree);
    }

    @Override
    public FSet<T> remove(T element) {
        if (element == null) {
            throw new NullPointerException("Cannot remove null element from FSet");
        }

        RedBlackTree<T> newTree = new RedBlackTree<>();
        newTree.removalBySkip(this.tree, element);

        return new FSet<>(newTree);
    }

    @Override
    public boolean contains(T element) {
        return this.tree.isNodeWithValueFound(element);
    }

    @Override
    public FSet<T> union(FSet<T> other) {

        RedBlackTree<T> newTree = new RedBlackTree<>();
        newTree.insertFromAnotherTree(this.tree);
        newTree.insertFromAnotherTree(other.tree);

        return new FSet<>(newTree);
    }


    @Override
    public FSet<T> intersection(FSet<T> other) {
        RedBlackTree<T> newTree = new RedBlackTree<>();
        RedBlackTree<T> tmpTree = new RedBlackTree<>();
        tmpTree.intersectWithOtherTree(this, other.tree, newTree);
        return new FSet<>(newTree);


    }

    @Override
    public FSet<T> symmetricDifference(FSet<T> other) {
        RedBlackTree<T> newTree = new RedBlackTree<>();
        RedBlackTree<T> tmpTree = new RedBlackTree<>();
        tmpTree.symmetricDifferenceWithOtherTree(this, other.tree, newTree);
        tmpTree.symmetricDifferenceWithOtherTree(other, this.tree, newTree);
        return new FSet<>(newTree);


    }

    @Override
    public boolean isEmpty() {
        return this.tree.getSize() == 0 ;
    }

    public void printSet(){
        tree.printRedBlackTree();
    }

    public void printInOrder(){
        tree.inorder();
    }

    public int size() {
       return tree.getSize();
    }


    @Override
    public String toString() {
        tree.printStandard();
        return "\nSet And Done";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FSet<?> fSet)) return false;
        return Objects.equals(tree, fSet.tree);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tree);
    }
}

/**
 * Interface class with methods for immutable Set called FSet.
 *
 * @param <T> placeholder for any datatype.
 */

 sealed interface SetProcedural<T extends Comparable<T>> permits FSet {

        /**
         * Add new element to immutable FSet.
         *
         * @param element to be added to immutable FSet.
         * @return A new immutable set object of type FSet.
         */
        FSet<T> add(T element);

        /**
         * Removes an element from FSet
         *
         * @param element to be removed
         * @return A new immutable FSet object without element.
         */
        FSet<T> remove(T element);

        /**
         * Check if element is present in FSet Object.
         *
         * @param element target to be found in FSet.
         * @return true if element present in FSet otherwise false.
         */
        boolean contains(T element);

        /**
         * Adds all unique elements from two FSets to a new FSet object
         *
         * @param other FSet with elements to be added.
         * @return A new FSet with the combine elements of FSet and other
         */
        FSet<T> union(FSet<T> other);

        /**
         * Adds all unique elements that are present in both FSets.
         *
         * @param other FSet with elements to be added
         * @return A new FSet object with the values present in both FSet and other.
         */
        FSet<T> intersection(FSet<T> other);

        /**
         * Adds all unique elements that are not present between two FSets.
         *
         * @param other FSet with elements to be excluded
         * @return A new FSet object with the symmetric difference between FSet and other.
         */
        FSet<T> symmetricDifference(FSet<T> other);

        /**
         * Check if FSet object is empty or without any elements.
         * FSet objects instantiated with null will be false
         *
         * @return A true if FSet only contains null or empty otherwise false.
         */
        boolean isEmpty();


}
