package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedactedTest {

    @Test
    void getValue_should_return_actual_value() {
        Redacted valueToBeSaved = Redacted.make("Secret");
        assertEquals("Secret",valueToBeSaved.getValue());
    }

    @Test
    void two_redacted_should_be_separate_instances(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        Redacted secondValueToBeSaved = Redacted.make("Password");
        assertEquals("Secret",valueToBeSaved.getValue());
        assertEquals("Password",secondValueToBeSaved.getValue());
    }

    @Test
    void make_should_not_accept_null() {
        assertThrows(IllegalArgumentException.class, () -> Redacted.make(null));
    }

    @Test
    void make_should_accept_empty_string() {
        Redacted empty = Redacted.make("");
        assertEquals("", empty.getValue());
    }
}