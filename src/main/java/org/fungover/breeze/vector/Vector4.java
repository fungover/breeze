package org.fungover.breeze.vector;

/**
 * A record that represents a 4D vector with basic arithmetic operations and vector calculations
 *
 * @param x the x - coordinate of the vector
 * @param y the y - coordinate of the vector
 * @param z the z - coordinate of the vector
 * @param w the w - coordinate of the vector
 */
public record Vector4(float x, float y, float z, float w) {

    /**
     * Error message used when vector parameter is null
     */
    public static final String ParameterNull = "Vector parameter cannot be null";

    /**
     * Adds the given vector to this vector
     *
     * @param v the vector to add
     * @return a new vector that is the sum of this vector and the given vector
     * @throws IllegalArgumentException if vector parameter is null
     */
    public Vector4 add(Vector4 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return new Vector4(this.x + v.x, this.y + v.y, this.z + v.z, this.w + v.w);
    }

    /**
     * Subtract the given vector from this vector
     *
     * @param v the vector to subtract
     * @return a new vector that is the difference between this vector and the given vector
     * @throws IllegalArgumentException if vector parameter is null
     */
    public Vector4 sub(Vector4 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return new Vector4(this.x - v.x, this.y - v.y, this.z - v.z, this.w - v.w);
    }

    /**
     * Multiplies this vector by the given scalar
     *
     * @param m the scalar to multiply by
     * @return a new vector that is the product of the vector and the given scalar
     */
    public Vector4 mul(float m) {
        return new Vector4(this.x * m, this.y * m, this.z * m, this.w * m);
    }

    /**
     * Divides this vector by the given scalar
     *
     * @param d the scalar to divide by
     * @return a new vector that is the quotient of this vector and the given scalar
     * @throws IllegalArgumentException if the scalar is zero
     */
    public Vector4 div(float d) {
        if (d == 0)
            throw new IllegalArgumentException("Cannot divide by zero");
        return new Vector4(this.x / d, this.y / d, this.z / d, this.w / d);
    }

    /**
     * Calculates the dot product of the vector and the given vector
     *
     * @param v the vector to calculate the dor product with
     * @return the dot product of this vector and the given vector
     * @throws IllegalArgumentException if vector parameter is null
     */
    public float dot(Vector4 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w;
    }

    /**
     * Calculates the length(magnitude) of this vector
     *
     * @return the length of this vector
     */
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
    }

    /**
     * Normalizes this vector (makes it unit length)
     *
     * @return a new vector that is the normalized version of this vector
     * @throws IllegalArgumentException if the vector has zero length
     */
    public Vector4 normalize() {
        float length = length();
        if (length == 0)
            throw new IllegalArgumentException("Cannot normalize zero-length vector");
        return new Vector4(this.x / length, this.y / length, this.z / length, this.w / length);
    }

    /**
     * Calculates the distance between this vector and the given vector
     *
     * @param v the vector to calculate the distance to
     * @return the distance between this vector and the given vector
     * @throws IllegalArgumentException if vector parameter is null
     */
    public float distance(Vector4 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        float dx = v.x - this.x;
        float dy = v.y - this.y;
        float dz = v.z - this.z;
        float dw = v.w - this.w;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /**
     * Performs linear interpolation between this vector and the given vector
     *
     * @param v the vector to interpolate to
     * @param t the interpolation factor (between 0 and 1)
     * @return a new vector that is the result of linear interpolation
     * @throws IllegalArgumentException if vector parameter is null
     * @throws IllegalArgumentException if t is not in the range 0 - 1
     */
    public Vector4 linear(Vector4 v, float t) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        if (t > 1)
            throw new IllegalArgumentException("lerp can not be larger than 1");
        if (t < 0)
            throw new IllegalArgumentException("lerp can not be negative");

        return new Vector4(this.x + t * (v.x - this.x), this.y + t * (v.y - this.y), this.z + t * (v.z - this.z), this.w + t * (v.w - this.w));
    }

    /**
     * Returns a vector with the minimum components of this vector and the given vector
     *
     * @param v the vector to compare with
     * @return a new vector with the minimum components
     * @throws IllegalArgumentException if vector parameter is null
     */
    public Vector4 min(Vector4 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return new Vector4(Math.min(this.x, v.x), Math.min(this.y, v.y), Math.min(this.z, v.z), Math.min(this.w, v.w));
    }

    /**
     * Returns a vector with the maximum components of this vector and the given vector
     *
     * @param v the vector to compare with
     * @return a new vector with the maximum components
     * @throws IllegalArgumentException if vector parameter is null
     */
    public Vector4 max(Vector4 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return new Vector4(Math.max(this.x, v.x), Math.max(this.y, v.y), Math.max(this.z, v.z), Math.max(this.w, v.w));
    }

    /**
     * Convert this vector to a 2D vector by excluding z and w component
     *
     * @return a new 2D vector
     */
    public Vector2 toVector2() {
        return new Vector2(this.x, this.y);
    }

    /**
     * Convert this vector to a 3D vector by excluding w component
     *
     * @return a new 3D vector
     */
    public Vector3 toVector3() {
        return new Vector3(this.x, this.y, this.z);
    }
}
