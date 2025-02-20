package org.fungover.breeze.ascii;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for formatting tables as ASCII text with optional headers, alignments, and borders.
 * and Enum representing text alignment options for table cells.
 */

public class AsciiTableFormatter {
    public enum Alignment {
        LEFT, CENTER, RIGHT
    }

    public static String formatTable(List<String> headers, List<List<String>> rows) {
        return formatTable(headers, rows, null);
    }

    /**
     * Formats a table with optional headers, rows, and custom alignments.     *
     *
     * @param headers    List of header strings; can be {@code null} or empty if no headers are required.
     * @param rows       List of row data, where each row is a list of cell strings.
     * @param alignments List of {@link Alignment} values corresponding to each column; defaults to LEFT if {@code null}
     * or incomplete.
     * @return A formatted ASCII table as a string.
     * @throws IllegalArgumentException if rows are empty or columns are inconsistent.
     */

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

    /**
     * Validates that all rows have the same number of columns.     *
     *
     * @param rows List of rows to validate.
     * @throws IllegalArgumentException if any row has a different number of columns.
     */

    private static void validateRows(List<List<String>> rows) {
        int columnCount = rows.get(0).size();
        for (List<String> row : rows) {
            if (row.size() != columnCount) {
                throw new IllegalArgumentException("All rows must have the same number of columns.");
            }
        }
    }

    /**
     * Calculates the maximum width for each column based on cell content.     *
     *
     * @param rows List of rows containing cell data.
     * @return A list of integers representing the width of each column.
     */

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

    /**
     * Creates a horizontal border for the table based on column widths.     *
     *
     * @param columnWidths List of column widths.
     * @return A string representing the table border.
     */

    private static String createBorder(List<Integer> columnWidths) {
        StringBuilder border = new StringBuilder("+");
        for (int width : columnWidths) {
            border.append("-").append("-".repeat(width)).append("-").append("+");
        }
        return border.append("\n").toString();
    }

    /**
     * Formats a single row of the table with proper alignment and padding.     *
     *
     * @param row        List of cell strings in the row.
     * @param widths     List of column widths.
     * @param alignments List of {@link Alignment} values for the row; defaults to LEFT if {@code null} or incomplete.
     * @return A formatted string representing the table row.
     */

    private static String formatRow(List<String> row, List<Integer> widths, List<Alignment> alignments) {
        StringBuilder formattedRow = new StringBuilder("|");
        for (int i = 0; i < row.size(); i++) {
            String cell = row.get(i);
            Alignment alignment = (alignments != null && alignments.size() > i) ? alignments.get(i) : Alignment.LEFT;
            formattedRow.append(" ").append(alignCell(cell, widths.get(i), alignment)).append(" |");
        }
        return formattedRow.toString();
    }

    /**
     * Aligns a single cell's content based on the specified alignment and column width.     *
     *
     * @param content   The cell content.
     * @param width     The width of the column.
     * @param alignment The desired {@link Alignment} for the content.
     * @return A string with aligned and padded content.
     */

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
