package org.fungover.breeze.geometric_shapes;

import java.awt.geom.Point2D;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a rectangle shape defined by its top-left corner, width, and height.
 */
public class RectangleShape implements Shape {

    private final Point2D topLeft;  // The top-left corner of the rectangle
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
    public RectangleShape(Point2D topLeft, double width, double height) {
        if (topLeft == null) {
            throw new IllegalArgumentException("Top-left point cannot be null.");
        }

        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height must be positive, non-zero values.");
        }

        this.topLeft = new Point2D.Double(); // Defensive copy
        this.width = width;
        this.height = height;
        this.containmentStrategy = new RectangleContainmentStrategy(this);
    }

    /**
     * Returns the top-left corner of the rectangle.
     *
     * @return the top-left corner of the rectangle
     */
    public Point2D.Double getTopLeft() {
        return new Point2D.Double(); // Defensive copy
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
    public boolean contains(Point2D p) {
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
    @Override
    public boolean intersects(Shape other) {
        if (other instanceof RectangleShape) {
            RectangleShape r = (RectangleShape) other;

            // Calculate the bottom-right corners of both rectangles
            Point2D r1BottomRight = new Point2D.Double(this.topLeft.getX() + this.width, this.topLeft.getY() + this.height);
            Point2D r2BottomRight = new Point2D.Double(r.getTopLeft().getX() + r.getWidth(), r.getTopLeft().getY() + r.getHeight());

            // Check for no horizontal overlap
            boolean noHorizontalOverlap = this.topLeft.getX() >= r2BottomRight.getX() || r.getTopLeft().getX() >= r1BottomRight.getX();
            System.out.println("No horizontal overlap: " + noHorizontalOverlap);

            // Check for no vertical overlap
            boolean noVerticalOverlap = this.topLeft.getY() >= r2BottomRight.getY() || r.getTopLeft().getY() >= r1BottomRight.getY();
            System.out.println("No vertical overlap: " + noVerticalOverlap);

            // If there is no horizontal or vertical overlap, they do not intersect
            boolean result = !(noHorizontalOverlap || noVerticalOverlap);
            System.out.println("Do the rectangles intersect? " + result);

            return result;
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
                new Point2D.Double((double) (topLeft.getX() + width), topLeft.getY()),
                new Point2D.Double(topLeft.getX(), (double) (topLeft.getY() + height)),
                new Point2D.Double((double) (topLeft.getX() + width), (double) (topLeft.getY() + height))
        ).toRectangle();  // Ensure toRectangle() is not recursively calling intersects or other methods
    }

    /**
     * Returns the center point of the rectangle.
     *
     * @return the center point of the rectangle
     */
    @Override
    public Point2D getCenter() {
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
    public Shape rotate(double angle, Point2D center) {
        double radianAngle = Math.toRadians(angle);

        // Rotate the four corners of the rectangle around the center point
        Point2D topLeftRotated = rotatePoint(this.topLeft, angle, center);
        Point2D topRightRotated = rotatePoint(new Point2D.Double(this.topLeft.getX() + width, this.topLeft.getY()), angle, center);
        Point2D bottomLeftRotated = rotatePoint(new Point2D.Double(this.topLeft.getX(), this.topLeft.getY() + height), angle, center);
        Point2D bottomRightRotated = rotatePoint(new Point2D.Double(this.topLeft.getX() + width, this.topLeft.getY() + height), angle, center);

        // Use the BoundingBox class to compute the minimal enclosing rectangle
        BoundingBox newBoundingBox = new BoundingBox(topLeftRotated, topRightRotated, bottomLeftRotated, bottomRightRotated);

        return new RectangleShape(newBoundingBox.getTopLeft(), newBoundingBox.getWidth(), newBoundingBox.getHeight());
    }

    /**
     * Rotates a specific point by a given angle around a center point.
     *
     * @param point the point to rotate
     * @param angle the angle to rotate (in degrees)
     * @param center the center point to rotate around
     * @return the rotated point
     */
    private Point2D rotatePoint(Point2D point, double angle, Point2D center) {
        double radians = Math.toRadians(angle);
        double x = center.getX() + (point.getX() - center.getX()) * Math.cos(radians) - (point.getY() - center.getY()) * Math.sin(radians);
        double y = center.getY() + (point.getX() - center.getX()) * Math.sin(radians) + (point.getY() - center.getY()) * Math.cos(radians);
        return new Point2D.Double(x, y);
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