package org.fungover.breeze.geometric_shapes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Point;
import java.awt.geom.Point2D;

public class RectangleTest {

    @Test
    public void testContainsPoint() {
        Point topLeft = new Point(0, 0);
        Rectangle rectangle = new Rectangle(topLeft, 10, 5);

        // Test points inside the rectangle
        Point insidePoint = new Point(5, 2);  // Inside the rectangle
        assertTrue(rectangle.contains(insidePoint));

        // Test points outside the rectangle
        Point outsidePoint = new Point(12, 6);  // Outside the rectangle
        assertFalse(rectangle.contains(outsidePoint));
    }

    @Test
    public void testGetArea() {
        Point topLeft = new Point(0, 0);
        Rectangle rectangle = new Rectangle(topLeft, 10, 5);

        // Area = width * height = 10 * 5 = 50
        assertEquals(50, rectangle.getArea());
    }

    @Test
    public void testGetPerimeter() {
        Point topLeft = new Point(0, 0);
        Rectangle rectangle = new Rectangle(topLeft, 10, 5);

        // Perimeter = 2 * (width + height) = 2 * (10 + 5) = 30
        assertEquals(30, rectangle.getPerimeter());
    }

    @Test
    public void testGetCenter() {
        Point topLeft = new Point(0, 0);
        Rectangle rectangle = new Rectangle(topLeft, 10.0, 5.0);

        // The center should be at (5, 2.5) for a 10x5 rectangle
        Point2D expectedCenter = new Point2D.Double(5.0, 2.5);  // Integer center for simplicity
        assertEquals(expectedCenter, rectangle.getCenter());
    }

    @Test
    public void testGetBoundingBox() {
        Point topLeft = new Point(0, 0);
        Rectangle rectangle = new Rectangle(topLeft, 10, 5);

        // The bounding box for a rectangle is the rectangle itself
        assertEquals(rectangle, rectangle.getBoundingBox());
    }

    @Test
    public void testScale() {
        Point topLeft = new Point(0, 0);
        Rectangle rectangle = new Rectangle(topLeft, 10, 5);

        // Scaling by a factor of 2 should double the width and height
        Rectangle scaledRectangle = (Rectangle) rectangle.scale(2);
        assertEquals(20, scaledRectangle.getWidth(), 0.0001);
        assertEquals(10, scaledRectangle.getHeight(), 0.0001);
    }
}
