package org.fungover.breeze.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector3Test {

    @Test
    void testAddition() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(4, 5, 6);
        Vector3 result = v1.add(v2);

        assertEquals(5, result.x, 1e-6);
        assertEquals(7, result.y, 1e-6);
        assertEquals(9, result.z, 1e-6);
    }

    @Test
    void testSubtraction() {
        Vector3 v1 = new Vector3(5, 5, 5);
        Vector3 v2 = new Vector3(2, 3, 4);
        Vector3 result = v1.subtract(v2);

        assertEquals(3, result.x, 1e-6);
        assertEquals(2, result.y, 1e-6);
        assertEquals(1, result.z, 1e-6);
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

        assertEquals(0, result.x, 1e-6);
        assertEquals(0, result.y, 1e-6);
        assertEquals(1, result.z, 1e-6);
    }

    @Test
    void testNormalization() {
        Vector3 v = new Vector3(3, 4, 0);
        Vector3 result = v.normalize();

        assertEquals(0.6f, result.x, 1e-6);
        assertEquals(0.8f, result.y, 1e-6);
        assertEquals(0, result.z, 1e-6);
    }
}
