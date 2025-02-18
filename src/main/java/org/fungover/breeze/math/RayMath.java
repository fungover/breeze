package org.fungover.breeze.math;


public class RayMath {

    private static final float EPSILON = 1e-6f;

    // Function to check if a ray hits a sphere
    public static boolean rayIntersectsSphere(Ray ray, Vector3 center, float radius) throws IllegalArgumentException {

        if (ray == null || center == null) {
            throw new IllegalArgumentException("Ray and center must not be null");
        }
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        if (ray.direction.x == 0 && ray.direction.y == 0 && ray.direction.z == 0) {
            throw new IllegalArgumentException("Ray direction must be non-zero and normalized");
        }

        Vector3 oc = ray.origin.subtract(center);
        float a = 1.0f; // Since direction is normalized
        float b = 2.0f * oc.dot(ray.direction);
        float c = oc.dot(oc) - radius * radius;
        float discriminant = b * b - 4 * a * c;
        return discriminant >= 0; // Ray hits or touches the sphere
    }

    //Function to check if a ray hits a plane and returns the hit point if it does.
    public static Vector3 rayIntersectsPlane(Ray ray, Vector3 planePoint, Vector3 planeNormal) {

        if (ray == null || planePoint == null || planeNormal == null) {
            throw new IllegalArgumentException("Parameters must not be null");
            }
        if (Math.abs(ray.direction.dot(ray.direction) - 1.0f) > EPSILON) {
            throw new IllegalArgumentException("Ray direction must be normalized");
            }
        if (Math.abs(planeNormal.dot(planeNormal) - 1.0f) > EPSILON) {
            throw new IllegalArgumentException("Plane normal must be normalized");
            }

        float denom = planeNormal.dot(ray.direction);

        // If the denominator is too small, the ray is almost parallel to the plane (no intersection)
        if (Math.abs(denom) < EPSILON) {
            return null; // No intersection
        }

        float t = (planePoint.subtract(ray.origin)).dot(planeNormal) / denom;

        // If t is negative, the plane is behind the ray (no valid intersection)
        if (t < 0) {
            return null;
        }

        // Calculate intersection point
        return new Vector3(
                ray.origin.x + ray.direction.x * t,
                ray.origin.y + ray.direction.y * t,
                ray.origin.z + ray.direction.z * t
        );
    }

    //This function checks if a ray hits a box (like a wall or a cube).
    public static boolean rayIntersectsBox(Ray ray, Vector3 boxMin, Vector3 boxMax) {

        if (ray == null || boxMin == null || boxMax == null) {
            throw new IllegalArgumentException("Parameters must not be null");
            }
        if (Math.abs(ray.direction.dot(ray.direction) - 1.0f) > EPSILON) {
            throw new IllegalArgumentException("Ray direction must be normalized");
            }
        if (boxMin.x > boxMax.x || boxMin.y > boxMax.y || boxMin.z > boxMax.z) {
            throw new IllegalArgumentException("boxMin must be less than boxMax");
            }

        float tMin = (boxMin.x - ray.origin.x) / ray.direction.x;
        float tMax = (boxMax.x - ray.origin.x) / ray.direction.x;

        if (tMin > tMax) {
            float temp = tMin;
            tMin = tMax;
            tMax = temp;
        }

        float tyMin = (boxMin.y - ray.origin.y) / ray.direction.y;
        float tyMax = (boxMax.y - ray.origin.y) / ray.direction.y;

        if (tyMin > tyMax) {
            float temp = tyMin;
            tyMin = tyMax;
            tyMax = temp;
        }

        if ((tMin > tyMax) || (tyMin > tMax)) {
            return false; // No intersection
        }

        tMin = Math.max(tMin, tyMin);
        tMax = Math.min(tMax, tyMax);

        float tzMin = (boxMin.z - ray.origin.z) / ray.direction.z;
        float tzMax = (boxMax.z - ray.origin.z) / ray.direction.z;

        if (tzMin > tzMax) {
            float temp = tzMin;
            tzMin = tzMax;
            tzMax = temp;
            }

            if ((tMin > tzMax) || (tzMin > tMax)) {
                return false; // No intersection
            }

        return true; // Ray hits the box
    }

    //Function to find the closest point on a ray to a target point.
    public static Vector3 closestPointOnRay(Ray ray, Vector3 point) {

        if (ray == null || point == null) {
            throw new IllegalArgumentException("Parameters must not be null");
            }
        if (Math.abs(ray.direction.dot(ray.direction) - 1.0f) > EPSILON) {
            throw new IllegalArgumentException("Ray direction must be normalized");
        }


        Vector3 originToPoint = point.subtract(ray.origin);
        float t = originToPoint.dot(ray.direction);

        if (t < 0) {
            return ray.origin; // Closest point is the ray origin
        }

        return new Vector3(
                ray.origin.x + ray.direction.x * t,
                ray.origin.y + ray.direction.y * t,
                ray.origin.z + ray.direction.z * t
        );
    }
}



