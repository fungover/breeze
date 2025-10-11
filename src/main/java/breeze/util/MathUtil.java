package breeze.util;

public class MathUtil {

    /**
     * Reverses the given input string.
     * @param input The string to reverse.
     * @return The reversed string, or null if input is null.
     */
    public static String reverse(String input) {
        if (input == null) {
            return null;
        }
        return new StringBuilder(input).reverse().toString();
    }
}