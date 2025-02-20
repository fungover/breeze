package org.fungover.breeze.math;

/**
 * Utility class for mathematical ray operations such as intersections.
 */

public class RayMath {

    private static final float EPSILON = 1e-6f;

    /**
     * Checks if a ray intersects with a sphere.
     *
     * @param ray The ray to check.
     * @param center The center of the sphere.
     * @param radius The radius of the sphere.
     * @return true if the ray intersects the sphere, false otherwise.
     * @throws IllegalArgumentException if parameters are null or radius is negative.
     */
    public static boolean rayIntersectsSphere(Ray ray, Vector3 center, float radius) throws IllegalArgumentException {

        if (ray == null || center == null) {
            throw new IllegalArgumentException("Ray and center must not be null");
        }
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        if (ray.getDirection().getX() == 0 && ray.getDirection().getY() == 0 && ray.getDirection().getZ() == 0) {
            throw new IllegalArgumentException("Ray direction must be non-zero and normalized");
        }

        Vector3 oc = ray.getOrigin().subtract(center);
        float a = ray.getDirection().dot(ray.getDirection());
        float b = 2.0f * oc.dot(ray.getDirection());
        float c = oc.dot(oc) - radius * radius;
        float discriminant = b * b - 4 * a * c;
        return discriminant >= 0; // Ray hits or touches the sphere
    }

    /**
     * Checks if a ray intersects with a plane and returns the intersection point if it does.
     *
     * @param ray The ray to check.
     * @param planePoint A point on the plane.
     * @param planeNormal The normal vector of the plane (must be normalized).
     * @return The intersection point as a {@code Vector3}, or null if there is no intersection.
     * @throws IllegalArgumentException if parameters are null or vectors are not normalized.
     */
    public static Vector3 rayIntersectsPlane(Ray ray, Vector3 planePoint, Vector3 planeNormal) {
        if (ray == null || planePoint == null || planeNormal == null) {
            throw new IllegalArgumentException("Parameters must not be null");
        }
        if (Math.abs(ray.getDirection().dot(ray.getDirection()) - 1.0f) > EPSILON) {
            throw new IllegalArgumentException("Ray direction must be normalized");
        }
        if (Math.abs(planeNormal.dot(planeNormal) - 1.0f) > EPSILON) {
            throw new IllegalArgumentException("Plane normal must be normalized");
        }

        float denom = planeNormal.dot(ray.getDirection());
        if (Math.abs(denom) < EPSILON) {
            return null; // No intersection, ray is parallel to the plane
        }

        float t = (planePoint.subtract(ray.getOrigin())).dot(planeNormal) / denom;
        if (t < 0) {
            return null; // Plane is behind the ray
        }

        return new Vector3(
                ray.getOrigin().getX() + ray.getDirection().getX() * t,
                ray.getOrigin().getY() + ray.getDirection().getY() * t,
                ray.getOrigin().getZ() + ray.getDirection().getZ() * t
        );
    }


    /**
     * Checks if a ray intersects with an axis-aligned bounding box (AABB).
     *
     * @param ray The ray to check.
     * @param boxMin The minimum corner of the bounding box.
     * @param boxMax The maximum corner of the bounding box.
     * @return true if the ray intersects the bounding box, false otherwise.
     * @throws IllegalArgumentException if parameters are null or boxMin is greater than boxMax.
     */
    public static boolean rayIntersectsBox(Ray ray, Vector3 boxMin, Vector3 boxMax) {

        if (ray == null || boxMin == null || boxMax == null) {
            throw new IllegalArgumentException("Parameters must not be null");
            }
        if (Math.abs(ray.getDirection().dot(ray.getDirection()) - 1.0f) > EPSILON) {
            throw new IllegalArgumentException("Ray direction must be normalized");
            }
        if (boxMin.getX() > boxMax.getX() || boxMin.getY() > boxMax.getY() || boxMin.getZ() > boxMax.getZ()) {
            throw new IllegalArgumentException("boxMin must be less than boxMax");
            }

        float tMin = (boxMin.getX() - ray.getOrigin().getX()) / ray.getDirection().getX();
        float tMax = (boxMax.getX() - ray.getOrigin().getX()) / ray.getDirection().getX();

        if (tMin > tMax) {
            float temp = tMin;
            tMin = tMax;
            tMax = temp;
        }

        float tyMin = (boxMin.getY() - ray.getOrigin().getY()) / ray.getDirection().getY();
        float tyMax = (boxMax.getY() - ray.getOrigin().getY()) / ray.getDirection().getY();

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

        float tzMin = (boxMin.getZ() - ray.getOrigin().getZ()) / ray.getDirection().getZ();
        float tzMax = (boxMax.getZ() - ray.getOrigin().getZ()) / ray.getDirection().getZ();

        if (tzMin > tzMax) {
            float temp = tzMin;
            tzMin = tzMax;
            tzMax = temp;
            }

        return (!(tMin > tzMax)) && (!(tzMin > tMax)); // No intersection
// Ray hits the box
    }

    /**
     * Finds the closest point on a ray to a given point in 3D space.
     *
     * @param ray   The ray from which to find the closest point.
     * @param point The target point in space.
     * @return The closest point on the ray to the given point.
     * @throws IllegalArgumentException if the ray or point is null, or if the ray direction is not normalized.
     */
    public static Vector3 closestPointOnRay(Ray ray, Vector3 point) {

        if (Math.abs(ray.getDirection().dot(ray.getDirection()) - 1.0f) > EPSILON) {
            throw new IllegalArgumentException("Ray direction must be normalized");
        }


        Vector3 originToPoint = point.subtract(ray.getOrigin());
        float t = originToPoint.dot(ray.getDirection());

        if (t < 0) {
            return ray.getOrigin(); // Closest point is the ray origin
        }

        return new Vector3(
            ray.getOrigin().getX() + ray.getDirection().getX() * t,
            ray.getOrigin().getY() + ray.getDirection().getY() * t,
            ray.getOrigin().getZ() + ray.getDirection().getZ() * t
        );
    }
}