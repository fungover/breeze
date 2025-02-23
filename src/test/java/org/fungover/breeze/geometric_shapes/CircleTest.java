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

    @Test
    void testGetCenter() {
        Point2D center = new Point2D.Double(5.0, 10.0);
        Circle circle = new Circle(center, 2);

        Point2D.Double returnedCenter = circle.getCenter();
        assertEquals(5.0, returnedCenter.getX());
        assertEquals(10.0, returnedCenter.getY());
    }

    @Test
    public void testIntersects() {
        Point2D center1 = new Point2D.Double(0.0, 0.0);
        Point2D center2 = new Point2D.Double(3.0, 0.0);

        Circle circle1 = new Circle(center1, 2.0);
        Circle circle2 = new Circle(center2, 2.0);

        assertTrue(circle1.intersects(circle2)); // Should intersect

        // Now test with no intersection
        Circle circle3 = new Circle(new Point2D.Double(10.0, 0.0), 2.0);
        assertFalse(circle1.intersects(circle3)); // Should not intersect
    }

    @Test
    public void testContainsShape() {
        Circle bigCircle = new Circle(new Point2D.Double(0.0, 0.0), 5.0);
        Circle smallCircle = new Circle(new Point2D.Double(2.0, 2.0), 2.0);

        assertTrue(bigCircle.containsShape(smallCircle)); // Small circle inside big circle

        // Test when the small circle is not contained
        Circle outsideCircle = new Circle(new Point2D.Double(7.0, 7.0), 2.0);
        assertFalse(bigCircle.containsShape(outsideCircle)); // Outside of big circle
    }

    @Test
    public void testToString() {
        Point2D center = new Point2D.Double(5.0, 10.0);
        Circle circle = new Circle(center, 2.0);

        String expectedString = "Circle [center=Point2D.Double[5.0, 10.0], radius=2,000000]";
        assertEquals(expectedString, circle.toString());
    }

    @Test
    public void testEquals() {
        Point2D center1 = new Point2D.Double(0.0, 0.0);
        Circle circle1 = new Circle(center1, 3.0);
        Circle circle2 = new Circle(center1, 3.0);

        // Same center and radius, should be equal
        assertTrue(circle1.equals(circle2));

        // Different radius
        Circle circle3 = new Circle(center1, 4.0);
        assertFalse(circle1.equals(circle3));

        // Different center
        Circle circle4 = new Circle(new Point2D.Double(1.0, 1.0), 3.0);
        assertFalse(circle1.equals(circle4));
    }

    @Test
    public void testHashCode() {
        Point2D center1 = new Point2D.Double(0.0, 0.0);
        Circle circle1 = new Circle(center1, 3.0);
        Circle circle2 = new Circle(center1, 3.0);

        assertEquals(circle1.hashCode(), circle2.hashCode()); // Same center and radius

        Circle circle3 = new Circle(new Point2D.Double(1.0, 1.0), 3.0);
        assertNotEquals(circle1.hashCode(), circle3.hashCode()); // Different center
    }
}
