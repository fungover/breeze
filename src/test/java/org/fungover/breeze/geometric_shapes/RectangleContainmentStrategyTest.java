package org.fungover.breeze.geometric_shapes;

import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RectangleContainmentStrategyTest {
    @Test
    public void testContainsShapeInside() {
        // Outer rectangle that should fully contain the inner rectangle
        RectangleShape outer = new RectangleShape(new Point(0, 0), 100, 100);
        RectangleShape inner = new RectangleShape(new Point(10, 10), 20, 20);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertTrue(strategy.containsShape(inner), "Outer rectangle should fully contain the inner rectangle.");
    }

    @Test
    public void testContainsShapePartiallyOutside() {
        // Outer rectangle that does NOT completely contain the inner rectangle
        RectangleShape outer = new RectangleShape(new Point(0, 0), 100, 100);
        RectangleShape partiallyOutside = new RectangleShape(new Point(90, 90), 20, 20);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(outer);

        assertFalse(strategy.containsShape(partiallyOutside), "Outer rectangle should not fully contain the partially outside rectangle.");
    }

    @Test
    public void testContainsSameRectangle() {
        // A rectangle should contain itself
        RectangleShape rect = new RectangleShape(new Point(0, 0), 50, 50);
        RectangleContainmentStrategy strategy = new RectangleContainmentStrategy(rect);

        assertTrue(strategy.containsShape(rect), "A rectangle should contain itself.");
    }
}