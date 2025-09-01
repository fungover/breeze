package org.fungover.breeze.geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void testValidTriangleCreation() {
        Triangle t = new Triangle(3, 4, 5);
        assertEquals(3, t.getA());
        assertEquals(4, t.getB());
        assertEquals(5, t.getC());
    }

    @Test
    void testInvalidTriangleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Triangle(1, 2, 10));
    }

    @Test
    void testPerimeter() {
        Triangle t = new Triangle(3, 4, 5);
        assertEquals(12, t.getPerimeter());
    }

    @Test
    void testArea() {
        Triangle t = new Triangle(3, 4, 5);
        assertEquals(6, t.getArea());
    }
}
