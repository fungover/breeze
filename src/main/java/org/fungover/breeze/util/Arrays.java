package org.fungover.breeze.util;

import java.lang.reflect.Array;

// JavaDocs

public class Arrays {


    private Arrays() {
        throw new IllegalStateException("Utility class");
    }


    public static <T> T[][] chunk(T[] array, int size) {

        if (size <= 0)
            throw new IllegalArgumentException("Size must be greater than 0");


       int numberOfChunks = (int) Math.ceil((double) array.length / size);
       T[][] chunks = (T[][]) Array.newInstance(array.getClass().getComponentType(),numberOfChunks, 0);

       for (int i = 0; i < numberOfChunks; i++) {
           int start = i * size;
           int length = Math.min(array.length - start, size);

           T[] chunk = (T[]) Array.newInstance(array.getClass().getComponentType(), length);
           System.arraycopy(array, start, chunk, 0, length);
           chunks[i] = chunk;
       }

        return chunks;
    }

}
