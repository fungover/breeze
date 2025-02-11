package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class ArraysTest {

    @Test
    void testZip_EqualLengthArrays() {
        String[] words = {"a1", "a2", "a3"};
        String[] numbers = {"b1", "b2", "b3"};

        Arrays.Pair<String, String>[] result = Arrays.zip(words, numbers);

        assertEquals(3, result.length);
        assertEquals(new Arrays.Pair<>("a1", "b1"), result[0]);
        assertEquals(new Arrays.Pair<>("a2", "b2"), result[1]);
        assertEquals(new Arrays.Pair<>("a3", "b3"), result[2]);
    }

    @Test
    void testZip_DifferentLengths_ShouldThrowException() {
        String[] words = {"a1", "a2"};
        String[] numbers = {"b1", "b2", "b3"};

        assertThrows(IllegalArgumentException.class, () -> Arrays.zip(words, numbers));
    }
    @Test
    void testZip_EmptyArrays() {
        String[] empty1 = {};
        String[] empty2 = {};

        Arrays.Pair<String, String>[] result = Arrays.zip(empty1, empty2);
        assertEquals(0, result.length);
    }
    @Test
    void testZip_NullElements() {
        String[] first = {null, "b"};
        String[] second = {"a", null};

        Arrays.Pair<String, String>[] result = Arrays.zip(first, second);

        assertEquals(2, result.length);
        assertNull(result[0].first);
        assertEquals("a", result[0].second);
        assertEquals("b", result[1].first);
        assertNull(result[1].second);
    }
    @Test
    void testZipWith_IntegerArrays() {
        Integer[] first = {1, 2, 3};
        Integer[] second = {10, 20, 30};
        Integer[] result = new Integer[first.length];

        BiFunction<Integer, Integer, Integer> sumFunction = Integer::sum;
        Arrays.zipWith(first, second, sumFunction, result);

        assertArrayEquals(new Integer[]{11, 22, 33}, result);
    }
    @Test
    void testZipWith_DifferentTypes() {
        Integer[] first = {1, 2, 3};
        String[] second = {"A", "B", "C"};
        String[] result = new String[first.length];

        BiFunction<Integer, String, String> combine = (num, str) -> num + str;
        Arrays.zipWith(first, second, combine, result);

        assertArrayEquals(new String[]{"1A", "2B", "3C"}, result);
    }
    @Test
    void testZipWith_DifferentLengths_ShouldThrowException() {
        Integer[] first = {1, 2};
        Integer[] second = {10, 20, 30};
        Integer[] result = new Integer[first.length];

        assertThrows(IllegalArgumentException.class, () ->
                Arrays.zipWith(first, second, Integer::sum, result)
        );
    }
    @Test
    void testWeaver_EqualLengthArrays() {
        String[] first = {"x", "y", "z"};
        String[] second = {"1", "2", "3"};
        String[] result = new String[first.length + second.length];

        Arrays.weaver(first, second, result);

        assertArrayEquals(new String[]{"x", "1", "y", "2", "z", "3"}, result);
    }

}
