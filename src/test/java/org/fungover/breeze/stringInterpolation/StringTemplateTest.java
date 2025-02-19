package org.fungover.breeze.stringInterpolation;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StringTemplateTest {
    @Test
    void testApplyFormatWithoutFormat() {
        String result = StringTemplate.applyFormat("Test", null);
        assertEquals("Test", result);

    }
    @Test
    void testApplyFormatWithNumericFormat() {
        String result = StringTemplate.applyFormat(123.456, "%.2f");
        assertEquals("123.46", result);
    }
    @Test
    void testApplyFormatWithDateTimeFormat(){
        LocalDate date = LocalDate.of(2025, 2, 19);
        String result = StringTemplate.applyFormat(date, "yyyy-MM-dd");
        assertEquals("2025-02-19", result);
    }

}
