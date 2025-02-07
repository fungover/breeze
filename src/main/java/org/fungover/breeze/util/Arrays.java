package org.fungover.breeze.util;

import java.lang.reflect.Array;

public class Arrays {
    public static <T> T[][] transpose(T[][] array){
        //Checks if the input array is null or if it has no rows or columns
        if (array == null || array.length == 0 || array[0].length == 0) {
            return array;
        }

        //If the input array is valid, it initializes rows and cols to the dimensions of the array.
        int rows = array.length;
        int cols = array[0].length;

        //Validates that all rows in the array have the same length.
        for (T[] row : array) {
            if (row.length != cols) {
                throw new IllegalArgumentException("Irregular array: all rows must have the same length");
            }
        }

        // Creates a new 2D array with the transposed dimensions (cols x rows).
        @SuppressWarnings("unchecked")
        T[][] transposed = (T[][]) Array.newInstance(array.getClass().getComponentType().getComponentType(), cols, rows);

        //If the checks pass, it transposes the array by switching rows and columns using a nested for loop.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = array[i][j];
            }
        }

        return transposed;
    }
}
