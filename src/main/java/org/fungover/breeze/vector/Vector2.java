package org.fungover.breeze.vector;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //basic arithmetic
    public Vector2 add(Vector2 v) {
        return new Vector2(this.x + v.x, this.y + v.y);
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(this.x - v.x, this.y - v.y);
    }

    public Vector2 mul(float m) {
        return new Vector2(this.x * m, this.y * m);
    }

    public Vector2 div(float d) {
        return new Vector2(this.x / d, this.y / d);
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
    public float distance(Vector2 v1,Vector2 v2) {
        return (float) Math.sqrt(Math.pow(v2.x - v1.x, 2 ) + Math.pow(v2.y - v1.y, 2));
    }


}
