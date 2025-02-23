package org.fungover.breeze.geometric_shapes;

import java.awt.*;
import java.awt.geom.Point2D;

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

    // Basic geometric properties
public interface BasicShapeGeometrics {
    double getArea(); // Calculate area of the shape
    double getPerimeter(); // Calculate perimeter of the shape
    Point2D.Double getCenter(); // Get the center point of the shape
}

    // Overlap and containment checks
public interface ShapeOverlayable {
    boolean contains(Point p); // Check if a point is inside the shape
    boolean containsShape(Shape other); // Check if one shape contains another
    boolean intersects(Shape other); // Check if two shapes intersect
}

    // Transformation operations
public interface ShapeTransformable {
    BoundingBox getBoundingBox(); // Get the smallest rectangle that encloses the shape
    Shape rotate(double angle, Point center); // Rotate the shape around a point
    Shape scale(double factor); // Scale the shape by a factor
    }
}