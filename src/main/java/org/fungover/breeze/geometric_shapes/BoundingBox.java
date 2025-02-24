package org.fungover.breeze.geometric_shapes;

import java.awt.Point;

/**
 * Represents a bounding box defined by its top-left corner, width, and height.
 * A bounding box is used to represent the minimal enclosing rectangle for a set of points.
 */
public class BoundingBox {
    private final Point topLeft;
    private final int width;
    private final int height;

    /**
     * Constructs a bounding box from an array of points.
     * The bounding box is the smallest rectangle that encloses all of the points.
     *
     * @param points an array of points that the bounding box should enclose
     * @throws IllegalArgumentException if no points are provided or if any point is null
     */
    public BoundingBox(Point... points) {
        if (points == null || points.length == 0) {
            throw new IllegalArgumentException("At least one point required");
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Points cannot be null");
                }
            }

        int minX = points[0].x;
        int minY = points[0].y;
        int maxX = points[0].x;
        int maxY = points[0].y;
        for (Point p : points) {
            if (p.x < minX) minX = p.x;
            if (p.y < minY) minY = p.y;
            if (p.x > maxX) maxX = p.x;
            if (p.y > maxY) maxY = p.y;
        }
        this.topLeft = new Point(minX, minY);
        this.width = Math.abs(maxX - minX);
        this.height = Math.abs(maxY - minY);
    }

    /**
     * Converts the bounding box to a rectangle shape.
     *
     * @return a RectangleShape that represents the bounding box
     */
    public RectangleShape toRectangle() {
        return new RectangleShape(topLeft, width, height);
    }

    /**
     * Checks if this bounding box intersects with another bounding box.
     *
     * @param other the other bounding box to check for intersection
     * @return true if the bounding boxes intersect; false otherwise
     */
    public boolean intersects(BoundingBox other) {
        if (this.topLeft.x + this.width < other.topLeft.x ||
                other.topLeft.x + other.width < this.topLeft.x) {
            return false;
        }
        if (this.topLeft.y + this.height < other.topLeft.y ||
                other.topLeft.y + other.height < this.topLeft.y) {
            return false;
        }
        return true;
    }

    /**
     * Returns the top-left corner of the bounding box.
     *
     * @return the top-left corner of the bounding box as a Point
     */
    public Point getTopLeft() {
        return new Point(topLeft);
    }

    /**
     * Returns the width of the bounding box.
     *
     * @return the width of the bounding box
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the bounding box.
     *
     * @return the height of the bounding box
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns a string representation of the bounding box.
     *
     * @return a string representation of the bounding box, including its top-left corner, width, and height
     */
    @Override
    public String toString() {
        return String.format("BoundingBox [topLeft=%s, width=%d, height=%d]", topLeft, width, height);
    }
}
