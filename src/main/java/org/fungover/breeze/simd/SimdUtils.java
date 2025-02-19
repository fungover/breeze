package org.fungover.breeze.simd;
import static org.fungover.breeze.simd.SimdArrayOps.SPECIES;

import java.util.Random;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorOperators;

public class SimdUtils {





  static void processSegmentElementwise(
      float[] arr1, float[] arr2, float[] result, int start, int end, VectorOperators.Binary op) {
    int i = start;
    // Process vectorized chunks.
    for (; i <= end - SPECIES.length(); i += SPECIES.length()) {
      FloatVector v1 = FloatVector.fromArray(SPECIES, arr1, i);
      FloatVector v2 = FloatVector.fromArray(SPECIES, arr2, i);
      FloatVector res = v1.lanewise(op, v2);
      res.intoArray(result, i);
    }
    // Process any remaining elements.
    if (i < end) {
      VectorMask<Float> m = SPECIES.indexInRange(i, end);
      FloatVector v1 = FloatVector.fromArray(SPECIES, arr1, i, m);
      FloatVector v2 = FloatVector.fromArray(SPECIES, arr2, i, m);
      FloatVector res = v1.lanewise(op, v2);
      res.intoArray(result, i, m);
    }
  }

  /**
   * Helper method to compute the dot product for a segment of two arrays.
   *
   * @param arr1  the first input array.
   * @param arr2  the second input array.
   * @param start the starting index (inclusive) of the segment.
   * @param end   the ending index (exclusive) of the segment.
   * @return the dot product computed for the given segment.
   */
  static float computeDotSegment(float[] arr1, float[] arr2, int start, int end) {
    float sum = 0f;
    int i = start;
    // Process vectorized chunks.
    for (; i <= end - SPECIES.length(); i += SPECIES.length()) {
      FloatVector v1 = FloatVector.fromArray(SPECIES, arr1, i);
      FloatVector v2 = FloatVector.fromArray(SPECIES, arr2, i);
      sum += v1.mul(v2).reduceLanes(VectorOperators.ADD);
    }
    // Process any remaining elements.
    if (i < end) {
      VectorMask<Float> m = SPECIES.indexInRange(i, end);
      FloatVector v1 = FloatVector.fromArray(SPECIES, arr1, i, m);
      FloatVector v2 = FloatVector.fromArray(SPECIES, arr2, i, m);
      sum += v1.mul(v2).reduceLanes(VectorOperators.ADD);
    }
    return sum;
  }




}
