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
import java.util.List;

public class CsvReader {

    private final char delimiter;
    private final boolean skipEmptyLines;

    private BufferedReader bufferedReader;

    private CsvReader(Builder builder) {
        this.delimiter = builder.delimiter;
        this.skipEmptyLines = builder.skipEmptyLines;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private char delimiter = ','; // Default value
        private boolean skipEmptyLines = true; // Default value

        private Builder() {
            // private constructor
        }

        public Builder withDelimiter(char delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        public Builder skipEmptyLines(boolean skipEmptyLines) {
            this.skipEmptyLines = skipEmptyLines;
            return this;
        }

        public CsvReader build() {
            return new CsvReader(this);
        }
    }

    public CsvReader withSource(String csvSource) {
        bufferedReader = new BufferedReader(new StringReader(csvSource));
        return this;
    }

    public CsvReader withSource(InputStream csvSource, String charsetName) {
        bufferedReader = new BufferedReader(new InputStreamReader(csvSource, Charset.forName(charsetName)));
        return this;
    }

    public CsvReader withSource(File csvSource, String charsetName) throws FileNotFoundException {
        bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvSource), Charset.forName(charsetName)));
        return this;
    }

    public List<String[]> readAll() throws IOException {

        // TODO: check that bufferedReader is not null

        List<String[]> rows = new ArrayList<>();

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            if (skipEmptyLines && line.trim().isEmpty()) { // Skip empty line
                continue;
            }
            List<String> parsed = parseLine(line);
            // Convert line to array and add to rows
            // (use parsed.size() instead of 0 to avoid creating two arrays each time):
            rows.add(parsed.toArray(new String[parsed.size()]));
        }
        return rows;
    }

    private List<String> parseLine(String line) {

        List<String> tokens = new ArrayList<>();
        if (line == null) {
            return tokens;
        }

        StringBuilder sb = new StringBuilder();
        int length = line.length();
        for (int i = 0; i < length; i++) {
            char c = line.charAt(i);
            if (c == delimiter) {
                String token = sb.toString();
                tokens.add(token);
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        String token = sb.toString();
        tokens.add(token);
        return tokens;
    }

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

}
