package org.fungover.breeze.simd;

import static org.fungover.breeze.simd.SimdUtils.computeDotSegment;
import static org.fungover.breeze.simd.SimdUtils.processSegmentElementwise;

import java.util.Arrays;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class SimdArrayOps {


  static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

  /**
   * Perform a generic elementwise operation on two arrays of equal length. The operation is applied
   * in bulk using vectorized processing.
   *
   * @param arr1 the first input array
   * @param arr2 the second input array
   * @param op   the binary operator to apply (e.g. ADD, SUB, MUL)
   * @return a new array that containing the result of applying op elementwise
   */
  public float[] elementwiseOperation(float[] arr1, float[] arr2,
      VectorOperators.Binary op) {
    int start = 0;
    int end = arr1.length;
    float[] result = new float[arr1.length];
    processSegmentElementwise(arr1, arr2, result, start, end, op);
    return result;
  }

  /**
   * Perform an addition operation on two arrays of equal length. The operation is applied in bulk
   * using vectorized processing.
   *
   * @param arr1 the first input array
   * @param arr2 the second input array
   * @return the result of multiplication on two float Arrays
   */
  public float[] addTwoVectorArrays(float[] arr1, float[] arr2) {
    return elementwiseOperation(arr1, arr2, VectorOperators.ADD);
  }

  /**
   * Perform a subtraction operation on two arrays of equal length. The operation is applied in bulk
   * using vectorized processing.
   *
   * @param arr1 the first input array
   * @param arr2 the second input array
   * @return the result of subtraction on two float Arrays
   */
  public float[] subTwoVectorArrays(float[] arr1, float[] arr2) {
    return elementwiseOperation(arr1, arr2, VectorOperators.SUB);
  }

  /**
   * Perform a multiplication operation on two arrays of equal length. The operation is applied in
   * bulk using vectorized processing.
   *
   * @param arr1 the first input aray
   * @param arr2 the second input array
   * @return the result of multiplication on two float Arrays
   */
  public float[] mulTwoVectorArrays(float[] arr1, float[] arr2) {
    return elementwiseOperation(arr1, arr2, VectorOperators.MUL);
  }

  /**
   * Performs dot product on two equally sized float arrays using the Vector API.
   *
   * @param arr1 the first input array
   * @param arr2 the second input array
   * @return the dot product of the two float arrays
   */
  public float dotTwoVectorArrays(float[] arr1, float[] arr2) {
    int end = arr1.length;
    int start = 0;
    return computeDotSegment(arr1, arr2, start, end);
  }


  public static void main(String[] args) {
    float[] TEST_ARR1 = {1, 2, 3, 4, 5, 6, 7, 8};
    float[] TEST_ARR2 = {8, 7, 6, 5, 4, 3, 2, 1};
    SimdArrayOpsParallel simdArrayOpsParallel = new SimdArrayOpsParallel();
    SimdArrayOps simdArrayOps = new SimdArrayOps();
    System.out.println("Add");
    System.out.println(Arrays.toString(simdArrayOps.addTwoVectorArrays(TEST_ARR1, TEST_ARR2)));
    System.out.println("sub");
    System.out.println(Arrays.toString(simdArrayOps.subTwoVectorArrays(TEST_ARR1, TEST_ARR2)));
    System.out.println("mul");
    System.out.println(Arrays.toString(simdArrayOps.mulTwoVectorArrays(TEST_ARR1, TEST_ARR2)));
    System.out.println("dot");
    System.out.println(simdArrayOps.dotTwoVectorArrays(TEST_ARR1, TEST_ARR2));
    System.out.println("----------PARALLEL----------");
    System.out.println(
        Arrays.toString(simdArrayOpsParallel.addTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2)));
    System.out.println("sub");
    System.out.println(
        Arrays.toString(simdArrayOpsParallel.subTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2)));
    System.out.println("mul");
    System.out.println(
        Arrays.toString(simdArrayOpsParallel.mulTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2)));
    System.out.println("dot");
    System.out.println(simdArrayOpsParallel.dotTwoVectorArraysParallel(TEST_ARR1, TEST_ARR2));
    System.out.println("------------TEST------------------");
    float[] TEST_ARR3 = {1, 2, 3, 4, 5, 6, 7, 8};
    float[] TEST_ARR4 = {8, 7, 6, 5, 4, 3, 2, 1};
    System.out.println("Add");
    System.out.println(Arrays.toString(simdArrayOps.addTwoVectorArrays(TEST_ARR3, TEST_ARR4)));
  }


}
