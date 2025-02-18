package org.fungover.breeze.math;

public class Ray {
    public final Vector3 origin;
    public final Vector3 direction;
    public Vector3 getOrigin() {
    return origin;
    }

    public Vector3 getDirection() {
    return direction;
    }

    public Ray(Vector3 origin, Vector3 direction) {
        if (origin == null || direction == null) {
            throw new IllegalArgumentException("Origin and direction cannot be null");
            }
        this.origin = origin;
        this.direction = direction.normalize();
    }
}
