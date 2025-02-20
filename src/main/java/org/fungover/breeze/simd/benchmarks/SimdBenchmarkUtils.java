package org.fungover.breeze.simd.benchmarks;

import org.fungover.breeze.simd.SimdArrayOps;
import org.fungover.breeze.simd.SimdArrayOpsParallel;

public class SimdBenchmarkUtils {

    /**
     * Benchmarks the sequential addition operation.
     *
     * @param simdOps    the instance of SimdArrayOps
     * @param arr1       first input array
     * @param arr2       second input array
     * @param iterations number of iterations to average
     * @return average time per iteration in nanoseconds
     */
    public static long benchmarkSequentialAddition(SimdArrayOps simdOps, float[] arr1, float[] arr2, int iterations) {
        long totalTime = 0;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            simdOps.addTwoVectorArrays(arr1, arr2);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }
        return totalTime / iterations;
    }

    /**
     * Benchmarks the parallel addition operation.
     *
     * @param simdOpsParallel the instance of SimdArrayOpsParallel
     * @param arr1            first input array
     * @param arr2            second input array
     * @param iterations      number of iterations to average
     * @return average time per iteration in nanoseconds
     */
    public static long benchmarkParallelAddition(SimdArrayOpsParallel simdOpsParallel, float[] arr1, float[] arr2, int iterations) {
        long totalTime = 0;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            simdOpsParallel.addTwoVectorArraysParallel(arr1, arr2);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }
        return totalTime / iterations;
    }

    /**
     * Benchmarks the sequential dot product operation.
     *
     * @param simdOps    the instance of SimdArrayOps
     * @param arr1       first input array
     * @param arr2       second input array
     * @param iterations number of iterations to average
     * @return average time per iteration in nanoseconds
     */
    public static long benchmarkSequentialDotProduct(SimdArrayOps simdOps, float[] arr1, float[] arr2, int iterations) {
        long totalTime = 0;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            simdOps.dotTwoVectorArrays(arr1, arr2);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }
        return totalTime / iterations;
    }

    /**
     * Benchmarks the parallel dot product operation.
     *
     * @param simdOpsParallel the instance of SimdArrayOpsParallel
     * @param arr1            first input array
     * @param arr2            second input array
     * @param iterations      number of iterations to average
     * @return average time per iteration in nanoseconds
     */
    public static long benchmarkParallelDotProduct(SimdArrayOpsParallel simdOpsParallel, float[] arr1, float[] arr2, int iterations) {
        long totalTime = 0;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            simdOpsParallel.dotTwoVectorArraysParallel(arr1, arr2);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }
        return totalTime / iterations;
    }
}
