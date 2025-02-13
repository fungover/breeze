package org.fungover.breeze.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class ArraysTest {
    @Test
    @DisplayName("Square Arrays")
    void squareArrays() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Integer[][] expected = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };

        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Rectangular Arrays")
    void rectArrays() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        Integer[][] expected = {
                {1, 4, 7, 10},
                {2, 5, 8, 11},
                {3, 6, 9, 12}
        };
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Single Row/Column Arrays")
    void singleRowColumnArrays() {
        Integer[][] input = {
                {1, 2, 3},
        };
        Integer[][] expected = {
                {1},
                {2},
                {3}
        };
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Irregular Arrays")
    void irregularArrays() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6, 7}
        };

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Arrays.transpose(input))
                .withMessage("Irregular array: all rows must have the same length");
    }

    @Test
    @DisplayName("Large Arrays")
    void largeArrays() {
        int size = 1000;
        Integer[][] input = new Integer[size][size];
        Integer[][] expected = new Integer[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                input[i][j] = i * size + j;
                expected[i][j] = j * size + i;
            }
        }
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Arrays with null element")
    void arraysWithNullElement() {
        Integer[][] input = {
                {1, null, 3},
                {4, 5, null},
        };
        Integer[][] expected = {
                {1, 4},
                {null, 5},
                {3, null},
        };
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);

    }

    @Test
    @DisplayName("Empty Arrays")
    void emtpyArrays() {
        Integer[][] input = {}; // Empty array
        Integer[][] expected = {}; // Expected result

        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("Preserve Type Information")
    void preservedTypeInformation() {
        String[][] input = {
                {"a", "b" },
                {"c", "d" },
        };
        String[][] expected = {
                {"a", "c" },
                {"b", "d" },
        };
        assertThat(Arrays.transpose(input)).isDeepEqualTo(expected);
    }
}
