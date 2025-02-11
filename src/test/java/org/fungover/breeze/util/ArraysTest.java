package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

}
