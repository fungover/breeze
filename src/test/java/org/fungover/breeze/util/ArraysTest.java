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




}
