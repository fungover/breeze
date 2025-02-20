package org.fungover.breeze.math;

/**
 * Represents a 3D ray with an origin and a direction.
 * A ray is defined by an origin point and a direction vector.
 * This class provides basic operations for ray manipulation.
 */
public class Ray {
    private final Vector3 origin;
    private final Vector3 direction;

    /**
     * Constructs a new Ray with the specified origin and direction.
     *
     * @param origin The starting point of the ray.
     * @param direction The direction of the ray. It is expected to be a normalized vector.
     */
    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction.normalize();  // Normalize the direction vector for consistency
    }

    /**
     * Returns the origin of the ray.
     *
     * @return The origin of the ray as a Vector3f.
     */
    public Vector3 getOrigin() {
        return origin;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return The direction of the ray as a Vector3f.
     */
    public Vector3 getDirection() {
        return direction;
    }

//    /**
//     * Sets the origin of the ray to a new point.
//     *
//     * @param origin The new origin of the ray as a Vector3.
//     */
//    public void setOrigin(Vector3 origin) {
//        this.origin = origin;
//    }

//    /**
//     * Sets the direction of the ray to a new vector.
//     * The direction is normalized for consistency.
//     *
//     * @param direction The new direction of the ray as a Vector3f.
//     */
//    public void setDirection(Vector3 direction) {
//        this.direction = direction.normalize();
//    }

//    /**
//     * Computes and returns a point along the ray at a given parameter t.
//     * The point at parameter t is calculated as: origin + t * direction
//     *
//     * @param t The parameter value along the ray. t = 0 gives the origin, t = 1 gives the point 1 unit along the ray.
//     * @return The point on the ray at parameter t as a Vector3f.
//     */
//    public Vector3 pointAtParameter(float t) {
//        return origin.add(direction.multiply(t));
//    }

    /**
     * Returns a string representation of the ray in the form "Ray(origin: (x, y, z), direction: (dx, dy, dz))".
     *
     * @return A string representation of the ray.
     */
    @Override
    public String toString() {
        return String.format("Ray(origin: %s, direction: %s)", origin, direction);
    }
}