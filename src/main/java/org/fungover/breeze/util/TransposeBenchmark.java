package org.fungover.breeze.util;

import java.util.Random;
import java.util.function.Supplier;

public class TransposeBenchmark {
    private static final Random RAND = new Random(); // Reusable Random instance

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000}; // Small, Medium, and Large

        for (int size : sizes) {
            System.out.println("\nTesting " + size + "×" + size + " matrix...");
            Integer[][] matrix = generateMatrix(size, size);

            // Warm-up runs to reduce JVM optimizations impact
            Arrays.transpose(matrix);
            Arrays.transposeFaster(matrix);

            // Benchmark both methods
            benchmark("Standard Transpose", () -> Arrays.transpose(matrix));
            benchmark("Faster Transpose", () -> Arrays.transposeFaster(matrix));
        }
    }

    /**
     * Benchmarks a given transposition method.
     *
     * @param name   Name of the method being tested.
     * @param method The transposition function as a Supplier.
     */
    private static void benchmark(String name, Supplier<Integer[][]> method) {
        final int ITERATIONS = 5; //Ran multiple times for fair average
        long totalTime = 0;

        for (int i = 0; i < ITERATIONS; i++) {
            long startTime = System.nanoTime();
            method.get(); //Execute transposition
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }
        double avgTimeNs = (totalTime / ITERATIONS) / 1000000.0;
        System.out.println("\nBenchmark " + name + ": " + avgTimeNs + " ms");
    }

    /**
     * Generates a random matrix of given dimensions.
     *
     * @param rows Number of rows.
     * @param cols Number of columns.
     * @return A 2D Integer array filled with random values.
     */
    private static Integer[][] generateMatrix(int rows, int cols) {
        Integer[][] matrix = new Integer[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = RAND.nextInt(100);
            }
        }
        return matrix;
    }
}
