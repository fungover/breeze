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
        var v = vector.add(vector2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(6.0f, within(1e-7f))
        );
    }

    @Test
    @DisplayName("Add method throws exception if vector parameter is null")
    void addMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector2 vector = new Vector2(3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.add(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Sub method subtracts new values from old values")
    void subMethodSubtractsNewValuesFromOldValues() {
        Vector2 vector = new Vector2(6, 6);
        Vector2 vector2 = new Vector2(3, 3);
        var v = vector.sub(vector2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f, within(1e-7f))
        );
    }

    @Test
    @DisplayName("Sub method throws exception if vector parameter is null")
    void subMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector2 vector = new Vector2(3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.sub(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Mul multiplies vector values with input")
    void mulMultipliesVectorValuesWithInput() {
        Vector2 vector = new Vector2(3, 3);
        var v = vector.mul(2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(6.0f, within(1e-7f))
        );
    }

    @Test
    @DisplayName("Div divides vector values with input")
    void divDividesVectorValuesWithInput() {
        Vector2 vector = new Vector2(6, 6);
        var v = vector.div(2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f, within(1e-7f))
        );
    }

    @Test
    @DisplayName("Trying to divide by 0 throws exception")
    void tryingToDivideBy0ThrowsException() {
        Vector2 vector = new Vector2(3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.div(0));
        assertThat(exception.getMessage()).isEqualTo("Cannot divide by zero");
    }

    @Test
    @DisplayName("Dot returns the dot product of vector 1 and 2")
    void dotReturnsTheDotProductOfVector1And2() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(3, 3);
        assertThat(vector.dot(vector2)).isCloseTo(18.0f,within(1e-7f));
    }

    @Test
    @DisplayName("Dot method throws exception if vector parameter is null")
    void dotMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector2 vector = new Vector2(3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.dot(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Length returns the length of the vector")
    void lengthReturnsTheLengthOfTheVector() {
        Vector2 vector = new Vector2(3, 4);
        assertThat(vector.length()).isCloseTo(5.0f,within(1e-7f));
    }

    @Test
    @DisplayName("Normalize returns a new normalized vector")
    void normalizeReturnsANewNormalizedVector() {
        Vector2 vector = new Vector2(3, 4);
        var v = vector.normalize();
        assertAll(
                () -> assertThat(v.x()).isCloseTo(0.6f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(0.8f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("Trying to normalize with zero length throws exception")
    void tryingToNormalizeWithZeroLengthThrowsException() {
        Vector2 vector = new Vector2(0, 0);
        var exception = assertThrows(IllegalArgumentException.class, vector::normalize);
        assertThat(exception.getMessage()).isEqualTo("Cannot normalize zero-length vector");
    }

    @Test
    @DisplayName("Distance calculates the distance between vectors")
    void distanceCalculatesTheDistanceBetweenVectors() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(6, 6);
        assertThat(vector.distance(vector2)).isCloseTo((float) Math.sqrt(18),within(1e-7f));
    }

    @Test
    @DisplayName("Distance method throws exception if vector parameter is null")
    void distanceMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector2 vector = new Vector2(3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.distance(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Linear interpolation find the middle between vectors")
    void linearInterpolationFindTheMiddleBetweenVectors() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(6, 6);
        var v = vector.linear(vector2, 0.5f);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(4.5f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(4.5f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("Linear method throws exception if vector parameter is null")
    void linearMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector2 vector = new Vector2(3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.linear(null, 0.5f));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
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
                () -> assertThat(v.x()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(5.0f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("min method throws exception if vector parameter is null")
    void minMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector2 vector = new Vector2(3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.min(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Max returns new Vector2 with the largest x and y values from two vectors")
    void maxReturnsNewVector2WithTheLargestXAndYValuesFromTwoVectors() {
        Vector2 vector = new Vector2(3, 6);
        Vector2 vector2 = new Vector2(4, 5);
        var v = vector.max(vector2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(4.0f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(6.0f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("Max method throws exception if vector parameter is null")
    void maxMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector2 vector = new Vector2(3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.max(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("toVector3 returns new Vector3 object with the Vector2 values plus input")
    void toVector3ReturnsVector3ObjectWithTheVector2ValuesPlusInput() {
        Vector2 vector = new Vector2(3, 3);
        var v = vector.toVector3(3);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(3.0f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("toVector4 returns new Vector4 object with the Vector2 values plus input")
    void toVector4ReturnsVector4ObjectWithTheVector2ValuesPlusInput() {
        Vector2 vector = new Vector2(3, 3);
        var v = vector.toVector4(3, 3);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(3.0f,within(1e-7f))
        );
    }


}
