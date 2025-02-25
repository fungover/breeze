package org.fungover.breeze.geometric_shapes;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BoundingBoxTest {

    @Test
    void testBoundingBoxCalculation() {
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
    void testToRectangle() {
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

    @Test
    public void testConstructorWithValidPoints() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(5, 5);
        BoundingBox bbox = new BoundingBox(p1, p2);

        assertNotNull(bbox);
        assertEquals(new Point(0, 0), bbox.getTopLeft());
        assertEquals(5, bbox.getWidth());
        assertEquals(5, bbox.getHeight());
    }

    @Test
    public void testConstructorWithNullPoints() {
        assertThrows(IllegalArgumentException.class, () -> new BoundingBox((Point[]) null));
    }

    @Test
    public void testConstructorWithEmptyPoints() {
        assertThrows(IllegalArgumentException.class, () -> new BoundingBox());
    }

    @Test
    public void testConstructorWithNullPoint() {
        assertThrows(IllegalArgumentException.class, () -> new BoundingBox(new Point(0, 0), null));
    }

    @Test
    public void testIntersects() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(5, 5);
        BoundingBox bbox1 = new BoundingBox(p1, p2);

        // BoundingBox that intersects bbox1
        Point p3 = new Point(3, 3);
        Point p4 = new Point(8, 8);
        BoundingBox bbox2 = new BoundingBox(p3, p4);

        // BoundingBox that does not intersect bbox1
        Point p5 = new Point(6, 6);
        Point p6 = new Point(10, 10);
        BoundingBox bbox3 = new BoundingBox(p5, p6);

        // Intersecting case
        assertTrue(bbox1.intersects(bbox2));

        // Non-intersecting case
        assertFalse(bbox1.intersects(bbox3));
    }

    @Test
    public void testToString() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(5, 5);
        BoundingBox bbox = new BoundingBox(p1, p2);

        String expectedString = String.format("BoundingBox [topLeft=%s, width=%d, height=%d]", p1, 5, 5);
        assertEquals(expectedString, bbox.toString());
    }
}