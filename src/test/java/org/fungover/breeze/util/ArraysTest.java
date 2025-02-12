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

}
