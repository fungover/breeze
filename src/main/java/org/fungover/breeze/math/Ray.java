package org.fungover.breeze.math;

public class Ray {
    public Vector3 origin;
    public Vector3 direction;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        float length = (float) Math.sqrt(direction.x * direction.x + direction.y * direction.y + direction.z * direction.z);
        this.direction = (length > 0) ? direction.normalize() : new Vector3(1, 0, 0); // Default to (1,0,0)
    }
}
