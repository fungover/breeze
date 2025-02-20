package org.fungover.breeze.util.size;

import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

class SizeFormatterTest {

    @Test
    void autoFormatUsesCorrectUnits() {
        assertEquals("1.46 KiB", SizeFormatter.autoFormat(1500, true, 2));
        assertEquals("1.50 KB", SizeFormatter.autoFormat(1500, false, 2));
        assertEquals("2.00 MB", SizeFormatter.autoFormat(2_000_000, false, 2));
    }

    @Test
    void convertBetweenUnits() {
        assertEquals(1.5, SizeFormatter.convert(1_500_000_000L, SizeUnit.BYTES, SizeUnit.GIGABYTES), 0.001);
        assertEquals(1024, SizeFormatter.convert(1, SizeUnit.KIBIBYTES, SizeUnit.BYTES), 0.001);
    }

    @Test
    void parseValidFormats() {
        assertEquals(1_500_000_000L, SizeFormatter.parse("1.5GB"));
        assertEquals(1024, SizeFormatter.parse("1KiB"));
        assertThrows(IllegalArgumentException.class, () -> SizeFormatter.parse("invalid"));
    }

    @Test
    void formatNetworkRates() {
        assertEquals("1.00 Mbps", SizeFormatter.formatRate(1_000_000, TimeUnit.SECONDS, 2));
        assertEquals("8.00 Kbps", SizeFormatter.formatRate(8_000, TimeUnit.SECONDS, 2));
        assertEquals("1.50 Gbps", SizeFormatter.formatRate(1_500_000_000, TimeUnit.SECONDS, 2));
    }

    @Test
    void handleEdgeCases() {
        // Test max value overflow
        assertThrows(ArithmeticException.class, () ->
                SizeFormatter.parse("9223372036854775808B"));

        // Test negative value formatting
        assertEquals("-1.46 KiB", SizeFormatter.autoFormat(-1500, true, 2));

        // Test max valid value
        assertDoesNotThrow(() ->
                SizeFormatter.parse("9223372036854775807B"));
    }

    @Test
    void formatRate_withHighPrecision_returnsThreeDecimalPlaces() {
        assertEquals("1.500 Gbps",
                SizeFormatter.formatRate(1_500_000_000, TimeUnit.SECONDS, 3));
    }

    @Test
    void parseInvalidNumbers_throwsProperExceptions() {
        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.parse("1.2.3GB"));

        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.parse("123.GB"));

        assertThrows(IllegalArgumentException.class, () ->
                SizeFormatter.parse("12A3MB"));
    }

    @Test
    void parseValidEdgeCases_returnsCorrectValues() {
        // Test leading decimal
        assertDoesNotThrow(() -> SizeFormatter.parse(".5KB"));

        // Test decimal vs binary units
        assertEquals(-1500, SizeFormatter.parse("-1.5KB"));  // Decimal base
        assertEquals(-1536, SizeFormatter.parse("-1.5KiB")); // Binary base
    }
}