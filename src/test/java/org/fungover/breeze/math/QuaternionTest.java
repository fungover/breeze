package org.fungover.breeze.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuaternionTest {

    @Test
    void testQuaternionMultiplication() {
        Quaternion q1 = new Quaternion(1, 0, 0, 0);
        Quaternion q2 = new Quaternion(0, 1, 0, 0);
        Quaternion result = q1.multiply(q2);

        assertEquals(0, result.x, 1e-6);
        assertEquals(0, result.y,  1e-6);
        assertEquals(1, result.z, 1e-6);
        assertEquals(0, result.w, 1e-6);
    }

    @Test
    void testQuaternionNormalization() {
        Quaternion q = new Quaternion(1, 2, 3, 4);
        Quaternion result = q.normalize();

        float length = (float) Math.sqrt(1*1 + 2*2 + 3*3 + 4*4);

        assertEquals(1 / length, result.x, 1e-6);
        assertEquals(2 / length, result.y, 1e-6);
        assertEquals(3 / length, result.z, 1e-6);
        assertEquals(4 / length, result.w, 1e-6);
    }

    @Test
    void testQuaternionRotation() {
        Vector3 point = new Vector3(1, 0, 0);
        float angle = (float) Math.toRadians(90);
        float sinHalfAngle = (float) Math.sin(angle / 2);
        float cosHalfAngle = (float) Math.cos(angle / 2);
        Quaternion rotation = new Quaternion(0, 0, sinHalfAngle, cosHalfAngle);

        Vector3 rotatedPoint = rotation.rotateVector(point);

        assertEquals(0, rotatedPoint.x, 1e-6);
        assertEquals(1, rotatedPoint.y, 1e-6);
        assertEquals(0, rotatedPoint.z, 1e-6);
    }

    @Test
    void testSlerp() {
        Quaternion q1 = new Quaternion(0, 0, 0, 1);
        Quaternion q2 = new Quaternion(0, 1, 0, 0);

        Quaternion result = Quaternion.slerp(q1, q2, 0.5f);

        assertEquals(0, result.x, 1e-6);
        assertEquals(Math.sqrt(2) / 2, result.y, 1e-6);
        assertEquals(0, result.z, 1e-6);
        assertEquals(Math.sqrt(2) / 2, result.w, 1e-6);
    }
}
