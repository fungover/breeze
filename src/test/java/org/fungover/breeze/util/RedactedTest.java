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
    void redacted_can_not_be_null() {
        assertThrows(IllegalArgumentException.class, () -> Redacted.make(null));
    }

    @Test
    void make_should_accept_empty_string() {
        Redacted empty = Redacted.make("");
        assertEquals("", empty.getValue());
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

    @Test
    void length_should_be_zero(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        assertEquals(0,valueToBeSaved.length());
    }

    @Test
    void charAt_should_be_zero(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        assertEquals(0,valueToBeSaved.charAt(10));
    }

    @Test
    void subSequence_wiped_should_return_wiped(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        valueToBeSaved.wipe();
        assertEquals("<wiped>",valueToBeSaved.subSequence(0,10));
    }

    @Test
    void subSequence_should_be_Redacted(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        assertEquals("<redacted>",valueToBeSaved.subSequence(0,10));
    }

    @Test
    void equals_should_be_true(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        Redacted secondValueToBeSaved = Redacted.make("Secret");
        assertEquals(valueToBeSaved,secondValueToBeSaved);
    }

    @Test
    void equals_should_not_be_true(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        Redacted secondValueToBeSaved = Redacted.make("Secret");
        secondValueToBeSaved.wipe();
        assertNotEquals(valueToBeSaved,secondValueToBeSaved);
    }

    @Test
    void hashcode_should_be_true(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        Redacted secondValueToBeSaved = Redacted.make("Secret");
        assertEquals(valueToBeSaved.hashCode(),secondValueToBeSaved.hashCode());
    }

    @Test
    void hashcode_should_not_be_true(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        Redacted secondValueToBeSaved = Redacted.make("Secret");
        secondValueToBeSaved.wipe();
        assertNotEquals(valueToBeSaved.hashCode(),secondValueToBeSaved.hashCode());
    }


}