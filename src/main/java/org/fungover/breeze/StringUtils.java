package org.fungover.breeze;

import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String truncate(String str, int maxLength, String suffix) {
        if (str == null) return null;
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - suffix.length()) + suffix;
    }

    public static String padLeft(String str, int maxLength, char padChar) {
        if (str == null) return null;
        if (str.length() >= maxLength) return str;

        int paddingSize = maxLength - str.length();
        String padding = String.valueOf(padChar).repeat(paddingSize);
        return padding + str;
    }

    public static String padRight(String str, int maxLength, char padChar) {
        if (str == null) return null;
        if (str.length() >= maxLength) return str;

        int paddingSize = maxLength - str.length();
        String padding = String.valueOf(padChar).repeat(paddingSize);
        return str + padding;
    }

    public static String reverse(String str) {
        if (str == null) return null;
        return new StringBuilder(str).reverse().toString();
    }

    public static List<String> splitToList(String str, String delimiter) {
        if (str == null) return List.of();
        return Arrays.asList(str.split(delimiter));

    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }

        if (searchStr.isEmpty()) {
            return str.isEmpty();
        }
        return str.toLowerCase().contains(searchStr.toLowerCase());
    }



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



