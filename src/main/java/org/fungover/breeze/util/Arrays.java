package org.fungover.breeze.util;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.Objects;

/**
 * A class containing static Array utility functions.
 */

public class Arrays {
    /**
     * Don't let anyone instantiate this class
     */
    private Arrays() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Helper class to represent a tuple (pair) of two elements.
     *
     * @param <T> Type of the first element.
     * @param <U> Type of the second element.
     */

    /**
     * A generic immutable pair class that holds two values of different types.
     *
     * @param <T> the type of the first element
     * @param <U> the type of the second element
     */

    public static class Pair<T, U> {
        private final T first;
        private final U second;

        /**
         * Constructs a new pair with the given values.
         *
         * @param first  the first value of the pair
         * @param second the second value of the pair
         */
        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        /**
         * Returns the first element of the pair.
         *
         * @return the first value of the pair
         */
        public T getFirst() {
            return first;
        }

        /**
         * Returns the second element of the pair.
         *
         * @return the second value of the pair
         */
        public U getSecond() {
            return second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        /**
         * Checks whether this pair is equal to another object.
         * Two pairs are considered equal if they have the same class and their elements are equal.
         *
         * @param obj the object to compare with this pair
         * @return true if the objects are equal, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Pair<?, ?> pair = (Pair<?, ?>) obj;
            return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
        }

        /**
         * Computes a hash code for this pair.
         * The hash code is computed based on the hash codes of the two elements.
         *
         * @return the hash code of this pair
         */

        /**
         * Pairs corresponding elements of two arrays into an array of Pairs.
         *
         * @param <T>    The type of the first array.
         * @param <U>    The type of the second array.
         * @param first  The first input array.
         * @param second The second input array.
         * @return An array of Pairs where each element contains one element from each input array.
         * @throws IllegalArgumentException If the input arrays are null or have different lengths.
         */
    }

    public static <T, U> Pair<T, U>[] zip(T[] first, U[] second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Input arrays cannot be null.");
        }
        if (first.length != second.length) {
            throw new IllegalArgumentException("Arrays must have the same length.");
        }

        @SuppressWarnings("unchecked")
        Pair<T, U>[] result = new Pair[first.length];

        for (int i = 0; i < first.length; i++) {
            result[i] = new Pair<>(first[i], second[i]);
        }

        return result;
    }

    /**
     * Combines corresponding elements from two arrays using a provided function.
     *
     * @param <T>         Type of elements in the first array.
     * @param <U>         Type of elements in the second array.
     * @param <R>         Type of the result array.
     * @param first       The first input array.
     * @param second      The second input array.
     * @param combiner    A function to combine elements from both arrays.
     * @param resultArray The pre-allocated result array where combined values will be stored.
     * @return The result array with combined values.
     * @throws IllegalArgumentException If any of the parameters are null or if arrays have different lengths.
     */

    public static <T, U, R> R[] zipWith(T[] first, U[] second, BiFunction<T, U, R> combiner, R[] resultArray) {
        if (first == null || second == null || combiner == null || resultArray == null) {
            throw new IllegalArgumentException("Inputs cannot be null.");
        }
        if (first.length != second.length) {
            throw new IllegalArgumentException("Arrays must have the same length.");
        }
        if (resultArray.length < first.length) {
            throw new IllegalArgumentException("Result array must be at least as long as input arrays.");
        }

        for (int i = 0; i < first.length; i++) {
            resultArray[i] = combiner.apply(first[i], second[i]);
        }

        return resultArray;
    }

    /**
     * Interleaves elements from two arrays, alternating between them.
     * If one array is longer, its remaining elements are appended at the end.
     *
     * @param <T>         Type of elements in the arrays.
     * @param first       The first input array.
     * @param second      The second input array.
     * @param resultArray The pre-allocated result array where interleaved values will be stored.
     * @return The result array containing interleaved values.
     * @throws IllegalArgumentException If any of the parameters are null.
     */

    public static <T> T[] weaver(T[] first, T[] second, T[] resultArray) {
        if (first == null || second == null || resultArray == null) {
            throw new IllegalArgumentException("Inputs cannot be null.");
        }

        int maxLength = first.length + second.length;
        if (resultArray.length < maxLength) {
            throw new IllegalArgumentException("Result array must be large enough to hold all elements.");
        }

        int i = 0, j = 0, k = 0;
        while (i < first.length || j < second.length) {
            if (i < first.length) resultArray[k++] = first[i++];
            if (j < second.length) resultArray[k++] = second[j++];
        }

        return resultArray;
    }
    /**
     * Transposes a 2D array, converting rows into columns and vice versa.
     *
     * <p>For example:
     * <pre>
     * Input:
     * [[1, 2, 3],
     *  [4, 5, 6]]
     *
     * Output:
     * [[1, 4],
     *  [2, 5],
     *  [3, 6]]
     * </pre>
     *
     * @param array The 2D array to transpose.
     * @param <T>   The type of elements in the array.
     * @return The transposed 2D array, where rows become columns.
     *
     * @throws IllegalArgumentException If the input array contains null rows or has inconsistent row lengths.
     */
    public static <T> T[][] transpose(T[][] array) {
        // Check if the input array is null or empty
        if (array == null || array.length == 0) {
            return array;
        }

        // Check if the first row exists and has elements
        if (array[0].length == 0) {
            return array;
        }

        // Determine dimensions of the array
        int rows = array.length;
        int cols = array[0].length;

        // Validate that all rows have the same length
        for (T[] row : array) {
            if (row == null) {
                throw new IllegalArgumentException("Irregular array: null rows are not allowed");
            }
            if (row.length != cols) {
                throw new IllegalArgumentException("Irregular array: all rows must have the same length");
            }
        }

        // Create a new 2D array with transposed dimensions (cols x rows)
        @SuppressWarnings("unchecked")
        T[][] transposed = (T[][]) Array.newInstance(array.getClass().getComponentType().getComponentType(), cols, rows);

        // Transpose the array by swapping rows and columns
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = array[i][j];
            }
        }

        return transposed;
    }
}

