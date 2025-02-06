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

    public void add(Vector3 other){
        this.x += other.x;
        this.y += other.y;
    }

    public void sub(Vector3 other){
        this.x -= other.x;
        this.y -= other.y;
    }

    public void mul(Vector3 other){
        this.x *= other.x;
        this.y *= other.y;
    }

    public void div(Vector3 other){
        this.x /= other.x;
        this.y /= other.y;
    }

    public float dot(Vector3 other){
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }
}
