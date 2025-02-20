package org.fungover.breeze.simd;

import jdk.incubator.vector.VectorOperators;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("SIMD Array Operations Tests")
class SimdArrayOpsTest {

    // For integer values, no suffix is needed.
    float[] TEST_ARR1 = {1, 2, 3, 4, 5, 6, 7, 8};
    float[] TEST_ARR2 = {8, 7, 4, 5, 4, 3, 2, 1};

    float[] TEST_NEG_ARR1 = {-1, -2, -3, -4, -5, -6, -7, -8};

    // For non-integer literals, we cast to float.
    float[] TEST_NEG_FLOAT = {(float) 8.2, (float) 7.2, (float) 4.4, (float) 5.5, 4, 3, (float) -2, 1};

    float[] TEST_SMALL_ARR2 = {1, 2};
    float[] TEST_SMALL_ARR1 = {1, 2};

    SimdArrayOps simdArrayOps = new SimdArrayOps();
    SimdArrayOpsParallel simdArrayOpsParallel = new SimdArrayOpsParallel();

    @Test
    @DisplayName("Dot Product: Sequential and Parallel results match and equal 120")
    void dotTwoVectorArrays() {
        var product = simdArrayOps.dotTwoVectorArrays(TEST_ARR1, TEST_ARR2);
        var productParallel = simdArrayOpsParallel.dotTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
        assertThat(productParallel == product).isTrue();
        assertThat(product).isEqualTo(114.0f);
    }

    @Test
    @DisplayName("Dot Product: Sequential and Parallel results match and equal -89.8 with float and negative")
    void dotTwoVectorNegativeArray() {
        var product = simdArrayOps.dotTwoVectorArrays(TEST_ARR1, TEST_NEG_FLOAT);
        var productParallel = simdArrayOpsParallel.dotTwoVectorArraysParallel(TEST_ARR1, TEST_NEG_FLOAT);
        assertThat(productParallel == product).isTrue();
        assertThat(product).isEqualTo(89.8f);

    }

    @Test
    @DisplayName("Dot Product: Sequential and Parallel results match and equal -204 for negative vectors")
    void dotTwoVectorNegativeFloatArray() {
        var product = simdArrayOps.dotTwoVectorArrays(TEST_ARR1, TEST_NEG_ARR1);
        var productParallel = simdArrayOpsParallel.dotTwoVectorArraysParallel(TEST_ARR1, TEST_NEG_ARR1);
        assertThat(productParallel == product).isTrue();
        assertThat(product).isEqualTo(-204);
    }

    @Test
    @DisplayName("Elementwise Operation ADD, SUB, MUL produce expected right result could not do with float values because of the rounding issue ")
    void elementwiseOperation() {
        var sum = simdArrayOps.elementwiseOperation(TEST_ARR1, TEST_ARR2, VectorOperators.ADD);
        assertThat(sum).isEqualTo(new float[]{9, 9, 7, 9, 9, 9, 9, 9});
        var diff = simdArrayOps.elementwiseOperation(TEST_ARR1, TEST_ARR2, VectorOperators.SUB);
        assertThat(diff).isEqualTo(new float[]{-7, -5, -1, -1, 1, 3, 5, 7});
        var prod = simdArrayOps.elementwiseOperation(TEST_ARR1, TEST_ARR2, VectorOperators.MUL);
        assertThat(prod).isEqualTo(new float[]{8, 14, 12, 20, 20, 18, 14, 8});
        var sumParallel = simdArrayOpsParallel.elementwiseOperationParallel(TEST_ARR1, TEST_ARR2, VectorOperators.ADD);
        assertThat(sumParallel).isEqualTo(new float[]{9, 9, 7, 9, 9, 9, 9, 9});
        var diffParallel = simdArrayOpsParallel.elementwiseOperationParallel(TEST_ARR1, TEST_ARR2, VectorOperators.SUB);
        assertThat(diffParallel).isEqualTo(new float[]{-7, -5, -1, -1, 1, 3, 5, 7});
        var prodParallel = simdArrayOpsParallel.elementwiseOperationParallel(TEST_ARR1, TEST_ARR2, VectorOperators.MUL);
        assertThat(prodParallel).isEqualTo(new float[]{8, 14, 12, 20, 20, 18, 14, 8});
    }


