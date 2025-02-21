package org.fungover.breeze.geometric_shapes;

import java.awt.Point;
import java.awt.geom.Point2D;
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
        if (other instanceof Rectangle) {
            Rectangle r = (Rectangle) other;
            return this.getBoundingBox().intersects(r.getBoundingBox());
        }
        return false; // Other shapes need specific implementation
    }

    @Override
    public boolean containsShape(Shape other) {
        if (other instanceof Rectangle) {
            Rectangle r = (Rectangle) other;
            return this.contains(r.topLeft) &&
                    this.contains(new Point((int) (r.topLeft.getX() + r.width), (int) r.topLeft.getY())) &&
                    this.contains(new Point((int) r.topLeft.getX(), (int) (r.topLeft.getY() + r.height))) &&
                    this.contains(new Point((int) (r.topLeft.getX() + r.width), (int) (r.topLeft.getY() + r.height)));
        }
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
        return new Rectangle(new Point(topLeft.x, topLeft.y), width, height);
    }

    @Override
    public Point2D.Double getCenter() {
        return new Point2D.Double(topLeft.getX() + width / 2, topLeft.getY() + height / 2);
    }

    @Override
    public Shape rotate(double angle, Point center) {
        // Rotate the four corners around the center
        Point topLeftRotated = rotatePoint(topLeft, angle, center);
        Point topRightRotated = rotatePoint(new Point((int) (topLeft.getX() + width), (int) topLeft.getY()), angle, center);
        Point bottomLeftRotated = rotatePoint(new Point((int) topLeft.getX(), (int) (topLeft.getY() + height)), angle, center);
        Point bottomRightRotated = rotatePoint(new Point((int) (topLeft.getX() + width), (int) (topLeft.getY() + height)), angle, center);

        // Determine new bounding box
        int minX = (int) Math.min(Math.min(topLeftRotated.getX(), topRightRotated.getX()), Math.min(bottomLeftRotated.getX(), bottomRightRotated.getX()));
        int minY = (int) Math.min(Math.min(topLeftRotated.getY(), topRightRotated.getY()), Math.min(bottomLeftRotated.getY(), bottomRightRotated.getY()));
        int maxX = (int) Math.max(Math.max(topLeftRotated.getX(), topRightRotated.getX()), Math.max(bottomLeftRotated.getX(), bottomRightRotated.getX()));
        int maxY = (int) Math.max(Math.max(topLeftRotated.getY(), topRightRotated.getY()), Math.max(bottomLeftRotated.getY(), bottomRightRotated.getY()));

        return new Rectangle(new Point(minX, minY), maxX - minX, maxY - minY);
    }

    private Point rotatePoint(Point p, double angle, Point center) {
        double radians = Math.toRadians(angle);
        int x = (int) (Math.cos(radians) * (p.getX() - center.getX()) - Math.sin(radians) * (p.getY() - center.getY()) + center.getX());
        int y = (int) (Math.sin(radians) * (p.getX() - center.getX()) + Math.cos(radians) * (p.getY() - center.getY()) + center.getY());
        return new Point(x, y);
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