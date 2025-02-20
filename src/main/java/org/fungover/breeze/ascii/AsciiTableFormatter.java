package org.fungover.breeze.ascii;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AsciiTableFormatter {

    public enum Alignment {
        LEFT, CENTER, RIGHT
    }

    public static String formatTable(List<String> headers, List<List<String>> rows) {
        return formatTable(headers, rows, null);
    }

    public static String formatTable(List<String> headers, List<List<String>> rows, List<Alignment> alignments) {
        if (headers != null && !headers.isEmpty()) {
            rows.add(0, headers);
        }

        validateRows(rows);
        List<Integer> columnWidths = calculateColumnWidths(rows);
        StringBuilder table = new StringBuilder();
        String border = createBorder(columnWidths);
        table.append(border);

        if (headers != null && !headers.isEmpty()) {
            table.append(formatRow(headers, columnWidths, alignments)).append("\n");
            table.append(border);
        }

        for (int i = (headers != null) ? 1 : 0; i < rows.size(); i++) {
            table.append(formatRow(rows.get(i), columnWidths, alignments)).append("\n");
        }
        table.append(border);

        return table.toString();
    }

    private static void validateRows(List<List<String>> rows) {
        int columnCount = rows.get(0).size();
        for (List<String> row : rows) {
            if (row.size() != columnCount) {
                throw new IllegalArgumentException("All rows must have the same number of columns.");
            }
        }
    }

    private static List<Integer> calculateColumnWidths(List<List<String>> rows) {
        int columnCount = rows.get(0).size();
        List<Integer> widths = new ArrayList<>(Arrays.asList(new Integer[columnCount]));
        for (int i = 0; i < columnCount; i++) widths.set(i, 0);

        for (List<String> row : rows) {
            for (int i = 0; i < columnCount; i++) {
                widths.set(i, Math.max(widths.get(i), row.get(i).length()));
            }
        }
        return widths;
    }

    private static String createBorder(List<Integer> columnWidths) {
        StringBuilder border = new StringBuilder("+");
        for (int width : columnWidths) {
            border.append("-").append("-".repeat(width)).append("-").append("+");
        }
        return border.append("\n").toString();
    }

    private static String formatRow(List<String> row, List<Integer> widths, List<Alignment> alignments) {
        StringBuilder formattedRow = new StringBuilder("|");
        for (int i = 0; i < row.size(); i++) {
            String cell = row.get(i);
            Alignment alignment = (alignments != null && alignments.size() > i) ? alignments.get(i) : Alignment.LEFT;
            formattedRow.append(" ").append(alignCell(cell, widths.get(i), alignment)).append(" |");
        }
        return formattedRow.toString();
    }

    private static String alignCell(String content, int width, Alignment alignment) {
        return switch (alignment) {
            case RIGHT -> String.format("%" + width + "s", content);
            case CENTER -> {
                int padding = width - content.length();
                int left = padding / 2;
                int right = padding - left;
                yield " ".repeat(left) + content + " ".repeat(right);
            }
            default -> String.format("%-" + width + "s", content);
        };
    }
}
