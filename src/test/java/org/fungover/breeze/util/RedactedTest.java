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
    void wipe_should_prevent_further_access(){
        Redacted valeToBeSaved = Redacted.make("Secret");
        valeToBeSaved.wipe();
        assertThrows(IllegalStateException.class, valeToBeSaved::getValue);
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

    @Test
    void length_should_be_zero(){
        Redacted valeToBeSaved = Redacted.make("Secret");
        assertEquals(0,valeToBeSaved.length());
    }

    @Test
    void charAt_should_be_zero(){
        Redacted valeToBeSaved = Redacted.make("Secret");
        assertEquals(0,valeToBeSaved.charAt(10));
    }

    @Test
    void subSequence_wiped_should_return_wiped(){
        Redacted valeToBeSaved = Redacted.make("Secret");
        valeToBeSaved.wipe();
        assertEquals("<wiped>",valeToBeSaved.subSequence(0,10));
    }

    @Test
    void subSequence_should_be_Redacted(){
        Redacted valeToBeSaved = Redacted.make("Secret");
        assertEquals("<redacted>",valeToBeSaved.subSequence(0,10));
    }

}