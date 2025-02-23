package org.fungover.breeze.geometric_shapes;

import java.awt.Point;

public class BoundingBox {
    private final Point topLeft;
    private final int width;
    private final int height;

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

    public RectangleShape toRectangle() {
        return new RectangleShape(topLeft, width, height);
    }

    // Intersection check between bounding boxes
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

    public Point getTopLeft() {
        return new Point(topLeft);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return String.format("BoundingBox [topLeft=%s, width=%d, height=%d]", topLeft, width, height);
    }
}
