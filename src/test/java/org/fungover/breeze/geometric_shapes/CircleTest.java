package org.fungover.breeze.geometric_shapes;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.geom.Point2D;

class CircleTest {

    @Test
    void testContainsPoint() {
        Point2D.Double center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // Test points inside the circle
        Point2D.Double insidePoint = new Point2D.Double(3, 4); // distance from center = 5 (inside)
        assertTrue(circle.contains(insidePoint));

        // Test points outside the circle
        Point2D.Double outsidePoint = new Point2D.Double(6, 6); // distance from center > 5
        assertFalse(circle.contains(outsidePoint));
    }

    @Test
    void testGetArea() {
        Point2D.Double center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // Area = π * radius^2 = 3.14159 * 25 ≈ 78.5398
        assertEquals(Math.PI * 25, circle.getArea(), 0.0001);
    }

    @Test
    void testGetPerimeter() {
        Point2D.Double center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // Perimeter = 2 * π * radius = 2 * 3.14159 * 5 ≈ 31.4159
        assertEquals(2 * Math.PI * 5, circle.getPerimeter(), 0.0001);
    }

    @Test
    void testGetBoundingBox() {
        Point2D.Double center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // The bounding box should have width and height equal to the diameter of the circle
        assertEquals(new RectangleShape(new Point2D.Double(-5, -5), 10, 10), circle.getBoundingBox());
    }

    @Test
    void testRotate() {
        Point2D.Double center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);
        Point2D.Double rotationPoint = new Point2D.Double(0, 0);

        // Rotating a circle around its center should not change its position
        Circle rotatedCircle = (Circle) circle.rotate(90, rotationPoint);
        assertEquals(circle, rotatedCircle);
    }

    @Test
    void testScale() {
        Point2D.Double center = new Point2D.Double(0, 0);
        Circle circle = new Circle(center, 5);

        // Scaling the circle by a factor of 2 should double its radius
        Circle scaledCircle = (Circle) circle.scale(2);
        assertEquals(10, scaledCircle.getRadius(), 0.0001);
    }

    @Test
    void testCircleContains() {
        Circle circle = new Circle(new Point2D.Double(5, 5), 3);
        Point2D.Double insidePoint = new Point2D.Double(5, 7); // Inside the circle
        Point2D.Double outsidePoint = new Point2D.Double(10, 10); // Outside the circle

        assertTrue(circle.contains(insidePoint)); // Should be inside
        assertFalse(circle.contains(outsidePoint)); // Should be outside
    }

    @Test
    void testContainsShape() {
        Circle outerCircle = new Circle(new Point2D.Double(0, 0), 10);
        Circle innerCircle = new Circle(new Point2D.Double(0, 0), 5);
        Circle nonContainedCircle = new Circle(new Point2D.Double(10, 10), 3); // Not inside

        assertTrue(outerCircle.containsShape(innerCircle), "The outer circle should contain the inner circle.");
        assertFalse(outerCircle.containsShape(nonContainedCircle), "The outer circle should not contain the non-contained circle.");
    }

    @Test
    void testGetCenter() {
        Circle circle = new Circle(new Point2D.Double(5, 5), 3);

        Point2D.Double expectedCenter = new Point2D.Double(5, 5);
        Point2D.Double actualCenter = circle.getCenter();

        assertEquals(expectedCenter, actualCenter, "The center should match the specified center.");
    }

    @Test
    void testToString() {
        Circle circle = new Circle(new Point2D.Double(5, 5), 3);

        String expected = "Circle [center=Point2D.Double[5.0, 5.0], radius=3,000000]";
        String actual = circle.toString();

        assertEquals(expected, actual, "The string representation of the circle should match.");
    }

    @Test
    void testEquals() {
        Circle circle1 = new Circle(new Point2D.Double(5, 5), 3);
        Circle circle2 = new Circle(new Point2D.Double(5, 5), 3);
        Circle circle3 = new Circle(new Point2D.Double(0, 0), 3);

        assertTrue(circle1.equals(circle2), "Circles with the same center and radius should be equal.");
        assertFalse(circle1.equals(circle3), "Circles with different centers should not be equal.");
    }

    @Test
    void testHashCode() {
        Circle circle1 = new Circle(new Point2D.Double(5, 5), 3);
        Circle circle2 = new Circle(new Point2D.Double(5, 5), 3);
        Circle circle3 = new Circle(new Point2D.Double(0, 0), 3);

        assertEquals(circle1.hashCode(), circle2.hashCode(), "Hash codes should be the same for equal circles.");
        assertNotEquals(circle1.hashCode(), circle3.hashCode(), "Hash codes should be different for non-equal circles.");
    }

    @Test
    void testIntersects() {
        Circle circle1 = new Circle(new Point2D.Double(0, 0), 5);
        Circle circle2 = new Circle(new Point2D.Double(4, 0), 5);  // Circles intersecting
        Circle circle3 = new Circle(new Point2D.Double(10, 0), 5); // Circles not intersecting

        assertTrue(circle1.intersects(circle2), "Circles should intersect.");
        assertFalse(circle1.intersects(circle3), "Circles should not intersect.");
    }
}