package org.fungover.breeze.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.*;

class Vector3Test {

    @Test
    @DisplayName("Add method adds new values to old values")
    void addMethodAddsNewValuesToOldValues() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(3, 3, 3);
        var v = vector.add(vector3);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(6.0f, within(1e-7f))
        );
    }

    @Test
    @DisplayName("Add method throws exception if vector parameter is null")
    void addMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector3 vector = new Vector3(3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.add(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Sub method subtracts new values from old values")
    void subMethodSubtractsNewValuesFromOldValues() {
        Vector3 vector = new Vector3(6, 6, 6);
        Vector3 vector2 = new Vector3(3, 3, 3);
        var v = vector.sub(vector2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(3.0f, within(1e-7f))
        );
    }
    @Test
    @DisplayName("Sub method throws exception if vector parameter is null")
    void subMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector3 vector = new Vector3(3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.sub(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Mul multiplies vector values with input")
    void mulMultipliesVectorValuesWithInput() {
        Vector3 vector = new Vector3(3, 3, 3);
        var v = vector.mul(2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(6.0f, within(1e-7f))
        );
    }

    @Test
    @DisplayName("Div divides vector values with input")
    void divDividesVectorValuesWithInput() {
        Vector3 vector = new Vector3(6, 6, 6);
        var v = vector.div(2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(3.0f, within(1e-7f))
        );
    }

    @Test
    @DisplayName("Trying to divide by 0 throws exception")
    void tryingToDivideBy0ThrowsException() {
        Vector3 vector = new Vector3(3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.div(0));
        assertThat(exception.getMessage()).isEqualTo("Cannot divide by zero");
    }

    @Test
    @DisplayName("Dot returns the dot product of vector 1 and 2")
    void dotReturnsTheDotProductOfVector1And2() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(3, 3, 3);
        assertThat(vector.dot(vector3)).isEqualTo(27.0f);
    }

    @Test
    @DisplayName("Dot method throws exception if vector parameter is null")
    void dotMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector3 vector = new Vector3(3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.dot(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Cross returns new Vector3 as a cross product of the two vectors")
    void crossReturnsNewVector3AsACrossProductOfTheTwoVectors() {
        Vector3 vector = new Vector3(1, 2, 3);
        Vector3 vector2 = new Vector3(4, 5, 6);
        var v = vector.cross(vector2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(-3.0f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(6.0f,within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(-3.0f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("Cross method throws exception if vector parameter is null")
    void crossMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector3 vector = new Vector3(3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.cross(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Length returns the length of the vector")
    void lengthReturnsTheLengthOfTheVector() {
        Vector3 vector = new Vector3(2, 4, 4);
        assertThat(vector.length()).isCloseTo(6.0f,within(1e-7f));
    }

    @Test
    @DisplayName("Normalize returns a new normalized vector")
    void normalizeReturnsANewNormalizedVector() {
        Vector3 vector = new Vector3(2, 4, 4);
        var v = vector.normalize();
        assertAll(
                () -> assertThat(v.x()).isCloseTo((float) 2/6 ,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo((float) 4/6 ,within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo((float) 4/6 ,within(1e-7f))
        );
    }

    @Test
    @DisplayName("Trying to normalize with zero length throws exception")
    void tryingToNormalizeWithZeroLengthThrowsException(){
        Vector3 vector = new Vector3(0, 0, 0);
        var exception = assertThrows(IllegalArgumentException.class, vector::normalize);
        assertThat(exception.getMessage()).isEqualTo("Cannot normalize zero-length vector");
    }

    @Test
    @DisplayName("Distance calculates the distance between vectors")
    void distanceCalculatesTheDistanceBetweenVectors() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(6, 6, 6);
        assertThat(vector.distance(vector3)).isCloseTo((float) Math.sqrt(27),within(1e-7f));
    }

    @Test
    @DisplayName("Distance method throws exception if vector parameter is null")
    void distanceMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector3 vector = new Vector3(3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.distance(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Linear interpolation find the middle between vectors")
    void linearInterpolationFindTheMiddleBetweenVectors() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(6, 6, 6);
        var v = vector.linear(vector3, 0.5f);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(4.5f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(4.5f,within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(4.5f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("Linear method throws exception if vector parameter is null")
    void linearMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector3 vector = new Vector3(3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.linear(null,0.5f));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Linear interpolation with lerp larger than 1 throws exception")
    void linearInterpolationWithLerpLargerThan1ThrowException() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(6, 6, 6);
        var exception = assertThrows(IllegalArgumentException.class, () ->
                vector.linear(vector3, 1.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be larger than 1");
    }

    @Test
    @DisplayName("Linear interpolation with lerp less than 0 throws exception")
    void linearInterpolationWithLerpLessThan0ThrowException() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(6, 6, 6);
        var exception = assertThrows(IllegalArgumentException.class, () ->
                vector.linear(vector3, -0.1f));
        assertThat(exception.getMessage()).isEqualTo("lerp can not be negative");
    }

    @Test
    @DisplayName("Min returns new Vector3 with the smallest x, y and z values from two vectors")
    void minReturnsNewVector3WithTheSmallestXYAndZValuesFromTwoVectors() {
        Vector3 vector = new Vector3(1, 6, 7);
        Vector3 vector3 = new Vector3(2, 5, 9);
        var v = vector.min(vector3);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(1.0f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(5.0f,within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(7.0f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("Min method throws exception if vector parameter is null")
    void minMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector3 vector = new Vector3(3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.min(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("Max returns new Vector3 with the largest x, y and z values from two vectors")
    void maxReturnsNewVector3WithTheLargestXYAndZValuesFromTwoVectors() {
        Vector3 vector = new Vector3(1, 6, 7);
        Vector3 vector3 = new Vector3(2, 5, 9);
        var v = vector.max(vector3);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(2.0f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(6.0f,within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(9.0f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("Max method throws exception if vector parameter is null")
    void maxMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector3 vector = new Vector3(3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.max(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    @Test
    @DisplayName("toVector2 returns new Vector2 object with the Vector3 x and y values")
    void toVector2ReturnsVector2ObjectWithTheVector3XAndYValues(){
        Vector3 vector = new Vector3(3, 3, 3);
        var v = vector.toVector2();
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f,within(1e-7f))
        );
    }

    @Test
    @DisplayName("toVector4 returns new Vector4 object with the Vector3 values plus input")
    void toVector4ReturnsVector4ObjectWithTheVector3ValuesPlusInput(){
        Vector3 vector = new Vector3(3, 3, 3);
        var v = vector.toVector4(3);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(3.0f,within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(3.0f,within(1e-7f))
        );
    }
}
