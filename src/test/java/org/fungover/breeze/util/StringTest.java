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
        assertThat(Strings.toSnakeCase("hello world!")).isEqualTo("hello_world!");
        assertThat(Strings.toSnakeCase("leading and trailing spaces")).isEqualTo("leading_and_trailing_spaces");
        assertThat(Strings.toSnakeCase("")).isEqualTo("");
        assertThat(Strings.toSnakeCase(null)).isNull();
    }

    @Test
    void removeWhitespace_shouldRemoveAllWhitespace() {
        assertThat(Strings.removeWhitespace(" a b c ")).isEqualTo("abc");
        assertThat(Strings.removeWhitespace("hello   world")).isEqualTo("helloworld");
        assertThat(Strings.removeWhitespace("\tspaces \n and newlines")).isEqualTo("spacesandnewlines");
        assertThat(Strings.removeWhitespace(null)).isNull();
        assertThat(Strings.removeWhitespace("")).isEqualTo("");
    }
//
//    @Test
//    void isPalindrome_shouldCheckForPalindrome() {
//        assertThat(Strings.isPalindrome("madam")).isTrue();
//        assertThat(Strings.isPalindrome("racecar")).isTrue();
//        assertThat(Strings.isPalindrome("a")).isTrue();
//        assertThat(Strings.isPalindrome("")).isTrue();
//        assertThat(Strings.isPalindrome(null)).isFalse();
//        assertThat(Strings.isPalindrome("hello")).isFalse();
//        assertThat(Strings.isPalindrome("Madam")).isFalse();
//    }

}
