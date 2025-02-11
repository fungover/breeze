package org.fungover.breeze.util;

import java.util.function.BiFunction;

/**
 * Utility class for performing array operations like zip, zipWith, and weaver.
 */

public class Arrays {

    /**
     * Helper class to represent a tuple (pair) of two elements.
     *
     * @param <T> Type of the first element.
     * @param <U> Type of the second element.
     */

    public static class Pair<T, U> {
        public final T first;
        public final U second;

        /**
         * Constructs a Pair object with given elements.
         *
         * @param first  The first element of the pair.
         * @param second The second element of the pair.
         */

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }
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
}
