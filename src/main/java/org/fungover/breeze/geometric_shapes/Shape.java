package org.fungover.breeze.geometric_shapes;

import java.awt.*;

public interface Shape {
    boolean contains(Point p); // Check if a point is inside the shape
    boolean intersects(Shape other); // Check if two shapes intersect
    boolean containsShape(Shape other); // Check if one shape contains another
    double getArea(); // Calculate area of the shape
    double getPerimeter(); // Calculate perimeter of the shape
    Rectangle getBoundingBox(); // Get the smallest rectangle that encloses the shape
    Point getCenter(); // Get the center point of the shape
    Shape rotate(double angle, Point center); // Rotate the shape around a point
    Shape scale(double factor); // Scale the shape by a factor
}

