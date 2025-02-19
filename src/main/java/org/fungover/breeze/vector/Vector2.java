package org.fungover.breeze.vector;

/**
 * A record that represents a 2D vector with basic arithmetic operations and vector calculations.
 *
 * @param x the x - coordinate of the vector
 * @param y the y - coordinate of the vector
 */
public record Vector2(float x, float y) {

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
    public Vector2 add(Vector2 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return new Vector2(this.x + v.x, this.y + v.y);
    }

    /**
     * Subtracts the given vector from this vector
     *
     * @param v the vector to subtract
     * @return a new vector that is the difference between this vector and the given vector
     * @throws IllegalArgumentException if vector parameter is null
     */
    public Vector2 sub(Vector2 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return new Vector2(this.x - v.x, this.y - v.y);
    }

    /**
     * Multiplies this vector by the given scalar
     *
     * @param m the scalar to multiply by
     * @return a new vector that is the product of the vector and the given scalar
     */
    public Vector2 mul(float m) {
        return new Vector2(this.x * m, this.y * m);
    }

    /**
     * Divides this vector by the given scalar
     *
     * @param d the scalar to divide by
     * @return a new vector that is the quotient of this vector and the given scalar
     * @throws IllegalArgumentException if the scalar is zero
     */
    public Vector2 div(float d) {
        if (d == 0)
            throw new IllegalArgumentException("Cannot divide by zero");
        return new Vector2(this.x / d, this.y / d);
    }

    /**
     * Calculates the dot product of the vector and the given vector
     *
     * @param v the vector to calculate the dor product with
     * @return the dot product of this vector and the given vector
     * @throws IllegalArgumentException if vector parameter is null
     */
    public float dot(Vector2 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return this.x * v.x + this.y * v.y;
    }

    /**
     * Calculates the length(magnitude) of this vector
     *
     * @return the length of this vector
     */
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Normalizes this vector (makes it unit length)
     *
     * @return a new vector that is the normalized version of this vector
     * @throws IllegalArgumentException if the vector has zero length
     */
    public Vector2 normalize() {
        float length = length();
        if (length == 0)
            throw new IllegalArgumentException("Cannot normalize zero-length vector");
        return new Vector2(this.x / length, this.y / length);
    }

    /**
     * Calculates the distance between this vector and the given vector
     *
     * @param v the vector to calculate the distance to
     * @return the distance between this vector and the given vector
     * @throws IllegalArgumentException if vector parameter is null
     */
    public float distance(Vector2 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return (float) Math.sqrt(Math.pow(v.x - this.x, 2) + Math.pow(v.y - this.y, 2));
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
    public Vector2 linear(Vector2 v, float t) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        if (t > 1)
            throw new IllegalArgumentException("lerp can not be larger than 1");
        if (t < 0)
            throw new IllegalArgumentException("lerp can not be negative");

        return new Vector2(this.x + t * (v.x - this.x), this.y + t * (v.y - this.y));
    }

    /**
     * Returns a vector with the minimum components of this vector and the given vector
     *
     * @param v the vector to compare with
     * @return a new vector with the minimum components
     * @throws IllegalArgumentException if vector parameter is null
     */
    public Vector2 min(Vector2 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return new Vector2(Math.min(this.x, v.x), Math.min(this.y, v.y));
    }

    /**
     * Returns a vector with the maximum components of this vector and the given vector
     *
     * @param v the vector to compare with
     * @return a new vector with the maximum components
     * @throws IllegalArgumentException if vector parameter is null
     */
    public Vector2 max(Vector2 v) {
        if (v == null)
            throw new IllegalArgumentException(ParameterNull);
        return new Vector2(Math.max(this.x, v.x), Math.max(this.y, v.y));
    }

    /**
     * Convert this vector to a 3D vector by adding a z component
     *
     * @param z the z component
     * @return a new 3D vector
     */
    public Vector3 toVector3(float z) {
        return new Vector3(this.x, this.y, z);
    }

    /**
     * Converts this vector to a 4D vector by adding z and w components
     *
     * @param z the z component
     * @param w the w component
     * @return a new 4D vector
     */
    public Vector4 toVector4(float z, float w) {
        return new Vector4(this.x, this.y, z, w);
    }


}
