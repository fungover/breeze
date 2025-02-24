package org.fungover.breeze.geometric_shapes;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a rectangle shape defined by its top-left corner, width, and height.
 */
public class RectangleShape implements Shape {

    private final Point topLeft;  // The top-left corner of the rectangle
    private final double width;
    private final double height;
    private final RectangleContainmentStrategy containmentStrategy;

    /**
     * Constructs a new rectangle with the given top-left corner, width, and height.
     *
     * @param topLeft the top-left corner of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @throws IllegalArgumentException if width or height is negative
     */
    public RectangleShape(Point topLeft, double width, double height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height must be non-negative");
        }

        this.topLeft = new Point(topLeft); // Defensive copy
        this.width = width;
        this.height = height;
        this.containmentStrategy = new RectangleContainmentStrategy(this);
    }

    /**
     * Returns the top-left corner of the rectangle.
     *
     * @return the top-left corner of the rectangle
     */
    public Point getTopLeft() {
        return new Point(topLeft); // Defensive copy
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return height;
    }

    /**
     * Determines whether a given point is inside the rectangle.
     *
     * @param p the point to check
     * @return true if the point is inside the rectangle; false otherwise
     */
    @Override
    public boolean contains(Point p) {
        // Check if the point is inside the rectangle
        return p.getX() >= topLeft.getX() &&
                p.getX() <= topLeft.getX() + width &&
                p.getY() >= topLeft.getY() &&
                p.getY() <= topLeft.getY() + height;
    }

    /**
     * Determines whether this rectangle intersects with another shape.
     *
     * @param other the shape to check for intersection
     * @return true if the rectangle intersects the other shape; false otherwise
     */
//    @Override
//    public boolean intersects(Shape other) {
//        if (other instanceof RectangleShape) {
//            RectangleShape r = (RectangleShape) other;
//            // Ensure that this check uses the bounding box correctly without recursion
//            BoundingBox thisBoundingBox = (BoundingBox) this.getBoundingBox();
//            BoundingBox otherBoundingBox = (BoundingBox) r.getBoundingBox();
//            return thisBoundingBox.intersects(otherBoundingBox);
//        }
//        return false; // Other shapes need specific implementation
//    }

    @Override
    public boolean intersects(Shape other) {
        if (other instanceof RectangleShape) {
            RectangleShape r = (RectangleShape) other;

            // Check for overlap between the two rectangles
            boolean horizontalOverlap = this.topLeft.getX() < r.getTopLeft().getX() + r.getWidth() &&
                    this.topLeft.getX() + this.width > r.getTopLeft().getX();
            boolean verticalOverlap = this.topLeft.getY() < r.getTopLeft().getY() + r.getHeight() &&
                    this.topLeft.getY() + this.height > r.getTopLeft().getY();

            return horizontalOverlap && verticalOverlap;
        }
        return false; // Return false for non-RectangleShape types
    }


    /**
     * Determines whether this rectangle contains another shape.
     *
     * @param other the shape to check if it's contained within this rectangle
     * @return true if the rectangle contains the other shape; false otherwise
     */
    @Override
    public boolean containsShape(Shape other) {
        return containmentStrategy.containsShape(other);
    }

    /**
     * Returns the area of the rectangle.
     *
     * @return the area of the rectangle
     */
    @Override
    public double getArea() {
        return width * height;
    }

    /**
     * Returns the perimeter of the rectangle.
     *
     * @return the perimeter of the rectangle
     */
    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    /**
     * Returns a bounding box that encloses the rectangle.
     *
     * @return a bounding box that encloses the rectangle
     */
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

    /**
     * Returns the center point of the rectangle.
     *
     * @return the center point of the rectangle
     */
    @Override
    public Point2D.Double getCenter() {
        double centerX = topLeft.getX() + width / 2.0;
        double centerY = topLeft.getY() + height / 2.0;
        return new Point2D.Double(centerX, centerY);
    }

    /**
     * Rotates the rectangle by a given angle around a specific center point.
     *
     * @param angle the angle to rotate (in degrees)
     * @param center the center point to rotate around
     * @return a new rectangle representing the rotated shape
     */
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

    /**
     * Rotates a specific point by a given angle around a center point.
     *
     * @param p the point to rotate
     * @param angle the angle to rotate (in degrees)
     * @param center the center point to rotate around
     * @return the rotated point
     */
    private Point rotatePoint(Point p, double angle, Point center) {
        double radians = Math.toRadians(angle);
        int x = (int) (Math.cos(radians) * (p.getX() - center.getX()) - Math.sin(radians) * (p.getY() - center.getY()) + center.getX());
        int y = (int) (Math.sin(radians) * (p.getX() - center.getX()) + Math.cos(radians) * (p.getY() - center.getY()) + center.getY());
        return new Point(x, y);
    }

    /**
     * Scales the rectangle by a given factor.
     *
     * @param factor the scaling factor
     * @return a new rectangle representing the scaled shape
     */
    @Override
    public Shape scale(double factor) {
        // Scale the rectangle by a given factor (scales width and height)
        return (Shape) new RectangleShape(topLeft, width * factor, height * factor);
    }

    /**
     * Returns a string representation of the rectangle.
     *
     * @return a string representation of the rectangle
     */
    @Override
    public String toString() {
        return String.format(Locale.FRANCE, "Rectangle [topLeft=%s, width=%f, height=%f]", topLeft, width, height);
    }

    /**
     * Determines whether this rectangle is equal to another object.
     *
     * @param obj the object to compare
     * @return true if the object is a rectangle with the same top-left point, width, and height; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RectangleShape rectangle = (RectangleShape) obj;
        return Double.compare(rectangle.width, width) == 0 &&
                Double.compare(rectangle.height, height) == 0 &&
                topLeft.equals(rectangle.topLeft);
    }

    /**
     * Returns a hash code for the rectangle.
     *
     * @return a hash code for the rectangle
     */
    @Override
    public int hashCode() {
        return Objects.hash(topLeft, width, height);
    }
}