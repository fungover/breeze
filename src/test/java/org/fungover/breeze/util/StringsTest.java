package org.fungover.breeze.util;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StringsTest {

    @Test
    void testIsNullOrEmpty() {
        assertTrue(Strings.isNullOrEmpty(null));
        assertTrue(Strings.isNullOrEmpty(""));
        assertFalse(Strings.isNullOrEmpty("Hello"));
    }

    @Test
    void testIsNullOrBlank(){
        assertTrue(Strings.isNullOrBlank(null));
        assertTrue(Strings.isNullOrBlank(""));
        assertTrue(Strings.isNullOrBlank("    "));
        assertFalse(Strings.isNullOrBlank("Hello"));
    }

    @Test
    void testTruncate() {
        assertEquals("He...", Strings.truncate("HelloWorld", 5, "..."));
        assertEquals("Hi", Strings.truncate("Hi", 5, "...")); // Ingen ändring
        assertEquals("", Strings.truncate("", 5, "...")); // Tom sträng returneras som den är
    }

    @Test
    void testPadLeft() {
        assertEquals("  hej", Strings.padLeft("hej", 5, ' '));
        assertEquals("hej", Strings.padLeft("hej", 3, ' ')); // Ingen padding behövs
    }

    @Test
    void testPadRight() {
        assertEquals("hej  ", Strings.padRight("hej", 5, ' '));
        assertEquals("hej", Strings.padRight("hej", 3, ' ')); // Ingen padding behövs
    }

    @Test
    void testReverse() {
        assertEquals("olleH", Strings.reverse("Hello"));
        assertEquals("", Strings.reverse(""));
        assertEquals("a", Strings.reverse("a"));
    }

    @Test
    void testSplitToList() {
        assertEquals(List.of("äpple", "banan", "kiwi"), Strings.splitToList("äpple,banan,kiwi", ","));
        assertEquals(List.of(), Strings.splitToList(null, ","));
    }




    @Test
    public void testContainsIgnoreCase_Basic() {
        assertTrue(Strings.containsIgnoreCase("Hello World", "world"));
        assertTrue(Strings.containsIgnoreCase("Hello World", "HELLO"));
        assertTrue(Strings.containsIgnoreCase("Hello World", "o W"));
    }

    @Test
    public void testContainsIgnoreCase_NotFound() {
        assertFalse(Strings.containsIgnoreCase("Hello World", "earth")); // Correct
        assertFalse(Strings.containsIgnoreCase("Hello World", "XYZ"));  // Correct
    }

    @Test
    public void testContainsIgnoreCase_NullInput() {
        assertFalse(Strings.containsIgnoreCase(null, "world"));
        assertFalse(Strings.containsIgnoreCase("Hello World", null));
        assertFalse(Strings.containsIgnoreCase(null, null));
    }

    @Test
    public void testContainsIgnoreCase_EmptyStrings() {
        assertTrue(Strings.containsIgnoreCase("", "")); // Both strings are empty
        assertFalse(Strings.containsIgnoreCase("Hello World", "")); // Non-empty string should not contain empty string
        assertFalse(Strings.containsIgnoreCase("", "world")); // Empty string cannot contain non-empty string
    }

    @Test
    public void testSubstringBefore_Basic() {
        assertEquals("Hello", Strings.substringBefore("Hello World", " "));
        assertEquals("Hello", Strings.substringBefore("Hello,World", ","));
        assertEquals("Hello", Strings.substringBefore("HelloXYZWorld", "XYZ"));
    }

    @Test
    public void testSubstringBefore_DelimiterNotFound() {
        assertEquals("Hello World", Strings.substringBefore("Hello World", "XYZ"));
    }

    @Test
    public void testSubstringBefore_NullInput() {
        assertNull(Strings.substringBefore(null, " "));
        assertEquals("Hello World", Strings.substringBefore("Hello World", null));
        assertNull(Strings.substringBefore(null, null));
    }

    @Test
    public void testSubstringBefore_EmptyStrings() {
        assertEquals("", Strings.substringBefore("", " "));
        assertEquals("Hello World", Strings.substringBefore("Hello World", ""));
    }

    // Tests for substringAfter
    @Test
    public void testSubstringAfter_Basic() {
        assertEquals("World", Strings.substringAfter("Hello World", " "));
        assertEquals("World", Strings.substringAfter("Hello,World", ","));
        assertEquals("World", Strings.substringAfter("HelloXYZWorld", "XYZ"));
    }

    @Test
    public void testSubstringAfter_DelimiterNotFound() {
        assertEquals("Hello World", Strings.substringAfter("Hello World", "XYZ"));
    }

    @Test
    public void testSubstringAfter_NullInput() {
        assertNull(Strings.substringAfter(null, " "));
        assertEquals("Hello World", Strings.substringAfter("Hello World", null));
        assertNull(Strings.substringAfter(null, null));
    }

    @Test
    public void testSubstringAfter_EmptyStrings() {
        assertEquals("", Strings.substringAfter("", " "));
        assertEquals("Hello World", Strings.substringAfter("Hello World", ""));
    }

    @Test
    public void testNonAlphaNumeric_ShouldRemoveSpecialCharacters(){
        assertEquals("HelloWorld123", Strings.removeNonAlphaNumeric("Hello, World! 123"));
        assertEquals("", Strings.removeNonAlphaNumeric("!!!"));
        assertEquals("ABCD", Strings.removeNonAlphaNumeric("AB!#¤%CD"));
        assertNull(Strings.removeNonAlphaNumeric(null));
    }

    @Test
    public void testCountVowels_allVowels() {
        assertEquals(5, Strings.countVowels("aeiou"));
        assertEquals(5, Strings.countVowels("AEIOU"));
    }

    @Test
    public void testCountVowels_noVowels() {
        assertEquals(0, Strings.countVowels("xyz"));
        assertEquals(0, Strings.countVowels("123"));
    }


}



