package org.fungover.breeze.csv;

public class CsvReader {

    private final char delimiter;

    private CsvReader(Builder builder) {
        this.delimiter = builder.delimiter;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private char delimiter = ','; // Default value

        private Builder() {
           // private constructor
        }

        public Builder withDelimiter(char delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        public CsvReader build() {
            return new CsvReader(this);
        }
    }
}
