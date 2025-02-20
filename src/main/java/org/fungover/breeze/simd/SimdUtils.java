package org.fungover.breeze.simd;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorOperators;

import java.util.Objects;

import static org.fungover.breeze.simd.SimdArrayOps.SPECIES;

public class SimdUtils {

    public void chunkElementwise(
            float[] arr1, float[] arr2, float[] result, int start, int end, VectorOperators.Binary op) {
        if (arr1.length != arr2.length) throw new IllegalArgumentException("Input arrays must have the same length");
        checkNullInputs(arr1, arr2);
        int i = start;
        // Process vectorized chunks.
        for (; i <= end - SPECIES.length(); i += SPECIES.length()) {
            FloatVector v1 = FloatVector.fromArray(SPECIES, arr1, i);
            FloatVector v2 = FloatVector.fromArray(SPECIES, arr2, i);
            FloatVector res = v1.lanewise(op, v2);
            res.intoArray(result, i);
        }
        // Process any remaining elements with mask to fill out
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
    public float dotProductForSpecies(float[] arr1, float[] arr2, int start, int end) {
        if (arr1.length != arr2.length) throw new IllegalArgumentException("Input arrays must have the same length");
        checkNullInputs(arr1, arr2);
        float sum = 0f;
        int i = start;
        // Process chunks.
        for (; i <= end - SPECIES.length(); i += SPECIES.length()) {
            FloatVector v1 = FloatVector.fromArray(SPECIES, arr1, i);
            FloatVector v2 = FloatVector.fromArray(SPECIES, arr2, i);
            sum += v1.mul(v2).reduceLanes(VectorOperators.ADD);
        }
        // Process any remaining elements with mask to fill out species.
        if (i < end) {
            VectorMask<Float> m = SPECIES.indexInRange(i, end);
            FloatVector v1 = FloatVector.fromArray(SPECIES, arr1, i, m);
            FloatVector v2 = FloatVector.fromArray(SPECIES, arr2, i, m);
            sum += v1.mul(v2).reduceLanes(VectorOperators.ADD);
        }
        return sum;
    }

    /**
     *nullChecker for arrays, if either is null then we throw and return a message that specifies which array was null.
     *Since primitive types like float cannot be null, you can only check the array references for null.
     *@throws NullPointerException if either array is null
     **/
    public void checkNullInputs(float[] arr1, float[] arr2) {
        Objects.requireNonNull(arr1, "arr1 must not be null");
        Objects.requireNonNull(arr2, "arr2 must not be null");
    }


}
