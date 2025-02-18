package org.fungover.breeze.vector;

public record Vector2 (float x,float y){

    //basic arithmetic
    public Vector2 add(Vector2 v) {
        return new Vector2(this.x + v.x, this.y + v.y);
    }

    public Vector2 sub(Vector2 v){
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
    public float distance(Vector2 v2) {
        return (float) Math.sqrt(Math.pow(v2.x - this.x, 2) + Math.pow(v2.y - this.y, 2));
    }

    //Linear interpolation
    public Vector2 linear(Vector2 v2, float t) {
        if(t > 1)
            throw new IllegalArgumentException("lerp can not be larger than 1");
        if(t < 0)
            throw new IllegalArgumentException("lerp can not be negative");

        return new Vector2(this.x + t * (v2.x - this.x), this.y + t * (v2.y - this.y));
    }

    //Component min/max
    public Vector2 min(Vector2 v2) {
        return new Vector2(Math.min(this.x, v2.x), Math.min(this.y, v2.y));
    }

    public Vector2 max(Vector2 v2) {
        return new Vector2(Math.max(this.x, v2.x), Math.max(this.y, v2.y));
    }

    //Convert to vector3 and vector4
    public Vector3 toVector3(float z) {
        return new Vector3(this.x, this.y, z);
    }

    public Vector4 toVector4(float z, float w) {
        return new Vector4(this.x, this.y, z, w);
    }


}
