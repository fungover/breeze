package org.fungover.breeze.stringInterpolation;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        String result = org.fungover.breeze.stringInterpolation.StringTemplate.format("Hello, {0}! Today is {1:yyyy-MM-dd}.", "Mark", LocalDate.now());
        System.out.println(result);
    }
}
