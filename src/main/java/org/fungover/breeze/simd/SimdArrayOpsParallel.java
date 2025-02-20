package org.fungover.breeze.simd;

import static org.fungover.breeze.simd.SimdUtils.dotProductForSpecies;
import static org.fungover.breeze.simd.SimdUtils.chunkElementwise;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class SimdArrayOpsParallel {


  static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

  /**
   * Perform a generic elementwise operation in parallel on two arrays of equal length. The
   * operation is applied in bulk using vectorized processing.
   *
   * @param arr1 the first input array
   * @param arr2 the second input array
   * @param op   the binary operator to apply (e.g. ADD, SUB, MUL)
   * @return a new array containing the result of applying op elementwise
   */
  public float[] elementwiseOperationParallel(float[] arr1, float[] arr2,
      VectorOperators.Binary op) {

    int n = arr1.length;
    float[] result = new float[n];
    // Get the available workers
    int numThreads = Runtime.getRuntime().availableProcessors();
    Thread[] threads = new Thread[numThreads];
    // Get the chunksize for each worker
    int chunkSizePerWorker = (n + numThreads - 1) / numThreads;

    for (int t = 0; t < numThreads; t++) {
      //Make the worker start at the allocated position
      final int start = t * chunkSizePerWorker;
      //Make designate the end for that worker
      final int end = Math.min(n, start + chunkSizePerWorker);
      //Give worker load
      threads[t] = new Thread(() -> chunkElementwise(arr1, arr2, result, start, end, op));
      //compute
      threads[t].start();
    }

    // Wait for all workers to complete
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    return result;
  }

  /**
   * Perform an addition operation in parallel on two arrays of equal length. The operation is
   * applied in bulk using vectorized processing.
   *
   * @param arr1 the first input array
   * @param arr2 the second input array
   * @return the result of multiplication on two float Arrays
   */
  public  float[] addTwoVectorArraysParallel(float[] arr1, float[] arr2) {
    return elementwiseOperationParallel(arr1, arr2, VectorOperators.ADD);
  }

  /**
   * Perform a subtraction operation in parallel on two arrays of equal length. The operation is
   * applied in bulk using vectorized processing.
   *
   * @param arr1 the first input array
   * @param arr2 the second input array
   * @return the result of subtraction on two float Arrays
   */
  public  float[] subTwoVectorArraysParallel(float[] arr1, float[] arr2) {
    return elementwiseOperationParallel(arr1, arr2, VectorOperators.SUB);
  }

  /**
   * Perform a multiplication operation in parallel on two arrays of equal length. The operation is
   * applied in bulk using vectorized processing.
   *
   * @param arr1 the first input array
   * @param arr2 the second input array
   * @return the result of multiplication on two float Arrays
   */
  public  float[] mulTwoVectorArraysParallel(float[] arr1, float[] arr2) {
    return elementwiseOperationParallel(arr1, arr2, VectorOperators.MUL);
  }

  /**
   * Performs dot product in parallel on two equally sized float arrays using the Vector API.
   *
   * @param arr1 the first input array
   * @param arr2 the second input array
   * @return the dot product of the two float arrays
   */
  public  float dotTwoVectorArraysParallel(float[] arr1, float[] arr2) {

    int n = arr1.length;
    int numThreads = Runtime.getRuntime().availableProcessors();
    Thread[] threads = new Thread[numThreads];

    //calculate the chynck for each worker
    int chunkSize = (n + numThreads - 1) / numThreads;
    //array for partial sum calcs
    float[] partialSums = new float[numThreads];

    for (int t = 0; t < numThreads; t++) {
      final int threadIndex = t;
      final int start = t * chunkSize;
      final int end = Math.min(n, start + chunkSize);
      threads[t] = new Thread(
          () -> partialSums[threadIndex] = dotProductForSpecies(arr1, arr2, start, end));
      threads[t].start();
    }

    // Wait for all threads.
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    // Combine partial dot product sums.
    float dot = 0f;
    for (float s : partialSums) {
      dot += s;
    }
    return dot;
  }
}


