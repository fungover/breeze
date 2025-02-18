package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedactedTest {


    @Test
    void redacted_Is_Able_to_Save_A_value () {
        Redacted redacted = Redacted.make("Secret");
        assertEquals("Secret",redacted.getValue());
    }

}