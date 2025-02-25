package org.fungover.breeze.geometric_shapes;

import java.awt.*;
import java.awt.geom.Point2D;

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
    Shape rotate(double angle, Point2D center);

    /**
     * Scales the shape by a specified factor.
     *
     * @param factor the scaling factor
     * @return a new shape that represents the scaled shape
     */
    Shape scale(double factor);
}
