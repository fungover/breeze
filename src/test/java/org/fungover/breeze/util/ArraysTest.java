package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
