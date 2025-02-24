package org.fungover.breeze.geometric_shapes;

import java.awt.Point;

/**
 * This class provides logic for determining if one rectangle contains another.
 * It provides methods to check if a point is strictly within the rectangle and if one rectangle contains another rectangle.
 */
public class RectangleContainmentStrategy {
    private final RectangleShape rectangle;

    /**
     * Constructs a containment strategy for the specified rectangle.
     *
     * @param rectangle the rectangle for which containment logic is defined
     */
    public RectangleContainmentStrategy(RectangleShape rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Checks if a point is strictly inside the rectangle (excluding the boundary).
     *
     * @param p the point to check
     * @return true if the point is strictly inside the rectangle; false if the point is on the boundary or outside
     */
    public boolean strictlyContains(Point p) {
        return p.getX() > rectangle.getTopLeft().getX() &&
                p.getX() < rectangle.getTopLeft().getX() + rectangle.getWidth() &&
                p.getY() > rectangle.getTopLeft().getY() &&
                p.getY() < rectangle.getTopLeft().getY() + rectangle.getHeight();
    }

    /**
     * Checks if the given shape is entirely contained within the rectangle.
     * Specifically, this method checks if all four corners of the other rectangle are strictly inside this rectangle.
     * If the rectangles are exactly the same, it returns true.
     *
     * @param other the shape to check for containment
     * @return true if the rectangle contains the other shape; false otherwise
     */
    public boolean containsShape(Shape other) {
        if (other instanceof RectangleShape) {
            RectangleShape r = (RectangleShape) other;

            // Handle the case where the rectangles are exactly the same
            if (r.getTopLeft().equals(rectangle.getTopLeft()) &&
                    Math.abs(r.getWidth() - rectangle.getWidth()) < 0.0001 &&
                    Math.abs(r.getHeight() - rectangle.getHeight()) < 0.0001) {
                return true; // The rectangles are exactly the same, so they contain each other
            }

            // Check if all four corners of 'r' are strictly within the rectangle (excluding boundaries)
            return rectangle.contains(r.getTopLeft()) &&
                    rectangle.contains(new Point((int) (r.getTopLeft().getX() + r.getWidth()), (int) r.getTopLeft().getY())) &&
                    rectangle.contains(new Point((int) r.getTopLeft().getX(), (int) (r.getTopLeft().getY() + r.getHeight()))) &&
                    rectangle.contains(new Point((int) (r.getTopLeft().getX() + r.getWidth()), (int) (r.getTopLeft().getY() + r.getHeight())));
        }
        return false;
    }
}
