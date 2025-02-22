package org.fungover.breeze.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 *
 */
public class CsvReader {

    private final char delimiter;
    private final boolean skipEmptyLines;
    private final boolean hasHeader;
    private final char quoteChar;
    private final boolean trimTokens;

    private BufferedReader bufferedReader;
    private String[] headers;

    private CsvReader(Builder builder) {
        this.delimiter = builder.delimiter;
        this.skipEmptyLines = builder.skipEmptyLines;
        this.hasHeader = builder.hasHeader;
        this.quoteChar = builder.quoteChar;
        this.trimTokens = builder.trimTokens;
    }

    /**
     * Creates a new builder instance for configuring and constructing a CsvReader.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for CsvReader.
     */
    public static class Builder {
        private char delimiter = ','; // Default value
        private boolean skipEmptyLines = true; // Default value
        private boolean hasHeader = false;
        private char quoteChar = '\"';
        private boolean trimTokens = false;

        private Builder() {
            // private constructor
        }

        /**
         * Sets the delimiter character for parsing CSV data.
         *
         * @param delimiter the delimiter character
         * @return this builder instance
         */
        public Builder withDelimiter(char delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        /**
         * Specifies whether empty lines should be skipped.
         *
         * @param skipEmptyLines true to skip empty lines, false otherwise
         * @return this builder instance
         */
        public Builder skipEmptyLines(boolean skipEmptyLines) {
            this.skipEmptyLines = skipEmptyLines;
            return this;
        }

        /**
         * Sets the quote character used for enclosing values.
         *
         * @param quoteChar the quote character
         * @return this builder instance
         */
        public Builder withQuoteChar(char quoteChar) {
            this.quoteChar = quoteChar;
            return this;
        }

        /**
         * Specifies whether the csv file contains a header row.
         *
         * @param hasHeader true if the CSV contains a header row, false otherwise
         * @return this builder instance
         */
        public Builder hasHeader(boolean hasHeader) {
            this.hasHeader = hasHeader;
            return this;
        }

        /**
         * Specifies whether tokens should be trimmed or not.
         *
         * @param trimTokens true if tokens should be trimmed, false otherwise
         * @return this builder instance
         */
        public Builder trimTokens(boolean trimTokens) {
            this.trimTokens = trimTokens;
            return this;
        }

        /**
         * Builds a new CsvReader instance with the config settings
         *
         * @return a new {@link CsvReader} instance
         */
        public CsvReader build() {
            return new CsvReader(this);
        }
    }

    /**
     * Sets the CSV source from a string.
     *
     * @param csvSource the CSV content as a string
     * @return this {@link CsvReader} instance
     */

    public CsvReader withSource(String csvSource) {
        bufferedReader = new BufferedReader(new StringReader(csvSource));
        return this;
    }

    /**
     * Sets the CSV source from an input stream.
     *
     * @param csvSource   the input stream containing CSV data
     * @param charsetName the character set of the input stream
     * @return this {@link CsvReader} instance
     */
    public CsvReader withSource(InputStream csvSource, String charsetName) {
        bufferedReader = new BufferedReader(new InputStreamReader(csvSource, Charset.forName(charsetName)));
        return this;
    }

