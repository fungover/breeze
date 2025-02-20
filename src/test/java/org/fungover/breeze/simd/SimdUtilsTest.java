package org.fungover.breeze.simd;

import jdk.incubator.vector.VectorOperators;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimdUtilsTest {

    SimdUtils simdUtils = new SimdUtils();

    @Test
    @DisplayName("checkNullInputs throws NullPointerException when first array is null")
    void checkNullInputs_FirstArrayNull() {
        float[] validArray = {1, 2, 3};
        assertThrows(NullPointerException.class, () ->
                simdUtils.checkNullInputs(null, validArray)
        );
    }

    @Test
    @DisplayName("checkNullInputs throws NullPointerException when second array is null")
    void checkNullInputs_SecondArrayNull() {
        float[] validArray = {1, 2, 3};
        assertThrows(NullPointerException.class, () ->
                simdUtils.checkNullInputs(validArray, null)
        );
    }

    @Test
    @DisplayName("checkNullInputs does not throw when both arrays are non-null")
    void checkNullInputs_BothArraysNonNull() {
        float[] arr1 = {1, 2, 3};
        float[] arr2 = {4, 5, 6};
        simdUtils.checkNullInputs(arr1, arr2);
    }

    @Test
    @DisplayName("chunkElementwise throws IllegalArgumentException for mismatched array lengths")
    void chunkElementwiseThrowsForMismatchedLengths() {
        float[] arr1 = {1, 2, 3, 4};
        float[] arr2 = {5, 6, 7};  // Different length
        float[] result = new float[arr1.length];

        assertThrows(IllegalArgumentException.class, () -> {
            simdUtils.chunkElementwise(arr1, arr2, result, 0, arr1.length, VectorOperators.ADD);
        });
    }

    @Test
    @DisplayName("chunkElementwise correctly computes elementwise addition")
    void chunkElementwiseAddition() {
        // Given two arrays of equal length.
        float[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8};
        float[] arr2 = {8, 7, 6, 5, 4, 3, 2, 1};
        float[] expected = {9, 9, 9, 9, 9, 9, 9, 9};
        float[] result = new float[arr1.length];

        simdUtils.chunkElementwise(arr1, arr2, result, 0, arr1.length, VectorOperators.ADD);
        // Use assertArrayEquals to compare arrays.
        assertArrayEquals(expected, result);
    }

    // --- Tests for dotProductForSpecies ---

    @Test
    @DisplayName("dotProductForSpecies throws IllegalArgumentException for mismatched array lengths")
    void dotProductForSpeciesThrowsForMismatchedLengths() {
        float[] arr1 = {1, 2, 3, 4};
        float[] arr2 = {5, 6, 7}; // Different length

        assertThrows(IllegalArgumentException.class, () -> {
            simdUtils.dotProductForSpecies(arr1, arr2, 0, arr1.length);
        });
    }

    @Test
    @DisplayName("dotProductForSpecies correctly computes dot product")
    void dotProductForSpeciesCorrectness() {
        // For example, use small arrays where we can easily compute the dot product.
        // arr1: {1,2,3,4} and arr2: {5,6,7,8}
        // Expected dot product: 1*5 + 2*6 + 3*7 + 4*8 = 5 + 12 + 21 + 32 = 70.
        float[] arr1 = {1, 2, 3, 4};
        float[] arr2 = {5, 6, 7, 8};
        float expected = 70f;
        float result = simdUtils.dotProductForSpecies(arr1, arr2, 0, arr1.length);
        // Compare with a tolerance if needed; here we assume exact arithmetic.
        assertEquals(expected, result);
    }

}