package org.fungover.breeze.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector3Test {

    @Test
    void testAddition() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(4, 5, 6);
        Vector3 result = v1.add(v2);

        assertEquals(5, result.getX(), 1e-6);
        assertEquals(7, result.getY(), 1e-6);
        assertEquals(9, result.getZ(), 1e-6);
    }

    @Test
    void testSubtraction() {
        Vector3 v1 = new Vector3(5, 5, 5);
        Vector3 v2 = new Vector3(2, 3, 4);
        Vector3 result = v1.subtract(v2);

        assertEquals(3, result.getX(), 1e-6);
        assertEquals(2, result.getY(), 1e-6);
        assertEquals(1, result.getZ(), 1e-6);
    }

    @Test
    void testValidMultiplication() {
        Vector3 vector = new Vector3(2.0f, -3.0f, 4.0f);
        Vector3 result = vector.multiply(2.0f);

        assertEquals(4.0f, result.x);
        assertEquals(-6.0f, result.y);
        assertEquals(8.0f, result.z);
    }

    @Test
    void testMultiplicationWithZero() {
        Vector3 vector = new Vector3(1.0f, 2.0f, 3.0f);
        Vector3 result = vector.multiply(0.0f);

        assertEquals(0.0f, result.x);
        assertEquals(0.0f, result.y);
        assertEquals(0.0f, result.z);
    }

    @Test
    void testMultiplicationWithNegativeNumber() {
        Vector3 vector = new Vector3(1.0f, -2.0f, 3.0f);
        Vector3 result = vector.multiply(-2.0f);

        assertEquals(-2.0f, result.x);
        assertEquals(4.0f, result.y);
        assertEquals(-6.0f, result.z);
    }

    @Test
    void testMultiplicationWithNaNShouldThrowException() {
        Vector3 vector = new Vector3(1.0f, 2.0f, 3.0f);
        assertThrows(IllegalArgumentException.class, () -> vector.multiply(Float.NaN));
    }

    @Test
    void testMultiplicationWithInfinityShouldThrowException() {
        Vector3 vector = new Vector3(1.0f, 2.0f, 3.0f);

        assertThrows(IllegalArgumentException.class, () -> vector.multiply(Float.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> vector.multiply(Float.NEGATIVE_INFINITY));
    }


    @Test
    void testDotProduct() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(4, 5, 6);
        float result = v1.dot(v2);

        assertEquals(32, result, 1e-6);
    }

    @Test
    void testCrossProduct() {
        Vector3 v1 = new Vector3(1, 0, 0);
        Vector3 v2 = new Vector3(0, 1, 0);
        Vector3 result = v1.cross(v2);

        assertEquals(0, result.getX(), 1e-6);
        assertEquals(0, result.getY(), 1e-6);
        assertEquals(1, result.getZ(), 1e-6);
    }

    @Test
    void testNormalization() {
        Vector3 v = new Vector3(3, 4, 0);
        Vector3 result = v.normalize();

        assertEquals(0.6f, result.getX(), 1e-6);
        assertEquals(0.8f, result.getY(), 1e-6);
        assertEquals(0, result.getZ(), 1e-6);
    }

    @Test
    void testZeroVectorNormalization() {
        Vector3 zero = new Vector3(0, 0, 0);
        Vector3 result = zero.normalize();
        assertEquals(0, result.getX(), 1e-6);
        assertEquals(0, result.getY(), 1e-6);
        assertEquals(0, result.getZ(), 1e-6);
    }

    @Test
    void testLargeVectorOperations() {
        Vector3 large = new Vector3(1e30f, 1e30f, 1e30f);
        Vector3 normalized = large.normalize();
        assertEquals(0/Math.sqrt(3), normalized.getX(), 1e-6);
        assertEquals(0/Math.sqrt(3), normalized.getY(), 1e-6);
        assertEquals(0/Math.sqrt(3), normalized.getZ(), 1e-6);
    }

    @Test
    void testValidVectorCreation() {
        // Test with valid numbers
        Vector3 vector = new Vector3(1.0f, 2.0f, 3.0f);
        assertNotNull(vector); // Ensure object is created
    }

    @Test
    void testNaNValuesShouldThrowException() {
        // x is NaN
        assertThrows(IllegalArgumentException.class, () -> new Vector3(Float.NaN, 1.0f, 1.0f));
        // y is NaN
        assertThrows(IllegalArgumentException.class, () -> new Vector3(1.0f, Float.NaN, 1.0f));
        // z is NaN
        assertThrows(IllegalArgumentException.class, () -> new Vector3(1.0f, 1.0f, Float.NaN));
    }

    @Test
    void testInfiniteValuesShouldThrowException() {
        // x is infinite
        assertThrows(IllegalArgumentException.class, () -> new Vector3(Float.POSITIVE_INFINITY, 1.0f, 1.0f));
        assertThrows(IllegalArgumentException.class, () -> new Vector3(Float.NEGATIVE_INFINITY, 1.0f, 1.0f));

        // y is infinite
        assertThrows(IllegalArgumentException.class, () -> new Vector3(1.0f, Float.POSITIVE_INFINITY, 1.0f));
        assertThrows(IllegalArgumentException.class, () -> new Vector3(1.0f, Float.NEGATIVE_INFINITY, 1.0f));

        // z is infinite
        assertThrows(IllegalArgumentException.class, () -> new Vector3(1.0f, 1.0f, Float.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> new Vector3(1.0f, 1.0f, Float.NEGATIVE_INFINITY));
    }

    @Test
    void testToStringWithWholeNumbers() {
        Vector3 vector = new Vector3(1.0f, 2.0f, 3.0f);
        assertEquals("Vector3(x: 1,000000, y: 2,000000, z: 3,000000)", vector.toString());
    }

    @Test
    void testToStringWithDecimals() {
        Vector3 vector = new Vector3(1.5f, 2.75f, 3.125f);
        assertEquals("Vector3(x: 1,500000, y: 2,750000, z: 3,125000)", vector.toString());
    }

    @Test
    void testToStringWithNegativeNumbers() {
        Vector3 vector = new Vector3(-1.5f, -2.75f, -3.125f);
        assertEquals("Vector3(x: -1,500000, y: -2,750000, z: -3,125000)", vector.toString());
    }

    @Test
    void testToStringWithZero() {
        Vector3 vector = new Vector3(0.0f, 0.0f, 0.0f);
        assertEquals("Vector3(x: 0,000000, y: 0,000000, z: 0,000000)", vector.toString());
    }
}
