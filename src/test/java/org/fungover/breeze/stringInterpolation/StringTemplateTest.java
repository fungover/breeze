package org.fungover.breeze.stringInterpolation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringTemplateTest {
    @Test
    void testApplyFormatWithoutFormat() {
        String result = StringTemplate.applyFormat("Test", null);
        assertEquals("Test", result);

    }
}
