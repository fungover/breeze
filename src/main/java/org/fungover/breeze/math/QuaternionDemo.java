package org.fungover.breeze.math;

public class QuaternionDemo {
    public static void main(String[] args) {
        Vector3 point = new Vector3(1, 0, 0); // A point on the X-axis

        // Create a Quaternion representing a 90-degree rotation around the Z-axis
        float angle = (float) Math.toRadians(90); // Convert to radians
        float sinHalfAngle = (float) Math.sin(angle / 2);
        float cosHalfAngle = (float) Math.cos(angle / 2);
        Quaternion rotation = new Quaternion(0, 0, sinHalfAngle, cosHalfAngle);

        // Rotate the point
        Vector3 rotatedPoint = rotation.rotateVector(point);

        // Print the result
        System.out.println("Original Point: " + point);
        System.out.println("Rotated Point: " + rotatedPoint);
    }
}


