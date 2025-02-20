package org.fungover.breeze.ascii;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AsciiTableFormatterTest {

    @Test
    void testTableWithHeaders() {
        List<String> headers = Arrays.asList("Name", "Age", "City");
        List<List<String>> rows = Arrays.asList(
                Arrays.asList("John Doe", "30", "Neverland"),
                Arrays.asList("Jane Doe", "25", "Winterland"),
                Arrays.asList("Doe Doe", "23", "Summerland")
        );
        String result = AsciiTableFormatter.formatTable(headers, new ArrayList<>(rows));
        assertTrue(result.contains("| Name     | Age | City       |"));
        assertTrue(result.contains("| John Doe | 30  | Neverland  |"));
    }

    @Test
    void testTableWithoutHeaders() {
        List<List<String>> rows = Arrays.asList(
                Arrays.asList("Dog", "4"),
                Arrays.asList("Cat", "2")
        );
        String result = AsciiTableFormatter.formatTable(null, new ArrayList<>(rows));
        assertTrue(result.contains("| Dog | 4 |"));
        assertTrue(result.contains("| Cat | 2 |"));
    }

    @Test
    void testColumnAlignment() {
        List<String> headers = Arrays.asList("Item", "Qty", "Price");
        List<List<String>> rows = Arrays.asList(
                Arrays.asList("Apple", "10", "$2.00"),
                Arrays.asList("Banana", "5", "$1.50")
        );
        List<AsciiTableFormatter.Alignment> alignments = Arrays.asList(
                AsciiTableFormatter.Alignment.LEFT,
                AsciiTableFormatter.Alignment.CENTER,
                AsciiTableFormatter.Alignment.RIGHT
        );
        String result = AsciiTableFormatter.formatTable(headers, new ArrayList<>(rows), alignments);
        assertTrue(result.contains("| Apple  | 10  | $2.00 |"));
        assertTrue(result.contains("| Banana |  5  | $1.50 |"));
    }

    @Test
    void testInvalidRowLengths() {
        List<String> headers = Arrays.asList("A", "B");
        List<List<String>> rows = Arrays.asList(
                Arrays.asList("1", "2"),
                Arrays.asList("3")
        );

        assertThrows(IllegalArgumentException.class, () ->
                AsciiTableFormatter.formatTable(headers, new ArrayList<>(rows))
        );
    }

}
