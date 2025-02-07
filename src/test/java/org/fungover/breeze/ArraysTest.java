package org.fungover.breeze;

import org.fungover.breeze.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArraysTest {
    @Test
    @DisplayName("Square Arrays")
    void squareArrays () {
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
    void rectArrays () {
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
    void irregularArrays () {

    }

    @Test
    @DisplayName("Large Arrays")
    void largeArrays () {
    
    }

    @Test
    @DisplayName("Arrays with null element")
    void arraysWithNullElement () {
    
    }
}
