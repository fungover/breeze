package org.fungover.breeze.collection;

import java.util.Objects;

/**
 * Node class that represent a datapoint for tree like data structure.
 */
class Node<T> {

    /**
     * value stored in node with any data type
     */
    HashCodeWrapper<T> value;

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


    /**
     * Default constructor that instantiates Node objects with predetermine values.
     *<p>
     *     This constructor create the Node with:
     *     <ul>
            <li> Field `value`: null</li>
     *      <li> Field `color`: false represents Red color in RedBlack Tree</li>
     *
     *     </ul>
     *<p/>
     *
     */
    public Node() {
        this.value = new HashCodeWrapper<>(null);
        this.color = false;
    }

    /**
     * Retrieves the value stored in the Node.
     * <p>
     * This method returns the value wrapped inside the {@link HashCodeWrapper} associated with the Node.
     * </p>
     *
     * @return the value stored in the Node, which is of type {@code T}.
     */
    public T getValue() {

        return this.value.getValue();
    }

    /**
     * Compares this Node to another object for equality.
     * <p>
     * The equality check is based on the {@link HashCodeWrapper} value stored in the Node.
     * This method returns {@code true} if the specified object is a {@code Node} and contains the same
     * value as this Node; otherwise, it returns {@code false}.
     * </p>
     *
     * @param o the object to compare to
     * @return {@code true} if this Node is equal to the specified object, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node <?> node)) return false;
        return value.equals(node.value);
    }

    /**
     * Returns the hash code value for the {@link HashCodeWrapper} value stored in this Node.
     * <p>
     *     This hash code is used for quickly identifying whether two {@code Node} objects
     *     might be equal, based on the value contained in the {@link HashCodeWrapper}.
     * </p>
     *
     * @return the hash code value for the {@link HashCodeWrapper} value stored in the Node.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Compares this {@code Node} object with another {@code Node} object.
     * <p>
     *     The comparison is based on the value stored in the {@link HashCodeWrapper}.
     *     It uses the {@link HashCodeWrapper#compareTo(HashCodeWrapper)} method to compare
     *     the wrapped values.
     * </p>
     *
     * @param otherNode the {@code Node} to be compared with
     * @return a negative integer if this {@code Node}'s value is less than the other {@code Node}'s value,
     *         a positive integer if this {@code Node}'s value is greater,
     *         or zero if both values are considered equal
     */
    public int compareTo(Node<T> otherNode) {
        return this.value.compareTo(otherNode.value);  // Use compareTo in HashCodeWrapper
    }


    /**
     * A wrapper class that holds a value of any type {@code T} and provides
     * a custom hash code and comparison logic for that value.
     * <p>
     *     This class implements {@link Comparable} interface to allow comparison
     *     based on the hash code and the value stored in the wrapper. It is mainly
     *     used internally within the {@link Node} class to facilitate comparison
     *     and hashing behavior.
     * </p>
     *
     * @param <T> The type of the value to be wrapped. The value must be {@link Comparable}
     *            in order to support comparison.
     */
    static final class HashCodeWrapper<T> implements Comparable<HashCodeWrapper<T>> {

        /**
         * The value of type {@code T} stored in the wrapper.
         */
        private final T value;

        /**
         * The precomputed hash code for the stored value. This allows more efficient
         * comparison and hashing.
         */
        private final int hashCode;


        /**
         * Constructs a {@link HashCodeWrapper} with the specified value.
         * <p>
         *     If the value is {@code null}, the hash code will be set to {@code 1}.
         * </p>
         *
         * @param value The value to be wrapped by this class. It can be of any type {@code T}.
         *              The value will be used for comparison and hashing.
         */
        public HashCodeWrapper(T value) {
            this.value =  value;
            this.hashCode = value != null ? value.hashCode() : 1;
        }


        /**
         * Compares this {@code HashCodeWrapper} object with another {@code HashCodeWrapper} object.
         * <p>
         *     The comparison first checks whether the values are {@code null}. If both values are {@code null},
         *     the objects are considered equal. If one value is {@code null}, it is considered smaller than a non-null value.
         *     If both values are non-null, the comparison is based on the hash code of the values.
         * </p>
         *
         * @param o the {@code HashCodeWrapper} object to be compared with
         * @return a negative integer if this {@code HashCodeWrapper}'s value is less than the other {@code HashCodeWrapper}'s value,
         *         a positive integer if this {@code HashCodeWrapper}'s value is greater,
         *         or zero if both values are considered equal
         *@example
         * <pre>
         *Node<Integer> node1 = new Node<>(10);
         *Node<Integer> node2 = new Node<>(20);
         *int result = node1.compareTo(node2); // result will be negative since 10 < 20
         * </pre>
         *
         * <pre>
         *@example
         *Node<String> node3 = new Node<>("apple");
         *Node<String> node4 = new Node<>("banana");
         *int result2 = node3.compareTo(node4); // result2 will be negative because "apple"
         *</pre>
         */
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


            // If hash codes are the same compare values.
            return ((Comparable<T>) this.value).compareTo( o.value);

        }


        /**
         * Returns the stored value of type {@code T}.
         *
         * @return The value wrapped by this {@link HashCodeWrapper}.
         */
        public T getValue() {
            return value;
        }

        /**
         * Returns the hash code of the stored value.
         *
         * @return The precomputed hash code of the value stored in this wrapper.
         */
        @Override
        public int hashCode() {
            return hashCode;
        }


        /**
         * Checks whether this {@link HashCodeWrapper} is equal to another object.
         * <p>
         *     Two {@link HashCodeWrapper} objects are considered equal if they
         *     have the same value stored inside, including handling {@code null} values.
         * </p>
         *
         * @param o The object to compare against.
         * @return {@code true} if the objects are considered equal, {@code false} otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashCodeWrapper<?> that = (HashCodeWrapper<?>) o;
            return Objects.equals(value, that.value);
        }
    }



}
