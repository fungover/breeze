package org.fungover.breeze.simd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import jdk.incubator.vector.VectorOperators;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SimdArrayOpsTest {

  float[] TEST_ARR1 = {1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f};
  float[] TEST_ARR2 = {8f, 7f, 4f, 5f, 4f, 3f, 2f, 1f};

  float[] TEST_NEG_ARR1 = {-1, -2, -3, -4, -5, -6, -7, -8};

  float[] TEST_NEG_FLOAT = {8.2f, 7.2f, 4.4f, 5.5f, 4f, 3f, -2f, 1f};

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
    assertThat(product).isEqualTo(120);
  }

  @Test
  @DisplayName("Dot Product: Sequential and Parallel results match and equal -89.4 with float and negative")
  void dotTwoVectorNegativeArray() {
    var product = simdArrayOps.dotTwoVectorArrays(TEST_ARR1, TEST_NEG_FLOAT);
    var productParallel = simdArrayOpsParallel.dotTwoVectorArraysParallel(TEST_ARR1, TEST_NEG_FLOAT);
    System.out.printf(String.valueOf(product));
    assertThat(productParallel == product).isTrue();
    assertThat(product).isEqualTo(-89.8);
  }

  @Test
  @DisplayName("Dot Product: Sequential and Parallel results match and equal -204 for negative vectors")
  void dotTwoVectorNegativeFloatArray() {
    var product = simdArrayOps.dotTwoVectorArrays(TEST_ARR1, TEST_NEG_ARR1);
    var productParallel = simdArrayOpsParallel.dotTwoVectorArraysParallel(TEST_ARR1, TEST_NEG_ARR1);
    System.out.printf(String.valueOf(product));
    assertThat(productParallel == product).isTrue();
    assertThat(product).isEqualTo(-204);
  }


  @Test
  @DisplayName("Elementwise Operation ADD, SUB, MUL produce expected right result")
  void elementwiseOperation() {
    var sum = simdArrayOps.elementwiseOperation(TEST_ARR1, TEST_ARR2, VectorOperators.ADD);
    assertThat(sum).isEqualTo(new float[]{9, 9, 9, 9, 9, 9, 9, 9});
    var diff = simdArrayOps.elementwiseOperation(TEST_ARR1, TEST_ARR2, VectorOperators.SUB);
    assertThat(diff).isEqualTo(new float[]{-7, -5, -3, -1, 1, 3, 5, 7});
    var prod = simdArrayOps.elementwiseOperation(TEST_ARR1, TEST_ARR2, VectorOperators.MUL);
    assertThat(prod).isEqualTo(new float[]{8, 14, 18, 20, 20, 18, 14, 8});
    var sumParallel = simdArrayOpsParallel.elementwiseOperationParallel(TEST_ARR1, TEST_ARR2, VectorOperators.ADD);
    assertThat(sumParallel).isEqualTo(new float[]{9, 9, 9, 9, 9, 9, 9, 9});
    var diffParallel = simdArrayOpsParallel.elementwiseOperationParallel(TEST_ARR1, TEST_ARR2, VectorOperators.SUB);
    assertThat(diffParallel).isEqualTo(new float[]{-7, -5, -3, -1, 1, 3, 5, 7});
    var prodParallel = simdArrayOpsParallel.elementwiseOperationParallel(TEST_ARR1, TEST_ARR2, VectorOperators.MUL);
    assertThat(prodParallel).isEqualTo(new float[]{8, 14, 18, 20, 20, 18, 14, 8});
  }

  @Test
  @DisplayName("Elementwise Operation on Negative/Mixed Arrays ADD, SUB, MUL produce right result")
  void elementwiseOperationNegative() {
    var sum = simdArrayOps.elementwiseOperation(TEST_NEG_ARR1, TEST_NEG_FLOAT, VectorOperators.ADD);
    assertThat(sum).isEqualTo(new float[]{7.2f, 5.2f, 1.4f, 1.5f, -1f, -3f, -9f, -7f});
    var diff = simdArrayOps.elementwiseOperation(TEST_NEG_ARR1, TEST_NEG_FLOAT, VectorOperators.SUB);
    assertThat(diff).isEqualTo(new float[]{-9.2f, -9.2f, -7.4f, -9.5f, -9f, -9f, -5f, -9f});
    var prod = simdArrayOps.elementwiseOperation(TEST_NEG_ARR1, TEST_NEG_FLOAT, VectorOperators.MUL);
    assertThat(prod).isEqualTo(new float[]{-8.2f, -14.4f, -13.2f, -22.0f, -20f, -18f, 14f, -8f});
    var sumParallel = simdArrayOpsParallel.elementwiseOperationParallel(TEST_NEG_ARR1, TEST_NEG_FLOAT, VectorOperators.ADD);
    assertThat(sumParallel).isEqualTo(new float[]{7.2f, 5.2f, 1.4f, 1.5f, -1f, -3f, -9f, -7f});
    var diffParallel = simdArrayOpsParallel.elementwiseOperationParallel(TEST_NEG_ARR1, TEST_NEG_FLOAT, VectorOperators.SUB);
    assertThat(diffParallel).isEqualTo(new float[]{-9.2f, -9.2f, -7.4f, -9.5f, -9f, -9f, -5f, -9f});
    var prodParallel = simdArrayOpsParallel.elementwiseOperationParallel(TEST_NEG_ARR1, TEST_NEG_FLOAT, VectorOperators.MUL);
    assertThat(prodParallel).isEqualTo(new float[]{-8.2f, -14.4f, -13.2f, -22.0f, -20f, -18f, 14f, -8f});
  }


  @Test
  @DisplayName("Addition: Sequential and Parallel results match")
  void addTwoVectorArrays() {
    var sum = simdArrayOps.addTwoVectorArrays(TEST_ARR1, TEST_ARR2);
    var sumParallel = simdArrayOpsParallel.addTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
    assertThat(sum).isEqualTo(new float[]{9, 9, 9, 9, 9, 9, 9, 9});
    assertThat(Arrays.equals(sum, sumParallel)).isEqualTo(true);
  }

  @Test
  @DisplayName("Subtraction: Sequential and Parallel results match and values")
  void subTwoVectorArrays() {
    var diff = simdArrayOps.subTwoVectorArrays(TEST_ARR1, TEST_ARR2);
    var diffParallel = simdArrayOpsParallel.subTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
    assertThat(diff).isEqualTo(new float[]{-7, -5, -3, -1, 1, 3, 5, 7});
    assertThat(Arrays.equals(diff, diffParallel)).isEqualTo(true);

  }

  @Test
  @DisplayName("Multiplication: Sequential and Parallel results match and values")
  void mulTwoVectorArrays() {
    var prod = simdArrayOps.mulTwoVectorArrays(TEST_ARR1, TEST_ARR2);
    var prodParallel = simdArrayOpsParallel.mulTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
    assertThat(prod).isEqualTo(new float[]{8, 14, 18, 20, 20, 18, 14, 8});
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

    assertThat(result[0]).isEqualTo(6);
    assertThat(result[5000000 / 2]).isEqualTo(6);
    assertThat(Arrays.equals(result, resultparallel)).isEqualTo(true);
    for (float value : result) {
      assertThat(value).isEqualTo(6);
    }
  }

  @Test
  @DisplayName("Dot Product Parallel: Throws for mismatched array lengths")
  void dotTwoVectorArraysParallelThrowsForMismatchedLengths() {
    // Define arrays of mismatched lengths.
    float[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8};
    float[] arr2 = {8, 7, 6, 5, 4}; // mismatched length

    // Expect an IllegalArgumentException because of the precondition check.
    assertThrows(Exception.class, () ->
            simdArrayOpsParallel.dotTwoVectorArraysParallel(arr1, arr2));
  }


}