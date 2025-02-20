package org.fungover.breeze.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


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
    void testZip_NullArray_ShouldThrowException() {
        String[] words = {"a", "b", "c"};
        assertThrows(IllegalArgumentException.class, () -> Arrays.zip(null, words));
        assertThrows(IllegalArgumentException.class, () -> Arrays.zip(words, null));
    }

    @Test
    void testZipWith_NullInput_ShouldThrowException() {
        Integer[] first = {1, 2, 3};
        Integer[] second = {10, 20, 30};
        Integer[] result = new Integer[first.length];

        assertThrows(IllegalArgumentException.class, () -> Arrays.zipWith(null, second, Integer::sum, result));
        assertThrows(IllegalArgumentException.class, () -> Arrays.zipWith(first, null, Integer::sum, result));
    }

    @Test
    void testZipWith_NullCombiner_ShouldThrowException() {
        Integer[] first = {1, 2, 3};
        Integer[] second = {10, 20, 30};
        Integer[] result = new Integer[first.length];

        assertThrows(IllegalArgumentException.class, () -> Arrays.zipWith(first, second, null, result));
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
    void testZipWith_ShortResultArray_ShouldThrowException() {
        Integer[] first = {1, 2, 3};
        Integer[] second = {10, 20, 30};
        Integer[] result = new Integer[2]; // För kort!

        assertThrows(IllegalArgumentException.class, () -> Arrays.zipWith(first, second, Integer::sum, result));
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
        String[] result = new String[4];

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

    @Test
    @DisplayName("Performance Benchmarking")
    void performanceBenchmarking() {
        int size = 1000; // You can adjust this size for different tests
        Integer[][] input = new Integer[size][size];
        Integer[][] expected = new Integer[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                input[i][j] = i * size + j;
                expected[i][j] = j * size + i;
            }
        }

        // Measure time for the original transpose
        long startTime = System.nanoTime();
        Integer[][] result = Arrays.transpose(input);
        long duration = System.nanoTime() - startTime;
        System.out.println("Original transpose time: " + TimeUnit.NANOSECONDS.toMillis(duration) + " ms");

        assertThat(result).isDeepEqualTo(expected);

        // Measure time for the optimized transpose
        startTime = System.nanoTime();
        result = Arrays.transposeFaster(input);
        duration = System.nanoTime() - startTime;
        System.out.println("Optimized transpose time: " + TimeUnit.NANOSECONDS.toMillis(duration) + " ms");

        assertThat(result).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw exception when array is null")
    void shouldThrowExceptionWhenArrayIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunk(null,1));
        assertThat(exception.getMessage()).isEqualTo("Input array must not be null");
    }

    @Test
    @DisplayName("Should throw exception when list is null")
    void shouldThrowExceptionWhenListIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunkList(null, 1));

        assertThat(exception.getMessage()).isEqualTo("Input list must not be null");
    }

    @Test
    @DisplayName("Should throw exception when array contains null elements")
    void shouldThrowExceptionWhenArrayContainsNullElements() {
        Integer [] arrayWithNull = {1, 2, 3, null};
        int size = 4;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunk(arrayWithNull, size));
        assertThat(exception.getMessage()).isEqualTo("Element must not be null");
    }

    @Test
    @DisplayName("Should throw exception when list contains null elements")
    void shouldThrowExceptionWhenListContainsNullElements() {
        List<Integer> listWithNull = new ArrayList<>(List.of(1, 2, 3));
        listWithNull.add(null);
        int size = 3;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunkList(listWithNull, size));
        assertThat(exception.getMessage()).isEqualTo("Element must not be null");
    }

    @Test
    @DisplayName("Should throw exception when array size is negative")
    void shouldThrowExceptionWhenArraySizeIsNegative() {
        Integer[] integerArray = {1, 2, 3};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunk(integerArray, -1));

        assertThat(exception.getMessage()).isEqualTo("Size must be greater than 0");
    }

    @Test
    @DisplayName("Should throw exception when list size is negative")
    void shouldThrowExceptionWhenListSizeIsNegative() {
        List<String> stringList = List.of("A","B","C");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunkList(stringList, -1));

        assertThat(exception.getMessage()).isEqualTo("Size must be greater than 0");
    }

    @Test
    @DisplayName("Should throw exception when array size is zero")
    void shouldThrowExceptionWhenArraySizeIsZero() {
        Double[] doubleArray = {1.1, 2.2, 3.3};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunk(doubleArray, 0));

        assertThat(exception.getMessage()).isEqualTo("Size must be greater than 0");
    }

    @Test
    @DisplayName("Should throw exception when list size is zero")
    void shouldThrowExceptionWhenListSizeIsZero() {
        List<Boolean> booleanList = List.of(true, false, true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunkList(booleanList, 0));

        assertThat(exception.getMessage()).isEqualTo("Size must be greater than 0");
    }

    @Test
    @DisplayName("Should return empty array when input is empty")
    void shouldReturnEmptyArrayWhenInputIsEmpty() {
        String[] emptyArray = {};
        String [][] result = Arrays.chunk(emptyArray, 5);

        assertThat(result).hasDimensions(0,0);
    }

    @Test
    @DisplayName("Should return empty list when input is empty")
    void shouldReturnEmptyListWhenInputIsEmpty() {
        List<Integer> emptyList = List.of();
        List<List<Integer>> result = Arrays.chunkList(emptyList, 5);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should chunk array into equal sized parts")
    void shouldChunkArrayIntoEqualSizedParts() {
        Integer [] evenIntegerArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int size = 3;

        Integer [][] result = Arrays.chunk(evenIntegerArray, size);

        assertThat(result).hasDimensions(3,size);
    }

    @Test
    @DisplayName("Should chunk list into equal sized parts")
    void shouldChunkListIntoEqualSizedParts() {
        List<String> evenStringList = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I");
        int size = 3;

        List<List<String>> result = Arrays.chunkList(evenStringList, size);

        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("Should handle uneven split for array with smaller last chunk")
    void shouldHandleUnevenSplitForArrayWithSmallerLastChunk() {
        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9, 10.1};
        int size = 3;
        int lastChunkSize = doubleArray.length % size;

        Double [][] result = Arrays.chunk(doubleArray, size);

        assertEquals(4, result.length);
        assertThat(result[0]).hasSize(size);
        assertThat(result[result.length - 1]).hasSize(lastChunkSize);
    }

    @Test
    @DisplayName("Should handle uneven split for list with smaller last chunk")
    void shouldHandleUnevenSplitForListWithSmallerLastChunk() {
        List<Boolean> booleanList = List.of(true, false, true, false, true, false, true, false, true, false);
        int size = 3;
        int lastChunkSize = booleanList.size() % size;

        List<List<Boolean>> result = Arrays.chunkList(booleanList, size);

        assertEquals(4, result.size());
        assertThat(result.getFirst()).hasSize(size);
        assertThat(result.getLast()).hasSize(lastChunkSize);
    }

    @Test
    @DisplayName("Should handle array smaller than chunk size")
    void shouldHandleArraySmallerThanChunkSize() {
        Boolean[] booleanArray = {true, false, true, false, true};
        int size = 6;

        Boolean [][] result = Arrays.chunk(booleanArray, size);

        assertThat(result).hasDimensions(1,booleanArray.length);
    }

    @Test
    @DisplayName("Should handle list smaller than chunk size")
    void shouldHandleListSmallerThanChunkSize() {
        List<Double> doubleList = List.of(1.1, 2.2, 3.3, 4.4, 5.5);
        int size = 6;

        List<List<Double>> result = Arrays.chunkList(doubleList, size);
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Should create single element chunks for array when size is one")
    void shouldCreateSingleElementChunksForArrayWhenSizeIsOne() {
        Integer[] integerArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int size = 1;

        Integer [][] result = Arrays.chunk(integerArray,size);
        assertThat(result).hasDimensions(integerArray.length, 1);
    }

    @Test
    @DisplayName("Should create single element chunks for list when size is one")
    void shouldCreateSingleElementChunksForListWhenSizeIsOne() {
        List<String> stringList = List.of("A", "B", "C", "D", "E", "F", "G", "I", "J" );
        int size = 1;

        List<List<String>> result = Arrays.chunkList(stringList, size);
        assertThat(result).hasSize(stringList.size());
    }

    @Test
    @DisplayName("Should handle large array chunking")
    void shouldHandleLargeArrayChunking() {
        Integer[] bigIntegerArray = new Integer[10_000_000];
        for (int i = 0; i < bigIntegerArray.length; i++) {
            bigIntegerArray[i] = i + 1;
        }

        int size = 10000;

        Integer [][] result = Arrays.chunk(bigIntegerArray, size);

        assertThat(result[0]).hasSize(size);
    }

    @Test
    @DisplayName("Should handle large list chunking")
    void shouldHandleLargeListChunking() {
        List<Integer> bigIntegerList = new ArrayList<>();
        for (int i = 1; i <= 10_000_000; i++) {
            bigIntegerList.add(i);
        }

        int size = 10000;

        List<List<Integer>> result = Arrays.chunkList(bigIntegerList, size);
        assertThat(result).hasSize(bigIntegerList.size() / size);
    }

    @Test
    @DisplayName("Should benchmark performance for large array")
    void shouldBenchmarkPerformanceForLargeArray() {
        Integer [] benchmarkArray = new Integer[10_000_000];
        for (int i = 0; i < benchmarkArray.length; i++) {
            benchmarkArray[i] = i + 1;
        }

        int size = 10000;

        long startTime = System.nanoTime();
        Integer [][] result = Arrays.chunk(benchmarkArray, size);
        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime) / 1_000_000;

        assertThat(result[0]).hasSize(size);
        System.out.println("Large array test complete in " + elapsedTime + " ms");
    }

    @Test
    @DisplayName("Should benchmark performance for large list")
    void shouldBenchmarkPerformanceForLargeList() {
        List<Integer> benchmarkList = new ArrayList<>();
        for (int i = 1; i <= 10_000_000; i++) {
            benchmarkList.add(i);
        }

        int size = 10000;

        long startTime = System.nanoTime();
        List<List<Integer>> result = Arrays.chunkList(benchmarkList, size);
        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime) / 1_000_000;

        assertThat(result).hasSize(benchmarkList.size() / size);
        System.out.println("Large list test complete in " + elapsedTime + " ms");
    }


}
