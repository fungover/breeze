package org.fungover.breeze.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RayMathTest {

//  Test if a ray correctly detects sphere collisions
    @Test
    //Checks if the ray correctly detects a hit
    void testRayIntersectsSphere_Hits() {
        Ray ray = new Ray(new Vector3(0, 0, 0), new Vector3(1, 0, 0));
        Vector3 sphereCenter = new Vector3(5, 0, 0);
        float sphereRadius = 1.0f;

        boolean result = RayMath.rayIntersectsSphere(ray, sphereCenter, sphereRadius);
        assertTrue(result, "Ray should hit the sphere!");
    }

    @Test
    //Checks if the ray correctly detects a miss
    void testRayIntersectsSphere_Misses() {
        Ray ray = new Ray(new Vector3(0, 0, 0), new Vector3(1, 0, 0));
        Vector3 sphereCenter = new Vector3(5, 5, 0); // Sphere is far from ray
        float sphereRadius = 1.0f;

        boolean result = RayMath.rayIntersectsSphere(ray, sphereCenter, sphereRadius);
        assertFalse(result, "Ray should miss the sphere!");
    }


//  Test if a ray correctly hits a plane
@Test
    //Checks if a ray correctly finds a plane intersection
void testRayIntersectsPlane_Hits() {
    Ray ray = new Ray(new Vector3(0, 0, 0), new Vector3(0, -1, 0)); // Ray pointing downward
    Vector3 planePoint = new Vector3(0, -5, 0); // Plane at y = -5
    Vector3 planeNormal = new Vector3(0, 1, 0); // Upward-facing plane

    Vector3 hitPoint = RayMath.rayIntersectsPlane(ray, planePoint, planeNormal);
    assertNotNull(hitPoint, "Ray should hit the plane!");
    assertEquals(0, hitPoint.x, 1e-6);
    assertEquals(-5, hitPoint.y, 1e-6);
    assertEquals(0, hitPoint.z, 1e-6);
}

    @Test
    //Checks if a ray correctly misses a plane intersection
    void testRayIntersectsPlane_Misses() {
        Ray ray = new Ray(new Vector3(0, 0, 0), new Vector3(1, 0, 0)); // Ray parallel to the plane
        Vector3 planePoint = new Vector3(0, -5, 0);
        Vector3 planeNormal = new Vector3(0, 1, 0);

        Vector3 hitPoint = RayMath.rayIntersectsPlane(ray, planePoint, planeNormal);
        assertNull(hitPoint, "Ray should not hit the plane!");
    }

//    Test if a ray correctly detects a box collision
@Test
    //Checks if the ray correctly hits a 3D box
void testRayIntersectsBox_Hits() {
    Ray ray = new Ray(new Vector3(0, 0, 0), new Vector3(1, 0, 0));
    Vector3 boxMin = new Vector3(10, -1, -1);
    Vector3 boxMax = new Vector3(12, 1, 1);

    boolean result = RayMath.rayIntersectsBox(ray, boxMin, boxMax);
    assertTrue(result, "Ray should hit the box!");
}

    @Test
    //Checks if the ray correctly misses a 3D box
    void testRayIntersectsBox_Misses() {
        Ray ray = new Ray(new Vector3(0, 0, 0), new Vector3(1, 0, 0));
        Vector3 boxMin = new Vector3(10, 10, 10); //✅ Move the box to (10,10,10)
        Vector3 boxMax = new Vector3(12, 12, 12); // ✅ Far away from the ray's path

        boolean result = RayMath.rayIntersectsBox(ray, boxMin, boxMax);
        assertFalse(result, "Ray should miss the box!"); //✅ Now the ray misses
    }

//    Test if a ray finds the closest point to another point
@Test
    //Checks if the function correctly finds the closest point on a ray to a given point
void testClosestPointOnRay() {
    Ray ray = new Ray(new Vector3(0, 0, 0), new Vector3(1, 0, 0)); // Ray along X-axis
    Vector3 point = new Vector3(3, 4, 0); // A point near the ray

    Vector3 closestPoint = RayMath.closestPointOnRay(ray, point);

    assertEquals(3, closestPoint.x, 1e-6);
    assertEquals(0, closestPoint.y, 1e-6);
    assertEquals(0, closestPoint.z, 1e-6);
    }

}
