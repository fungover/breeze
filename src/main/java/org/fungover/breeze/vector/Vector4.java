package org.fungover.breeze.vector;

import java.util.Arrays;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

/**
 * This class represents a 4D vector with x, y, z, and w components.
 */
public final class Vector4 {
    /**
     * The components of the vector.
     */
    private final float[] components;
    public static final String PARAMETER_NULL = "Vector parameter cannot be null";

    /**
     * Constructs a new Vector4 with the given components.
     *
     * @param x The x component.
     * @param y The y component.
     * @param z The z component.
     * @param w The w component.
     */
    public Vector4(float x, float y, float z, float w) {
        this.components = new float[]{x, y, z, w};
    }

    /**
     * Gets the x component of the vector.
     *
     * @return The x component.
     */
    public float x() { return components[0]; }

    /**
     * Gets the y component of the vector.
     *
     * @return The y component.
     */
    public float y() { return components[1]; }

    /**
     * Gets the z component of the vector.
     *
     * @return The z component.
     */
    public float z() { return components[2]; }

    /**
     * Gets the w component of the vector.
     *
     * @return The w component.
     */
    public float w() { return components[3]; }

    /**
     * Adds the given vector to this vector.
     *
     * @param v The vector to add.
     * @return A new Vector4 representing the sum.
     * @throws IllegalArgumentException if the vector parameter is null.
     */
    public Vector4 add(Vector4 v) {
        if (v == null) throw new IllegalArgumentException(PARAMETER_NULL);

        VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;
        FloatVector vector1 = FloatVector.fromArray(SPECIES, this.components, 0);
        FloatVector vector2 = FloatVector.fromArray(SPECIES, v.components, 0);
        FloatVector resultVector = vector1.add(vector2);
        float[] result = new float[4];
        resultVector.intoArray(result, 0);

        return new Vector4(result[0], result[1], result[2], result[3]);
    }

    /**
     * Subtracts the given vector from this vector.
     *
     * @param v The vector to subtract.
     * @return A new Vector4 representing the difference.
     * @throws IllegalArgumentException if the vector parameter is null.
     */
    public Vector4 sub(Vector4 v) {
        if (v == null) throw new IllegalArgumentException(PARAMETER_NULL);

        VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;
        FloatVector vector1 = FloatVector.fromArray(SPECIES, this.components, 0);
        FloatVector vector2 = FloatVector.fromArray(SPECIES, v.components, 0);
        FloatVector resultVector = vector1.sub(vector2);
        float[] result = new float[4];
        resultVector.intoArray(result, 0);

        return new Vector4(result[0], result[1], result[2], result[3]);
    }

    /**
     * Multiplies this vector by the given scalar.
     *
     * @param m The scalar to multiply by.
     * @return A new Vector4 representing the product.
     */
    public Vector4 mul(float m) {
        VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;
        FloatVector vector = FloatVector.fromArray(SPECIES, this.components, 0);
        FloatVector scalarVector = FloatVector.broadcast(SPECIES, m);
        FloatVector resultVector = vector.mul(scalarVector);
        float[] result = new float[4];
        resultVector.intoArray(result, 0);

        return new Vector4(result[0], result[1], result[2], result[3]);
    }

    /**
     * Divides this vector by the given scalar.
     *
     * @param d The scalar to divide by.
     * @return A new Vector4 representing the quotient.
     * @throws IllegalArgumentException if the scalar is zero.
     */
    public Vector4 div(float d) {
        if (Math.abs(d) < 1e-6f) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }

        VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;
        FloatVector vector = FloatVector.fromArray(SPECIES, this.components, 0);
        FloatVector scalarVector = FloatVector.broadcast(SPECIES, d);
        FloatVector resultVector = vector.div(scalarVector);
        float[] result = new float[4];
        resultVector.intoArray(result, 0);

