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
    if (arr1.length != arr2.length) {
      throw new IllegalArgumentException("Input arrays must have the same length");
    }
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
    if (arr1.length != arr2.length) {
      throw new IllegalArgumentException("Input arrays must have the same length");
    }
    int end = arr1.length;
    int start = 0;
    return computeDotSegment(arr1, arr2, start, end);
  }

}
