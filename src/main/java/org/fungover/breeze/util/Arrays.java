package org.fungover.breeze.util;

import java.lang.reflect.Array;

public class Arrays {

    /**
     * Transposes a 2D array, converting rows into columns and vice versa.
     *
     * <p>For example:
     * <pre>
     * Input:
     * [[1, 2, 3],
     *  [4, 5, 6]]
     *
     * Output:
     * [[1, 4],
     *  [2, 5],
     *  [3, 6]]
     * </pre>
     *
     * @param array The 2D array to transpose.
     * @param <T>   The type of elements in the array.
     * @return The transposed 2D array, where rows become columns.
     * @throws IllegalArgumentException If the input array contains null rows or has inconsistent row lengths.
     */
    public static <T> T[][] transpose(T[][] array) {
        // Check if the input array is null or empty
        if (array == null || array.length == 0) {
            return array;
        }

        // Check if the first row exists and has elements
        if (array[0].length == 0) {
            return array;
        }

        // Determine dimensions of the array
        int rows = array.length;
        int cols = array[0].length;

        // Validate that all rows have the same length
        for (T[] row : array) {
            if (row == null) {
                throw new IllegalArgumentException("Irregular array: null rows are not allowed");
            }
            if (row.length != cols) {
                throw new IllegalArgumentException("Irregular array: all rows must have the same length");
            }
        }

        // Create a new 2D array with transposed dimensions (cols x rows)
        @SuppressWarnings("unchecked")
        T[][] transposed = (T[][]) Array.newInstance(array.getClass().getComponentType().getComponentType(), cols, rows);

        // Transpose the array by swapping rows and columns
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = array[i][j];
            }
        }
        return transposed;
    }


    public static <T> T[][] transposeFaster(T[][] array) {
        // Check if the input array is null or empty
        if (array == null || array.length == 0) {
            return array;
        }

        // Check if the first row exists and has elements
        if (array[0].length == 0) {
            return array;
        }

        // Determine dimensions of the array
        int rows = array.length;
        int cols = array[0].length;

        // Validate that all rows have the same length
        for (T[] row : array) {
            if (row == null) {
                throw new IllegalArgumentException("Irregular array: null rows are not allowed");
            }
            if (row.length != cols) {
                throw new IllegalArgumentException("Irregular array: all rows must have the same length");
            }
        }

        // Create a new 2D array with transposed dimensions (cols x rows)
        @SuppressWarnings("unchecked")
        T[][] transposed = (T[][]) Array.newInstance(array.getClass().getComponentType().getComponentType(), cols, rows);

        // Transpose the array by swapping rows and columns
        final int BLOCK_SIZE = 32;
        for (int i = 0; i < rows; i += BLOCK_SIZE) {
            for (int j = 0; j < cols; j += BLOCK_SIZE) {
                for (int ii = i; ii < Math.min(i + BLOCK_SIZE, rows); ii++) {
                    for (int jj = j; jj < Math.min(j + BLOCK_SIZE, cols); jj++) {
                        transposed[jj][ii] = array[ii][jj];
                    }
                }
            }
        }
        return transposed;
    }
}
/**
 * Efficiently transposes a 2D array using a blocked approach to improve cache utilization.
 *
 * <p>Standard row-by-row transposition can lead to inefficient memory access patterns.
 * This implementation processes the array in 32×32 blocks, reducing cache misses and
 * improving performance, especially for large datasets.</p>
 *
 * <p>Example:</p>
 * <pre>
 * Input:
 * [[1, 2, 3],
 *  [4, 5, 6]]
 *
 * Output:
 * [[1, 4],
 *  [2, 5],
 *  [3, 6]]
 * </pre>
 *
 * @param array The 2D array to transpose.
 * @param <T>   The type of elements in the array.
 * @return The transposed 2D array with optimized memory access.
 * @throws IllegalArgumentException If the input array contains null rows or has inconsistent row lengths.
 */




