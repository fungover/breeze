package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {

    @Test
    void capitalize_shouldCapitalizeFistLetter() {
        assertThat(Strings.capitalize("hello")).isEqualTo("Hello");
        assertThat(Strings.capitalize("java")).isEqualTo("Java");
        assertThat(Strings.capitalize("")).isEqualTo("");
        assertThat(Strings.capitalize(null)).isNull();
    }

    @Test
    void countOccurences_shouldCountSubstringOccurences() {
        assertThat(Strings.countOccurrences("banana", "a")).isEqualTo(3);
        assertThat(Strings.countOccurrences("hello world", "l")).isEqualTo(3);
        assertThat(Strings.countOccurrences("test", "z")).isEqualTo(0);
        assertThat(Strings.countOccurrences(null, "a")).isEqualTo(0);
        assertThat(Strings.countOccurrences("hello", null)).isEqualTo(0);
        assertThat(Strings.countOccurrences("hello", "")).isEqualTo(0);
    }

    @Test
    void toSnakeCase_shouldConvertToSnakeCase() {
        assertThat(Strings.toSnakeCase("HelloWorld")).isEqualTo("hello_world");
        assertThat(Strings.toSnakeCase("snakeCaseTest")).isEqualTo("snake_case_test");
        assertThat(Strings.toSnakeCase("already_snake_case")).isEqualTo("already_snake_case");
        assertThat(Strings.toSnakeCase("")).isEqualTo("");
        assertThat(Strings.toSnakeCase(null)).isNull();
    }
}
