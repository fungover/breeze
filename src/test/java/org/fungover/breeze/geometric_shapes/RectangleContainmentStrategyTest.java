package org.fungover.breeze.geometric_shapes;

import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class RectangleContainmentStrategyTest {
    @Test
    public void testContainsShapeInside() {
        // Outer rectangle that should fully contain the inner rectangle
        RectangleShape outer = new RectangleShape(new Point2D.Double(0, 0), 100, 100);
        RectangleShape inner = new RectangleShape(new Point2D.Double(10, 10), 20, 20);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertTrue(strategy.containsShape(inner), "Outer rectangle should fully contain the inner rectangle.");
    }

    @Test
    public void testContainsShapePartiallyOutside() {
        // Outer rectangle that does NOT completely contain the inner rectangle
        RectangleShape outer = new RectangleShape(new Point2D.Double(0, 0), 100, 100);
        RectangleShape partiallyOutside = new RectangleShape(new Point2D.Double(90, 90), 20, 20);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertTrue(strategy.containsShape(partiallyOutside), "Outer rectangle should not fully contain the partially outside rectangle.");
    }

    @Test
    public void testContainsSameRectangle() {
        // A rectangle should contain itself
        RectangleShape rect = new RectangleShape(new Point2D.Double(0, 0), 50, 50);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(rect);

        assertTrue(strategy.containsShape(rect), "A rectangle should contain itself.");
    }

    @Test
    public void testContainsShapeWhenContained() {
        // Create a large rectangle (outer rectangle)
        RectangleShape largeRect = new RectangleShape(new Point2D.Double(0, 0), 10.0, 10.0);

        // Create a smaller rectangle (inner rectangle)
        RectangleShape smallRect = new RectangleShape(new Point2D.Double(2, 2), 4.0, 4.0);

        // Rectangle containment strategy for the large rectangle
        RectangleContainmentStrategy containmentStrategy = new RectangleContainmentStrategy(largeRect);

        // Check if the large rectangle contains the small rectangle
        assertTrue(containmentStrategy.containsShape(smallRect)); // Small rectangle should be contained in large rectangle
    }

    @Test
    public void testContainsShapeWhenNotContained() {
        // Create a large rectangle (outer rectangle)
        RectangleShape largeRect = new RectangleShape(new Point2D.Double(0, 0), 10.0, 10.0);

        // Create a smaller rectangle that partially exceeds the boundaries of the large rectangle
        RectangleShape smallRect = new RectangleShape(new Point2D.Double(8, 8), 4.0, 4.0); // Exceeds the large rectangle on the top-right corner

        // Rectangle containment strategy for the large rectangle
        RectangleContainmentStrategy containmentStrategy = new RectangleContainmentStrategy(largeRect);

        // Check if the large rectangle contains the small rectangle
        assertTrue(containmentStrategy.containsShape(smallRect)); // Small rectangle should not be contained in large rectangle
    }

    @Test
    public void testContainsShapeWhenExactlyFitting() {
        // Create a large rectangle (outer rectangle)
        RectangleShape largeRect = new RectangleShape(new Point2D.Double(0, 0), 10.0, 10.0);

        // Create a rectangle that fits exactly inside the large rectangle, touching edges but not exceeding
        RectangleShape exactFitRect = new RectangleShape(new Point2D.Double(0, 0), 10.0, 10.0);

        // Rectangle containment strategy for the large rectangle
        RectangleContainmentStrategy containmentStrategy = new RectangleContainmentStrategy(largeRect);

        // Check if the large rectangle contains the exact fit rectangle
        assertTrue(containmentStrategy.containsShape(exactFitRect)); // Should return true because it is an exact fit
    }

    @Test
    void testContainsShapeWithNegativeCoordinates() {
        RectangleShape outer = new RectangleShape(new Point2D.Double(-10, -10), 20, 20);
        RectangleShape inner = new RectangleShape(new Point2D.Double(-5, -5), 10, 10);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertTrue(strategy.containsShape(inner), "Should handle negative coordinates correctly");
    }

    @Test
    void testContainsNonRectangleShape() {
        RectangleShape outer = new RectangleShape(new Point2D.Double(0, 0), 10, 10);
        Shape circle = new Circle(new Point2D.Double(5, 5), 2);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertThrows(IllegalArgumentException.class, () -> strategy.containsShape(circle),
                "Should reject non-rectangle shapes");
    }

    @Test
    void testContainsShapeWithZeroDimensions() {
        RectangleShape outer = new RectangleShape(new Point2D.Double(0, 0), 10, 10);
        RectangleShape zeroWidth = new RectangleShape(new Point2D.Double(5, 5), 0, 5);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertThrows(IllegalArgumentException.class, () -> strategy.containsShape(zeroWidth),
                "Should reject rectangles with zero dimensions");
    }

    @Test
    void testContainsShapeWithNullShape() {
        RectangleShape outer = new RectangleShape(new Point2D.Double(0, 0), 10, 10);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertThrows(IllegalArgumentException.class, () -> strategy.containsShape(null),
                "Should throw exception when passed a null shape");
    }

    @Test
    void testContainsShapeAtBoundary() {
        RectangleShape outer = new RectangleShape(new Point2D.Double(0, 0), 10, 10);
        RectangleShape atBoundary = new RectangleShape(new Point2D.Double(0, 0), 10, 10); // Exact boundary match
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertTrue(strategy.containsShape(atBoundary), "Rectangle should contain the boundary-matching rectangle.");
    }

    @Test
    void testContainsShapeWithEmptyRectangle() {
        RectangleShape outer = new RectangleShape(new Point2D.Double(0, 0), 10, 10);
        RectangleShape emptyRectangle = new RectangleShape(new Point2D.Double(5, 5), 0, 0); // Empty rectangle
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertThrows(IllegalArgumentException.class, () -> strategy.containsShape(emptyRectangle),
                "Should reject rectangles with zero width and height");
    }

    @Test
    void testContainsShapeWithNegativeDimensions() {
        // Outer rectangle (normal dimensions)
        RectangleShape outer = new RectangleShape(new Point2D.Double(0, 0), 10, 10);
        // Rectangle with negative width (invalid)
        assertThrows(IllegalArgumentException.class, () -> new RectangleShape(new Point2D.Double(5, 5), -5, 5),
                "Should reject rectangles with negative dimensions");

        // Rectangle with negative height (invalid)
        assertThrows(IllegalArgumentException.class, () -> new RectangleShape(new Point2D.Double(5, 5), 5, -5),
                "Should reject rectangles with negative dimensions");

        // Rectangle with both negative width and height (invalid)
        assertThrows(IllegalArgumentException.class, () -> new RectangleShape(new Point2D.Double(5, 5), -5, -5),
                "Should reject rectangles with negative dimensions");
    }

    @Test
    void testConstructorWithNullPoint() {
        assertThrows(IllegalArgumentException.class,
                () -> new RectangleShape(null, 5.0, 10.0),
                "Should reject null point!");
    }

}