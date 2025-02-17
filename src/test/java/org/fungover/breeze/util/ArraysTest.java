package org.fungover.breeze.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.function.BiFunction;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class ArraysTest {

    @Test
    void testPairEquality() {
        Arrays.Pair<String, String> pair1 = new Arrays.Pair<>("a1", "b1");
        Arrays.Pair<String, String> pair2 = new Arrays.Pair<>("a1", "b1");

        assertEquals(pair1, pair2); // Ska vara true om equals() fungerar korrekt
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
        assertNull(result[0].getFirst());
        assertEquals("a", result[0].getSecond());
        assertEquals("b", result[1].getFirst());
        assertNull(result[1].getSecond());
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
    @Test
    void testWeaver_DifferentLengths() {
        String[] first = {"a", "b"};
        String[] second = {"1", "2", "3"};
        String[] result = new String[first.length + second.length];

        Arrays.weaver(first, second, result);

        assertArrayEquals(new String[]{"a", "1", "b", "2", "3"}, result);
    }
    @Test
    void testWeaver_EmptyArrays() {
        String[] empty1 = {};
        String[] empty2 = {};
        String[] result = new String[0];

        Arrays.weaver(empty1, empty2, result);
        assertArrayEquals(new String[]{}, result);
    }
    @Test
    void testWeaver_NullElements() {
        String[] first = {null, "b"};
        String[] second = {"a", null, "c"};
        String[] result = new String[first.length + second.length];

        Arrays.weaver(first, second, result);

        assertArrayEquals(new String[]{null, "a", "b", null, "c"}, result);
    }

    @Test
    void testWeaver_NullArray_ShouldThrowException() {
        String[] words = {"a", "b", "c"};
        String[] result = new String[6];

        assertThrows(IllegalArgumentException.class, () -> Arrays.weaver(null, words, result));
        assertThrows(IllegalArgumentException.class, () -> Arrays.weaver(words, null, result));
        assertThrows(IllegalArgumentException.class, () -> Arrays.weaver(words, words, null));
    }

    @Test
    void testWeaver_LargeArrays_Performance() {
        int size = 100000;
        Integer[] first = new Integer[size];
        Integer[] second = new Integer[size];
        Integer[] result = new Integer[size * 2];

        for (int i = 0; i < size; i++) {
            first[i] = i;
            second[i] = i + 100000;
        }

        Arrays.weaver(first, second, result);

        assertEquals(200000, result.length);
        assertEquals(0, result[0]);
        assertEquals(100000, result[1]);
        assertEquals(1, result[2]);
        assertEquals(100001, result[3]);
    }

    @Test
    void testWeaver_ShortResultArray_ShouldThrowException() {
        String[] first = {"a", "b"};
        String[] second = {"1", "2", "3"};
        String[] result = new String[4]; // För kort!

        assertThrows(IllegalArgumentException.class, () -> Arrays.weaver(first, second, result));
    }

    @Test
    void testWeaver_VerifyInterleavingOrder() {
        String[] first = {"a", "b", "c"};
        String[] second = {"1", "2", "3", "4"};
        String[] result = new String[first.length + second.length];

        Arrays.weaver(first, second, result);

        assertArrayEquals(new String[]{"a", "1", "b", "2", "c", "3", "4"}, result);
    }

    @Test
    @DisplayName("Square Arrays")
    void squareArrays() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Integer[][] expected = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };

        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Rectangular Arrays")
    void rectArrays() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        Integer[][] expected = {
                {1, 4, 7, 10},
                {2, 5, 8, 11},
                {3, 6, 9, 12}
        };
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Single Row/Column Arrays")
    void singleRowColumnArrays() {
        Integer[][] input = {
                {1, 2, 3},
        };
        Integer[][] expected = {
                {1},
                {2},
                {3}
        };
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Irregular Arrays")
    void irregularArrays() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6, 7}
        };

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Arrays.transpose(input))
                .withMessage("Irregular array: all rows must have the same length");
    }

    @Test
    @DisplayName("Large Arrays")
    void largeArrays() {
        int size = 1000;
        Integer[][] input = new Integer[size][size];
        Integer[][] expected = new Integer[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                input[i][j] = i * size + j;
                expected[i][j] = j * size + i;
            }
        }
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Arrays with null element")
    void arraysWithNullElement() {
        Integer[][] input = {
                {1, null, 3},
                {4, 5, null},
        };
        Integer[][] expected = {
                {1, 4},
                {null, 5},
                {3, null},
        };
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);

    }

    @Test
    @DisplayName("Empty Arrays")
    void emtpyArrays() {
        Integer[][] input = {}; // Empty array
        Integer[][] expected = {}; // Expected result

        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Preserve Type Information")
    void preservedTypeInformation() {
        String[][] input = {
                {"a", "b" },
                {"c", "d" },
        };
        String[][] expected = {
                {"a", "c" },
                {"b", "d" },
        };
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }
}
