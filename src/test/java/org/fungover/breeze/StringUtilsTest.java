package org.fungover.breeze;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void testIsNullOrEmpty() {
        assertTrue(StringUtils.isNullOrEmpty(null));
        assertTrue(StringUtils.isNullOrEmpty(""));
        assertFalse(StringUtils.isNullOrEmpty("Hello"));
    }

    @Test
    void testIsNullOrBlank(){
        assertTrue(StringUtils.isNullOrBlank(null));
        assertTrue(StringUtils.isNullOrBlank(""));
        assertTrue(StringUtils.isNullOrBlank("    "));
        assertFalse(StringUtils.isNullOrBlank("Hello"));
    }

    @Test
    void testTruncate() {
        assertEquals("He...", StringUtils.truncate("HelloWorld", 5, "..."));
        assertEquals("Hi", StringUtils.truncate("Hi", 5, "...")); // Ingen ändring
        assertEquals("", StringUtils.truncate("", 5, "...")); // Tom sträng returneras som den är
    }

    @Test
    void testPadLeft() {
        assertEquals("  hej", StringUtils.padLeft("hej", 5, ' '));
        assertEquals("hej", StringUtils.padLeft("hej", 3, ' ')); // Ingen padding behövs
    }

    @Test
    void testPadRight() {
        assertEquals("hej  ", StringUtils.padRight("hej", 5, ' '));
        assertEquals("hej", StringUtils.padRight("hej", 3, ' ')); // Ingen padding behövs
    }

    @Test
    void testReverse() {
        assertEquals("olleH", StringUtils.reverse("Hello"));
        assertEquals("", StringUtils.reverse(""));
        assertEquals("a", StringUtils.reverse("a"));
    }

    @Test
    void testSplitToList() {
        assertEquals(List.of("äpple", "banan", "kiwi"), StringUtils.splitToList("äpple,banan,kiwi", ","));
        assertEquals(List.of(), StringUtils.splitToList(null, ","));
    }




    @Test
    public void testContainsIgnoreCase_Basic() {
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "world"));
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "HELLO"));
        assertTrue(StringUtils.containsIgnoreCase("Hello World", "o W"));
    }

    @Test
    public void testContainsIgnoreCase_NotFound() {
        assertFalse(StringUtils.containsIgnoreCase("Hello World", "earth")); // Correct
        assertFalse(StringUtils.containsIgnoreCase("Hello World", "XYZ"));  // Correct
    }

    @Test
    public void testContainsIgnoreCase_NullInput() {
        assertFalse(StringUtils.containsIgnoreCase(null, "world"));
        assertFalse(StringUtils.containsIgnoreCase("Hello World", null));
        assertFalse(StringUtils.containsIgnoreCase(null, null));
    }

    @Test
    public void testContainsIgnoreCase_EmptyStrings() {
        assertTrue(StringUtils.containsIgnoreCase("", ""));
        assertFalse(StringUtils.containsIgnoreCase("Hello World", ""));
        assertFalse(StringUtils.containsIgnoreCase("", "world"));
    }




}

