package org.fungover.breeze.math;

import java.util.Locale;

public class Quaternion {
    private final float w;
    private final float x;
    private final float y;
    private final float z;

    // Constructor
    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Default constructor (Identity quaternion)
    public Quaternion() {
        this(1.0f, 0.0f, 0.0f, 0.0f);
    }

    // Getter and Setter methods
    public final float getW() { return w; }
    public final float getX() { return x; }
    public final float getY() { return y; }
    public final float getZ() { return z; }


    // Normalize a quaternion
    public Quaternion normalize() {
        float len = length();
        if (len == 0) {
            return new Quaternion();
        }
        return new Quaternion(w / len, x / len, y / len, z / len);
    }

    // Length of the quaternion (magnitude)
    public float length() {
        return (float) Math.sqrt(w * w + x * x + y * y + z * z);
    }

    // Conjugate of the quaternion
    public Quaternion conjugate() {
        return new Quaternion(w, -x, -y, -z);
    }

    // Inverse of the quaternion
    public Quaternion inverse() {
        float lenSquared = length() * length();
        if (lenSquared == 0) {
            return new Quaternion();
        }
        return conjugate().multiply(1.0f / lenSquared);
    }

    // Quaternion multiplication
    public Quaternion multiply(Quaternion q) {
        float newW = w * q.getW() - x * q.getX() - y * q.getY() - z * q.getZ();
        float newX = w * q.getX() + x * q.getW() + y * q.getZ() - z * q.getY();
        float newY = w * q.getY() + y * q.getW() + z * q.getX() - x * q.getZ();
        float newZ = w * q.getZ() + z * q.getW() + x * q.getY() - y * q.getX();
        return new Quaternion(newW, newX, newY, newZ);
    }

    // Multiply by a scalar value
    public Quaternion multiply(float scalar) {
        return new Quaternion(w * scalar, x * scalar, y * scalar, z * scalar);
    }

    // Add another quaternion
    public Quaternion add(Quaternion q) {
        return new Quaternion(w + q.getW(), x + q.getX(), y + q.getY(), z + q.getZ());
    }

    // Slerp (Spherical Linear Interpolation)
    public static Quaternion slerp(Quaternion a, Quaternion b, float t) {
        float dot = a.getW() * b.getW() + a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();

        if (dot < 0.0f) {
            b = new Quaternion(-b.getW(), -b.getX(), -b.getY(), -b.getZ());
            dot = -dot;
        }

        final float THRESHOLD = 0.9995f;
        if (dot > THRESHOLD) {
            Quaternion result = a.add(b.subtract(a).multiply(t));
            result = result.normalize();
            return result;
        }

        float theta_0 = (float) Math.acos(dot);
        float theta = theta_0 * t;
        Quaternion bTemp = b.subtract(a.multiply(dot));
        bTemp = bTemp.normalize();

        return a.multiply((float) Math.cos(theta)).add(bTemp.multiply((float) Math.sin(theta)));
    }

    // Subtract another quaternion
    public Quaternion subtract(Quaternion q) {
        return new Quaternion(w - q.getW(), x - q.getX(), y - q.getY(), z - q.getZ());
    }

    /**
     * Rotates a vector by the quaternion. This method uses the formula:
     * v' = q * v * q^-1, where v is the vector, and q is the quaternion.
     *
     * @param v The vector to rotate.
     * @return The rotated vector as a Vector3.
     */
    public Vector3 rotate(Vector3 v) {
        // Convert the vector to a quaternion with w = 0 (since it's purely a vector, not a rotation)
        Quaternion qv = new Quaternion(0, v.getX(), v.getY(), v.getZ());

        // Apply the rotation: q * v * q^-1
        Quaternion qInv = this.inverse(); // Inverse of the quaternion for the rotation
        Quaternion rotated = this.multiply(qv).multiply(qInv);

        // The rotated vector is stored in the result quaternion's vector part (x, y, z)
        return new Vector3(rotated.getX(), rotated.getY(), rotated.getZ());
    }

    // To String (for debugging)
    @Override
    public String toString() {
        return String.format(Locale.FRANCE, "Quaternion(w: %f, x: %f, y: %f, z: %f)", w, x, y, z);
    }

}