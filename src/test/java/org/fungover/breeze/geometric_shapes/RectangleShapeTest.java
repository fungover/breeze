package org.fungover.breeze.geometric_shapes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Locale;

class RectangleShapeTest {

    @Test
    void testContainsPoint() {
        Point topLeft = new Point(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // Test points inside the rectangle
        Point insidePoint = new Point(5, 2);  // Inside the rectangle
        assertTrue(rectangle.contains(insidePoint));

        // Test points outside the rectangle
        Point outsidePoint = new Point(12, 6);  // Outside the rectangle
        assertFalse(rectangle.contains(outsidePoint));
    }

    @Test
    void testGetArea() {
        Point topLeft = new Point(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // Area = width * height = 10 * 5 = 50
        assertEquals(50, rectangle.getArea());
    }

    @Test
    void testGetPerimeter() {
        Point topLeft = new Point(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // Perimeter = 2 * (width + height) = 2 * (10 + 5) = 30
        assertEquals(30, rectangle.getPerimeter());
    }

    @Test
    void testGetCenter() {
        Point topLeft = new Point(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10.0, 5.0);

        // The center should be at (5, 2.5) for a 10x5 rectangle
        Point2D expectedCenter = new Point2D.Double(5.0, 2.5);  // Integer center for simplicity
        assertEquals(expectedCenter, rectangle.getCenter());
    }

    @Test
    void testGetBoundingBox() {
        Point topLeft = new Point(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // The bounding box for a rectangle is the rectangle itself
        assertEquals(rectangle, rectangle.getBoundingBox());
    }

    @Test
    void testScale() {
        Point topLeft = new Point(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // Scaling by a factor of 2 should double the width and height
        RectangleShape scaledRectangle = (RectangleShape) rectangle.scale(2);
        assertEquals(20, scaledRectangle.getWidth(), 0.0001);
        assertEquals(10, scaledRectangle.getHeight(), 0.0001);
    }

    @Test
    public void testConstructorWithValidValues() {
        Point topLeft = new Point(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 5.0, 10.0);

        assertEquals(5.0, rectangle.getWidth());
        assertEquals(10.0, rectangle.getHeight());
        assertEquals(topLeft, rectangle.getTopLeft());
    }

    @Test
    public void testConstructorWithInvalidWidth() {
        Point topLeft = new Point(0, 0);
        assertThrows(IllegalArgumentException.class, () -> new RectangleShape(topLeft, -5.0, 10.0));
    }

    @Test
    public void testConstructorWithInvalidHeight() {
        Point topLeft = new Point(0, 0);
        assertThrows(IllegalArgumentException.class, () -> new RectangleShape(topLeft, 5.0, -10.0));
    }

    @Test
    public void testContains() {
        Point topLeft = new Point(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 5.0, 10.0);

        Point insidePoint = new Point(2, 3);
        Point outsidePoint = new Point(6, 11);

        assertTrue(rectangle.contains(insidePoint)); // Point inside rectangle
        assertFalse(rectangle.contains(outsidePoint)); // Point outside rectangle
    }

    @Test
    public void testContainsShape() {
        RectangleShape bigRect = new RectangleShape(new Point(0, 0), 10.0, 10.0);
        RectangleShape smallRect = new RectangleShape(new Point(2, 2), 5.0, 5.0);

        assertTrue(bigRect.containsShape(smallRect)); // Big rectangle should contain small rectangle

        RectangleShape outsideRect = new RectangleShape(new Point(6, 6), 5.0, 5.0);
        assertFalse(bigRect.containsShape(outsideRect)); // Big rectangle should not contain outside rectangle
    }

    @Test
    public void testToString() {
        Point topLeft = new Point(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 5.0, 10.0);

        String expectedString = String.format(Locale.FRANCE, "Rectangle [topLeft=%s, width=%f, height=%f]", topLeft, 5.0, 10.0);
        assertEquals(expectedString, rectangle.toString());
    }
}