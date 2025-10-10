package breeze.util;

public class MathUtil {

    /**
     * Calculates the area of a rectangle.
     * @param length The length of the rectangle.
     * @param width The width of the rectangle.
     * @return The calculated area.
     * @throws IllegalArgumentException if length or width is non-positive.
     */
    public static double calculateArea(double length, double width) {
        if (length <= 0 || width <= 0) {
            throw new IllegalArgumentException(
                    "Length and width must be positive. Got length=" + length + ", width=" + width
            );
        }
        return length * width;
    }
}