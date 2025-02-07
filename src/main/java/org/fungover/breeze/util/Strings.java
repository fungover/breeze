package org.fungover.breeze.util;

/**
 * A utility class for performing common string manipulations.
 */

public class Strings {

    /**
     * Capitalizes the first letter of the given string.
     *
     * @param str the input string
     * @return the string with the first letter capitalized, or null if input is null
     */
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Counts the number of occurrences to a substring within a string.
     *
     * @param str the main string
     * @param sub the substring to count
     * @return the number of times the substring appears in the string, or 0 if str or sub is null/empty
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
        str = str.trim().replaceAll("\\s+", "_");

        str = str.replaceAll("([A-Z]{2,})(?=[A-Z][a-z])", "$1_");
        str = str.replaceAll("([a-z\\d])([A-Z])", "$1_$2");

        return str.toLowerCase();

    }

    /**
     * Removes all whitespace character from a string.
     *
     * @param str the input string
     * @return the string without whitespace, or null if input is null
     */

    public static String removeWhitespace(String str) {
        return (str == null) ? null : str.replaceAll("\\s+", "");
    }

    /**
     * Checks if a string is palindrome
     *
     * @param str the input string
     * @return true if the string is a palindrome, false otherwise (null returns false)
     */
    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        String lowerCasedStr = str.toLowerCase();
        String reversedStr = new StringBuilder(lowerCasedStr).reverse().toString();
        return lowerCasedStr.equals(reversedStr);
    }
}
