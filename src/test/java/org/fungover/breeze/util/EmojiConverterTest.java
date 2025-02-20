package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmojiConverterTest {

    @Test
    void testIsAscii() {
        assertTrue(EmojiConverter.isAscii("\\( ﾟヮﾟ)/"));
        assertFalse(EmojiConverter.isAscii("(shrug)"));
    }

    @Test
    void testIsUtf() {
        assertTrue(EmojiConverter.isUtf("(shrug)"));
        assertFalse(EmojiConverter.isUtf("¯\\_(ツ)_/¯"));
    }

    @Test
    void testToAscii() {
        assertEquals("¯\\_(ツ)_/¯", EmojiConverter.toAscii("(shrug)"));
        assertEquals("\\( ﾟヮﾟ)/", EmojiConverter.toAscii("(yay)"));
        assertEquals("unknown", EmojiConverter.toAscii("unknown"));
    }

    @Test
    void testToUtf() {
        assertEquals("(shrug)", EmojiConverter.toUtf("¯\\_(ツ)_/¯"));
        assertEquals("(yay)", EmojiConverter.toUtf("\\( ﾟヮﾟ)/"));
        assertEquals("unknown", EmojiConverter.toUtf("unknown"));
    }

    @Test
    void testNullAndEmptyHandling() {
        assertFalse(EmojiConverter.isAscii(null));
        assertFalse(EmojiConverter.isUtf(null));
        assertEquals("", EmojiConverter.toAscii(""));
        assertEquals("", EmojiConverter.toUtf(""));
        assertNull(null, EmojiConverter.toAscii(null));
        assertNull(null, EmojiConverter.toUtf(null));
    }
}

