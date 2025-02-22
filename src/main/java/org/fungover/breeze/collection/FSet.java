package org.fungover.breeze.collection;

import java.util.Objects;

/**
 * FSet is an immutable set implementation based on a RedBlackTree.
 * It provides various set operations like add, remove, union, intersection, and symmetric difference.
 *
 * @param <T> The type of elements in the set, must be Comparable.
 */
public final class FSet<T extends Comparable<T>> implements SetProcedural<T> {

    private final RedBlackTree<T> tree;



    /**
     * Default constructor that initializes an empty RedBlackTree for the set.
     */
    public FSet() {
        this.tree = new RedBlackTree<>();
    }

    /**
     * Constructor that initializes the FSet with an existing RedBlackTree.
     *
     * @param tree The RedBlackTree to initialize the FSet with.
     */
    public FSet(RedBlackTree<T> tree) {
        this.tree = new RedBlackTree<>(tree);
    }


    /**
     * Adds a new element to the set, returning a new FSet with the added element.
     *
     * @param element The element to be added to the set.
     * @return A new FSet with the element added.
     * @throws NullPointerException if the element is null.
     */
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

    /**
     * Removes an element from the set, returning a new FSet with the element removed.
     *
     * @param element The element to be removed from the set.
     * @return A new FSet without the element.
     * @throws NullPointerException if the element is null.
     */
    @Override
    public FSet<T> remove(T element) {
        if (element == null) {
            throw new NullPointerException("Cannot remove null element from FSet");
        }

        RedBlackTree<T> newTree = new RedBlackTree<>();
        newTree.removalBySkip(this.tree, element);

        return new FSet<>(newTree);
    }

    /**
     * Checks if the set contains a specific element.
     *
     * @param element The element to check for in the set.
     * @return true if the element is in the set, false otherwise.
     */
    @Override
    public boolean contains(T element) {
        return this.tree.isNodeWithValueFound(element);
    }

    /**
     * Returns a new FSet that is the union of this FSet and another FSet.
     * The union contains all unique elements from both sets.
     *
     * @param other The other FSet to union with.
     * @return A new FSet containing the union of this FSet and the other FSet.
     */
    @Override
    public FSet<T> union(FSet<T> other) {

        RedBlackTree<T> newTree = new RedBlackTree<>();
        newTree.insertFromAnotherTree(this.tree);
        newTree.insertFromAnotherTree(other.tree);

        return new FSet<>(newTree);
    }


    /**
     * Returns a new FSet that is the intersection of this FSet and another FSet.
     * The intersection contains only the elements that are in both sets.
     *
     * @param other The other FSet to intersect with.
     * @return A new FSet containing the intersection of this FSet and the other FSet.
     */
    @Override
    public FSet<T> intersection(FSet<T> other) {
        RedBlackTree<T> newTree = new RedBlackTree<>();
        RedBlackTree<T> tmpTree = new RedBlackTree<>();
        tmpTree.intersectWithOtherTree(this, other.tree, newTree);
        return new FSet<>(newTree);


    }

    /**
     * Returns a new FSet that is the symmetric difference of this FSet and another FSet.
     * The symmetric difference contains all elements that are in either of the sets, exclusive elements from both.
     *
     * @param other The other FSet to compute the symmetric difference with.
     * @return A new FSet containing the symmetric difference of this FSet and the other FSet.
     */
    @Override
    public FSet<T> symmetricDifference(FSet<T> other) {
        RedBlackTree<T> newTree = new RedBlackTree<>();
        tree.symmetricDifferenceWithOtherTree(tree, other.tree, newTree);
        return new FSet<>(newTree);


    }

    /**
     * Checks if the FSet is empty (contains no elements).
     *
     * @return true if the FSet is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.tree.getSize() == 0 ;
    }

    /**
     * Prints the RedBlackTree structure of the FSet.
     */
    public void printTree(){
        tree.printRedBlackTree();
    }

    /**
     * Prints the elements of the FSet in order.
     */
    public void printInOrder(){
        tree.inorder();
    }

    /**
     * Returns the size of the FSet (number of elements).
     *
     * @return The number of elements in the FSet.
     */
    public int size() {
       return tree.getSize();
    }


    /**
     * Returns a string and prompts a representation of the FSet.
     *
     * Important values are prompted out.
     *
     * @return A string "Set And Done".
     */
    @Override
    public String toString() {
        return "FSet {"+ tree.stringSetBuilder()+" }";
    }

    /**
     * Checks if two FSets are equal. FSets are considered equal if they contain the same elements.
     *
     * @param o The object to compare this FSet with.
     * @return true if the two FSets are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FSet<?> fSet)) return false;
        return Objects.equals(tree, fSet.tree);
    }

    /**
     * Returns the hash code of the FSet.
     *
     * @return The hash code of the FSet.
     */
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
