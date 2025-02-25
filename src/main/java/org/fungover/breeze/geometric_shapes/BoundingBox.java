package org.fungover.breeze.geometric_shapes;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Locale;

/**
 * Represents a bounding box defined by its top-left corner, width, and height.
 * A bounding box is used to represent the minimal enclosing rectangle for a set of points.
 */
public class BoundingBox {
    private final Point2D topLeft;  // Use Point2D.Double instead of Point2D
    private final double width;
    private final double height;

    /**
     * Constructs a bounding box from an array of points.
     * The bounding box is the smallest rectangle that encloses all of the points.
     *
     * @param points an array of points that the bounding box should enclose
     * @throws IllegalArgumentException if no points are provided or if any point is null
     */
    public BoundingBox(Point2D... points) {
        if (points == null || points.length == 0) {
            throw new IllegalArgumentException("At least one point required");
        }
        for (Point2D p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Points cannot be null");
            }
        }
        double minX = points[0].getX();
        double minY = points[0].getY();
        double maxX = points[0].getX();
        double maxY = points[0].getY();
        for (Point2D p : points) {
            if (p.getX() < minX) minX = p.getX();
            if (p.getY() < minY) minY = p.getY();
            if (p.getX() > maxX) maxX = p.getX();
            if (p.getY() > maxY) maxY = p.getY();
        }
        this.topLeft = new Point2D.Double(minX, minY);  // Use Point2D.Double here
        this.width = Math.abs(maxX - minX);
        this.height = Math.abs(maxY - minY);
    }


    /**
     * Returns the top-left corner of the bounding box.
     *
     * @return the top-left corner of the bounding box as a Point
     */
    public Point2D getTopLeft() {
        return topLeft;
    }

    /**
     * Returns the width of the bounding box.
     *
     * @return the width of the bounding box
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the bounding box.
     *
     * @return the height of the bounding box
     */
    public double getHeight() {
        return height;
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
        if (this.topLeft.getX() + this.width < other.topLeft.getX() ||
                other.topLeft.getX() + other.width < this.topLeft.getX()) {
            return false;
        }
        if (this.topLeft.getY() + this.height < other.topLeft.getY() ||
                other.topLeft.getY() + other.height < this.topLeft.getY()) {
            return false;
        }
        return true;
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
