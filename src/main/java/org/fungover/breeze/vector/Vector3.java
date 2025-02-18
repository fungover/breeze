package org.fungover.breeze.vector;

public record Vector3(float x, float y, float z) {

    //basic arithmetic
    public Vector3 add(Vector3 v) {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector3 sub(Vector3 v) {
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vector3 mul(float m) {
        return new Vector3(this.x * m, this.y * m, this.z * m);
    }

    public Vector3 div(float d) {
        return new Vector3(this.x / d, this.y / d, this.z / d);
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
    public float distance(Vector3 v2) {
        return (float) Math.sqrt(Math.pow(v2.x - this.x, 2) + Math.pow(v2.y - this.y, 2) + Math.pow(v2.z - this.z, 2));
    }

    //Linear interpolation
    public Vector3 linear(Vector3 v2, float t) {
        if (t > 1)
            throw new IllegalArgumentException("lerp can not be larger than 1");
        if (t < 0)
            throw new IllegalArgumentException("lerp can not be negative");
        return new Vector3(this.x + t * (v2.x - this.x), this.y + t * (v2.y - this.y), this.z + t * (v2.z - this.z));
    }

    //Component min/max
    public Vector3 min(Vector3 v2) {
        return new Vector3(Math.min(this.x, v2.x), Math.min(this.y, v2.y), Math.min(this.z, v2.z));
    }

    public Vector3 max(Vector3 v2) {
        return new Vector3(Math.max(this.x, v2.x), Math.max(this.y, v2.y), Math.max(this.z, v2.z));
    }

    //Convert to vector2 and vector4
    public Vector2 toVector2() {
        return new Vector2(this.x, this.y);
    }

    public Vector4 toVector4(float w) {
        return new Vector4(this.x, this.y, this.z, w);
    }
}
