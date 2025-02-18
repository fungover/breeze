package org.fungover.breeze.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


class Vector4Test {

    @Test
    @DisplayName("Add method adds new values to old values")
    void addMethodAddsNewValuesToOldValues() {
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
    @DisplayName("Sub method subtracts new values from old values")
    void subMethodSubtractsNewValuesFromOldValues() {
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
    @DisplayName("Mul multiplies vector values with input")
    void mulMultipliesVectorValuesWithInput() {
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
    @DisplayName("Div divides vector values with input")
    void divDividesVectorValuesWithInput() {
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
    @DisplayName("Dot returns the dot product of vector 1 and 2")
    void dotReturnsTheDotProductOfVector1And2() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(3, 3, 3, 3);
        assertThat(vector.dot(vector2)).isEqualTo(36.0f);
    }

    @Test
    @DisplayName("Length returns the length of the vector")
    void lengthReturnsTheLengthOfTheVector() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        assertThat(vector.length()).isEqualTo(6.0f);
    }

    @Test
    @DisplayName("Normalize returns a new normalized vector")
    void normalizeReturnsANewNormalizedVector() {
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
    @DisplayName("Distance calculates the distance between vectors")
    void distanceCalculatesTheDistanceBetweenVectors() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        assertThat(vector.distance(vector2)).isEqualTo((float)Math.sqrt(36.0f));
    }

    @Test
    @DisplayName("Linear interpolation find the middle between vectors")
    void linearInterpolationFindTheMiddleBetweenVectors() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        var v = vector.linear(vector2, 0.5f);
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
        vector.linear(vector2, 1.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be larger than 1");
    }

    @Test
    @DisplayName("Linear interpolation with lerp less than 0 throws exception")
    void linearInterpolationWithLerpLessThan0ThrowsException(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        var exception = assertThrows(IllegalArgumentException.class, ()->
                vector.linear(vector2, -0.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be negative");
    }

    @Test
    @DisplayName("Min returns new Vector4 with the smallest x, y, z and w values from two vectors")
    void minReturnsNewVector4WithTheSmallestXYZAndWValuesFromTwoVectors() {
        Vector4 vector = new Vector4(1, 4, 5, 8);
        Vector4 vector2 = new Vector4(2, 3, 6, 7);
        var v = vector.min(vector2);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(1.0f),
                () -> assertThat(v.getY()).isEqualTo(3.0f),
                () -> assertThat(v.getZ()).isEqualTo(5.0f),
                () -> assertThat(v.getW()).isEqualTo(7.0f)
        );
    }

    @Test
    @DisplayName("Max returns new Vector4 with the largest x, y, z and w values from two vectors")
    void maxReturnsNewVector4WithTheLargestXYZAndWValuesFromTwoVectors() {
        Vector4 vector = new Vector4(1, 4, 5, 8);
        Vector4 vector2 = new Vector4(2, 3, 6, 7);
        var v = vector.max(vector2);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(2.0f),
                () -> assertThat(v.getY()).isEqualTo(4.0f),
                () -> assertThat(v.getZ()).isEqualTo(6.0f),
                () -> assertThat(v.getW()).isEqualTo(8.0f)
        );
    }

    @Test
    @DisplayName("toVector2 returns new Vector2 object with the Vector4 x and y values")
    void toVector2ReturnsVector2ObjectWithTheVector4XAndYValues(){
        Vector4 vector = new Vector4(1, 2, 3, 4);
        var v = vector.toVector2();
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(1.0f),
                () -> assertThat(v.getY()).isEqualTo(2.0f)
        );
    }

    @Test
    @DisplayName("toVector3 returns new Vector3 object with the Vector4 x, y and z values")
    void toVector3ReturnsVector3ObjectWithTheVector4XYAndZValues(){
        Vector4 vector = new Vector4(1, 2, 3, 4);
        var v = vector.toVector3();
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(1.0f),
                () -> assertThat(v.getY()).isEqualTo(2.0f),
                () -> assertThat(v.getZ()).isEqualTo(3.0f)
        );
    }
}
