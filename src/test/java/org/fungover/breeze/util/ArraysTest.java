package org.fungover.breeze.util;

import static org.junit.jupiter.api.Assertions.*;
import com.google.common.collect.ImmutableList;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;

public class ArraysTest {

    @Test
    void testNormalArray() {
        Integer[] arr = {1, 2, 3, 4};
        ImmutableList<Tuple2<Integer, Integer>> result = Arrays.sliding2(arr);

        assertEquals(3, result.size());
        assertEquals(Tuple.of(1, 2), result.get(0));  // Fixed
        assertEquals(Tuple.of(2, 3), result.get(1));  // Fixed
        assertEquals(Tuple.of(3, 4), result.get(2));  // Fixed
    }

    @Test
    void testTwoElementArray() {
        String[] arr = {"A", "B"};
        ImmutableList<Tuple2<String, String>> result = Arrays.sliding2(arr);

        assertEquals(1, result.size());
        assertEquals(Tuple.of("A", "B"), result.get(0));
    }

    @Test
    void testSingleElementArray() {
        Integer[] arr = {42};
        assertTrue(Arrays.sliding2(arr).isEmpty());
    }

    @Test
    void testEmptyArray() {
        Integer[] arr = {};
        assertTrue(Arrays.sliding2(arr).isEmpty());
    }

    @Test
    void testArrayWithNulls() {
        Integer[] arr = {1, null, 3};
        ImmutableList<Tuple2<Integer, Integer>> result = Arrays.sliding2(arr);

        assertEquals(2, result.size());
        assertEquals(Tuple.of(1, null), result.get(0));
        assertEquals(Tuple.of(null, 3), result.get(1));
    }

    @Test
    void testLargeArrayPerformance() {
        int size = 1_000_000;  // 1 million elements
        Integer[] largeArray = new Integer[size];

        // Fill array with numbers 0 to 999,999
        for (int i = 0; i < size; i++) {
            largeArray[i] = i;
        }

        long startTime = System.nanoTime();  // Start timing

        ImmutableList<Tuple2<Integer, Integer>> result = Arrays.sliding2(largeArray);

        long endTime = System.nanoTime();  // End timing
        long durationMs = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        // Check number of pairs (N-1)
        assertEquals(size - 1, result.size());

        // Check a few pairs to ensure correctness
        assertEquals(Tuple.of(0, 1), result.get(0));
        assertEquals(Tuple.of(999998, 999999), result.get(size - 2));

        System.out.println("Large array test completed in " + durationMs + " ms");

        // Optional: Assert performance (e.g., should complete in < 500ms)
        assertTrue(durationMs < 500, "Performance issue: took too long!");
    }
}
