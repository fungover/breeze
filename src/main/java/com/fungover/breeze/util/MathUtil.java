package com.fungover.breeze.util;

public class MathUtil {

    /**
     * Beräknar arean för en rektangel.
     * @param length Rektangelns längd.
     * @param width Rektangelns bredd.
     * @return Arean, eller 0 om längd/bredd är noll eller negativ.
     */
    public static double calculateArea(double length, double width) {
        if (length <= 0 || width <= 0) {
            return 0;
        }
        return length * width;
    }
}