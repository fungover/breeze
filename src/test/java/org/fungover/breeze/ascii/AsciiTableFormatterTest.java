package org.fungover.breeze.ascii;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
