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
    public void add(Vector3 v){
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }

    public void sub(Vector3 v){
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
    }

    public void mul(Vector3 v){
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
    }

    public void div(Vector3 v){
        this.x /= v.x;
        this.y /= v.y;
    }

    //Dot product
    public float dot(Vector3 v){
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    //Cross product
    public Vector3 cross(Vector3 v){
        return new Vector3(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
    }

    //length/magnitude
    public float length(){
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    //Normalization
    public Vector3 normalize(){
        float length = length();
        return new Vector3(this.x / length, this.y / length, this.z / length);
    }




}
