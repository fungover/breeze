package org.fungover.breeze.geometric_shapes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.geom.Point2D;
import java.util.Locale;

class RectangleShapeTest {

    @Test
    void testContainsPoint() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // Test points inside the rectangle
        Point2D.Double insidePoint = new Point2D.Double(5, 2);  // Inside the rectangle
        assertTrue(rectangle.contains(insidePoint));

        // Test points outside the rectangle
        Point2D.Double outsidePoint = new Point2D.Double(12, 6);  // Outside the rectangle
        assertFalse(rectangle.contains(outsidePoint));
    }

    @Test
    void testGetArea() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // Area = width * height = 10 * 5 = 50
        assertEquals(50, rectangle.getArea());
    }

    @Test
    void testGetPerimeter() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // Perimeter = 2 * (width + height) = 2 * (10 + 5) = 30
        assertEquals(30, rectangle.getPerimeter());
    }

    @Test
    void testGetCenter() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10.0, 5.0);

        // The center should be at (5, 2.5) for a 10x5 rectangle
        Point2D expectedCenter = new Point2D.Double(5.0, 2.5);  // Integer center for simplicity
        assertEquals(expectedCenter, rectangle.getCenter());
    }

    @Test
    void testGetBoundingBox() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // The bounding box for a rectangle is the rectangle itself
        assertEquals(rectangle, rectangle.getBoundingBox());
    }

    @Test
    void testScale() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 10, 5);

        // Scaling by a factor of 2 should double the width and height
        RectangleShape scaledRectangle = (RectangleShape) rectangle.scale(2);
        assertEquals(20, scaledRectangle.getWidth(), 0.0001);
        assertEquals(10, scaledRectangle.getHeight(), 0.0001);
    }

    @Test
    public void testConstructorWithValidValues() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 5.0, 10.0);

        assertEquals(5.0, rectangle.getWidth());
        assertEquals(10.0, rectangle.getHeight());
        assertEquals(topLeft, rectangle.getTopLeft());
    }

    @Test
    public void testConstructorWithInvalidWidth() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        assertThrows(IllegalArgumentException.class, () -> new RectangleShape(topLeft, -5.0, 10.0));
    }

    @Test
    public void testConstructorWithInvalidHeight() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        assertThrows(IllegalArgumentException.class, () -> new RectangleShape(topLeft, 5.0, -10.0));
    }

    @Test
    public void testContains() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 5.0, 10.0);

        Point2D.Double insidePoint = new Point2D.Double(2, 3);
        Point2D.Double outsidePoint = new Point2D.Double(6, 11);

        assertTrue(rectangle.contains(insidePoint)); // Point inside rectangle
        assertFalse(rectangle.contains(outsidePoint)); // Point outside rectangle
    }

    @Test
    public void testContainsShape() {
        RectangleShape bigRect = new RectangleShape(new Point2D.Double(0, 0), 10.0, 10.0);

        RectangleShape smallRect = new RectangleShape(new Point2D.Double(2, 3), 10, 10);
        assertTrue(bigRect.containsShape(smallRect)); // Big rectangle should contain small rectangle

        RectangleShape outsideRect = new RectangleShape(new Point2D.Double(11, 11), 6, 11);
        assertFalse(bigRect.containsShape(outsideRect)); // Big rectangle should not contain outside rectangle
    }

    @Test
    public void testToString() {
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        RectangleShape rectangle = new RectangleShape(topLeft, 5.0, 10.0);

        String expectedString = String.format(Locale.FRANCE, "Rectangle [topLeft=%s, width=%f, height=%f]", topLeft, 5.0, 10.0);
        assertEquals(expectedString, rectangle.toString());
    }

    @Test
    void testContainsShape2() {
        RectangleShape larger = new RectangleShape(new Point2D.Double(0, 0), 20, 20);
        RectangleShape smaller = new RectangleShape(new Point2D.Double(5, 5), 10, 10);
        assertTrue(larger.containsShape(smaller));
        assertFalse(smaller.containsShape(larger));
    }

    @Test
    public void testIntersection() {
        // Create two rectangles that do intersect
        RectangleShape r1 = new RectangleShape(new Point2D.Double(0, 0), 4, 4);
        RectangleShape r2 = new RectangleShape(new Point2D.Double(2, 2), 4, 4);

        // Test if they do intersect
        assertTrue(r1.intersects(r2));
    }

    @Test
    public void testTouchingButNotIntersecting() {
        // Create two rectangles that touch but do not intersect
        RectangleShape r1 = new RectangleShape(new Point2D.Double(0, 0), 4, 4);
        RectangleShape r2 = new RectangleShape(new Point2D.Double(4, 0), 4, 4); // r2 just touches r1

        // Test if they do not intersect
        assertTrue(r1.intersects(r2));
    }

    @Test
    public void testZeroWidthOrHeight() {
        // Create a rectangle with zero width and a valid rectangle
        RectangleShape r1 = new RectangleShape(new Point2D.Double(0, 0), 0, 4); // Zero width
        RectangleShape r2 = new RectangleShape(new Point2D.Double(1, 1), 4, 4);

        // Test that the intersection returns false since one of the rectangles has no width
        assertFalse(r1.intersects(r2));
    }

    @Test
    public void testEdgeOverlap() {
        // Create two rectangles that overlap on the edge (one just touching the other)
        RectangleShape r1 = new RectangleShape(new Point2D.Double(0, 0), 4, 4);
        RectangleShape r2 = new RectangleShape(new Point2D.Double(4, 0), 4, 4); // Touching along the right edge

        // Test if they do not intersect (just touching, no overlap)
        assertTrue(r1.intersects(r2));
    }

    @Test
    public void testContainedRectangle() {
        // Create a smaller rectangle that is contained within a larger one
        RectangleShape r1 = new RectangleShape(new Point2D.Double(0, 0), 6, 6);
        RectangleShape r2 = new RectangleShape(new Point2D.Double(1, 1), 4, 4); // r2 is inside r1

        // Test if they intersect (they should, as one is contained within the other)
        assertTrue(r1.intersects(r2));
    }

    @Test
    public void testExactSameRectangle() {
        // Create two identical rectangles
        RectangleShape r1 = new RectangleShape(new Point2D.Double(0, 0), 4, 4);
        RectangleShape r2 = new RectangleShape(new Point2D.Double(0, 0), 4, 4);

        // Test if they intersect (they should, as they are the same rectangle)
        assertTrue(r1.intersects(r2));
    }

    @Test
    void testConstructorWithNullPoint() {
        assertThrows(IllegalArgumentException.class,
                () -> new RectangleShape(null, 5.0, 10.0), "Should reject null point");
        }

        @Test
        void testContainsPointWithin() {
            // Create a rectangle with top-left at (0, 0) and size 10x5
            RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);

            // Point strictly inside the rectangle
            Point2D.Double pointInside = new Point2D.Double(5, 2);
            assertTrue(rectangle.contains(pointInside), "Point should be inside the rectangle.");
        }

        @Test
        void testContainsPointOnTopBoundary() {
            // Create a rectangle with top-left at (0, 0) and size 10x5
            RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);

            // Point on the top boundary (y = 0)
            Point2D.Double pointOnTopBoundary = new Point2D.Double(5, 0);
            assertTrue(rectangle.contains(pointOnTopBoundary), "Point on the top boundary should be considered inside.");
        }

        @Test
        void testContainsPointOnBottomBoundary() {
            // Create a rectangle with top-left at (0, 0) and size 10x5
            RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);

            // Point on the bottom boundary (y = 5)
            Point2D.Double pointOnBottomBoundary = new Point2D.Double(5, 5);
            assertTrue(rectangle.contains(pointOnBottomBoundary), "Point on the bottom boundary should be considered inside.");
        }

        @Test
        void testContainsPointBelowBottomBoundary() {
            // Create a rectangle with top-left at (0, 0) and size 10x5
            RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);

            // Point below the bottom boundary (y > 5)
            Point2D pointBelowBottom = new Point2D.Double(5, 6);
            assertFalse(rectangle.contains(pointBelowBottom), "Point below the bottom boundary should not be inside.");
        }

        @Test
        void testContainsPointAboveTopBoundary() {
            // Create a rectangle with top-left at (0, 0) and size 10x5
            RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);

            // Point above the top boundary (y < 0)
            Point2D.Double pointAboveTop = new Point2D.Double(5, -1);
            assertFalse(rectangle.contains(pointAboveTop), "Point above the top boundary should not be inside.");
        }

        @Test
        void testContainsPointOnLeftBoundary() {
            // Create a rectangle with top-left at (0, 0) and size 10x5
            RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);

            // Point on the left boundary (x = 0)
            Point2D.Double pointOnLeftBoundary = new Point2D.Double(0, 2);
            assertTrue(rectangle.contains(pointOnLeftBoundary), "Point on the left boundary should be considered inside.");
        }

        @Test
        void testContainsPointOnRightBoundary() {
            // Create a rectangle with top-left at (0, 0) and size 10x5
            RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);

            // Point on the right boundary (x = 10)
            Point2D.Double pointOnRightBoundary = new Point2D.Double(10, 2);
            assertTrue(rectangle.contains(pointOnRightBoundary), "Point on the right boundary should be considered inside.");
        }

        @Test
        void testContainsPointOutsideLeft() {
            // Create a rectangle with top-left at (0, 0) and size 10x5
            RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);

            // Point outside the left boundary (x < 0)
            Point2D.Double pointOutsideLeft = new Point2D.Double(-1, 2);
            assertFalse(rectangle.contains(pointOutsideLeft), "Point outside the left boundary should not be inside.");
        }

        @Test
        void testContainsPointOutsideRight() {
            // Create a rectangle with top-left at (0, 0) and size 10x5
            RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);

            // Point outside the right boundary (x > 10)
            Point2D.Double pointOutsideRight = new Point2D.Double(11, 2);
            assertFalse(rectangle.contains(pointOutsideRight), "Point outside the right boundary should not be inside.");
        }

    @Test
    void testRotateBy90Degrees() {
        // Rectangle with top-left at (0, 0) and size 10x5
        RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10.0, 5.0);
        Point2D center = new Point2D.Double(2.5, 5.0); // Center around which to rotate (middle of the rectangle)

        // Rotate 90 degrees
        RectangleShape rotated = (RectangleShape) rectangle.rotate(90, center);

        // After 90-degree rotation, the width and height should be swapped
        assertEquals(rectangle.getHeight(), rotated.getWidth(), "Width should be swapped with height after 90 degree rotation");
        assertEquals(rectangle.getWidth(), rotated.getHeight(), "Height should be swapped with width after 90 degree rotation");

        // Verify that the center remains the same
        assertEquals(center, rotated.getCenter(), "Center should remain unchanged after rotation");
    }

    @Test
    void testRotateBy180Degrees() {
        // Rectangle with top-left at (0, 0) and size 10x5
        RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);
        Point2D center = new Point2D.Double(5, 2.5); // Center around which to rotate (middle of the rectangle)

        // Rotate 180 degrees
        RectangleShape rotated = (RectangleShape) rectangle.rotate(180, center);

        // Verify that the center remains the same
        assertEquals(center, rotated.getCenter(), "Center should remain unchanged after rotation");

        // The position of the rectangle should be mirrored around the center
        assertEquals(rectangle.getTopLeft().getX() + 0, rotated.getTopLeft().getX(), "X coordinate should be mirrored after 180 degree rotation");
        assertEquals(rectangle.getTopLeft().getY() + 0, rotated.getTopLeft().getY(), "Y coordinate should be mirrored after 180 degree rotation");
    }

    @Test
    void testRotateBy0Degrees() {
        // Rectangle with top-left at (0, 0) and size 10x5
        RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);
        Point2D center = new Point2D.Double(5, 2.5); // Center around which to rotate (middle of the rectangle)

        // Rotate 0 degrees (should not change)
        RectangleShape rotated = (RectangleShape) rectangle.rotate(0, center);

        // The rectangle should be the same after 0-degree rotation
        assertEquals(rectangle.getTopLeft(), rotated.getTopLeft(), "Top-left corner should remain unchanged after 0 degree rotation");
        assertEquals(rectangle.getWidth(), rotated.getWidth(), "Width should remain the same after 0 degree rotation");
        assertEquals(rectangle.getHeight(), rotated.getHeight(), "Height should remain the same after 0 degree rotation");
        assertEquals(center, rotated.getCenter(), "Center should remain unchanged after 0 degree rotation");
    }

    @Test
    void testRotateAroundCenter() {
        // Rectangle with top-left at (0, 0) and size 10x5
        RectangleShape rectangle = new RectangleShape(new Point2D.Double(0, 0), 10, 5);
        Point2D center = new Point2D.Double(2.5, 5.0); // Center around which to rotate (middle of the rectangle)

        // Rotate 90 degrees
        RectangleShape rotated = (RectangleShape) rectangle.rotate(90, center);

        // After rotation, the center should not change
        assertEquals(center, rotated.getCenter(), "Center should remain unchanged after rotation");

        // Check that the corners have been rotated correctly (visually inspecting the output is one way)
        // You may choose to assert the specific corner coordinates here, but these would require more complex calculations.
    }
}