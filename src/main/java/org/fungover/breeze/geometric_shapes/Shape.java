package org.fungover.breeze.geometric_shapes;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Represents a generic geometric shape. This interface provides methods for
 * checking containment, intersection, transformation, and other geometric operations
 * on shapes.
 */
public interface Shape {

    boolean contains(Point p);

    boolean intersects(Shape other);

    boolean containsShape(Shape other);

    double getArea();

    double getPerimeter();

    Shape getBoundingBox();

    Point2D.Double getCenter();

    Shape rotate(double angle, Point center);

    Shape scale(double factor);
}
