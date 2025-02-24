package org.fungover.breeze.geometric_shapes;

import java.awt.Point;

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
