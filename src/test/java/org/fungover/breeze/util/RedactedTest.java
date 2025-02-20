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

    @Test
    void make_should_handle_special_characters() {
        Redacted special = Redacted.make("!@#$%^&*()");
        assertEquals("!@#$%^&*()", special.getValue());
    }

    @Test
    void make_should_handle_very_long_input() {
        String longInput = "x".repeat(10000);
        Redacted valueToBeSaved = Redacted.make(longInput);
        assertEquals(longInput, valueToBeSaved.getValue());
    }

    @Test
    void wipe_should_prevent_further_access(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        valueToBeSaved.wipe();
        assertEquals("<wiped>", valueToBeSaved.getValue());
    }

    @Test
    void toString_should_be_Redacted(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        assertEquals("<redacted>",valueToBeSaved.toString());
    }

    @Test
    void toString_wiped_should_return_wiped(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        valueToBeSaved.wipe();
        assertEquals("<wiped>",valueToBeSaved.toString());
    }
}