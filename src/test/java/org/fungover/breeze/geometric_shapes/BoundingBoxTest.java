package org.fungover.breeze.geometric_shapes;

import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import static org.junit.jupiter.api.Assertions.*;

class BoundingBoxTest {

    @Test
    void testBoundingBoxCalculation() {
        // Given four points
        Point2D.Double p1 = new Point2D.Double(2, 3);
        Point2D.Double p2 = new Point2D.Double(5, 1);
        Point2D.Double p3 = new Point2D.Double(4, 6);
        Point2D.Double p4 = new Point2D.Double(1, 4);

        // When creating the BoundingBox
        BoundingBox box = new BoundingBox(p1, p2, p3, p4);

        // Expected bounding box:
        // minX = 1, minY = 1, maxX = 5, maxY = 6, so width = 4, height = 5.
        Point2D.Double expectedTopLeft = new Point2D.Double(1, 1);
        assertEquals(expectedTopLeft, box.getTopLeft());
        assertEquals(4, box.getWidth());
        assertEquals(5, box.getHeight());
    }

    @Test
    void testToRectangle() {
        // Given points forming a perfect square
        Point2D.Double p1 = new Point2D.Double(10, 10);
        Point2D.Double p2 = new Point2D.Double(20, 10);
        Point2D.Double p3 = new Point2D.Double(10, 20);
        Point2D.Double p4 = new Point2D.Double(20, 20);

        BoundingBox box = new BoundingBox(p1, p2, p3, p4);
        RectangleShape rectangle = box.toRectangle();

        // The expected rectangle has topLeft (10,10) and dimensions 10x10.
        assertEquals(new Point2D.Double(0, 0), rectangle.getTopLeft());
        assertEquals(10, rectangle.getWidth(), 0.001);
        assertEquals(10, rectangle.getHeight(), 0.001);
    }

    @Test
    public void testConstructorWithValidPoints() {
        Point2D.Double p1 = new Point2D.Double(0, 0);
        Point2D.Double p2 = new Point2D.Double(5, 5);
        BoundingBox bbox = new BoundingBox(p1, p2);

        assertNotNull(bbox);
        assertEquals(new Point2D.Double(0, 0), bbox.getTopLeft());
        assertEquals(5, bbox.getWidth());
        assertEquals(5, bbox.getHeight());
    }

    @Test
    public void testConstructorWithNullPoints() {
        assertThrows(IllegalArgumentException.class, () -> new BoundingBox((Point2D.Double[]) null));
    }

    @Test
    public void testConstructorWithEmptyPoints() {
        assertThrows(IllegalArgumentException.class, BoundingBox::new);
    }

    @Test
    public void testConstructorWithNullPoint() {
        assertThrows(IllegalArgumentException.class, () -> new BoundingBox(new Point2D.Double(0, 0), null));
    }

    @Test
    public void testIntersects() {
        Point2D.Double p1 = new Point2D.Double(0, 0);
        Point2D.Double p2 = new Point2D.Double(5, 5);
        BoundingBox bbox1 = new BoundingBox(p1, p2);

        // BoundingBox that intersects bbox1
        Point2D.Double p3 = new Point2D.Double(3, 3);
        Point2D.Double p4 = new Point2D.Double(8, 8);
        BoundingBox bbox2 = new BoundingBox(p3, p4);

        // BoundingBox that does not intersect bbox1
        Point2D.Double p5 = new Point2D.Double(6, 6);
        Point2D.Double p6 = new Point2D.Double(10, 10);
        BoundingBox bbox3 = new BoundingBox(p5, p6);

        // Intersecting case
        assertTrue(bbox1.intersects(bbox2));

        // Non-intersecting case
        assertFalse(bbox1.intersects(bbox3));
    }
}