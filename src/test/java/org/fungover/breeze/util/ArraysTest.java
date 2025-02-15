package org.fungover.breeze.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class ArraysTest {



    @Test
    @DisplayName("Utility class should not be instantiated")
    void utilityClassShouldNotBeInstantiated() throws Exception {
        Constructor <Arrays> constructor = Arrays.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);

        Throwable cause = exception.getCause();
        assertNotNull(cause);

        assertEquals(IllegalStateException.class, exception.getCause().getClass());
        assertEquals("Utility class", exception.getCause().getMessage());
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
