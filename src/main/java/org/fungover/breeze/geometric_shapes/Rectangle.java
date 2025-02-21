package org.fungover.breeze.geometric_shapes;

import java.awt.Point;
import java.util.Objects;

public class Rectangle implements Shape {

    private final Point topLeft;  // The top-left corner of the rectangle
    private final double width;
    private final double height;

    // Constructor for Rectangle
    public Rectangle(Point topLeft, double width, double height) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    // Getter for top-left corner
    public Point getTopLeft() {
        return topLeft;
    }

    // Getter for width
    public double getWidth() {
        return width;
    }

    // Getter for height
    public double getHeight() {
        return height;
    }

    @Override
    public boolean contains(Point p) {
        // Check if the point is inside the rectangle
        return p.getX() >= topLeft.getX() &&
                p.getX() <= topLeft.getX() + width &&
                p.getY() >= topLeft.getY() &&
                p.getY() <= topLeft.getY() + height;
    }

    @Override
    public boolean intersects(Shape other) {
        // Implement intersection logic (can be extended to other shapes)
        return false;
    }

    @Override
    public boolean containsShape(Shape other) {
        // Implement logic for checking if one shape is contained within the rectangle
        return false;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public Rectangle getBoundingBox() {
        return this;  // A rectangle's bounding box is the rectangle itself
    }

    @Override
    public Point getCenter() {
        // Return the center of the rectangle
        double centerX = topLeft.getX() + width / 2;
        double centerY = topLeft.getY() + height / 2;
        return new Point((int) centerX, (int) centerY);
    }

    @Override
    public Shape rotate(double angle, Point center) {
        // Implement rotation logic (if needed)
        return this;  // For simplicity, returning the same rectangle here
    }

    @Override
    public Shape scale(double factor) {
        // Scale the rectangle by a given factor (scales width and height)
        return new Rectangle(topLeft, width * factor, height * factor);
    }

    @Override
    public String toString() {
        return String.format("Rectangle [topLeft=%s, width=%f, height=%f]", topLeft, width, height);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rectangle rectangle = (Rectangle) obj;
        return Double.compare(rectangle.width, width) == 0 &&
                Double.compare(rectangle.height, height) == 0 &&
                topLeft.equals(rectangle.topLeft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, width, height);
    }
}