package org.fungover.breeze.vector;

public class Vector4 {
    private float x;
    private float y;
    private float z;
    private float w;


    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }
    //basic arithmetic
    public void add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
    }

    public void sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
    }
    public void mul(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
    }
    public void div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
    }

    //Dot product
    public float dot(Vector4 v) {
        return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w;
    }

    //length/magnitude
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
    }

    //Normalization
    public Vector4 normalize() {
        float length = length();
        return new Vector4(this.x / length, this.y / length, this.z / length, this.w / length);
    }

    //Distance between vectors
    public float distance(Vector4 v1, Vector4 v2) {
        return (float) Math.sqrt(Math.pow(v2.x -v1.x, 2) + Math.pow(v2.y - v1.y, 2) + Math.pow(v2.z - v1.z, 2) + Math.pow(v2.w - v1.w, 2));
    }

    //Linear interpolation
    public Vector4 linear(Vector4 v1, Vector4 v2, float t) {
        return new Vector4(v1.x + t * (v2.x - v1.x), v1.y + t * (v2.y - v1.y), v1.z + t * (v2.z -v1.z), v1.w + t * (v2.w - v1.w));
    }

    //Component min/max
    public Vector4 min(Vector4 v1, Vector4 v2) {
        return new Vector4(Math.min(v1.x ,v2.x) ,Math.min(v1.y ,v2.y), Math.min(v1.z ,v2.z),Math.min(v1.w ,v2.w) );
    }

    public Vector4 max(Vector4 v1, Vector4 v2) {
        return new Vector4(Math.max(v1.x ,v2.x), Math.max(v1.y, v2.y), Math.max(v1.z ,v2.z),Math.max(v1.w ,v2.w) );
    }

    //Convert to vector2 and vector3
    public Vector2 toVector2() {
        return new Vector2(this.x, this.y);
    }
    public Vector3 toVector3() {
        return new Vector3(this.x, this.y, this.z);
    }



}