        return new Vector4(result[0], result[1], result[2], result[3]);
    }

    /**
     * Calculates the dot product of this vector and the given vector.
     *
     * @param v The vector to dot product with.
     * @return The dot product of the two vectors.
     * @throws IllegalArgumentException if the vector parameter is null.
     */
    public float dot(Vector4 v) {
        if (v == null) throw new IllegalArgumentException(PARAMETER_NULL);

        VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;
        FloatVector vector1 = FloatVector.fromArray(SPECIES, this.components, 0);
        FloatVector vector2 = FloatVector.fromArray(SPECIES, v.components, 0);
        FloatVector resultVector = vector1.mul(vector2);
        return resultVector.reduceLanes(VectorOperators.ADD);
    }

    /**
     * Calculates the length (magnitude) of this vector.
     *
     * @return The length of the vector.
     */
    public float length() {
        return (float) Math.sqrt(this.dot(this));
    }

    /**
     * Normalizes this vector (makes it unit length).
     *
     * @return A new Vector4 representing the normalized vector.
     * @throws IllegalArgumentException if the vector length is zero.
     */
    public Vector4 normalize() {
        float length = length();
        if (length < 1e-6f) throw new IllegalArgumentException("Cannot normalize zero-length vector");
        return div(length);
    }

    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param v The vector to measure distance to.
     * @return The distance between the two vectors.
     * @throws IllegalArgumentException if the vector parameter is null.
     */
    public float distance(Vector4 v) {
        if (v == null) throw new IllegalArgumentException(PARAMETER_NULL);
        return sub(v).length();
    }

    /**
     * Performs linear interpolation between this vector and the given vector.
     *
     * @param v The vector to interpolate with.
     * @param t The interpolation factor (between 0 and 1).
     * @return A new Vector4 representing the interpolated vector.
     * @throws IllegalArgumentException if the vector parameter is null or if t is not between 0 and 1.
     */
    public Vector4 linear(Vector4 v, float t) {
        if (v == null) throw new IllegalArgumentException(PARAMETER_NULL);
        if (t > 1 || t < 0) throw new IllegalArgumentException("Interpolation factor must be between 0 and 1");

        VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;
        FloatVector vector1 = FloatVector.fromArray(SPECIES, this.components, 0);
        FloatVector vector2 = FloatVector.fromArray(SPECIES, v.components, 0);
        FloatVector tVector = FloatVector.broadcast(SPECIES, t);
        FloatVector diffVector = vector2.sub(vector1);
        FloatVector resultVector = vector1.add(tVector.mul(diffVector));
        float[] result = new float[4];
        resultVector.intoArray(result, 0);

        return new Vector4(result[0], result[1], result[2], result[3]);
    }

    /**
     * Returns a vector with the minimum components of this vector and the given vector.
     *
     * @param v The vector to compare with.
     * @return A new Vector4 with the minimum components.
     * @throws IllegalArgumentException if the vector parameter is null.
     */
    public Vector4 min(Vector4 v) {
        if (v == null) throw new IllegalArgumentException(PARAMETER_NULL);

        VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;
        FloatVector vector1 = FloatVector.fromArray(SPECIES, this.components, 0);
        FloatVector vector2 = FloatVector.fromArray(SPECIES, v.components, 0);
        FloatVector resultVector = vector1.min(vector2);
        float[] result = new float[4];
        resultVector.intoArray(result, 0);

        return new Vector4(result[0], result[1], result[2], result[3]);
    }

    /**
     * Returns a vector with the maximum components of this vector and the given vector.
     *
     * @param v The vector to compare with.
     * @return A new Vector4 with the maximum components.
     * @throws IllegalArgumentException if the vector parameter is null.
     */
    public Vector4 max(Vector4 v) {
        if (v == null) throw new IllegalArgumentException(PARAMETER_NULL);

        VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;
        FloatVector vector1 = FloatVector.fromArray(SPECIES, this.components, 0);
        FloatVector vector2 = FloatVector.fromArray(SPECIES, v.components, 0);
        FloatVector resultVector = vector1.max(vector2);
        float[] result = new float[4];
        resultVector.intoArray(result, 0);

        return new Vector4(result[0], result[1], result[2], result[3]);
    }

    /**
     * Converts this vector to a 2D vector by excluding the z and w components.
     *
     * @return A new Vector2 representing the 2D vector.
     */
    public Vector2 toVector2() {
        return new Vector2(this.x(), this.y());
    }

    /**
     * Converts this vector to a 3D vector by excluding the w component.
     *
     * @return A new Vector3 representing the 3D vector.
     */
    public Vector3 toVector3() {
        return new Vector3(this.x(), this.y(), this.z());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector4 v)) return false;
        return Arrays.equals(components, v.components);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(components);
    }

    @Override
    public String toString() {
        return String.format("Vector4[%.2f, %.2f, %.2f, %.2f]", x(), y(), z(), w());
    }
}
