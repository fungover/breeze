package org.fungover.breeze.math;

import java.util.Locale;

/**
 * Represents a 3D vector with floating-point components (x, y, z).
 * Provides common vector operations such as addition, subtraction, scaling, dot product, cross product, and normalization.
 */
public class Vector3 {
    float x;
    float y;
    float z;

    /**
     * Constructs a new Vector3 with the given components.
     *
     * @param x The x-component of the vector.
     * @param y The y-component of the vector.
     * @param z The z-component of the vector.
     */
    public Vector3(float x, float y, float z) {

        if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z)) {
            throw new IllegalArgumentException("Vector components cannot be NaN");
        }
        if (Float.isInfinite(x) || Float.isInfinite(y) || Float.isInfinite(z)) {
            throw new IllegalArgumentException("Vector components cannot be infinite");
        }

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a new Vector3 initialized to the zero vector (0, 0, 0).
     */
    public Vector3() {
        this(0.0f, 0.0f, 0.0f);
    }

    /**
     * Returns the x-component of the vector.
     *
     * @return The x-component of the vector.
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the y-component of the vector.
     *
     * @return The y-component of the vector.
     */

    public float getY() {
        return y;
    }

    /**
     * Returns the z-component of the vector.
     *
     * @return The z-component of the vector.
     */
    public float getZ() {
        return z;
    }


    /**
     * Adds another vector to this vector and returns the result.
     *
     * @param v The vector to add.
     * @return A new Vector3 representing the sum of this vector and the given vector.
     */
    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    /**
     * Subtracts another vector from this vector and returns the result.
     *
     * @param v The vector to subtract.
     * @return A new Vector3 representing the difference between this vector and the given vector.
     */
    public Vector3 subtract(Vector3 v) {
        return new Vector3(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    /**
     * Multiplies the vector by a scalar and returns the result.
     *
     * @param scalar The scalar value to multiply the vector by.
     * @return A new Vector3 representing the scaled vector.
     * @throws IllegalArgumentException if scalar is NaN or infinite
     */
    public Vector3 multiply(float scalar) {
        if (Float.isNaN(scalar) || Float.isInfinite(scalar)) {
            throw new IllegalArgumentException("Scalar must be a finite number");
        }
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    /**
     * Calculates the dot product of this vector and another vector.
     *
     * @param v The vector to compute the dot product with.
     * @return The dot product as a float.
     */
    public float dot(Vector3 v) {
        return x * v.getX() + y * v.getY() + z * v.getZ();
    }

    /**
     * Calculates the cross product of this vector and another vector.
     * The result is a vector perpendicular to both input vectors.
     *
     * @param v The vector to compute the cross product with.
     * @return A new Vector3 representing the cross product.
     */
    public Vector3 cross(Vector3 v) {
        float newX = y * v.getZ() - z * v.getY();
        float newY = z * v.getX() - x * v.getZ();
        float newZ = x * v.getY() - y * v.getX();
        return new Vector3(newX, newY, newZ);
    }

    /**
     * Returns the length (magnitude) of the vector.
     *
     * @return The length of the vector.
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Normalizes the vector, i.e., scales it to have a length of 1.
     * If the length of the vector is zero, a zero vector is returned.
     *
     * @return A new Vector3 representing the normalized vector.
     */
    public Vector3 normalize() {
        float len = length();
        if (len == 0) {
            return new Vector3();
        }
        return new Vector3(x / len, y / len, z / len);
    }

    /**
     * Returns a string representation of the vector in the form (x, y, z).
     *
     * @return A string representation of the vector.
     */
    @Override
    public String toString() {
        return String.format(Locale.FRANCE, "Vector3(x: %f, y: %f, z: %f)", x, y, z);
    }
}