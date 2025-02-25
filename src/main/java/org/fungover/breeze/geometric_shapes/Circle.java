package org.fungover.breeze.geometric_shapes;

import java.awt.geom.Point2D;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a circle shape defined by its centre and radius.
 */
public class Circle implements Shape {

    private final Point2D center;
    private final double radius;

    /**
     * Constructs a circle with the given center and radius.
     *
     * @param center the center point of the circle
     * @param radius the radius of the circle
     */
    public Circle(Point2D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Returns the center point of the circle.
     *
     * @return the center point of the circle as a Point2D.Double
     */
    @Override
    public Point2D.Double getCenter() {
        return new Point2D.Double((int)center.getX(), (int)center.getY());
    }


    /**
     * Returns the radius of the circle.
     *
     * @return the radius of the circle
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Determines if the given point is inside the circle.
     *
     * @param p the point to check
     * @return true if the point is inside the circle; false otherwise
     */
    @Override
    public boolean contains(Point2D p) {
        // Check if the distance from the point to the center is less than the radius
        return center.distance(p) <= radius;
    }

    /**
     * Determines if this circle intersects with another shape.
     *
     * @param other the other shape to check for intersection
     * @return true if this circle intersects with the other shape; false otherwise
     */
    @Override
    public boolean intersects(Shape other) {
        if (other instanceof Circle) {
            Circle otherCircle = (Circle) other;

            // Calculate the distance between the centers of the two circles
            double distanceBetweenCenters = center.distance(otherCircle.getCenter());

            // Circles intersect if the distance between their centers is less than or equal to the sum of their radii
            return distanceBetweenCenters < (this.radius + otherCircle.getRadius());
        }
        // Implement logic for other shapes if needed
        return false;
    }

    /**
     * Determines if this circle contains another shape.
     *
     * @param other the shape to check if it's contained within this circle
     * @return true if the circle contains the other shape; false otherwise
     */
    @Override
    public boolean containsShape(Shape other) {
        if (other instanceof Circle) {
            Circle otherCircle = (Circle) other;
            // A circle contains another circle if the distance between centers
            // is less than or equal to the difference of their radii
            double distanceBetweenCenters = center.distance(otherCircle.getCenter());
            return distanceBetweenCenters + otherCircle.getRadius() <= radius;
        }
        // Implement logic for other shapes if needed
        return false;
    }

    /**
     * Returns the area of the circle.
     *
     * @return the area of the circle
     */
    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    /**
     * Returns the perimeter (circumference) of the circle.
     *
     * @return the perimeter (circumference) of the circle
     */
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    /**
     * Returns the bounding box of the circle, which is a square.
     *
     * @return a bounding box for the circle as a RectangleShape
     */
    @Override
    public Shape getBoundingBox() {
        double x = center.getX() - radius;
        double y = center.getY() - radius;
        double width = 2 * radius;
        double height = 2 * radius;

        // Return a new Rectangle instance using the custom Rectangle class
//        return (Shape) new RectangleShape(new Point2D.Double((int) x, (int) y), width, height)
        return new RectangleShape(new Point2D.Double(x, y), width, height);
    }

    /**
     * Rotates the circle around a given center point by the specified angle.
     *
     * @param angle the angle to rotate the circle (in degrees)
     * @param center the center point to rotate around
     * @return a new rotated circle
     */
    @Override
    public Shape rotate(double angle, Point2D center) {
        // Rotation does not change the shape of a circle, but if needed, adjust its center
        // by rotating the center point around the provided point
        double radianAngle = Math.toRadians(angle);
        double newX = center.getX() + (this.center.getX() - center.getX()) * Math.cos(radianAngle) - (this.center.getY() - center.getY()) * Math.sin(radianAngle);
        double newY = center.getY() + (this.center.getX() - center.getX()) * Math.sin(radianAngle) + (this.center.getY() - center.getY()) * Math.cos(radianAngle);
//        return (Shape) new Circle(new Point2D.Double(newX, newY), radius);
        return new Circle(new Point2D.Double(newX, newY), radius);
    }

    /**
     * Scales the radius of the circle by a given factor.
     *
     * @param factor the scaling factor
     * @return a new scaled circle
     */
    @Override
    public Shape scale(double factor) {
        // Scaling the circle simply multiplies the radius by the factor
        return new Circle(center, radius * factor);
    }

    /**
     * Returns a string representation of the circle.
     *
     * @return a string representation of the circle
     */
    @Override
    public String toString() {
        return String.format(Locale.FRANCE, "Circle [center=%s, radius=%f]", center.toString(), radius);
    }

    /**
     * Determines whether this circle is equal to another object.
     *
     * @param obj the object to compare
     * @return true if the object is a circle with the same center and radius; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Circle circle = (Circle) obj;
        return Double.compare(circle.radius, radius) == 0 &&
                center.distance(circle.center) < 0.0001;
    }

    /**
     * Returns a hash code for the circle.
     *
     * @return a hash code for the circle
     */
    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }
}
