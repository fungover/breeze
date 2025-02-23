package org.fungover.breeze.geometric_shapes;

import java.awt.Point;

public class RectangleContainmentStrategy {
    private final RectangleShape rectangle;

    public RectangleContainmentStrategy(RectangleShape rectangle) {
        this.rectangle = rectangle;
    }

    public boolean containsShape(Shape other) {
        if (other instanceof RectangleShape) {
            RectangleShape r = (RectangleShape) other;
            // Check if all four corners of 'r' are within the rectangle
            return rectangle.contains(r.getTopLeft()) &&
                    rectangle.contains(new Point((int) (r.getTopLeft().getX() + r.getWidth()), (int) r.getTopLeft().getY())) &&
                    rectangle.contains(new Point((int) r.getTopLeft().getX(), (int) (r.getTopLeft().getY() + r.getHeight()))) &&
                    rectangle.contains(new Point((int) (r.getTopLeft().getX() + r.getWidth()), (int) (r.getTopLeft().getY() + r.getHeight())));
        }
        // Extend logic for other shape types as needed
        return false;
    }
}
