package org.fungover.breeze.math;

public class RayMath {

    // Function to check if a ray hits a sphere
    public static boolean rayIntersectsSphere(Ray ray, Vector3 center, float radius) {
        Vector3 oc = ray.origin.subtract(center);
        float a = ray.direction.dot(ray.direction);
        float b = 2.0f * oc.dot(ray.direction);
        float c = oc.dot(oc) - radius * radius;
        float discriminant = b * b - 4 * a * c;
        return discriminant > 0; // If >0, the ray hits the sphere
    }


    //Function to check if a ray hits a plane and returns the hit point if it does.
    public static Vector3 rayIntersectsPlane(Ray ray, Vector3 planePoint, Vector3 planeNormal) {
        float denom = planeNormal.dot(ray.direction);

        // If the denominator is too small, the ray is almost parallel to the plane (no intersection)
        if (Math.abs(denom) < 1e-6) {
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

        return true; // Ray hits the box
    }

    //Function to find the closest point on a ray to a target point.
    public static Vector3 closestPointOnRay(Ray ray, Vector3 point) {
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



