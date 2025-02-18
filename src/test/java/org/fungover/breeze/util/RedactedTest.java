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

}