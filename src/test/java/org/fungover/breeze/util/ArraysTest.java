package org.fungover.breeze.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;



class ArraysTest {



    @Test
    @DisplayName("Chunk array throws exception for negative size")
    void chunkArrayThrowsExceptionForNegativeSize() {
        Integer[] integerArray = {1, 2, 3};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunk(integerArray, -1));

        assertThat(exception.getMessage()).isEqualTo("Size must be greater than 0");
    }

    @Test
    @DisplayName("Chunk list throws exception for negative size")
    void chunkListThrowsExceptionForNegativeSize() {
        List<String> stringList = List.of("A","B","C");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunkList(stringList, -1));

        assertThat(exception.getMessage()).isEqualTo("Size must be greater than 0");
    }

    @Test
    @DisplayName("Chunk array throws exception for zero size")
    void chunkArrayThrowsExceptionForZeroSize() {
        Double[] doubleArray = {1.1, 2.2, 3.3};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunk(doubleArray, 0));

        assertThat(exception.getMessage()).isEqualTo("Size must be greater than 0");
    }

    @Test
    @DisplayName("Chunk list throws exception for zero size")
    void chunkListThrowsExceptionForZeroSize() {
        List<Boolean> booleanList = List.of(true, false, true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Arrays.chunkList(booleanList, 0));

        assertThat(exception.getMessage()).isEqualTo("Size must be greater than 0");
    }

    @Test
    @DisplayName("Chunk array returns empty array when input is empty")
    void chunkArrayReturnsEmptyArrayWhenInputIsEmpty() {
        String[] emptyArray = {};
        String [][] result = Arrays.chunk(emptyArray, 5);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Chunk list returns empty list when input is empty")
    void chunkListReturnsEmptyListWhenInputIsEmpty() {
        List<Integer> emptyList = List.of();
        List<List<Integer>> result = Arrays.chunkList(emptyList, 5);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Chunk array evenly into equal sized chunks")
    void chunkArrayEvenlyIntoEqualSizedChunks() {
        Integer [] evenIntegerArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int size = 3;

        Integer [][] result = Arrays.chunk(evenIntegerArray, size);

        assertThat(result).hasDimensions(3,size);
    }

    @Test
    @DisplayName("Chunk list evenly into equal sized chunks")
    void chunkListEvenlyIntoEqualSizedChunks() {
        List<String> evenStringList = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I");
        int size = 3;

        List<List<String>> result = Arrays.chunkList(evenStringList, size);

        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("Chunk array handles uneven split with smaller last chunk ")
    void chunkArrayHandlesUnevenSplitWithSmallerLastChunk() {
        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9, 10.1};
        int size = 3;
        int lastChunkSize = doubleArray.length % size;

        Double [][] result = Arrays.chunk(doubleArray, size);


        assertEquals(4, result.length);
        assertThat(result[0]).hasSize(size);
        assertThat(result[result.length - 1]).hasSize(lastChunkSize);

    }

    @Test
    @DisplayName("Chunk list handles uneven split with smaller last chunk")
    void chunkListHandlesUnevenSplitWithSmallerLastChunk() {
        List<Boolean> booleanList = List.of(true, false, true, false, true, false, true, false, true, false);
        int size = 3;
        int lastChunkSize = booleanList.size() % size;

        List<List<Boolean>> result = Arrays.chunkList(booleanList, size);

        assertEquals(4, result.size());
        assertThat(result.getFirst()).hasSize(size);
        assertThat(result.getLast()).hasSize(lastChunkSize);
    }

    @Test
    @DisplayName("Chunk array handles input smaller than chunk size")
    void chunkArrayHandlesInputSmallerThanChunkSize() {
        Boolean[] booleanArray = {true, false, true, false, true};
        int size = 6;

        Boolean [][] result = Arrays.chunk(booleanArray, size);

        assertThat(result).hasDimensions(1,booleanArray.length);
    }

    @Test
    @DisplayName("Chunk list handles input smaller than chunk size")
    void chunkListHandlesInputSmallerThanChunkSize() {
        List<Double> doubleList = List.of(1.1, 2.2, 3.3, 4.4, 5.5);
        int size = 6;

        List<List<Double>> result = Arrays.chunkList(doubleList, size);
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Chunk array creates one element chunks when size is one")
    void chunkArrayCreatesOneElementChunksWhenSizeIsOne() {
        Integer[] integerArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int size = 1;

        Integer [][] result = Arrays.chunk(integerArray,size);
        assertThat(result).hasDimensions(integerArray.length, 1);
    }

    @Test
    @DisplayName("Chunk list creates one element chunks when size is one")
    void chunkListCreatesOneElementChunksWhenSizeIsOne() {
        List<String> stringList = List.of("A", "B", "C", "D", "E", "F", "G", "I", "J" );
        int size = 1;

        List<List<String>> result = Arrays.chunkList(stringList, size);
        assertThat(result).hasSize(stringList.size());
    }


}
