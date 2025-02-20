package org.fungover.breeze.simd;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

<<<<<<< HEAD
=======
import static org.fungover.breeze.simd.SimdUtils.chunkElementwise;
import static org.fungover.breeze.simd.SimdUtils.dotProductForSpecies;
>>>>>>> c0f78e8 (reformat)

public class SimdArrayOps {

    SimdUtils simdUtils = new SimdUtils();
    static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

<<<<<<< HEAD
    /**
     * Perform a generic elementwise operation on two arrays of equal length. The operation is applied
     * in bulk using vectorized processing.
     *
     * @param arr1 the first input array
     * @param arr2 the second input array
     * @param op   the binary operator to apply (e.g. ADD, SUB, MUL)
     * @return a new array that containing the result of applying op elementwise
     * @throws NullPointerException if binary Operator is null
     */
    public float[] elementwiseOperation(float[] arr1, float[] arr2,
                                        VectorOperators.Binary op) {
        if (op == null) throw new NullPointerException("Binary Operator can not be null");
        simdUtils.checkNullInputs(arr1,arr2);
        int start = 0;
        int end = arr1.length;
        float[] result = new float[arr1.length];
        simdUtils.chunkElementwise(arr1, arr2, result, start, end, op);
        return result;
    }

    /**
     * Perform an addition operation on two arrays of equal length. The operation is applied in bulk
     * using vectorized processing.
     *
     * @param arr1 the first input array
     * @param arr2 the second input array
     * @return the sum of addition on two float Arrays
     */
    public float[] addTwoVectorArrays(float[] arr1, float[] arr2) {
        simdUtils.checkNullInputs(arr1,arr2);
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
        simdUtils.checkNullInputs(arr1,arr2);
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
        simdUtils.checkNullInputs(arr1,arr2);
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
        simdUtils.checkNullInputs(arr1,arr2);
        int end = arr1.length;
        int start = 0;
        return simdUtils.dotProductForSpecies(arr1, arr2, start, end);
    }

}
=======
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
        chunkElementwise(arr1, arr2, result, start, end, op);
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
        return dotProductForSpecies(arr1, arr2, start, end);
    }

}
>>>>>>> c0f78e8 (reformat)
