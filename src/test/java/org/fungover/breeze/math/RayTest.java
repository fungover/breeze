package org.fungover.breeze.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RayTest {

/* Test to check if the ray direction is normalized. */
    @Test
    /* test constructor */
    void testRayInitialization() {
        Vector3 origin = new Vector3(1, 2, 3);
        Vector3 direction = new Vector3(4, 0, 0);
        Ray ray = new Ray(origin, direction);

        /* Check if origin is stored correctly */
        assertEquals(1, ray.getOrigin().getX(), 1e-6);
        assertEquals(2, ray.getOrigin().getY(), 1e-6);
        assertEquals(3, ray.getOrigin().getZ(), 1e-6);

        /* Check if direction is normalized (unit vector) */
        float length = (float) Math.sqrt(ray.getDirection().getX() * ray.getDirection().getX() +
                ray.getDirection().getY() * ray.getDirection().getY() +
                ray.getDirection().getZ() * ray.getDirection().getZ());
        assertEquals(1.0, length, 1e-6, "Ray direction should be normalized!");
    }

    @Test
    /* Test normalization */
    void testRayDirectionNormalization() {
        Vector3 origin = new Vector3(0, 0, 0);
        Vector3 direction = new Vector3(10, 0, 0); // Not normalized

        Ray ray = new Ray(origin, direction);

        /* The direction should be normalized to (1,0,0) */
        assertEquals(1, ray.getDirection().getX(), 1e-6);
        assertEquals(0, ray.getDirection().getY(), 1e-6);
        assertEquals(0, ray.getDirection().getZ(), 1e-6);
    }

    @Test
    /* Test Zero-Length Direction (Edge Case) */
    void testZeroLengthDirection() {
        Vector3 origin = new Vector3(0, 0, 0);
        Vector3 direction = new Vector3(0, 0, 0); // Invalid direction

        Ray ray = new Ray(origin, direction);

        /* Ensure it defaults to (0,0,0) instead of crashing */
        assertEquals(0, ray.getDirection().getX(), 1e-6);
        assertEquals(0, ray.getDirection().getY(), 1e-6);
        assertEquals(0, ray.getDirection().getZ(), 1e-6);
    }

}
