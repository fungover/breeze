package org.fungover.breeze.math;

public class Vector3 {
    public final float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Addition
    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    // Subtraction
    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Dot Product (returns a single float)
    public float dot(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Cross Product (returns a new Vector3)
    public Vector3 cross(Vector3 other) {
        return new Vector3(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    public float lengthSquared() {
        return  x * x + y * y + z * z;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }


    public Vector3 normalize() {
        float length = length();
        if (length == 0) return new Vector3(0, 0, 0); // Prevent division by zero
        return new Vector3(this.x / length, this.y / length, this.z / length);
    }


    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
