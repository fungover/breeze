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
        assertTrue(StringUtils.containsIgnoreCase("", "")); // Both strings are empty
        assertFalse(StringUtils.containsIgnoreCase("Hello World", "")); // Non-empty string should not contain empty string
        assertFalse(StringUtils.containsIgnoreCase("", "world")); // Empty string cannot contain non-empty string
    }

    @Test
    public void testSubstringBefore_Basic() {
        assertEquals("Hello", StringUtils.substringBefore("Hello World", " "));
        assertEquals("Hello", StringUtils.substringBefore("Hello,World", ","));
        assertEquals("Hello", StringUtils.substringBefore("HelloXYZWorld", "XYZ"));
    }

    @Test
    public void testSubstringBefore_DelimiterNotFound() {
        assertEquals("Hello World", StringUtils.substringBefore("Hello World", "XYZ"));
    }

    @Test
    public void testSubstringBefore_NullInput() {
        assertNull(StringUtils.substringBefore(null, " "));
        assertEquals("Hello World", StringUtils.substringBefore("Hello World", null));
        assertNull(StringUtils.substringBefore(null, null));
    }

    @Test
    public void testSubstringBefore_EmptyStrings() {
        assertEquals("", StringUtils.substringBefore("", " "));
        assertEquals("Hello World", StringUtils.substringBefore("Hello World", ""));
    }

    // Tests for substringAfter
    @Test
    public void testSubstringAfter_Basic() {
        assertEquals("World", StringUtils.substringAfter("Hello World", " "));
        assertEquals("World", StringUtils.substringAfter("Hello,World", ","));
        assertEquals("World", StringUtils.substringAfter("HelloXYZWorld", "XYZ"));
    }

    @Test
    public void testSubstringAfter_DelimiterNotFound() {
        assertEquals("Hello World", StringUtils.substringAfter("Hello World", "XYZ"));
    }

    @Test
    public void testSubstringAfter_NullInput() {
        assertNull(StringUtils.substringAfter(null, " "));
        assertEquals("Hello World", StringUtils.substringAfter("Hello World", null));
        assertNull(StringUtils.substringAfter(null, null));
    }

    @Test
    public void testSubstringAfter_EmptyStrings() {
        assertEquals("", StringUtils.substringAfter("", " "));
        assertEquals("Hello World", StringUtils.substringAfter("Hello World", ""));
    }
}



