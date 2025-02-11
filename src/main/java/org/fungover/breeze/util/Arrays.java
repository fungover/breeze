package org.fungover.breeze.util;

import com.google.common.collect.ImmutableList;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.ArrayList;
import java.util.List;

public class Arrays {

    // Generic method to create a list of sliding pairs from an array
    public static <T> ImmutableList<Tuple2<T, T>> sliding2(T[] array) {

        // Check if the array is null or has fewer than 2 elements (not enough to form pairs)
        if (array == null || array.length < 2) {
            return ImmutableList.of(); // Return an empty list if invalid input
        }

        // List to store pairs of adjacent elements
        List<Tuple2<T, T>> pairs = new ArrayList<>();

        // Loop through the array and create pairs of adjacent elements
        for (int i = 0; i < array.length - 1; i++) {

            // Create a pair (Tuple2) for the current element and the next element
            pairs.add(Tuple.of(array[i], array[i + 1]));
        }

        // Return the list of pairs wrapped in an immutable list
        return ImmutableList.copyOf(pairs);
    }
}
