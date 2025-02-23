package org.fungover.breeze.geometric_shapes;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Locale;
import java.util.Objects;

public class Circle implements Shape {

    private final Point2D center;
    private final double radius;

    // Constructor
    public Circle(Point2D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Returns the center point of the circle.
     *
     * @return the center point as a Point object
     */
    @Override
    public Point2D.Double getCenter() {
        return new Point2D.Double((int)center.getX(), (int)center.getY());
    }


    // Getter for radius
    public double getRadius() {
        return radius;
    }

    /**
     * @param p
     * @return
     */
    @Override
    public boolean contains(Point p) {
        // Check if the distance from the point to the center is less than the radius
        return center.distance(p) <= radius;
    }

    @Override
    public boolean intersects(Shape other) {
        if (other instanceof Circle) {
            Circle otherCircle = (Circle) other;
            double distanceBetweenCenters = center.distance(otherCircle.getCenter());
            return distanceBetweenCenters <= (this.radius + otherCircle.getRadius());
        }
        // Implement logic for other shapes if needed
        return false;
    }

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

    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public Shape getBoundingBox() {
        double x = center.getX() - radius;
        double y = center.getY() - radius;
        double width = 2 * radius;
        double height = 2 * radius;

        // Return a new Rectangle instance using the custom Rectangle class
        return (Shape) new RectangleShape(new Point((int) x, (int) y), width, height);
    }

    @Override
    public Shape rotate(double angle, Point center) {
        // Rotation does not change the shape of a circle, but if needed, adjust its center
        // by rotating the center point around the provided point
        double radianAngle = Math.toRadians(angle);
        double newX = center.getX() + (this.center.getX() - center.getX()) * Math.cos(radianAngle) - (this.center.getY() - center.getY()) * Math.sin(radianAngle);
        double newY = center.getY() + (this.center.getX() - center.getX()) * Math.sin(radianAngle) + (this.center.getY() - center.getY()) * Math.cos(radianAngle);
        return (Shape) new Circle(new Point2D.Double(newX, newY), radius);
    }

    @Override
    public Shape scale(double factor) {
        // Scaling the circle simply multiplies the radius by the factor
        return (Shape) new Circle(center, radius * factor);
    }

    @Override
    public String toString() {
        return String.format(Locale.FRANCE, "Circle [center=%s, radius=%f]", center.toString(), radius);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Circle circle = (Circle) obj;
        return Double.compare(circle.radius, radius) == 0 &&
                center.distance(circle.center) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }
}
