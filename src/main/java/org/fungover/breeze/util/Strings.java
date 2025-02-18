package org.fungover.breeze.util;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for common string operations.
 */
public class Strings {

    /**
     * Checks if a string is null or empty.
     *
     * @param str the string to check
     * @return {@code true} if the string is null or empty, otherwise {@code false}
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if a string is null or contains only whitespace.
     *
     * @param str the string to check
     * @return {@code true} if the string is null or only contains whitespace, otherwise {@code false}
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }


    /**
     * Truncates a string to a maximum length and appends a suffix if truncated.
     *
     * @param str       the string to truncate
     * @param maxLength the maximum length of the resulting string
     * @param suffix    the suffix to append if the string is truncated
     * @return the truncated string with the suffix if needed
     */
    public static String truncate(String str, int maxLength, String suffix) {
        if (str == null) return null;
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - suffix.length()) + suffix;
    }


    /**
     * Pads a string on the left side with a given character up to a specified length.
     *
     * @param str       the original string
     * @param maxLength the total length of the resulting string
     * @param padChar   the character to pad with
     * @return the padded string
     */
    public static String padLeft(String str, int maxLength, char padChar) {
        if (str == null) return null;
        if (str.length() >= maxLength) return str;

        int paddingSize = maxLength - str.length();
        String padding = String.valueOf(padChar).repeat(paddingSize);
        return padding + str;
    }


    /**
     * Pads a string on the right side with a given character up to a specified length.
     *
     * @param str       the original string
     * @param maxLength the total length of the resulting string
     * @param padChar   the character to pad with
     * @return the padded string
     */
    public static String padRight(String str, int maxLength, char padChar) {
        if (str == null) return null;
        if (str.length() >= maxLength) return str;

        int paddingSize = maxLength - str.length();
        String padding = String.valueOf(padChar).repeat(paddingSize);
        return str + padding;
    }

    /**
     * Reverses a string.
     *
     * @param str the string to reverse
     * @return the reversed string
     */
    public static String reverse(String str) {
        if (str == null) return null;
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Splits a string into a list using a given delimiter.
     *
     * @param str       the string to split
     * @param delimiter the delimiter to use
     * @return a list of substrings
     */
    public static List<String> splitToList(String str, String delimiter) {
        if (str == null) return List.of();
        return Arrays.asList(str.split(delimiter));

    }

    /**
     * Checks if a string contains another string, ignoring case sensitivity.
     *
     * @param str       the main string
     * @param searchStr the string to search for
     * @return {@code true} if {@code str} contains {@code searchStr}, otherwise {@code false}
     */
    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }

        if (searchStr.isEmpty()) {
            return str.isEmpty();
        }
        return str.toLowerCase().contains(searchStr.toLowerCase());
    }

    /**
     * Returns the substring before the first occurrence of a given separator.
     *
     * @param str       the original string
     * @param separator the separator to search for
     * @return the substring before the separator, or the original string if the separator is not found
     */
    public static String substringBefore(String str, String separator) {
        if (str == null || separator == null) {
            return str;
        }

        if (separator.isEmpty()) {
            return str;
        }
        int index = str.indexOf(separator);
        if (index == -1) {
            return str;
        }
        return str.substring(0, index);
    }

    /**
     * Returns the substring after the first occurrence of a given separator.
     *
     * @param str       the original string
     * @param seperator the separator to search for
     * @return the substring after the separator, or the original string if the separator is not found
     */
    public static String substringAfter(String str, String seperator) {
        if (str == null || seperator == null) {
            return str;
        }
        int index = str.indexOf(seperator);
        if (index == -1) {
            return str;
        }
        return str.substring(index + seperator.length());
    }


}



