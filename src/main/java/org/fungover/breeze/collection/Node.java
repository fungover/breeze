package org.fungover.breeze.collection;

import java.util.Objects;

/**
 * Node class that represent a datapoint for tree like data structure.
 */
class Node<T> {

    /**
     * value stored in node with any data type
     */
    private HashCodeWrapper<T> value;

    /**
     * Represents the left child of the node
     */
    Node <T> left;

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
     *
     * @param value stored in node
     */


    public Node( T value) {
          /*
          value stored in node
         */

        this.value = new HashCodeWrapper<>(value);

        /*
          color represented as red with false
         */
        this.color = false;
    }

    public Node() {
        this.value = new HashCodeWrapper<>(null);
        this.color = false;
    }

    /**
     * Getter method for value field of Node.
     *
     * @return the stored value in Node.
     */
    public T getValue() {

        return this.value.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node <?> node)) return false;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    static final class HashCodeWrapper<T> implements Comparable<HashCodeWrapper<T>> {

        private final Comparable<T> value;
        private final int hashCode;


        public HashCodeWrapper(T value) {
            this.value = (Comparable<T>) value;
            this.hashCode = value != null ? value.hashCode() : 0;
        }



        @Override
        public int compareTo(HashCodeWrapper<T> o) {

            if (o.value == null && this.value == null)
                return 0;

            if (this.value == null) {
                return -1;  // NullObject is always considered smaller than any other value
            }

            if (o.value == null) {
                return 1;  // Any value is greater than NullObject
            }

            var hashCodeDistinct = Integer.compare(this.hashCode, o.hashCode);
            if (hashCodeDistinct != 0) {
                return hashCodeDistinct;
            }


            return this.value.compareTo((T) o.value);

        }


        public T getValue() {
            return (T) value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashCodeWrapper<?> that = (HashCodeWrapper<?>) o;
            return Objects.equals(value, that.value);
        }
    }



}
