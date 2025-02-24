package org.fungover.breeze.geometric_shapes;

import java.awt.Point;

public class RectangleContainmentStrategy {
    private final RectangleShape rectangle;

    public RectangleContainmentStrategy(RectangleShape rectangle) {
        this.rectangle = rectangle;
    }

    public boolean strictlyContains(Point p) {
        return p.getX() > rectangle.getTopLeft().getX() &&
                p.getX() < rectangle.getTopLeft().getX() + rectangle.getWidth() &&
                p.getY() > rectangle.getTopLeft().getY() &&
                p.getY() < rectangle.getTopLeft().getY() + rectangle.getHeight();
    }

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
