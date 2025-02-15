package org.fungover.breeze.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Utility class that provides methods for chunking arrays and list into smaller part.
 * This class can not be instantiated.
 */
public class Arrays {

    /**
     * Private constructor to prevent instantiation of utility class.
     *
     * @throws IllegalStateException if an attempt is made to instantiate this class.
     */
   private Arrays() {
       throw new IllegalStateException("Utility class");
   }

    /**
     * Creates a new 2D array with the specific number of rows and columns.
     *
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
     *
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
     * @throws IllegalArgumentException if the array is null, contains null elements or size is <=0.
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
        } else if (array.length == 0) {
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
     * @throws IllegalArgumentException if the list is null, contains null elements or size is <=0.
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
