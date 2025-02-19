package org.fungover.breeze.vector;

/**
 * A record that represents a 3D vector with basic arithmetic operations and vector calculations.
 *
 * @param x the x - coordinate of the vector
 * @param y the y - coordinate of the vector
 * @param z the z - coordinate of the vector
 */
public record Vector3(float x, float y, float z) {

    /**
     * Adds the given vector to this vector
     *
     * @param v the vector to add
     * @return a new vector that is the sum of this vector and the given vector
     */
    public Vector3 add(Vector3 v) {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    /**
     * Subtracts the given vector from this vector
     *
     * @param v the vector to subtract
     * @return a new vector that is the difference between this vector and the given vector
     */
    public Vector3 sub(Vector3 v) {
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    /**
     * Multiplies this vector by the given scalar
     *
     * @param m the scalar to multiply by
     * @return a new vector that is the product of the vector and the given scalar
     */
    public Vector3 mul(float m) {
        return new Vector3(this.x * m, this.y * m, this.z * m);
    }

    /**
     * Divides this vector by the given scalar
     *
     * @param d the scalar to divide by
     * @return a new vector that is the quotient of this vector and the given scalar
     * @throws IllegalArgumentException if the scalar is zero
     */
    public Vector3 div(float d) {
        if (d == 0)
            throw new IllegalArgumentException("Cannot divide by zero");
        return new Vector3(this.x / d, this.y / d, this.z / d);
    }

    /**
     * Calculates the dot product of the vector and the given vector
     *
     * @param v the vector to calculate the dor product with
     * @return the dot product of this vector and the given vector
     */
    public float dot(Vector3 v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    /**
     * Calculates the cross product of this vector and the given vector.
     *
     * @param v the vector to calculate the cross product with
     * @return a new vector that is the cross product of this vector and the given vector
     */
    public Vector3 cross(Vector3 v) {
        return new Vector3(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
    }

    /**
     * Calculates the length(magnitude) of this vector
     *
     * @return the length of this vector
     */
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Normalizes this vector (makes it unit length)
     *
     * @return a new vector that is the normalized version of this vector
     * @throws IllegalArgumentException if the vector has zero length
     */
    public Vector3 normalize() {
        float length = length();
        if (length == 0)
            throw new IllegalArgumentException("Cannot normalize zero-length vector");
        return new Vector3(this.x / length, this.y / length, this.z / length);
    }


    /**
     * Calculates the distance between this vector and the given vector
     *
     * @param v the vector to calculate the distance to
     * @return the distance between this vector and the given vector
     */
    public float distance(Vector3 v) {
        return (float) Math.sqrt(Math.pow(v.x - this.x, 2) + Math.pow(v.y - this.y, 2) + Math.pow(v.z - this.z, 2));
    }

    /**
     * Performs linear interpolation between this vector and the given vector
     *
     * @param v the vector to interpolate to
     * @param t the interpolation factor (between 0 and 1)
     * @return a new vector that is the result of linear interpolation
     * @throws IllegalArgumentException if t is not in the range 0 - 1
     */
    public Vector3 linear(Vector3 v, float t) {
        if (t > 1)
            throw new IllegalArgumentException("lerp can not be larger than 1");
        if (t < 0)
            throw new IllegalArgumentException("lerp can not be negative");

        return new Vector3(this.x + t * (v.x - this.x), this.y + t * (v.y - this.y), this.z + t * (v.z - this.z));
    }

    /**
     * Returns a vector with the minimum components of this vector and the given vector
     *
     * @param v the vector to compare with
     * @return a new vector with the minimum components
     */
    public Vector3 min(Vector3 v) {
        return new Vector3(Math.min(this.x, v.x), Math.min(this.y, v.y), Math.min(this.z, v.z));
    }

    /**
     * Returns a vector with the maximum components of this vector and the given vector
     *
     * @param v the vector to compare with
     * @return a new vector with the maximum components
     */
    public Vector3 max(Vector3 v) {
        return new Vector3(Math.max(this.x, v.x), Math.max(this.y, v.y), Math.max(this.z, v.z));
    }

    /**
     * Convert this vector to a 2D vector by not including the z component
     *
     * @return a new 2D vector
     */
    public Vector2 toVector2() {
        return new Vector2(this.x, this.y);
    }

    /**
     * Converts this vector to a 4D vector by adding a W component
     *
     * @param w the w component
     * @return a new 4D vector
     */
    public Vector4 toVector4(float w) {
        return new Vector4(this.x, this.y, this.z, w);
    }
}
