package org.fungover.breeze.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Vector3Test {

    @Test
    @DisplayName("Add method adds new values to old values")
    void addMethodAddsNewValuesToOldValues() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(3, 3, 3);
        var v = vector.add(vector3);
        assertAll(
                () -> assertThat(v.x()).isEqualTo(6.0f),
                () -> assertThat(v.y()).isEqualTo(6.0f),
                () -> assertThat(v.z()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Sub method subtracts new values from old values")
    void subMethodSubtractsNewValuesFromOldValues() {
        Vector3 vector = new Vector3(6, 6, 6);
        Vector3 vector2 = new Vector3(3, 3, 3);
        var v = vector.sub(vector2);
        assertAll(
                () -> assertThat(v.x()).isEqualTo(3.0f),
                () -> assertThat(v.y()).isEqualTo(3.0f),
                () -> assertThat(v.z()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Mul multiplies vector values with input")
    void mulMultipliesVectorValuesWithInput() {
        Vector3 vector = new Vector3(3, 3, 3);
        var v = vector.mul(2);
        assertAll(
                () -> assertThat(v.x()).isEqualTo(6.0f),
                () -> assertThat(v.y()).isEqualTo(6.0f),
                () -> assertThat(v.z()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Div divides vector values with input")
    void divDividesVectorValuesWithInput() {
        Vector3 vector = new Vector3(6, 6, 6);
        var v = vector.div(2);
        assertAll(
                () -> assertThat(v.x()).isEqualTo(3.0f),
                () -> assertThat(v.y()).isEqualTo(3.0f),
                () -> assertThat(v.z()).isEqualTo(3.0f)
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
    @DisplayName("Cross returns new Vector3 as a cross product of the two vectors")
    void crossReturnsNewVector3AsACrossProductOfTheTwoVectors() {
        Vector3 vector = new Vector3(1, 2, 3);
        Vector3 vector2 = new Vector3(4, 5, 6);
        var v = vector.cross(vector2);
        assertAll(
                () -> assertThat(v.x()).isEqualTo(-3.0f),
                () -> assertThat(v.y()).isEqualTo(6.0f),
                () -> assertThat(v.z()).isEqualTo(-3.0f)
        );
    }

    @Test
    @DisplayName("Length returns the length of the vector")
    void lengthReturnsTheLengthOfTheVector() {
        Vector3 vector = new Vector3(2, 4, 4);
        assertThat(vector.length()).isEqualTo(6.0f);
    }

    @Test
    @DisplayName("Normalize returns a new normalized vector")
    void normalizeReturnsANewNormalizedVector() {
        Vector3 vector = new Vector3(2, 3, 4);
        var v = vector.normalize();
        assertAll(
                () -> assertThat(v.x()).isEqualTo(0.37139067f),
                () -> assertThat(v.y()).isEqualTo(0.55708605f),
                () -> assertThat(v.z()).isEqualTo(0.74278134f)
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
        assertThat(vector.distance(vector3)).isEqualTo((float) Math.sqrt(27));
    }

    @Test
    @DisplayName("Linear interpolation find the middle between vectors")
    void linearInterpolationFindTheMiddleBetweenVectors() {
        Vector3 vector = new Vector3(3, 3, 3);
        Vector3 vector3 = new Vector3(6, 6, 6);
        var v = vector.linear(vector3, 0.5f);
        assertAll(
                () -> assertThat(v.x()).isEqualTo(4.5f),
                () -> assertThat(v.y()).isEqualTo(4.5f),
                () -> assertThat(v.z()).isEqualTo(4.5f)
        );
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
                () -> assertThat(v.x()).isEqualTo(1.0f),
                () -> assertThat(v.y()).isEqualTo(5.0f),
                () -> assertThat(v.z()).isEqualTo(7.0f)
        );
    }

    @Test
    @DisplayName("Max returns new Vector3 with the largest x, y and z values from two vectors")
    void maxReturnsNewVector3WithTheLargestXYAndZValuesFromTwoVectors() {
        Vector3 vector = new Vector3(1, 6, 7);
        Vector3 vector3 = new Vector3(2, 5, 9);
        var v = vector.max(vector3);
        assertAll(
                () -> assertThat(v.x()).isEqualTo(2.0f),
                () -> assertThat(v.y()).isEqualTo(6.0f),
                () -> assertThat(v.z()).isEqualTo(9.0f)
        );
    }

    @Test
    @DisplayName("toVector2 returns new Vector2 object with the Vector3 x and y values")
    void toVector2ReturnsVector2ObjectWithTheVector3XAndYValues(){
        Vector3 vector = new Vector3(3, 3, 3);
        var v = vector.toVector2();
        assertAll(
                () -> assertThat(v.x()).isEqualTo(3.0f),
                () -> assertThat(v.y()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("toVector4 returns new Vector4 object with the Vector3 values plus input")
    void toVector4ReturnsVector4ObjectWithTheVector3ValuesPlusInput(){
        Vector3 vector = new Vector3(3, 3, 3);
        var v = vector.toVector4(3);
        assertAll(
                () -> assertThat(v.x()).isEqualTo(3.0f),
                () -> assertThat(v.y()).isEqualTo(3.0f),
                () -> assertThat(v.z()).isEqualTo(3.0f),
                () -> assertThat(v.w()).isEqualTo(3.0f)
        );
    }
}
