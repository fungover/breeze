package org.fungover.breeze.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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



}
