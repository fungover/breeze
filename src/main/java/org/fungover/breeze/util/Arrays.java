package org.fungover.breeze.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Arrays {


   private Arrays() {
       throw new IllegalStateException("Utility class");
   }

   private static <T> T[][] create2DArray(Class<?> componentType, int row, int col) {
       return (T[][]) Array.newInstance(componentType, row, col);
   }

   private static <T> T[] create1DArray(Class<?> componentType, int row) {
       return (T[]) Array.newInstance(componentType, row);
   }

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
