package org.fungover.breeze.util;

import java.lang.reflect.Array;

public class Arrays {
    public static <T> T[][] transpose(T[][] array){
        if (array == null || array.length == 0 || array[0].length == 0) {
            return array;
        }

        int rows = array.length;
        int cols = array[0].length;

        // Validate array regularity
        for (T[] row : array) {
            if (row.length != cols) {
                throw new IllegalArgumentException("Irregular array: all rows must have the same length");
            }
        }

        @SuppressWarnings("unchecked")
        T[][] transposed = (T[][]) Array.newInstance(array.getClass().getComponentType().getComponentType(), cols, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = array[i][j];
            }
        }

        return transposed;
    }
}
