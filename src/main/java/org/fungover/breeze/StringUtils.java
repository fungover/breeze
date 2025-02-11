package org.fungover.breeze;

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
        if (str.length() <= maxLength) return str;

        int paddingSize = maxLength - str.length();
        String padding = String.valueOf(padChar).repeat(paddingSize);
        return str + padding;
    }

    public static String padRight(String str, int maxLength, char padChar) {
        if (str == null) return null;
        if (str.length() <= maxLength) return str;

        int paddingSize = maxLength - str.length();
        String padding = String.valueOf(padChar).repeat(paddingSize);
        return padding + str;
    }
}
