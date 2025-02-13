package org.fungover.breeze.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


class Vector4Test {

    @Test
    @DisplayName("Adds to vector")
    void addsToVector() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(3, 3, 3, 3);
        vector.add(vector2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f),
                () -> assertThat(vector.getZ()).isEqualTo(6.0f),
                () -> assertThat(vector.getW()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Sub from vector")
    void subFromVector() {
        Vector4 vector = new Vector4(6, 6, 6, 6);
        Vector4 vector2 = new Vector4(3, 3, 3, 3);
        vector.sub(vector2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f),
                () -> assertThat(vector.getZ()).isEqualTo(3.0f),
                () -> assertThat(vector.getW()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Mul vector")
    void mulVector() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        vector.mul(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f),
                () -> assertThat(vector.getZ()).isEqualTo(6.0f),
                () -> assertThat(vector.getW()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Div vector")
    void divVector() {
        Vector4 vector = new Vector4(6, 6, 6, 6);
        vector.div(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f),
                () -> assertThat(vector.getZ()).isEqualTo(3.0f),
                () -> assertThat(vector.getW()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Correct dot product")
    void correctDotProduct() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(3, 3, 3, 3);
        assertThat(vector.dot(vector2)).isEqualTo(36.0f);
    }

    @Test
    @DisplayName("Correct length")
    void correctLength() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        assertThat(vector.length()).isEqualTo(6.0f);
    }

    @Test
    @DisplayName("Normalize vector")
    void normalizeVector() {
        Vector4 vector = new Vector4(3, 4, 5, 6);
        var v = vector.normalize();
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(0.3234983f),
                () -> assertThat(v.getY()).isEqualTo(0.4313311f),
                () -> assertThat(v.getZ()).isEqualTo(0.5391638f),
                () -> assertThat(v.getW()).isEqualTo(0.6469966f)
        );
    }

    @Test
    @DisplayName("Distance between vectors")
    void distanceBetweenVectors(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        assertThat(vector.distance(vector2,vector)).isEqualTo((float)Math.sqrt(36.0f));
    }

    @Test
    @DisplayName("Linear interpolation finds point between vectors")
    void linearInterpolationFindsPointBetweenVectors(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        var v = vector.linear(vector, vector2, 0.5f);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(4.5f),
                () -> assertThat(v.getY()).isEqualTo(4.5f),
                () -> assertThat(v.getZ()).isEqualTo(4.5f),
                () -> assertThat(v.getW()).isEqualTo(4.5f)
        );
    }

    @Test
    @DisplayName("Linear interpolation with lerp larger than 1 throws exception")
    void linearInterpolationWithLerpLargerThan1ThrowsException(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        var exception = assertThrows(IllegalArgumentException.class, ()->
        vector.linear(vector, vector2, 1.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be larger than 1");
    }

    @Test
    @DisplayName("Linear interpolation with lerp less than 0 throws exception")
    void linearInterpolationWithLerpLessThan0ThrowsException(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        var exception = assertThrows(IllegalArgumentException.class, ()->
                vector.linear(vector, vector2, -0.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be negative");
    }
}