    /**
     * Sets the CSV source from a file.
     *
     * @param csvSource   the CSV file
     * @param charsetName the character set of the file
     * @return this {@link CsvReader} instance
     * @throws FileNotFoundException if the file is not found
     */
    public CsvReader withSource(File csvSource, String charsetName) throws FileNotFoundException {
        bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvSource), Charset.forName(charsetName)));
        return this;
    }

    /**
     * Reads all rows from the CSV source.
     *
     * @return a list of string arrays, where each array represents a row
     * @throws IOException if an I/O error occurs
     */
    public List<String[]> readAll() throws IOException {

        if (bufferedReader == null) {
            throw new IllegalStateException("withSource(..) must be called before readAll()");
        }

        List<String[]> rows = new ArrayList<>();
//TODO: skip headerline
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            if (skipEmptyLines && line.trim().isEmpty()) { // Skip empty line
                continue;
            }
            List<String> parsed = parseLine(line);
            // Convert line to array and add to rows
            rows.add(parsed.toArray(String[]::new));
        }
        return rows;
    }

    public Stream<String[]> stream() {

        if (bufferedReader == null) {
            throw new IllegalStateException("withSource(..) must be called before stream()");
        }

        AtomicBoolean skipped = new AtomicBoolean(!hasHeader);

        return bufferedReader.lines()
                .filter(_ -> skipped.getAndSet(true)) // skips first element if hasHeader == true
                .filter(this::handleEmptyLines)
                .map(this::parseLine)
                .map(list -> list.toArray(String[]::new));
    }

    /**
     * This predicate checks to see whether a line should be allowed to pass the filter or not,
     * by looking at the configuration from the user (skipEmptyLine) and the line itself.
     *
     * @param line the line to test
     * @return true of the line should be allowed to pass, false otherwise
     */
    private boolean handleEmptyLines(String line) {
        return !skipEmptyLines || !line.trim().isEmpty();
    }

    private List<String> parseLine(String line) {

        List<String> tokens = new ArrayList<>();
        if (line == null) {
            return tokens;
        }

        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        int length = line.length();
        for (int i = 0; i < length; i++) {
            char currentChar = line.charAt(i);

            if (currentChar == quoteChar) {
                if (inQuotesAndNextCharIsAlsoAQuote(line, inQuotes, i, length)) {
                    // The csv contains an escaped quoteChar, i.e. double quotChars inside a quote
                    // E.g. "Some text with ""a quote"" inside the quoted value". Ad a single quoteChar
                    sb.append(quoteChar);
                    i++; // Skip the second quote char
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (isTrueDelimiter(currentChar, inQuotes)) {
                addTokenToCollection(sb, tokens);
            } else {
                sb.append(currentChar);
            }
        }
        addTokenToCollection(sb, tokens);
        return tokens;
    }

    private void addTokenToCollection(StringBuilder sb, List<String> tokens) {
        String token = sb.toString();
        if (trimTokens) {
            token = token.trim();
        }
        tokens.add(token);
        sb.setLength(0);
    }

    private boolean isTrueDelimiter(char currentChar, boolean inQuotes) {
        return currentChar == delimiter && !inQuotes;
    }

    /**
     * Reads the next row from the CSV source.
     *
     * @return an array of strings representing the next row, or an empty array if the end is reached
     * @throws IOException if an I/O error occurs
     */
    public String[] readNext() throws IOException {
        String line = bufferedReader.readLine();
        if (line == null) {
            return new String[0];
        }
        if (skipEmptyLines && line.trim().isEmpty()) {
            return readNext();
        }
        return parseLine(line).toArray(new String[0]);
    }

    /**
     * Allows the user make a customed header
     *
     * @param customHeaders the customed header the user can choose
     */
    public void setCustomHeaders(String[] customHeaders) {
        this.headers = customHeaders;
    }

    /**
     * Reads the next row from the CSV source as a map.
     *
     * @return a map where keys are header values and values are row values, or an empty map if the end is reached
     * @throws IOException if an I/O error occurs
     */
    public Map<String, String> readNextAsMap() throws IOException {
        if (headers == null && hasHeader) {
            String headerLine = bufferedReader.readLine();
            if (headerLine == null) {
                return Collections.emptyMap();
            }
            headers = parseLine(headerLine).toArray(new String[0]);
        }

        String line = bufferedReader.readLine();
        if (line == null) {
            return Collections.emptyMap();
        }

        if (skipEmptyLines && line.trim().isEmpty()) {
            return readNextAsMap();
        }

        List<String> values = parseLine(line);
        Map<String, String> rowMap = new HashMap<>();

        for (int i = 0; i < headers.length && i < values.size(); i++) {
            rowMap.put(headers[i], values.get(i));
        }

        return rowMap;
    }

    private boolean inQuotesAndNextCharIsAlsoAQuote(String line, boolean inQuotes, int i, int len) {
        return inQuotes && i + 1 < len && line.charAt(i + 1) == quoteChar;
    }

}
