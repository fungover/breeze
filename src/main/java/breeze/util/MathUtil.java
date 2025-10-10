package breeze.util;

public class MathUtil {

    /**
     * Calculates the area of a rectangle.
     * @param length The length of the rectangle.
     * @param width The width of the rectangle.
     * @return The area, or 0 if length/width is zero or negative.
     */
    public static double calculateArea(double length, double width) {
        if (length <= 0 || width <= 0) {
            return 0;
        }
        return length * width;
    }
}