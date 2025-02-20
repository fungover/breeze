package org.fungover.breeze.util;
import org.fungover.breeze.control.Tuple2;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.function.BiFunction;
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
    private Arrays() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Combines corresponding elements from two arrays into an array of Tuple2 objects.
     * <p>
     * Each element from the first array is paired with the corresponding element from the second array
     * to form a Tuple2. The resulting array will have the same length as the input arrays.
     * </p>
     *
     * @param <T> The type of elements in the first array.
     * @param <U> The type of elements in the second array.
     * @param first The first input array.
     * @param second The second input array.
     * @return An array of {@code Tuple2<T, U>} where each tuple contains one element from each input array.
     * @throws IllegalArgumentException If either input array is null or if they have different lengths.
     */
    public static <T extends Comparable<? super T> & Serializable,
            U extends Comparable<? super U> & Serializable> Tuple2<T, U>[] zip(T[] first, U[] second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Input arrays cannot be null.");
        }
        if (first.length != second.length) {
            throw new IllegalArgumentException("Arrays must have the same length.");
        }

        // Creating a generic array using reflection
        @SuppressWarnings("unchecked")
        Tuple2<T, U>[] result = (Tuple2<T, U>[]) Array.newInstance(Tuple2.class, first.length);

        for (int i = 0; i < first.length; i++) {
            result[i] = Tuple2.of(first[i], second[i]); // Creating a tuple for each pair of elements
        }
        return result;
    }

    /**
     * Combines corresponding elements from two arrays using a provided function.
     *
     * @param <T>         Type of elements in the first array.
     * @param <U>         Type of elements in the second array.
     * @param <R>         Type of the result array.
     * @param first       The first input array.
     * @param second      The second input array.
     * @param combiner    A function to combine elements from both arrays.
     * @param resultArray The pre-allocated result array where combined values will be stored.
     * @return The result array with combined values.
     * @throws IllegalArgumentException If any of the parameters are null or if arrays have different lengths.
     */

    public static <T, U, R> R[] zipWith(T[] first, U[] second, BiFunction<T, U, R> combiner, R[] resultArray) {
        if (first == null || second == null || combiner == null || resultArray == null) {
            throw new IllegalArgumentException("Inputs cannot be null.");
        }
        if (first.length != second.length) {
            throw new IllegalArgumentException("Arrays must have the same length.");
        }
        if (resultArray.length < first.length) {
            throw new IllegalArgumentException("Result array must be at least as long as input arrays.");
        }
        for (int i = 0; i < first.length; i++) {
            resultArray[i] = combiner.apply(first[i], second[i]);
        }
        return resultArray;
    }

    /**
     * Interleaves elements from two arrays, alternating between them.
     * If one array is longer, its remaining elements are appended at the end.
     *
     * @param <T>         Type of elements in the arrays.
     * @param first       The first input array.
     * @param second      The second input array.
     * @param resultArray The pre-allocated result array where interleaved values will be stored.
     * @return resultArray The result array containing interleaved values.
     * @throws IllegalArgumentException If any of the parameters are null.
     */

    public static <T> T[] weaver(T[] first, T[] second, T[] resultArray) {
        if (first == null || second == null || resultArray == null) {
            throw new IllegalArgumentException("Inputs cannot be null.");
        }

        int maxLength = first.length + second.length;
        if (resultArray.length < maxLength) {
            throw new IllegalArgumentException("Result array must be large enough to hold all elements.");
        }

        int i = 0, j = 0, k = 0;
        while (i < first.length || j < second.length) {
            if (i < first.length) resultArray[k++] = first[i++];
            if (j < second.length) resultArray[k++] = second[j++];
        }

        return resultArray;
    }

    /**
     * Creates a new 2D array with the specific number of rows and columns.
     *
     * @param componentType The type of element in the array.
     * @param row           Number of row in the array.
     * @param col           Number of column in the array.
     * @param <T>           The type of the array elements.
     * @return A new empty 2D array of the specific type and size
     */
    @SuppressWarnings("unchecked")
    private static <T> T[][] create2DArray(Class<?> componentType, int row, int col) {
        return (T[][]) Array.newInstance(componentType, row, col);
    }

    /**
     * Creates a new 1D array with the specific number of rows.
     *
     * @param componentType The type of element in the array.
     * @param length        The length of the array.
     * @param <T>           The type of the array elements.
     * @return A new empty 1D array of the specific type and size.
     */
    @SuppressWarnings("unchecked")
    private static <T> T[] create1DArray(Class<?> componentType, int length) {
        return (T[]) Array.newInstance(componentType, length);
    }

    /**
     * Splits an array into smaller array-chunks of specific size.
     * The last chunk may be smaller if the array length is not evenly divisible.
     *
     * @param array The input array to be chunked.
     * @param size  The size of each chunk.
     * @param <T>   The type of elements in the array.
     * @return A 2D array where each inner array is a chunk of the original array.
     * @throws IllegalArgumentException if the array is null, contains null elements or size is negative or zero.
     */

    public static <T> T[][] chunk(T[] array, int size) {

        if (array == null) {
            throw new IllegalArgumentException("Input array must not be null");
        } else {
            for (T element : array) {
                if (element == null) {
                    throw new IllegalArgumentException("Element must not be null");
                }
            }
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }

        if (array.length == 0) {
            return create2DArray(array.getClass().getComponentType(), 0, 0);
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
     *
     * @param list The input list to be chunked.
     * @param size The size of each chunk.
     * @param <T>  The type of elements in the list.
     * @return A list of list where each inner list is a chunk of the original list.
     * @throws IllegalArgumentException if the list is null, contains null elements or size is negative or zero.
     */
    public static <T> List<List<T>> chunkList(List<T> list, int size) {
        if (list == null) {
            throw new IllegalArgumentException("Input list must not be null");
        } else {
            for (T element : list) {
                if (element == null) {
                    throw new IllegalArgumentException("Element must not be null");
                }
            }
        }

        if (size <= 0)
            throw new IllegalArgumentException("Size must be greater than 0");

        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            chunks.add(List.copyOf(list.subList(i, Math.min(list.size(), i + size))));
        }

        return Collections.unmodifiableList(chunks);
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

        // Handle the 1×1 edge case
        if (rows == 1 && cols == 1) {
            return array; // No need to transpose a single-element array
        }

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
