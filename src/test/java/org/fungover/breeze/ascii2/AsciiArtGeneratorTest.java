package org.fungover.breeze.ascii2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AsciiArtGeneratorTest {

    @Test
    void testStandardFontHello() {
        AsciiArtGenerator gen = new AsciiArtGenerator(new StandardFont());
        String output = gen.generate("HELLO");
        assertTrue(output.contains("H  H"));
    }

    @Test
    void testBlockFontHello() {
        AsciiArtGenerator gen = new AsciiArtGenerator(new BlockFont());
        String output = gen.generate("HELLO");
        assertTrue(output.contains("██  ██"));
    }

    @Test
    void testNullInputThrowsException() {
        AsciiArtGenerator gen = new AsciiArtGenerator(new StandardFont());
        assertThrows(IllegalArgumentException.class, () -> gen.generate(null));
    }

    @Test
    void testEmptyInputThrowsException() {
        AsciiArtGenerator gen = new AsciiArtGenerator(new StandardFont());
        assertThrows(IllegalArgumentException.class, () -> gen.generate(""));
    }

    @Test
    void testTooLongInputThrowsException() {
        AsciiArtGenerator gen = new AsciiArtGenerator(new StandardFont());
        String longText = "A".repeat(60);
        assertThrows(IllegalArgumentException.class, () -> gen.generate(longText));
    }
}
