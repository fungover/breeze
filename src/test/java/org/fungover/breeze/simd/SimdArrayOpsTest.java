package org.fungover.breeze.simd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import jdk.incubator.vector.VectorOperators;
import org.junit.jupiter.api.Test;

class SimdArrayOpsTest {

  float[] TEST_ARR1 = {1, 2, 3, 4, 5, 6, 7, 8};
  float[] TEST_ARR2 = {8, 7, 6, 5, 4, 3, 2, 1};

  float[] TEST_SMALL_ARR2 = {1, 2};
  float[] TEST_SMALL_ARR1 = {1, 2};

  SimdArrayOps simdArrayOps = new SimdArrayOps();
  SimdArrayOpsParallel simdArrayOpsParallel = new SimdArrayOpsParallel();

  @Test
  void dotTwoVectorArrays() {
    var product = simdArrayOps.dotTwoVectorArrays(TEST_ARR1, TEST_ARR2);
    var productParallel = simdArrayOpsParallel.dotTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
    assertThat(productParallel == product).isTrue();
    assertThat(product).isEqualTo(120);
  }

  @Test
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
  void addTwoVectorArrays() {
    var sum = simdArrayOps.addTwoVectorArrays(TEST_ARR1, TEST_ARR2);
    var sumParallel = simdArrayOpsParallel.addTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
    assertThat(sum).isEqualTo(new float[]{9, 9, 9, 9, 9, 9, 9, 9});
    assertThat(Arrays.equals(sum, sumParallel)).isEqualTo(true);
  }

  @Test
  void subTwoVectorArrays() {
    var diff = simdArrayOps.subTwoVectorArrays(TEST_ARR1, TEST_ARR2);
    var diffParallel = simdArrayOpsParallel.subTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
    assertThat(diff).isEqualTo(new float[]{-7, -5, -3, -1, 1, 3, 5, 7});
    assertThat(Arrays.equals(diff, diffParallel)).isEqualTo(true);

  }

  @Test
  void mulTwoVectorArrays() {
    var prod = simdArrayOps.mulTwoVectorArrays(TEST_ARR1, TEST_ARR2);
    var prodParallel = simdArrayOpsParallel.mulTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2);
    assertThat(prod).isEqualTo(new float[]{8, 14, 18, 20, 20, 18, 14, 8});
    assertThat(Arrays.equals(prod, prodParallel)).isEqualTo(true);
  }

  @Test
  void mulTwoVectorSmallArrays() {
    var prod = simdArrayOps.mulTwoVectorArrays(TEST_SMALL_ARR1, TEST_SMALL_ARR2);
    var prodparallel = simdArrayOpsParallel.mulTwoVectorArraysParallel(TEST_SMALL_ARR1, TEST_SMALL_ARR2);
    assertThat(prod).isEqualTo(new float[]{1, 4});
    assertThat(Arrays.equals(prod, prodparallel)).isEqualTo(true);
  }

  @Test
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
}