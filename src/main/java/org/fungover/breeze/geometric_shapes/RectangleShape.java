package org.fungover.breeze.geometric_shapes;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Locale;
import java.util.Objects;

public class RectangleShape implements Shape {

    private final Point topLeft;  // The top-left corner of the rectangle
    private final double width;
    private final double height;
    private final RectangleContainmentStrategy containmentStrategy;

    // Constructor for Rectangle
    public RectangleShape(Point topLeft, double width, double height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height must be non-negative");
        }

        this.topLeft = new Point(topLeft); // Defensive copy
        this.width = width;
        this.height = height;
        this.containmentStrategy = new RectangleContainmentStrategy(this);
    }

    // Getter for top-left corner
    public Point getTopLeft() {
        return new Point(topLeft); // Defensive copy
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
        if (other instanceof RectangleShape) {
            RectangleShape r = (RectangleShape) other;
            // Ensure that this check uses the bounding box correctly without recursion
            BoundingBox thisBoundingBox = (BoundingBox) this.getBoundingBox();
            BoundingBox otherBoundingBox = (BoundingBox) r.getBoundingBox();
            return thisBoundingBox.intersects(otherBoundingBox);
        }
        return false; // Other shapes need specific implementation
    }

    @Override
    public boolean containsShape(Shape other) {
        return containmentStrategy.containsShape(other);
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
    public Shape getBoundingBox() {
        // Directly return a new BoundingBox object without invoking other methods that could cause recursion
        return new BoundingBox(
                topLeft,
                new Point((int) (topLeft.getX() + width), topLeft.y),
                new Point(topLeft.x, (int) (topLeft.getY() + height)),
                new Point((int) (topLeft.getX() + width), (int) (topLeft.getY() + height))
        ).toRectangle();  // Ensure toRectangle() is not recursively calling intersects or other methods
    }


    @Override
    public Point2D.Double getCenter() {
        double centerX = topLeft.getX() + width / 2.0;
        double centerY = topLeft.getY() + height / 2.0;
        return new Point2D.Double(centerX, centerY);
    }

    @Override
    public Shape rotate(double angle, Point center) {
        Point topLeftRotated = rotatePoint(topLeft, angle, center);
        Point topRightRotated = rotatePoint(new Point((int) (topLeft.getX() + width), topLeft.y), angle, center);
        Point bottomLeftRotated = rotatePoint(new Point(topLeft.x, (int) (topLeft.getY() + height)), angle, center);
        Point bottomRightRotated = rotatePoint(new Point((int) (topLeft.getX() + width), (int) (topLeft.getY() + height)), angle, center);

        // Use the BoundingBox class to compute the minimal enclosing rectangle
        BoundingBox newBoundingBox = new BoundingBox(topLeftRotated, topRightRotated, bottomLeftRotated, bottomRightRotated);
        return (Shape) new RectangleShape(newBoundingBox.getTopLeft(), newBoundingBox.getWidth(), newBoundingBox.getHeight());
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
        return (Shape) new RectangleShape(topLeft, width * factor, height * factor);
    }

    @Override
    public String toString() {
        return String.format(Locale.FRANCE, "Rectangle [topLeft=%s, width=%f, height=%f]", topLeft, width, height);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RectangleShape rectangle = (RectangleShape) obj;
        return Double.compare(rectangle.width, width) == 0 &&
                Double.compare(rectangle.height, height) == 0 &&
                topLeft.equals(rectangle.topLeft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, width, height);
    }
}