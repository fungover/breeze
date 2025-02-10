package org.fungover.breeze;

public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
       return str == null || str.isEmpty();
    }

    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
