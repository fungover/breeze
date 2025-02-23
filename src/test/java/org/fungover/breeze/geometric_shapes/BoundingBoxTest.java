package org.fungover.breeze.geometric_shapes;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BoundingBoxTest {

    @Test
    public void testBoundingBoxCalculation() {
        // Given four points
        Point p1 = new Point(2, 3);
        Point p2 = new Point(5, 1);
        Point p3 = new Point(4, 6);
        Point p4 = new Point(1, 4);

        // When creating the BoundingBox
        BoundingBox box = new BoundingBox(p1, p2, p3, p4);

        // Expected bounding box:
        // minX = 1, minY = 1, maxX = 5, maxY = 6, so width = 4, height = 5.
        Point expectedTopLeft = new Point(1, 1);
        assertEquals(expectedTopLeft, box.getTopLeft());
        assertEquals(4, box.getWidth());
        assertEquals(5, box.getHeight());
    }

    @Test
    public void testToRectangle() {
        // Given points forming a perfect square
        Point p1 = new Point(10, 10);
        Point p2 = new Point(20, 10);
        Point p3 = new Point(10, 20);
        Point p4 = new Point(20, 20);

        BoundingBox box = new BoundingBox(p1, p2, p3, p4);
        RectangleShape rectangle = box.toRectangle();

        // The expected rectangle has topLeft (10,10) and dimensions 10x10.
        assertEquals(new Point(10, 10), rectangle.getTopLeft());
        assertEquals(10, rectangle.getWidth(), 0.001);
        assertEquals(10, rectangle.getHeight(), 0.001);
    }
}