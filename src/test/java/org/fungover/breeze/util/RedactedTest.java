package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedactedTest {


    @Test
    void getValue_Should_Return_Actual_Value() {
        Redacted ValueToBeSaved = Redacted.make("Secret");
        assertEquals("Secret",ValueToBeSaved.getValue());
    }

}