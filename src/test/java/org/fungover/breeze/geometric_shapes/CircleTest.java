package org.fungover.breeze.geometric_shapes;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Point;
import java.awt.geom.Point2D;

class CircleTest {

    @Test
    void testContainsPoint() {
        Point2D center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // Test points inside the circle
        Point insidePoint = new Point(3, 4); // distance from center = 5 (inside)
        assertTrue(circle.contains(insidePoint));

        // Test points outside the circle
        Point outsidePoint = new Point(6, 6); // distance from center > 5
        assertFalse(circle.contains(outsidePoint));
    }

    @Test
    void testGetArea() {
        Point2D center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // Area = π * radius^2 = 3.14159 * 25 ≈ 78.5398
        assertEquals(Math.PI * 25, circle.getArea(), 0.0001);
    }

    @Test
    void testGetPerimeter() {
        Point2D center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // Perimeter = 2 * π * radius = 2 * 3.14159 * 5 ≈ 31.4159
        assertEquals(2 * Math.PI * 5, circle.getPerimeter(), 0.0001);
    }

    @Test
    void testGetBoundingBox() {
        Point2D center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // The bounding box should have width and height equal to the diameter of the circle
        assertEquals(new RectangleShape(new Point(-5, -5), 10, 10), circle.getBoundingBox());
    }

    @Test
    void testRotate() {
        Point2D center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);
        Point rotationPoint = new Point(0, 0);

        // Rotating a circle around its center should not change its position
        Circle rotatedCircle = (Circle) circle.rotate(90, rotationPoint);
        assertEquals(circle, rotatedCircle);
    }

    @Test
    void testScale() {
        Point2D center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // Scaling the circle by a factor of 2 should double its radius
        Circle scaledCircle = (Circle) circle.scale(2);
        assertEquals(10, scaledCircle.getRadius(), 0.0001);
    }
}
