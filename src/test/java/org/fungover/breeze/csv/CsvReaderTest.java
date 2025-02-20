package org.fungover.breeze.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    @Test
    void readAll_String() throws IOException {

        // Arrange
        String csvContent = """
                123,234,865
                4534346,5,77
                243,23,6767
                """;

        CsvReader classUnderTest = CsvReader.builder()
                .withDelimiter(',')
                .build();

        // Act
        List<String[]> actual = classUnderTest.withSource(csvContent).readAll();

        // Assert
        assertThat(actual).containsExactly(
                new String[]{"123", "234", "865"},
                new String[]{"4534346", "5", "77"},
                new String[]{"243", "23", "6767"});
    }

    @Test
    void readAll_InputStream() throws IOException {

        // Arrange
        String csvContent = """
                123,234,865
                4534346,5,77
                243,23,6767
                """;

        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        CsvReader classUnderTest = CsvReader.builder().build();

        // Act
        List<String[]> actual = classUnderTest.withSource(inputStream, "UTF-8").readAll();

        // Assert
        assertThat(actual).containsExactly(
                new String[]{"123", "234", "865"},
                new String[]{"4534346", "5", "77"},
                new String[]{"243", "23", "6767"});
    }

    @Test
    void readAll_File() throws IOException {

        // Arrange
        String csvContent = """
                123,234,865
                4534346,5,77
                243,23,6767
                """;

        File file = Files.writeString(Files.createTempFile("testCsv", ".csv"), csvContent, StandardCharsets.UTF_8).toFile();

        CsvReader classUnderTest = CsvReader.builder().build();

        // Act
        List<String[]> actual = classUnderTest.withSource(file, "UTF-8").readAll();

        // Assert
        assertThat(actual).containsExactly(
                new String[]{"123", "234", "865"},
                new String[]{"4534346", "5", "77"},
                new String[]{"243", "23", "6767"});
    }


    @Test
    @DisplayName("Should not read empty lines")
    void shouldNotReadEmptyLines() throws IOException {
        String csvData = "name,age,city\n\nBert,30,Karlskrona\n\nSigmund,25,Gothenburg\n";
        CsvReader reader = CsvReader.builder()
                .build()
                .withSource(csvData);

        List<String[]> rows = reader.readAll();
        assertEquals(3, rows.size());  // Empty lines should be skipped
        assertArrayEquals(new String[] {"name", "age", "city"}, rows.get(0));  // Header row
        assertArrayEquals(new String[] {"Bert", "30", "Karlskrona"}, rows.get(1));
        assertArrayEquals(new String[] {"Sigmund", "25", "Gothenburg"}, rows.get(2));
    }


    @Test
    @DisplayName("Should ignore rows with only whitespace")
    void shouldIgnoreRowsWithOnlyWhitespace() throws IOException {
        String csvData = "name,age,city\n   \nSpacer,30,Stockholm\n  \nJerry,25,Oslo\n";
        CsvReader reader = CsvReader.builder()
                .build()
                .withSource(csvData);

        List<String[]> rows = reader.readAll();
        assertEquals(3, rows.size());
        assertArrayEquals(new String[] {"name", "age", "city"}, rows.get(0));
        assertArrayEquals(new String[] {"Spacer", "30", "Stockholm"}, rows.get(1));
        assertArrayEquals(new String[] {"Jerry", "25", "Oslo"}, rows.get(2));
    }

    @Test
    @DisplayName("Should Not Ignore whitespaceInTokens")
    void shouldNotIgnoreWhiteSpaceInTokens() throws IOException {
        String csvData = "Mr Anderson,30,New York";
        CsvReader reader = CsvReader.builder()
                .build()
                .withSource(csvData);

        List<String[]> rows = reader.readAll();
        assertArrayEquals(new String[] {"Mr Anderson", "30", "New York"}, rows.getFirst());
    }

// Doesn't work yet
//    @Test
//    @DisplayName("ShouldTrimWhitespaceAroundTokens")
//    void shoultTrimWhitespaceAroundTokens() throws IOException {
//        String csvData = "name,age,city\nSteve ,55 , London \nJanet, 25, Los Angeles";
//        CsvReader reader = CsvReader.builder()
//                .build()
//                .withSource(csvData);
//
//        List<String[]> rows = reader.readAll();
//        assertEquals(3, rows.size());
//        assertArrayEquals(new String[] {"name", "age", "city"}, rows.get(0));
//        assertArrayEquals(new String[] {"Steve", "55", "London"}, rows.get(1));
//        assertArrayEquals(new String[] {"Janet", "25", "Los Angeles"}, rows.get(2));
//    }



    @Test
    @DisplayName("Read next should work on not empty lines")
    void readNextShouldWorkOnNotEmptyLines() throws IOException {
        String csvData = "\nAlice,30,Stockholm\n\nBob,25,Gothenburg\n";
        CsvReader reader = CsvReader.builder()
                .build()
                .withSource(csvData);

        // Läs första icke-tomma raden
        String[] result = reader.readNext();
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals("Alice", result[0]);

        result = reader.readNext();
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals("25", result[1]);
    }

    @Test
    @DisplayName("Customed delimiter should work as input")
    void customedDelimiterShouldWorkAsInput() throws IOException {
        // Test med anpassad delimiter
        String csvSource = "Alice|30|Stockholm\nBob|25|Gothenburg\n";
        CsvReader csvReader = CsvReader.builder()
                .withDelimiter('|')
                .skipEmptyLines(true)
                .build();
        csvReader.withSource(csvSource);

        String[] result = csvReader.readNext();
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals("Alice", result[0]);
        assertEquals("30", result[1]);
        assertEquals("Stockholm", result[2]);

        result = csvReader.readNext();
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals("Bob", result[0]);
        assertEquals("25", result[1]);
        assertEquals("Gothenburg", result[2]);
    }

    @Test
    @DisplayName("Should map header keys and column values together if hasHeader is true")
    void shouldMapHeaderKeysAndColumnValuesTogetherIfHasHeaderIsTrue() throws IOException {
        String csvData = "Name,Age,City\nJohn,30,New York\nAlice,25,Los Angeles\nBob,35,Chicago";
        CsvReader reader = CsvReader.builder()
                .hasHeader(true)
                .build()
                .withSource(csvData);


        Map<String, String> firstRow = reader.readNextAsMap();
        assertEquals("John", firstRow.get("Name"));
        assertEquals("30", firstRow.get("Age"));
        assertEquals("New York", firstRow.get("City"));

        Map<String, String> secondRow = reader.readNextAsMap();
        assertEquals("Alice", secondRow.get("Name"));
        assertEquals("25", secondRow.get("Age"));
        assertEquals("Los Angeles", secondRow.get("City"));
    }

}
