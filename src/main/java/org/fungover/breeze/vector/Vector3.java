package org.fungover.breeze.vector;

public class Vector3 {

    private float x;
    private float y;
    private float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    //basic arithmetic
    public void add(Vector3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }

    public void sub(Vector3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
    }

    public void mul(Vector3 v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
    }

    public void div(Vector3 v) {
        this.x /= v.x;
        this.y /= v.y;
    }

    //Dot product
    public float dot(Vector3 v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    //Cross product
    public Vector3 cross(Vector3 v) {
        return new Vector3(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
    }

    //length/magnitude
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    //Normalization
    public Vector3 normalize() {
        float length = length();
        return new Vector3(this.x / length, this.y / length, this.z / length);
    }

    //Distance between vectors
    public float distance(Vector3 v1, Vector3 v2) {
        return (float) Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2) + Math.pow(v2.z - v1.z, 2));
    }

    //Linear interpolation
    public Vector3 linear(Vector3 v1, Vector3 v2, float t) {
        return new Vector3(v1.x + t * (v2.x - v1.x), v1.y + t * (v2.y - v1.y), v1.z + t * (v2.z - v1.z));
    }

    //Component min/max
    public Vector3 min(Vector3 v1, Vector3 v2) {
        return new Vector3(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
    }

    public Vector3 max(Vector3 v1, Vector3 v2) {
        return new Vector3(Math.max(v1.x, v2.x ), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
    }

    //Convert to vector2 and vector4
    public Vector2 toVector2() {
        return new Vector2(this.x, this.y);
    }
    public Vector4 toVector4(float w) {
        return new Vector4(this.x, this.y, this.z, w);
    }


}
