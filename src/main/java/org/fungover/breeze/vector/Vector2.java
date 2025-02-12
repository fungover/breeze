package org.fungover.breeze.vector;

public class Vector2 {
    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
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

    //basic arithmetic
    public void add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void sub(Vector2 v){
        this.x -= v.x;
        this.y -= v.y;
    }

    public void mul(float m) {
        this.x *= m;
        this.y *= m;
    }

    public void div(float d) {
        this.x /= d;
        this.y /= d;
    }

    //dot product
    public float dot(Vector2 v) {
        return this.x * v.x + this.y * v.y;
    }

    //length/magnitude
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    //Normalization
    public Vector2 normalize() {
        float length = length();
        return new Vector2(this.x / length, this.y / length);
    }

    //Distance between vectors
    public float distance(Vector2 v1, Vector2 v2) {
        return (float) Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }

    //Linear interpolation
    public Vector2 linear(Vector2 v1, Vector2 v2, float t) {
        if(t > 1)
            throw new IllegalArgumentException("lerp can not be larger than 1");

        return new Vector2(v1.x + t * (v2.x - v1.x), v1.y + t * (v2.y - v1.y));
    }

    //Component min/max
    public Vector2 min(Vector2 v1, Vector2 v2) {
        return new Vector2(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y));
    }

    public Vector2 max(Vector2 v1, Vector2 v2) {
        return new Vector2(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y));
    }

    //Convert to vector3 and vector4
    public Vector3 toVector3(float z) {
        return new Vector3(this.x, this.y, z);
    }

    public Vector4 toVector4(float z, float w) {
        return new Vector4(this.x, this.y, z, w);
    }


}
