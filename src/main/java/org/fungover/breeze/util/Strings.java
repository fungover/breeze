package org.fungover.breeze.util;

/**
 * A utility class for performing common string manipulations.
 */

public class Strings {

    /**
     * Capitalizes the fist letter of the given string.
     *
     * @param str the input string
     * @return the string with the fist letter capitalized, or null if input is null
     */
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Counts the number of occurrences fo a substring within a string.
     *
     * @param str the main string
     * @param sub the substring to count
     * @return the number of substring appears in the string, or 0 if str or sub is null/empty
     */
    public static int countOccurrences(String str, String sub) {
        if (str == null || sub == null || sub.isEmpty()) {
            return 0;
        }
        int count = 0, index = 0;
        while ((index = str.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length();
        }
        return count;
    }

    /**
     * Converts camelCase, PascalCase or text with spaces to snake_case.
     *
     * @param str the input string
     * @return the snake_case version of the string, or null if input is null
     */
    public static String toSnakeCase(String str) {
        if (str == null) {
            return null;
        }
        str = str.replaceAll("\\s+", "_");

        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Removes all whitespace charachter from a string.
     *
     * @param str the input string
     * @return the string without whitespace, or null if input is null
     */

    public static String removeWhitespace(String str) {
        return (str == null) ? null : str.replaceAll("\\s+", "");
    }
}