    @Test
    @DisplayName("Addition: Sequential and Parallel results match")
    void addTwoVectorArrays() {
        var sum = simdArrayOps.addTwoVectorArrays(TEST_ARR1, TEST_ARR2);
        var sumParallel = simdArrayOpsParallel.addTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
        assertThat(sum).isEqualTo(new float[]{9, 9, 7, 9, 9, 9, 9, 9});
        assertThat(Arrays.equals(sum, sumParallel)).isEqualTo(true);
    }

    @Test
    @DisplayName("Subtraction: Sequential and Parallel results match and values")
    void subTwoVectorArrays() {
        var diff = simdArrayOps.subTwoVectorArrays(TEST_ARR1, TEST_ARR2);
        var diffParallel = simdArrayOpsParallel.subTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
        assertThat(diff).isEqualTo(new float[]{-7, -5, -1, -1, 1, 3, 5, 7});
        assertThat(Arrays.equals(diff, diffParallel)).isEqualTo(true);
    }

    @Test
    @DisplayName("Multiplication: Sequential and Parallel results match and values")
    void mulTwoVectorArrays() {
        var prod = simdArrayOps.mulTwoVectorArrays(TEST_ARR1, TEST_ARR2);
        var prodParallel = simdArrayOpsParallel.mulTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
        assertThat(prod).isEqualTo(new float[]{8, 14, 12, 20, 20, 18, 14, 8});
        assertThat(Arrays.equals(prod, prodParallel)).isEqualTo(true);
    }

    @Test
    @DisplayName("Multiplication on Small Arrays: Sequential and Parallel results match and values")
    void mulTwoVectorSmallArrays() {
        var prod = simdArrayOps.mulTwoVectorArrays(TEST_SMALL_ARR1, TEST_SMALL_ARR2);
        var prodparallel = simdArrayOpsParallel.mulTwoVectorArraysParallel(TEST_SMALL_ARR1, TEST_SMALL_ARR2);
        assertThat(prod).isEqualTo(new float[]{1, 4});
        assertThat(Arrays.equals(prod, prodparallel)).isEqualTo(true);
    }

    @Test
    @DisplayName("Multiplication on Huge Arrays: All values equal expected result")
    void mulTwoVectorHugeArrays() {
        int ten_mill = 10000000;
        float[] hugeArr1 = new float[ten_mill];
        float[] hugeArr2 = new float[ten_mill];

        Arrays.fill(hugeArr1, 2);
        Arrays.fill(hugeArr2, 3);

        float[] result = simdArrayOps.mulTwoVectorArrays(hugeArr1, hugeArr2);
        float[] resultparallel = simdArrayOpsParallel.mulTwoVectorArraysParallel(hugeArr1, hugeArr2);
        assertThat(Arrays.equals(result, resultparallel)).isEqualTo(true);
        //CHECK ALL
        for (float value : result) {
            assertThat(value).isEqualTo(6);
        }
    }

    @Test
    @DisplayName("chunkElementwise throws IllegalArgumentException for mismatched array lengths")
    void chunkElementwiseThrowsForMismatchedLengths() {
        // Create two arrays of different lengths.
        float[] arr1 = {1, 2, 3, 4};
        float[] result = new float[4];

        // Expect an IllegalArgumentException because arr1.length != arr2.length.
        assertThrows(IllegalArgumentException.class, () -> {
            SimdUtils.chunkElementwise(arr1, TEST_ARR2, result, 0, arr1.length, VectorOperators.ADD);
        });
    }

        @Test
        @DisplayName("dotProductForSpecies throws IllegalArgumentException for mismatched array lengths")
        void dotProductForSpeciesThrowsForMismatchedLengths() {
            float[] arr1 = {5, 6, 7};
            assertThrows(IllegalArgumentException.class, () -> {
                SimdUtils.dotProductForSpecies(TEST_ARR1, arr1, 0, arr1.length);
            });
        }

}

