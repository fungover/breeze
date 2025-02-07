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
}
