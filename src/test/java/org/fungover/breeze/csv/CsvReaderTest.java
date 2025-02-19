package org.fungover.breeze.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String csvData = "name,age,city\n\nBert,30,Karlskrona\n\nSigmund,25,Göteborg\n";
        CsvReader reader = CsvReader.builder()
                .build()
                .withSource(csvData);

        List<String[]> rows = reader.readAll();
        assertEquals(3, rows.size());  // Empty lines should be skipped
        assertArrayEquals(new String[] {"name", "age", "city"}, rows.get(0));  // Header row
        assertArrayEquals(new String[] {"Bert", "30", "Karlskrona"}, rows.get(1));
        assertArrayEquals(new String[] {"Sigmund", "25", "Göteborg"}, rows.get(2));
    }
}
