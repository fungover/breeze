package org.fungover.breeze.collection;

/**
 * Node class that represent a datapoint for tree like data structure.
 * @param <T> represents any valid data type.
 */
public class Node <T> {

    /**
     * value stored in node with any data type
     */
    T value;

    /**
     * Represents the left child of the node
     */
    Node<T> left;

    /**
     * Represents the right child of the node
     */
    Node<T> right;

    /**
     * Represents the parent node of current node
     */
    Node<T> parent;

    /**
     * Represents the color red and black as boolean value: false (red), true (black)
     */
    boolean color;

    /**
     * Constructor to create instance of node
     * @param value stored in node
     */
    public Node(T value) {
        /**
         * value stored in node
         */
        this.value = value;

        /**
         * color represented as red with false
         */
        this.color = false;
    }


    /**
     * Getter method for value field of Node.
     * @return the stored value in Node.
     */
    public T getValue() {
        return value;
    }

}
