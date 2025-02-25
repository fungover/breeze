package org.fungover.breeze.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the Vector4 class.
 */
class Vector4Test {

    /**
     * Tests that the add method correctly adds two vectors.
     */
    @Test
    @DisplayName("Add method adds new values to old values")
    void addMethodAddsNewValuesToOldValues() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(3, 3, 3, 3);
        var v = vector.add(vector2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(6.0f, within(1e-7f))
        );
    }

    /**
     * Tests that the add method throws an exception when the vector parameter is null.
     */
    @Test
    @DisplayName("Add method throws exception if vector parameter is null")
    void addMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.add(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    /**
     * Tests that the sub method correctly subtracts one vector from another.
     */
    @Test
    @DisplayName("Sub method subtracts new values from old values")
    void subMethodSubtractsNewValuesFromOldValues() {
        Vector4 vector = new Vector4(6, 6, 6, 6);
        Vector4 vector2 = new Vector4(3, 3, 3, 3);
        var v = vector.sub(vector2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(3.0f, within(1e-7f))
        );
    }

    /**
     * Tests that the sub method throws an exception when the vector parameter is null.
     */
    @Test
    @DisplayName("Sub method throws exception if vector parameter is null")
    void subMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.sub(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    /**
     * Tests that the mul method correctly multiplies a vector by a scalar.
     */
    @Test
    @DisplayName("Mul multiplies vector values with input")
    void mulMultipliesVectorValuesWithInput() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        var v = vector.mul(2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(6.0f, within(1e-7f))
        );
    }

    /**
     * Tests that the div method correctly divides a vector by a scalar.
     */
    @Test
    @DisplayName("Div divides vector values with input")
    void divDividesVectorValuesWithInput() {
        Vector4 vector = new Vector4(6, 6, 6, 6);
        var v = vector.div(2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(3.0f, within(1e-7f))
        );
    }

    /**
     * Tests that the div method throws an exception when dividing by zero.
     */
    @Test
    @DisplayName("Trying to divide by 0 throws exception")
    void tryingToDivideBy0ThrowsException() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.div(0));
        assertThat(exception.getMessage()).isEqualTo("Cannot divide by zero");
    }

    /**
     * Tests that the dot method correctly calculates the dot product of two vectors.
     */
    @Test
    @DisplayName("Dot returns the dot product of vector 1 and 2")
    void dotReturnsTheDotProductOfVector1And2() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(3, 3, 3, 3);
        assertThat(vector.dot(vector2)).isCloseTo(36.0f, within(1e-7f));
    }

    /**
     * Tests that the dot method throws an exception when the vector parameter is null.
     */
    @Test
    @DisplayName("Dot method throws exception if vector parameter is null")
    void dotMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.dot(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    /**
     * Tests that the length method correctly calculates the length of a vector.
     */
    @Test
    @DisplayName("Length returns the length of the vector")
    void lengthReturnsTheLengthOfTheVector() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        assertThat(vector.length()).isCloseTo(6.0f, within(1e-7f));
    }

    /**
     * Tests that the normalize method correctly normalizes a vector.
     */
    @Test
    @DisplayName("Normalize returns a new normalized vector")
    void normalizeReturnsANewNormalizedVector() {
        Vector4 vector = new Vector4(3, 4, 5, 6);
        var v = vector.normalize();
        assertAll(
                () -> assertThat(v.x()).isCloseTo(0.3234983f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(0.4313311f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(0.5391638f, within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(0.6469966f, within(1e-7f))
        );
    }

    /**
     * Tests that the normalize method throws an exception when the vector length is zero.
     */
    @Test
    @DisplayName("Trying to normalize with zero length throws exception")
    void tryingToNormalizeWithZeroLengthThrowsException(){
        Vector4 vector = new Vector4(0, 0, 0, 0);
        var exception = assertThrows(IllegalArgumentException.class, vector::normalize);
        assertThat(exception.getMessage()).isEqualTo("Cannot normalize zero-length vector");
    }

    /**
     * Tests that the distance method correctly calculates the distance between two vectors.
     */
    @Test
    @DisplayName("Distance calculates the distance between vectors")
    void distanceCalculatesTheDistanceBetweenVectors() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        assertThat(vector.distance(vector2)).isCloseTo((float)Math.sqrt(36.0f), within(1e-7f));
    }

    /**
     * Tests that the distance method throws an exception when the vector parameter is null.
     */
    @Test
    @DisplayName("Distance method throws exception if vector parameter is null")
    void distanceMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.distance(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    /**
     * Tests that the linear method correctly performs linear interpolation between two vectors.
     */
    @Test
    @DisplayName("Linear interpolation finds the middle between vectors")
    void linearInterpolationFindTheMiddleBetweenVectors() {
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        var v = vector.linear(vector2, 0.5f);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(4.5f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(4.5f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(4.5f, within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(4.5f, within(1e-7f))
        );
    }

    /**
     * Tests that the linear method throws an exception when the vector parameter is null.
     */
    @Test
    @DisplayName("Linear method throws exception if vector parameter is null")
    void linearMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.linear(null, 0.5f));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    /**
     * Tests that the linear method throws an exception when the interpolation factor is greater than 1.
     */
    @Test
    @DisplayName("Linear interpolation with lerp larger than 1 throws exception")
    void linearInterpolationWithLerpLargerThan1ThrowsException(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        var exception = assertThrows(IllegalArgumentException.class, () ->
                vector.linear(vector2, 1.1f));
        assertThat(exception.getMessage()).isEqualTo("Interpolation factor must be between 0 and 1");
    }

    /**
     * Tests that the linear method throws an exception when the interpolation factor is less than 0.
     */
    @Test
    @DisplayName("Linear interpolation with lerp less than 0 throws exception")
    void linearInterpolationWithLerpLessThan0ThrowsException(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        Vector4 vector2 = new Vector4(6, 6, 6, 6);
        var exception = assertThrows(IllegalArgumentException.class, () ->
                vector.linear(vector2, -0.1f));
        assertThat(exception.getMessage()).isEqualTo("Interpolation factor must be between 0 and 1");
    }

    /**
     * Tests that the min method correctly returns a vector with the minimum components of two vectors.
     */
    @Test
    @DisplayName("Min returns new Vector4 with the smallest x, y, z and w values from two vectors")
    void minReturnsNewVector4WithTheSmallestXYZAndWValuesFromTwoVectors() {
        Vector4 vector = new Vector4(1, 4, 5, 8);
        Vector4 vector2 = new Vector4(2, 3, 6, 7);
        var v = vector.min(vector2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(1.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(3.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(5.0f, within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(7.0f, within(1e-7f))
        );
    }

    /**
     * Tests that the min method throws an exception when the vector parameter is null.
     */
    @Test
    @DisplayName("Min method throws exception if vector parameter is null")
    void minMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.min(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    /**
     * Tests that the max method correctly returns a vector with the maximum components of two vectors.
     */
    @Test
    @DisplayName("Max returns new Vector4 with the largest x, y, z and w values from two vectors")
    void maxReturnsNewVector4WithTheLargestXYZAndWValuesFromTwoVectors() {
        Vector4 vector = new Vector4(1, 4, 5, 8);
        Vector4 vector2 = new Vector4(2, 3, 6, 7);
        var v = vector.max(vector2);
        assertAll(
                () -> assertThat(v.x()).isCloseTo(2.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(4.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(6.0f, within(1e-7f)),
                () -> assertThat(v.w()).isCloseTo(8.0f, within(1e-7f))
        );
    }

    /**
     * Tests that the max method throws an exception when the vector parameter is null.
     */
    @Test
    @DisplayName("Max method throws exception if vector parameter is null")
    void maxMethodThrowsExceptionIfVectorParameterIsNull(){
        Vector4 vector = new Vector4(3, 3, 3, 3);
        var exception = assertThrows(IllegalArgumentException.class, () -> vector.max(null));
        assertThat(exception.getMessage()).isEqualTo("Vector parameter cannot be null");
    }

    /**
     * Tests that the toVector2 method correctly converts a Vector4 to a Vector2.
     */
    @Test
    @DisplayName("toVector2 returns new Vector2 object with the Vector4 x and y values")
    void toVector2ReturnsVector2ObjectWithTheVector4XAndYValues(){
        Vector4 vector = new Vector4(1, 2, 3, 4);
        var v = vector.toVector2();
        assertAll(
                () -> assertThat(v.x()).isCloseTo(1.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(2.0f, within(1e-7f))
        );
    }

    /**
     * Tests that the toVector3 method correctly converts a Vector4 to a Vector3.
     */
    @Test
    @DisplayName("toVector3 returns new Vector3 object with the Vector4 x, y and z values")
    void toVector3ReturnsVector3ObjectWithTheVector4XYAndZValues(){
        Vector4 vector = new Vector4(1, 2, 3, 4);
        var v = vector.toVector3();
        assertAll(
                () -> assertThat(v.x()).isCloseTo(1.0f, within(1e-7f)),
                () -> assertThat(v.y()).isCloseTo(2.0f, within(1e-7f)),
                () -> assertThat(v.z()).isCloseTo(3.0f, within(1e-7f))
        );
    }
}
