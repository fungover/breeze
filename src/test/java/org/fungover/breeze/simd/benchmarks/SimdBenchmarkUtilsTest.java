package org.fungover.breeze.simd.benchmarks;

import org.fungover.breeze.simd.SimdArrayOps;
import org.fungover.breeze.simd.SimdArrayOpsParallel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimdBenchmarkUtilsTest {

    private float[] arr1;
    private float[] arr2;
    private SimdArrayOps simdOps;
    private SimdArrayOpsParallel simdOpsParallel;
    private static final int ARRAY_SIZE = 1024; // smaller array for unit testing
    private static final int ITERATIONS = 5;

    @BeforeEach
    public void setUp() {
        arr1 = new float[ARRAY_SIZE];
        arr2 = new float[ARRAY_SIZE];
        // Populate the arrays with random data
        for (int i = 0; i < ARRAY_SIZE; i++) {
            arr1[i] = (float) Math.random();
            arr2[i] = (float) Math.random();
        }
        simdOps = new SimdArrayOps();
        simdOpsParallel = new SimdArrayOpsParallel();
    }

    @Test
    public void testBenchmarkSequentialAddition() {
        long avgTime = SimdBenchmarkUtils.benchmarkSequentialAddition(simdOps, arr1, arr2, ITERATIONS);
        assertTrue(avgTime > 0, "must be positive");
    }

    @Test
    public void testBenchmarkParallelAddition() {
        long avgTime = SimdBenchmarkUtils.benchmarkParallelAddition(simdOpsParallel, arr1, arr2, ITERATIONS);
        assertTrue(avgTime > 0, "must be positive");
    }

    @Test
    public void testBenchmarkSequentialDotProduct() {
        long avgTime = SimdBenchmarkUtils.benchmarkSequentialDotProduct(simdOps, arr1, arr2, ITERATIONS);
        assertTrue(avgTime > 0, "must be positive");
    }

    @Test
    public void testBenchmarkParallelDotProduct() {
        long avgTime = SimdBenchmarkUtils.benchmarkParallelDotProduct(simdOpsParallel, arr1, arr2, ITERATIONS);
        assertTrue(avgTime > 0, "must be positive");
    }
}
