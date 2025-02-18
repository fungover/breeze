package org.fungover.breeze.vector;

public record Vector4 (float x, float y, float z, float w){


    //basic arithmetic
    public Vector4 add(Vector4 v) {
        return new Vector4(this.x + v.x, this.y + v.y, this.z + v.z, this.w + v.w);
    }

    public Vector4 sub(Vector4 v) {
        return new Vector4(this.x - v.x, this.y - v.y, this.z - v.z, this.w - v.w);
    }

    public Vector4 mul(float m) {
        return new Vector4(this.x * m, this.y * m, this.z * m, this.w * m);
    }

    public Vector4 div(float d) {
        if(d == 0)
            throw new IllegalArgumentException("Cannot divide by zero");
        return new Vector4(this.x / d, this.y / d, this.z / d, this.w / d);
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
    public float distance(Vector4 v2) {
        return (float) Math.sqrt(Math.pow(v2.x - this.x, 2) + Math.pow(v2.y - this.y, 2) + Math.pow(v2.z - this.z, 2) + Math.pow(v2.w - this.w, 2));
    }

    //Linear interpolation
    public Vector4 linear(Vector4 v2, float t) {
        if (t > 1)
            throw new IllegalArgumentException("lerp can not be larger than 1");
        if (t < 0)
            throw new IllegalArgumentException("lerp can not be negative");

        return new Vector4(this.x + t * (v2.x - this.x), this.y + t * (v2.y - this.y), this.z + t * (v2.z - this.z), this.w + t * (v2.w - this.w));
    }

    //Component min/max
    public Vector4 min(Vector4 v2) {
        return new Vector4(Math.min(this.x, v2.x), Math.min(this.y, v2.y), Math.min(this.z, v2.z), Math.min(this.w, v2.w));
    }

    public Vector4 max(Vector4 v2) {
        return new Vector4(Math.max(this.x, v2.x), Math.max(this.y, v2.y), Math.max(this.z, v2.z), Math.max(this.w, v2.w));
    }

    //Convert to vector2 and vector3
    public Vector2 toVector2() {
        return new Vector2(this.x, this.y);
    }

    public Vector3 toVector3() {
        return new Vector3(this.x, this.y, this.z);
    }
}
