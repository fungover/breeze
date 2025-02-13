package org.fungover.breeze.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Vector3Test {

    @Test
    @DisplayName("Adds to vector")
    void addsToVector() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(3, 3, 3);
        vector.add(vector3);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f),
                () -> assertThat(vector.getZ()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Sub from vector")
    void subFromVector() {
        Vector3 vector = new Vector3(6, 6, 6);
        Vector3 vector2 = new Vector3(3, 3, 3);
        vector.sub(vector2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f),
                () -> assertThat(vector.getZ()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Mul vector")
    void mulVector() {
        Vector3 vector = new Vector3(3, 3, 3);
        vector.mul(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f),
                () -> assertThat(vector.getZ()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Div vector")
    void divVector() {
        Vector3 vector = new Vector3(6, 6, 6);
        vector.div(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f),
                () -> assertThat(vector.getZ()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Correct dot product")
    void correctDotProduct() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(3, 3, 3);
        assertThat(vector.dot(vector3)).isEqualTo(27.0f);
    }

    @Test
    @DisplayName("new vector from cross product")
    void newVectorFromCrossProduct() {
        Vector3 vector = new Vector3(1, 2, 3);
        Vector3 vector2 = new Vector3(4, 5, 6);
        var v = vector.cross(vector2);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(-3.0f),
                () -> assertThat(v.getY()).isEqualTo(6.0f),
                () -> assertThat(v.getZ()).isEqualTo(-3.0f)
        );
    }

    @Test
    @DisplayName("Correct length")
    void correctLength() {
        Vector3 vector = new Vector3(2, 4, 4);
        assertThat(vector.length()).isEqualTo(6.0f);
    }

    @Test
    @DisplayName("Normalize vector")
    void normalizeVector() {
        Vector3 vector = new Vector3(2, 3, 4);
        var v = vector.normalize();
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(0.37139067f),
                () -> assertThat(v.getY()).isEqualTo(0.55708605f),
                () -> assertThat(v.getZ()).isEqualTo(0.74278134f)
        );
    }

    @Test
    @DisplayName("Distance between vectors")
    void distanceBetweenVectors(){
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector2 = new Vector3(6, 6, 6);
        assertThat(vector.distance(vector2,vector)).isEqualTo((float) Math.sqrt(27));
    }
    
    @Test
    @DisplayName("Linear interpolation finds point between vectors")
    void linearInterpolationFindsPointBetweenVectors(){
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector2 = new Vector3(6, 6, 6);
        var v = vector.linear(vector, vector2, 0.5f);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(4.5f),
                () -> assertThat(v.getY()).isEqualTo(4.5f),
                () -> assertThat(v.getZ()).isEqualTo(4.5f)
        );
    }

    @Test
    @DisplayName("Linear interpolation with lerp larger than 1 throw exception")
    void linearInterpolationWithLerpLargerThan1ThrowException() {
        Vector3 vector = new Vector3(3, 3,3);
        Vector3 vector3 = new Vector3(6, 6,6);
        var exception = assertThrows(IllegalArgumentException.class, () ->
                vector.linear(vector, vector3, 1.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be larger than 1");
    }

    @Test
    @DisplayName("Linear interpolation with lerp less than 0 throw exception")
    void linearInterpolationWithLerpLessThan0ThrowException() {
        Vector3 vector = new Vector3(3, 3,3);
        Vector3 vector3 = new Vector3(6, 6,6);
        var exception = assertThrows(IllegalArgumentException.class, () ->
                vector.linear(vector, vector3, -0.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be negative");
    }

    @Test
    @DisplayName("Find the min value of two vectors")
    void findTheMinValueOfTwoVectors() {
        Vector3 vector = new Vector3(1, 6, 7);
        Vector3 vector3 = new Vector3(2, 5, 9);
        var v = vector.min(vector, vector3);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(1.0f),
                () -> assertThat(v.getY()).isEqualTo(5.0f),
                () -> assertThat(v.getZ()).isEqualTo(7.0f)
        );
    }

    @Test
    @DisplayName("Find the max value of two vectors")
    void findTheMaxValueOfTwoVectors() {
        Vector3 vector = new Vector3(1, 6, 7);
        Vector3 vector3 = new Vector3(2, 5, 9);
        var v = vector.max(vector, vector3);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(2.0f),
                () -> assertThat(v.getY()).isEqualTo(6.0f),
                () -> assertThat(v.getZ()).isEqualTo(9.0f)
        );
    }

    @Test
    @DisplayName("New vector2 object")
    void newVector2Object(){
        Vector3 vector = new Vector3(3, 3, 3);
        var v = vector.toVector2();
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(3.0f),
                () -> assertThat(v.getY()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("New vector4 object")
    void newVector4Object(){
        Vector3 vector = new Vector3(3, 3, 3);
        var v = vector.toVector4(3);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(3.0f),
                () -> assertThat(v.getY()).isEqualTo(3.0f),
                () -> assertThat(v.getZ()).isEqualTo(3.0f),
                () -> assertThat(v.getW()).isEqualTo(3.0f)
        );
    }
}
