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
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    public Vector2 mul(float m) {
        return new Vector2(x * m, y * m);
    }

    public Vector2 div(float d) {
        return new Vector2(x / d, y / d);
    }
    //dot product
    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }
    //length/magnitude
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }



}
