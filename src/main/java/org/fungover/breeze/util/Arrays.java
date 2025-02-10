package org.fungover.breeze.util;

import java.util.function.BiFunction;

public class Arrays {

    // Helper class to store tuple pairs for zip()
    public static class Pair<T, U> {
        public final T first;
        public final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    // Zip method - Pairs elements together
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
    // ZipWith method - Combines elements using a function
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

    // Weaver method - Interleaves elements
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
