package org.fungover.breeze.util;

import java.util.function.BiFunction;

public class Arrays {

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
