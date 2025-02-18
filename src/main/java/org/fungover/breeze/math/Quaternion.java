package org.fungover.breeze.math;

public class Quaternion {
    public final float x, y, z, w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getW() { return w; }

    public Quaternion multiply(Quaternion q) {
        return new Quaternion(
                w * q.x + x * q.w + y * q.z - z * q.y,
                w * q.y - x * q.z + y * q.w + z * q.x,
                w * q.z + x * q.y - y * q.x + z * q.w,
                w * q.w - x * q.x - y * q.y - z * q.z
        );
    }

    // ✅ Normalize the Quaternion
    public Quaternion normalize() {
        float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
        if (length == 0) return new Quaternion(0, 0, 0, 1); // Identity quaternion
        return new Quaternion(x / length, y / length, z / length, w / length);
    }

    // ✅ Spherical Linear Interpolation (SLERP)
    public static Quaternion slerp(Quaternion q1, Quaternion q2, float t) {
        // Clamp t to range [0, 1]
        t = Math.max(0, Math.min(1, t));

        float dot = q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w;

        // If the dot product is negative, negate one quaternion to take the shortest path
        if (dot < 0.0f) {
            q2 = new Quaternion(-q2.x, -q2.y, -q2.z, -q2.w);
            dot = -dot;
        }

        final float THRESHOLD = 0.9995f;
        if (dot > THRESHOLD) {
            // If the quaternions are very close, use linear interpolation to avoid precision errors
            Quaternion result = new Quaternion(
                    q1.x + t * (q2.x - q1.x),
                    q1.y + t * (q2.y - q1.y),
                    q1.z + t * (q2.z - q1.z),
                    q1.w + t * (q2.w - q1.w)
            );
            return result.normalize();
        }

        // Compute interpolation
        float theta_0 = (float) Math.acos(dot);
        float theta = theta_0 * t;
        float sin_theta = (float) Math.sin(theta);
        float sin_theta_0 = (float) Math.sin(theta_0);

        float s1 = (float) Math.cos(theta) - dot * sin_theta / sin_theta_0;
        float s2 = sin_theta / sin_theta_0;

        return new Quaternion(
                s1 * q1.x + s2 * q2.x,
                s1 * q1.y + s2 * q2.y,
                s1 * q1.z + s2 * q2.z,
                s1 * q1.w + s2 * q2.w
        );
    }

    // Convert quaternion to a rotation matrix and apply it to a Vector3
    public Vector3 rotateVector(Vector3 v) {
        Quaternion vectorQuat = new Quaternion(v.x, v.y, v.z, 0);
        Quaternion inverse = new Quaternion(-x, -y, -z, w);
        Quaternion rotated = this.multiply(vectorQuat).multiply(inverse);
        return new Vector3(rotated.x, rotated.y, rotated.z);
    }
}
