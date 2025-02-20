package org.fungover.breeze.ascii;


import java.util.List;

public class AsciiTableFormatter {

    public enum Alignment {
        LEFT, CENTER, RIGHT
    }
    public static String formatTable(List<String> headers, List<List<String>> rows) {
        return formatTable(headers, rows);
    }
    private static void validateRows(List<List<String>> rows) {
        int columnCount = rows.get(0).size();
        for (List<String> row : rows) {
            if (row.size() != columnCount) {
                throw new IllegalArgumentException("All rows must have the same number of columns.");
            }
        }
    }


}
