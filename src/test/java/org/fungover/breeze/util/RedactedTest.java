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
    void two_Redacted_Should_Be_Separate_Instences(){
        Redacted valueToBeSaved = Redacted.make("Secret");
        Redacted secondValueToBeSaved = Redacted.make("Password");
        assertEquals("Secret",valueToBeSaved.getValue());
        assertEquals("Password",secondValueToBeSaved.getValue());
    }

}