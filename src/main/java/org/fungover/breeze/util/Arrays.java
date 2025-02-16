package org.fungover.breeze.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A class containing static Array utility functions.
 */
public class Arrays {

    /**
     * Don't let anyone instantiate this class
     */
    private Arrays() {}

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
     *
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

    /**
     * Creates a new 2D array with the specific number of rows and columns.
     * @param componentType The type of element in the array.
     * @param row Number of row in the array.
     * @param col Number of column in the array.
     * @param <T> The type of the array elements.
     * @return A new empty 2D array of the specific type and size
     */
    @SuppressWarnings("unchecked")
    private static <T> T[][] create2DArray(Class<?> componentType, int row, int col) {
        return (T[][]) Array.newInstance(componentType, row, col);
    }

    /**
     * Creates a new 1D array with the specific number of rows.
     * @param componentType The type of element in the array.
     * @param length The length of the array.
     * @param <T> The type of the array elements.
     * @return A new empty 1D array of the specific type and size.
     */
    @SuppressWarnings("unchecked")
    private static <T> T[] create1DArray(Class<?> componentType, int length) {
        return (T[]) Array.newInstance(componentType, length);
    }

    /**
     * Splits an array into smaller array-chunks of specific size.
     * The last chunk may be smaller if the array length is not evenly divisible.
     * @param array The input array to be chunked.
     * @param size The size of each chunk.
     * @param <T> The type of elements in the array.
     * @return A 2D array where each inner array is a chunk of the original array.
     * @throws IllegalArgumentException if the array is null, contains null elements or size is negative or zero.
     */

    public static <T> T[][] chunk(T[] array, int size) {

        if (array == null ) {
            throw new IllegalArgumentException("Input array must not be null");
        } else {
            for(T element : array) {
                if (element == null) {
                    throw new IllegalArgumentException("Element must not be null");
                }
            }
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }

        if (array.length == 0) {
            return create2DArray(array.getClass().getComponentType(), 0,0);
        }

        int numberOfChunks = (int) Math.ceil((double) array.length / size);
        Class<?> componentType = array.getClass().getComponentType();

        T[][] chunks = create2DArray(componentType, numberOfChunks, size);

        for (int i = 0; i < numberOfChunks; i++) {
            int start = i * size;
            int length = Math.min(array.length - start, size);

            T[] chunk = create1DArray(componentType, length);

            System.arraycopy(array, start, chunk, 0, length);
            chunks[i] = chunk;
        }

        return chunks;

    }

    /**
     * Splits a list into smaller list-chunks of specific size.
     * The last chunk may be smaller if the list length is not evenly divisible.
     * @param list The input list to be chunked.
     * @param size The size of each chunk.
     * @param <T> The type of elements in the list.
     * @return A list of list where each inner list is a chunk of the original list.
     * @throws IllegalArgumentException if the list is null, contains null elements or size is negative or zero.
     */
    public static <T>List<List<T>> chunkList(List<T> list, int size) {
        if(list == null) {
            throw new IllegalArgumentException("Input list must not be null");
        } else {
            for(T element : list) {
                if (element == null) {
                    throw new IllegalArgumentException("Element must not be null");
                }
            }
        }

        if (size <= 0)
            throw new IllegalArgumentException("Size must be greater than 0");

        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i+= size) {
            chunks.add(List.copyOf(list.subList(i, Math.min(list.size(), i + size))));
        }

        return Collections.unmodifiableList(chunks);
    }

}
