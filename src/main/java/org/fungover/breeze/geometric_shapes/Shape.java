package org.fungover.breeze.geometric_shapes;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Represents a generic geometric shape. This interface provides methods for
 * checking containment, intersection, transformation, and other geometric operations
 * on shapes.
 */
public interface Shape {

    /**
     * Checks if a point is contained within the shape.
     *
     * @param p the point to check
     * @return true if the point is inside the shape; false otherwise
     */
    boolean contains(Point p);

    /**
     * Checks if this shape intersects with another shape.
     *
     * @param other the other shape to check for intersection
     * @return true if this shape intersects with the other shape; false otherwise
     */
    boolean intersects(Shape other);

    /**
     * Checks if this shape contains another shape.
     *
     * @param other the other shape to check
     * @return true if this shape contains the other shape; false otherwise
     */
    boolean containsShape(Shape other);

    /**
     * Returns the area of the shape.
     *
     * @return the area of the shape
     */
    double getArea();

    /**
     * Returns the perimeter of the shape.
     *
     * @return the perimeter of the shape
     */
    double getPerimeter();

    /**
     * Returns the smallest bounding box that encloses the shape.
     *
     * @return the bounding box of the shape
     */
    Shape getBoundingBox();

    /**
     * Returns the center point of the shape.
     *
     * @return the center point of the shape
     */
    Point2D.Double getCenter();

    /**
     * Rotates the shape around a given center by a specified angle.
     *
     * @param angle the angle to rotate the shape (in degrees)
     * @param center the center point to rotate around
     * @return a new shape that represents the rotated shape
     */
    Shape rotate(double angle, Point center);

    /**
     * Scales the shape by a specified factor.
     *
     * @param factor the scaling factor
     * @return a new shape that represents the scaled shape
     */
    Shape scale(double factor);

    // Basic geometric properties

    /**
     * Interface for basic geometric properties of shapes, including area, perimeter, and center.
     */
    public interface BasicShapeGeometrics {
        /**
         * Returns the area of the shape.
         *
         * @return the area of the shape
         */
        double getArea();

        /**
         * Returns the perimeter of the shape.
         *
         * @return the perimeter of the shape
         */
        double getPerimeter();

        /**
         * Returns the center point of the shape.
         *
         * @return the center point of the shape
         */
        Point2D.Double getCenter();
    }

    // Overlap and containment checks

    /**
     * Interface for checking shape containment and intersection operations.
     */
    public interface ShapeOverlayable {
        /**
         * Checks if a point is inside the shape.
         *
         * @param p the point to check
         * @return true if the point is inside the shape; false otherwise
         */
        boolean contains(Point p);

        /**
         * Checks if this shape contains another shape.
         *
         * @param other the shape to check for containment
         * @return true if this shape contains the other shape; false otherwise
         */
        boolean containsShape(Shape other);

        /**
         * Checks if two shapes intersect.
         *
         * @param other the other shape to check for intersection
         * @return true if the shapes intersect; false otherwise
         */
        boolean intersects(Shape other);
    }

    // Transformation operations

    /**
     * Interface for performing geometric transformations (e.g., rotation and scaling).
     */
    public interface ShapeTransformable {
        /**
         * Returns the smallest bounding box that encloses the shape.
         *
         * @return the bounding box of the shape
         */
        BoundingBox getBoundingBox();

        /**
         * Rotates the shape around a given center by a specified angle.
         *
         * @param angle the angle to rotate the shape (in degrees)
         * @param center the center point to rotate around
         * @return a new shape that represents the rotated shape
         */
        Shape rotate(double angle, Point center);

        /**
         * Scales the shape by a specified factor.
         *
         * @param factor the scaling factor
         * @return a new shape that represents the scaled shape
         */
        Shape scale(double factor);
    }
}
