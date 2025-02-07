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

}
