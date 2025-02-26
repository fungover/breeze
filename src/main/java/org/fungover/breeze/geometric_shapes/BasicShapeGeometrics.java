package org.fungover.breeze.geometric_shapes;

import java.awt.geom.Point2D;

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
    Point2D getCenter();
}
