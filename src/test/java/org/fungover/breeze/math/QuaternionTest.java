package org.fungover.breeze.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuaternionTest {

    @Test
    public void testNormalize() {
        Quaternion q = new Quaternion(1, 2, 3, 4);
        Quaternion normalized = q.normalize();

        // Check if the length of the normalized quaternion is 1
        assertEquals(1.0f, normalized.length(), 1e-6);
    }

    @Test
    public void testMultiply() {
        Quaternion q1 = new Quaternion(1, 0, 0, 0);  // Identity quaternion
        Quaternion q2 = new Quaternion(0, 1, 0, 0);  // 90-degree rotation quaternion

        Quaternion result = q1.multiply(q2);

        // Multiply by the identity quaternion should return the other quaternion
        assertEquals(q2.getW(), result.getW(), 1e-6);
        assertEquals(q2.getX(), result.getX(), 1e-6);
        assertEquals(q2.getY(), result.getY(), 1e-6);
        assertEquals(q2.getZ(), result.getZ(), 1e-6);
    }

    @Test
    public void testInverse() {
        Quaternion q = new Quaternion(1, 2, 3, 4);
        Quaternion inverse = q.inverse();

        // Check if q * inverse(q) is approximately the identity quaternion
        Quaternion result = q.multiply(inverse);
        assertEquals(1.0f, result.getW(), 1e-6);
        assertEquals(0.0f, result.getX(), 1e-6);
        assertEquals(0.0f, result.getY(), 1e-6);
        assertEquals(0.0f, result.getZ(), 1e-6);
    }

    @Test
    public void testSlerp() {
        Quaternion q1 = new Quaternion(1, 0, 0, 0);  // Identity quaternion
        Quaternion q2 = new Quaternion(0, 1, 0, 0);  // 90-degree rotation quaternion

        // Slerp between the identity quaternion and a 90-degree rotation quaternion
        Quaternion result = Quaternion.slerp(q1, q2, 0.5f);

        // The result should be a quaternion between q1 and q2 (roughly 45 degrees of rotation)
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    public void testAdd() {
        Quaternion q1 = new Quaternion(1, 0, 0, 0);
        Quaternion q2 = new Quaternion(1, 1, 1, 1);

        Quaternion result = q1.add(q2);

        assertEquals(2.0f, result.getW(), 1e-6);
        assertEquals(1.0f, result.getX(), 1e-6);
        assertEquals(1.0f, result.getY(), 1e-6);
        assertEquals(1.0f, result.getZ(), 1e-6);
    }

    @Test
    public void testSubtract() {
        Quaternion q1 = new Quaternion(1, 0, 0, 0);
        Quaternion q2 = new Quaternion(1, 1, 1, 1);

        Quaternion result = q1.subtract(q2);

        assertEquals(0.0f, result.getW(), 1e-6);
        assertEquals(-1.0f, result.getX(), 1e-6);
        assertEquals(-1.0f, result.getY(), 1e-6);
        assertEquals(-1.0f, result.getZ(), 1e-6);
    }

    @Test
    public void testRotateVector() {
        // Test identity rotation
        Vector3 v = new Vector3(1, 0, 0);
        Quaternion q = new Quaternion(1, 0, 0, 0);  // No rotation (identity)
        Vector3 rotated = q.rotate(v);
        assertEquals(v.getX(), rotated.getX(), 1e-6);
        assertEquals(v.getY(), rotated.getY(), 1e-6);
        assertEquals(v.getZ(), rotated.getZ(), 1e-6);
    }

    @Test
    public void testQuaternionToString() {
        Quaternion q = new Quaternion(1, 2, 3, 4);
        String expectedString = "Quaternion(w: 1,000000, x: 2,000000, y: 3,000000, z: 4,000000)";
        assertEquals(expectedString, q.toString());
    }
}