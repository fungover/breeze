package org.fungover.breeze.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Arrays {

   private Arrays() {
       throw new IllegalStateException("Utility class");
   }


    public static <T> T[][] chunk(T[] array, int size) {

        if (array == null ) {
            throw new IllegalArgumentException("Input array must not be null");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }

        if (array.length == 0) {
            return (T[][]) Array.newInstance(array.getClass().getComponentType(), 0,0);
        }

        int numberOfChunks = (int) Math.ceil((double) array.length / size);
       Object[][] chunks = (Object[][]) Array.newInstance(array.getClass().getComponentType(), numberOfChunks,0);

       for (int i = 0; i < numberOfChunks; i++) {
           int start = i * size;
           int length = Math.min(array.length - start, size);

           Object[] chunk = (Object[]) Array.newInstance(array.getClass().getComponentType(), length);

           System.arraycopy(array, start, chunk, 0, length);
           chunks[i] = chunk;
       }

        return (T[][]) chunks;

    }

    public static <T>List<List<T>> chunkList(List<T> list, int size) {
        if(list == null)
            throw new IllegalArgumentException("Input list must not be null");

        if (size <= 0)
            throw new IllegalArgumentException("Size must be greater than 0");


        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i+= size) {
            chunks.add(List.copyOf(list.subList(i, Math.min(list.size(), i + size))));
        }
        return Collections.unmodifiableList(chunks);
    }

}
