package org.fungover.breeze.simd.bench;

import java.util.Random;
import org.fungover.breeze.simd.SimdArrayOps;
import org.fungover.breeze.simd.SimdArrayOpsParallel;

public class BenchmarkSimdOps {

  private static final int SIZE = 10000000;
  private static final int ITERATIONS = 10;
  private static float[] arr1;
  private static float[] arr2;


  public static void main(String[] args) {
    arr1 = new float[SIZE];
    arr2 = new float[SIZE];
    Random rnd = new Random(1234);
    for (int i = 0; i < SIZE; i++) {
      arr1[i] = rnd.nextFloat();
      arr2[i] = rnd.nextFloat();
    }

    SimdArrayOpsParallel simdArrayOpsParallel = new SimdArrayOpsParallel();
    SimdArrayOps simdArrayOps = new SimdArrayOps();

    // Some Warm up to make it to not have "cold start" results https://www.baeldung.com/java-jvm-warmup
    for (int i = 0; i < 5; i++) {
      simdArrayOps.addTwoVectorArrays(arr1, arr2);
      simdArrayOps.dotTwoVectorArrays(arr1, arr2);
      simdArrayOpsParallel.addTwoVectorArraysParallel(arr1, arr2);
      simdArrayOpsParallel.dotTwoVectorArraysParallel(arr1, arr2);
      scalarAdd(arr1, arr2);
      scalarDot(arr1, arr2);
    }

    // Run benchmarks
    benchmark("Sequential Add", () -> simdArrayOps.addTwoVectorArrays(arr1, arr2));
    benchmark("Sequential Dot", () -> {
      float d = simdArrayOps.dotTwoVectorArrays(arr1, arr2);
      return new float[]{d};
    });
    benchmark("Parallel Add", () -> simdArrayOpsParallel.addTwoVectorArraysParallel(arr1, arr2));
    benchmark("Parallel Dot", () -> {
      float d = simdArrayOpsParallel.dotTwoVectorArraysParallel(arr1, arr2);
      return new float[]{d};
    });
    benchmark("Scalar Add", () -> scalarAdd(arr1, arr2));
    benchmark("Scalar Dot", () -> {
      float d = scalarDot(arr1, arr2);
      return new float[]{d};
    });
  }

  /**
   * benchmark that runs the provided operation for a number of iterations, and prints the average
   * execution time in ns (Nano seconds)
   *
   * @param label A description for the benchmark.
   * @param op    A lambda that performs the operation to be benchmarked.
   */
  private static void benchmark(String label, Operation op) {
    long startTime = System.nanoTime();
    Object result = null;
    for (int i = 0; i < ITERATIONS; i++) {
      result = op.run();
    }
    long endTime = System.nanoTime();
    long avgTime = (endTime - startTime) / ITERATIONS;
    System.out.println(
        label + ": " + avgTime + " ns (last result = " + arrayToString(result) + ")");
  }

  private static String arrayToString(Object obj) {
    if (obj instanceof float[]) {
      float[] arr = (float[]) obj;
      if (arr.length == 1) {
        return String.valueOf(arr[0]);
      }
      return "Array[" + arr.length + "]";
    }
    return String.valueOf(obj);
  }

  /**
   * "normal" elementwise calculating without using Vectors
   */
  private static float[] scalarAdd(float[] a, float[] b) {
    int n = a.length;
    float[] result = new float[n];
    for (int i = 0; i < n; i++) {
      result[i] = a[i] + b[i];
    }
    return result;
  }

  /**
   * "normal" calculating dot product without using Vectors
   */
  private static float scalarDot(float[] a, float[] b) {
    int n = a.length;
    float dot = 0f;
    for (int i = 0; i < n; i++) {
      dot += a[i] * b[i];
    }
    return dot;
  }

  /**
   * Functional interface used to pass operations into the benchmark helper.
   */
  @FunctionalInterface
  private interface Operation {

    Object run();
  }
}
