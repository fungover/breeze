package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RedactedTest {

    @Test
    void getValue_Should_Return_Actual_Value() {
        Redacted valueToBeSaved = Redacted.make("Secret");
        assertEquals("Secret",valueToBeSaved.getValue());
    }

    @Test
    void two_Redacted_Should_Be_Separate_Instances(){
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
    void wipe_should_prevent_further_access(){
        Redacted valeToBeSaved = Redacted.make("Secret");
        valeToBeSaved.wipe();
        assertThrows(IllegalStateException.class, () -> valeToBeSaved.getValue());
    }

    @Test
    void toString_should_be_Redacted(){
        Redacted valeToBeSaved = Redacted.make("Secret");
        assertEquals("<redacted>",valeToBeSaved.toString());
    }

    @Test
    void toString_wiped_should_return_wiped(){
        Redacted valeToBeSaved = Redacted.make("Secret");
        valeToBeSaved.wipe();
        assertEquals("<wiped>",valeToBeSaved.toString());
    }
}