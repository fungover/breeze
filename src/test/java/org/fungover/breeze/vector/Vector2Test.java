package org.fungover.breeze.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


class Vector2Test {

    @Test
    @DisplayName("Add method adds new values to old values")
    void addMethodAddsNewValuesToOldValues() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(3, 3);
        vector.add(vector2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Sub method subtracts new values from old values")
    void subMethodSubtractsNewValuesFromOldValues() {
        Vector2 vector = new Vector2(6, 6);
        Vector2 vector2 = new Vector2(3, 3);
        vector.sub(vector2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Mul multiplies vector values with input")
    void mulMultipliesVectorValuesWithInput() {
        Vector2 vector = new Vector2(3, 3);
        vector.mul(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Div divides vector values with input")
    void divDividesVectorValuesWithInput() {
        Vector2 vector = new Vector2(6, 6);
        vector.div(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Dot returns the dot product of vector 1 and 2")
    void dotReturnsTheDotProductOfVector1And2() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(3, 3);
        assertThat(vector.dot(vector2)).isEqualTo(18.0f);
    }

    @Test
    @DisplayName("Length returns the length of the vector")
    void lengthReturnsTheLengthOfTheVector() {
        Vector2 vector = new Vector2(3, 4);
        assertThat(vector.length()).isEqualTo(5.0f);
    }

    @Test
    @DisplayName("Normalize returns a new normalized vector")
    void normalizeReturnsANewNormalizedVector() {
        Vector2 vector = new Vector2(3, 4);
        var v = vector.normalize();
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(0.6f),
                () -> assertThat(v.getY()).isEqualTo(0.8f)
        );
    }

    @Test
    @DisplayName("Distance calculates the distance between vectors")
    void distanceCalculatesTheDistanceBetweenVectors() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(6, 6);
        assertThat(vector.distance(vector2)).isEqualTo((float) Math.sqrt(18));
    }

    @Test
    @DisplayName("Linear interpolation find the middle between vectors")
    void linearInterpolationFindTheMiddleBetweenVectors() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(6, 6);
        var v = vector.linear(vector2, 0.5f);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(4.5f),
                () -> assertThat(v.getY()).isEqualTo(4.5f)
        );
    }

    @Test
    @DisplayName("Linear interpolation with lerp larger than 1 throw exception")
    void linearInterpolationWithLerpLargerThan1ThrowException() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(6, 6);
        var exception = assertThrows(IllegalArgumentException.class, () ->
                vector.linear(vector2, 1.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be larger than 1");
    }

    @Test
    @DisplayName("Linear interpolation with lerp less than 0 throw exception")
    void linearInterpolationWithLerpLessThan0ThrowException() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(6, 6);
        var exception = assertThrows(IllegalArgumentException.class, () ->
                vector.linear(vector2, -0.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be negative");
    }

    @Test
    @DisplayName("Min returns new Vector2 with the smallest x and y values from two vectors")
    void minReturnsNewVector2WithTheSmallestXAndYValuesFromTwoVectors() {
        Vector2 vector = new Vector2(3, 6);
        Vector2 vector2 = new Vector2(4, 5);
        var v = vector.min(vector2);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(3.0f),
                () -> assertThat(v.getY()).isEqualTo(5.0f)
        );
    }

    @Test
    @DisplayName("Max returns new Vector2 with the largest x and y values from two vectors")
    void maxReturnsNewVector2WithTheLargestXAndYValuesFromTwoVectors() {
        Vector2 vector = new Vector2(3, 6);
        Vector2 vector2 = new Vector2(4, 5);
        var v = vector.max(vector2);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(4.0f),
                () -> assertThat(v.getY()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("toVector3 returns new Vector3 object with the Vector2 values plus input")
    void toVector3ReturnsVector3ObjectWithTheVector2ValuesPlusInput(){
        Vector2 vector = new Vector2(3, 3);
        var v = vector.toVector3(3);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(3.0f),
                () -> assertThat(v.getY()).isEqualTo(3.0f),
                () -> assertThat(v.getZ()).isEqualTo(3.0f)
        );
    }
    @Test
    @DisplayName("toVector4 returns new Vector4 object with the Vector2 values plus input")
    void toVector4ReturnsVector4ObjectWithTheVector2ValuesPlusInput(){
        Vector2 vector = new Vector2(3, 3);
        var v = vector.toVector4(3,3);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(3.0f),
                () -> assertThat(v.getY()).isEqualTo(3.0f),
                () -> assertThat(v.getZ()).isEqualTo(3.0f),
                () -> assertThat(v.getW()).isEqualTo(3.0f)
        );
    }


}
