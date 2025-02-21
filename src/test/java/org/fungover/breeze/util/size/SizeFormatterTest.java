package org.fungover.breeze.util.size;

import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SizeFormatter.
 * This class contains unit tests for the SizeFormatter utility class,
 * covering various scenarios including formatting, conversion, parsing,
 * and edge cases.
 */
class SizeFormatterTest {

    /**
     * Tests that autoFormat uses the correct units for different base settings.
     */
    @Test
    void autoFormatUsesCorrectUnits() {
        assertEquals("1.46 KiB", SizeFormatter.autoFormat(1500, true, 2));
        assertEquals("1.50 KB", SizeFormatter.autoFormat(1500, false, 2));
        assertEquals("2.00 MB", SizeFormatter.autoFormat(2_000_000, false, 2));
    }

    /**
     * Tests the conversion between different size units.
     */
    @Test
    void convertBetweenUnits() {
        assertEquals(1.5, SizeFormatter.convert(1_500_000_000L, SizeUnit.BYTES, SizeUnit.GIGABYTES), 0.001);
        assertEquals(1024, SizeFormatter.convert(1, SizeUnit.KIBIBYTES, SizeUnit.BYTES), 0.001);
    }

    /**
     * Tests parsing of valid size formats and invalid input handling.
     */
    @Test
    void parseValidFormats() {
        assertEquals(1_500_000_000L, SizeFormatter.parse("1.5GB"));
        assertEquals(1024, SizeFormatter.parse("1KiB"));
        assertThrows(IllegalArgumentException.class, () -> SizeFormatter.parse("invalid"));
    }

    /**
     * Tests formatting of network rates with different units.
     */
    @Test
    void formatNetworkRates() {
        assertEquals("1.00 Mbps", SizeFormatter.formatRate(1_000_000, TimeUnit.SECONDS, 2));
        assertEquals("8.00 Kbps", SizeFormatter.formatRate(8_000, TimeUnit.SECONDS, 2));
        assertEquals("1.50 Gbps", SizeFormatter.formatRate(1_500_000_000, TimeUnit.SECONDS, 2));
    }

    /**
     * Tests handling of edge cases in parsing and formatting.
     */
    @Test
    void handleEdgeCases() {
        assertThrows(ArithmeticException.class, () ->
                SizeFormatter.parse("9223372036854775808B")); // Exceeds Long.MAX_VALUE

        assertEquals("-1.46 KiB", SizeFormatter.autoFormat(-1500, true, 2));

        assertDoesNotThrow(() ->
                SizeFormatter.parse("9223372036854775807B")); // Valid large value
    }

    /**
     * Tests the precision of network rate formatting.
     */
    @Test
    void testNetworkRatePrecision() {
        assertEquals("1.500 Gbps",
                SizeFormatter.formatRate(1_500_000_000, TimeUnit.SECONDS, 3));
    }

    /**
     * Tests that invalid number formats throw proper exceptions.
     */
    @Test
    void parseInvalidNumbers_throwsProperExceptions() {
        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.parse("1.2.3GB"));

        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.parse("123.GB"));

        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.parse("12A3MB"));
    }

    /**
     * Tests parsing of valid edge cases and returns correct values.
     */
    @Test
    void parseValidEdgeCases_returnsCorrectValues() {
        assertEquals(500, SizeFormatter.parse(".5KB")); // 0.5 * 1000 = 500 bytes
        assertEquals(-1500, SizeFormatter.parse("-1.5KB")); // -1.5 * 1000 = -1500
        assertEquals(-1536, SizeFormatter.parse("-1.5KiB")); // -1.5 * 1024 = -1536
    }

    /**
     * Tests that convert rejects null units.
     */
    @Test
    void convertRejectsNullUnits() {
        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.convert(1, null, SizeUnit.BYTES));

        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.convert(1, SizeUnit.BYTES, null));
    }

    /**
     * Tests that autoFormat rejects negative decimal places.
     */
    @Test
    void autoFormatRejectsNegativeDecimalPlaces() {
        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.autoFormat(1000, false, -1));
    }

    /**
     * Tests that formatRate rejects null time unit.
     */
    @Test
    void formatRateRejectsNullTimeUnit() {
        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.formatRate(1000, null, 2));
    }
}
