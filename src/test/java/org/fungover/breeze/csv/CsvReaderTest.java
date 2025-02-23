package org.fungover.breeze.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CsvReaderTest {

    @Test
    @DisplayName("Throw exception if BufferedReader is null when calling readAll")
    void readAll_whenBufferedReaderIsNull_throwException() {

        // Arrange
        CsvReader classUnderTest = CsvReader.builder().build();

        // Act
        Throwable actual = catchThrowable(classUnderTest::readAll);

        // Assert
        assertThat(actual)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("withSource(..) must be called before readAll()");
    }

    @Test
    @DisplayName("readAll with String CSV")
    void readAll_String() throws IOException {

        // Arrange
        String csvContent = """
                123,234,865
                4534346,5,77
                243,23,6767
                """;

        CsvReader classUnderTest = CsvReader.builder()
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
    @DisplayName("readAll with InputStream CSV")
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

    @ParameterizedTest(name = "readAll from file with charset {0}")
    @MethodSource("provideCharsetsFor_readAll_File")
    void readAll_File(Charset charset) throws IOException {

        // Arrange
        String csvContent = """
                123,234,865
                4534346,5,77
                243,23,6767
                """;

        File file = Files.writeString(Files.createTempFile("testCsv", ".csv"), csvContent, charset).toFile();

        CsvReader classUnderTest = CsvReader.builder().build();

        // Act
        List<String[]> actual = classUnderTest.withSource(file, charset.name()).readAll();

        // Assert
        assertThat(actual).containsExactly(
                new String[]{"123", "234", "865"},
                new String[]{"4534346", "5", "77"},
                new String[]{"243", "23", "6767"});
    }

    private static Stream<Arguments> provideCharsetsFor_readAll_File() {
        return Stream.of(
                Arguments.of(StandardCharsets.UTF_8),
                Arguments.of(StandardCharsets.ISO_8859_1),
                Arguments.of(StandardCharsets.US_ASCII)
        );
    }

    @ParameterizedTest(name = "readAll with delimiter [{0}]")
    @MethodSource("provideDelimitersAndCsvContent")
    void readAll_String(char delimiter, String csvContent) throws IOException {

        // Arrange
        CsvReader classUnderTest = CsvReader.builder()
                .withDelimiter(delimiter)
                .build();

        // Act
        List<String[]> actual = classUnderTest.withSource(csvContent).readAll();

        // Assert
        assertThat(actual).containsExactly(
                new String[]{"123", "234", "865"},
                new String[]{"4534346", "5", "77"},
                new String[]{"243", "23", "6767"});
    }

    private static Stream<Arguments> provideDelimitersAndCsvContent() {
        return Stream.of(
                Arguments.of(',', "123,234,865\n4534346,5,77\n243,23,6767\n"),
                Arguments.of(';', "123;234;865\n4534346;5;77\n243;23;6767\n"),
                Arguments.of(' ', "123 234 865\n4534346 5 77\n243 23 6767\n"),
                Arguments.of('\t', "123\t234\t865\n4534346\t5\t77\n243\t23\t6767\n")
        );
    }

    @Test
    void readAll_csvHasHeader() throws IOException {

        // Arrange
        String csvContent = """
                A,B,C
                4534346,5,77
                243,23,6767
                """;

        CsvReader classUnderTest = CsvReader.builder()
                .hasHeader(true)
                .build();

        // Act
        List<String[]> actual = classUnderTest.withSource(csvContent).readAll();

        // Assert
        assertThat(actual).containsExactly(
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
        assertArrayEquals(new String[]{"name", "age", "city"}, rows.get(0));  // Header row
        assertArrayEquals(new String[]{"Bert", "30", "Karlskrona"}, rows.get(1));
        assertArrayEquals(new String[]{"Sigmund", "25", "Gothenburg"}, rows.get(2));
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
        assertArrayEquals(new String[]{"name", "age", "city"}, rows.get(0));
        assertArrayEquals(new String[]{"Spacer", "30", "Stockholm"}, rows.get(1));
        assertArrayEquals(new String[]{"Jerry", "25", "Oslo"}, rows.get(2));
    }

    @Test
    @DisplayName("Should Not Ignore whitespaceInTokens")
    void shouldNotIgnoreWhiteSpaceInTokens() throws IOException {
        String csvData = "Mr Anderson,30,New York";
        CsvReader reader = CsvReader.builder()
                .build()
                .withSource(csvData);

        List<String[]> rows = reader.readAll();
        assertArrayEquals(new String[]{"Mr Anderson", "30", "New York"}, rows.getFirst());
    }

    @Test
    @DisplayName("ShouldTrimWhitespaceAroundTokens")
    void shouldTrimWhitespaceAroundTokens() throws IOException {
        String csvData = "name,age,city\nSteve ,55 , London \nJanet, 25, Los Angeles";
        CsvReader reader = CsvReader.builder()
                .trimTokens(true)
                .build()
                .withSource(csvData);

        List<String[]> rows = reader.readAll();
        assertEquals(3, rows.size());
        assertArrayEquals(new String[]{"name", "age", "city"}, rows.get(0));
        assertArrayEquals(new String[]{"Steve", "55", "London"}, rows.get(1));
        assertArrayEquals(new String[]{"Janet", "25", "Los Angeles"}, rows.get(2));
    }

    @Test
    @DisplayName("Read next should work on not empty lines")
    void readNextShouldWorkOnNotEmptyLines() throws IOException {
        String csvData = "\nAlice,30,Stockholm\n\nBob,25,Gothenburg\n";
        CsvReader reader = CsvReader.builder()
                .build()
                .withSource(csvData);

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
    @DisplayName("Custom delimiter should work as input")
    void customDelimiterShouldWorkAsInput() throws IOException {
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

    @Test
    @DisplayName("readAll with quote handling")
    void readAll_withQuotes() throws IOException {

        // Arrange
        String csvContent = """
                Hello,"Hello, World!"
                Good bye,"have a nice ""flight""!
                """;
        CsvReader classUnderTest = CsvReader.builder().withQuoteChar('"').build();

        // Act
        List<String[]> actual = classUnderTest.withSource(csvContent).readAll();

        // Assert
        assertThat(actual).containsExactly(
                new String[]{"Hello", "Hello, World!"},
                new String[]{"Good bye", "have a nice \"flight\"!"}
        );
    }

    @Test
    @DisplayName("Custom header should be set")
    void customHeaderShouldBeSet() throws IOException {
        String csvData = "Thing,30,3\nStuff,25,4";
        CsvReader reader = CsvReader.builder()
                .hasHeader(true)
                .build()
                .withSource(csvData);

        reader.setCustomHeaders(new String[]{"Title", "Price", "Amount"});

        Map<String, String> firstRow = reader.readNextAsMap();
        assertEquals("Thing", firstRow.get("Title"));
        assertEquals("30", firstRow.get("Price"));
        assertEquals("3", firstRow.get("Amount"));

        Map<String, String> secondRow = reader.readNextAsMap();
        assertEquals("Stuff", secondRow.get("Title"));
        assertEquals("25", secondRow.get("Price"));
        assertEquals("4", secondRow.get("Amount"));
    }

    @Test
    void testStreamWithValidSource() {
        String csvContent = "name,age\nAlice,30\nBob,25";

        CsvReader reader = CsvReader.builder()
                .withDelimiter(',')
                .hasHeader(true)
                .build();

        reader.withSource(new ByteArrayInputStream(csvContent.getBytes()), "UTF-8");
        List<String[]> rows = reader.stream()
                .toList();

        assertEquals(2, rows.size(), "Should read 2 rows");
        assertArrayEquals(new String[]{"Alice", "30"}, rows.get(0), "First row should be 'Alice, 30'");
        assertArrayEquals(new String[]{"Bob", "25"}, rows.get(1), "Second row should be 'Bob, 25'");
    }

    @ParameterizedTest(name = "Return CSV as stream, with header: {1}")
    @MethodSource("provideArgumentsFor_stream")
    void stream(String csvContent, boolean hasHeader) {

        // Arrange
        CsvReader classUnderTest = CsvReader.builder()
                .trimTokens(true)
                .hasHeader(hasHeader)
                .withDelimiter(',')
                .withQuoteChar('"')
                .skipEmptyLines(true)
                .build();

        // Act
        Stream<String[]> actual = classUnderTest.withSource(csvContent).stream();

        // Assert
        assertThat(actual.toList())
                .containsExactly(
                        new String[]{"Steve Peters", "55", "Rome"},
                        new String[]{"Cathy \"Kitty\" Carpenter", "31", "New York"}
                );
    }

    private static Stream<Arguments> provideArgumentsFor_stream() {
        return Stream.of(
                Arguments.of("""
                        name,age,city
                        Steve Peters, 55, Rome
                        
                        "Cathy ""Kitty"" Carpenter",31,New York
                        """, true),
                Arguments.of("""
                        Steve Peters, 55, Rome
                        
                        "Cathy ""Kitty"" Carpenter",31,New York
                        """, false)
        );
    }

    @Test
    @DisplayName("Throw exception if BufferedReader is null when calling stream")
    void stream_whenBufferedReaderIsNull_throwException() {

        // Arrange
        CsvReader classUnderTest = CsvReader.builder().build();

        // Act
        Throwable actual = catchThrowable(classUnderTest::stream);

        // Assert
        assertThat(actual)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("withSource(..) must be called before stream()");
    }

    public static class Person {
        public String name;
        public int age;
        public double weight;
        public boolean married;

        public Person(String name, int age, double weight, boolean married) {
            this.name = name;
            this.age = age;
            this.weight = weight;
            this.married = married;
        }
    }

    @Test
    void readALl_customObjects() {

        // Arrange
        String csvContent = """
                "Cathy ""Kitty"" Carpenter",31,65.5,false
                Steve Peters,55,85.7,true
                """;

        Function<String[], Person> mapper = values ->
                new Person(
                        values[0],
                        Integer.parseInt(values[1]),
                        Double.parseDouble(values[2]),
                        Boolean.parseBoolean(values[3]));

        CsvReader classUnderTest = CsvReader.builder().build();

        // Act
        List<Person> actual = classUnderTest.withSource(csvContent).readAll(mapper);

        // Assert
        assertThat(actual).hasSize(2)
                .extracting("name", "age", "weight", "married")
                .containsExactly(
                        tuple("Cathy \"Kitty\" Carpenter",31,65.5,false),
                        tuple("Steve Peters", 55, 85.7,true)
                );
    }

    @Test
    @DisplayName("AutoClosable.close closes and sets resource to null")
    void close() throws IOException {

        // Arrange
        CsvReader classUnderTest = CsvReader.builder().build().withSource("a,b,c\n1,2,3");

        // Act
        classUnderTest.close();

        // Assert
        assertThatThrownBy(classUnderTest::readAll)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("withSource(..) must be called before readAll()");
    }

    @Test
    @DisplayName("AutoClosable works when used with try-with-resources")
    void close_tryWithResources() throws IOException {

        // arrange
        CsvReader classUnderTest;
        try (CsvReader tempReader = CsvReader.builder().build().withSource("a,b,c\n1,2,3")) {
            classUnderTest = tempReader;
        }

        // Act
        Throwable actual = catchThrowable(classUnderTest::readAll);

        // Assert
        assertThat(actual)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("withSource(..) must be called before readAll()");
    }

    @Test
    @DisplayName("Calling close multiple times should not throw an exception")
    void shouldNotThrowExceptionIfClosedTwice() throws Exception {

        // Arrange
        CsvReader classUnderTest = CsvReader.builder().build().withSource("a,b,c\n1,2,3");

        // Act
        classUnderTest.close();

        // Assert
        assertThatCode(classUnderTest::close).doesNotThrowAnyException();
    }
}
