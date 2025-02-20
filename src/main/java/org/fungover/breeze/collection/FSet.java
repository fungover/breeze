package org.fungover.breeze.collection;

public final class FSet<T extends Comparable<T>> implements SetProcedural<T> {
    private final RedBlackTree<T> tree;



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
    public FSet remove(T element) {
        return null;
    }

    @Override
    public boolean contains(T element) {
        return false;
    }

    @Override
    public FSet union(T other) {
        return null;
    }

    @Override
    public FSet intersection(T other) {
        return null;
    }

    @Override
    public FSet difference(T other) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

/**
 * Interface class with methods for immutable Set called FSet.
 *
 * @param <T> placeholder for any datatype.
 */

 sealed interface SetProcedural<T> permits FSet {

        /**
         * Add new element to immutable FSet.
         *
         * @param element to be added to immutable FSet.
         * @return A new immutable set object of type FSet.
         */
        FSet add(T element);

        /**
         * Removes an element from FSet
         *
         * @param element to be removed
         * @return A new immutable FSet object without element.
         */
        FSet remove(T element);

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
        FSet union(T other);

        /**
         * Adds all unique elements that are present in both FSets.
         *
         * @param other FSet with elements to be added
         * @return A new FSet object with the values present in both FSet and other.
         */
        FSet intersection(T other);

        /**
         * Adds all unique elements that are not present between two FSets.
         *
         * @param other FSet with elements to be excluded
         * @return A new FSet object with the symmetric difference between FSet and other.
         */
        FSet difference(T other);

        /**
         * Check if FSet object is empty or without any elements.
         * FSet objects instantiated with null will be false
         *
         * @return A true if FSet only contains null or empty otherwise false.
         */
        boolean isEmpty();


}
