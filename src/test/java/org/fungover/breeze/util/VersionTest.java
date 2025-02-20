package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * VersionTest class contains unit tests for the Version class.
 * These tests verify the functionality for version parsing, comparison, and error handling.
 */
public class VersionTest {

    @Test
    public void testVersionComparison() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.4");
        Version v3 = new Version("1.2.3-alpha");
        Version v4 = new Version("1.2.3-beta");
        Version v5 = new Version("2.0.0");

        assertTrue(v1.isLessThan(v2));
        assertTrue(v2.isGreaterThan(v1));
        assertTrue(v1.isGreaterThan(v3));
        assertTrue(v3.isLessThan(v4));
        assertTrue(v1.isEqual(new Version("1.2.3")));
        assertTrue(v2.isLessThan(v5));
    }

    @Test
    public void testInvalidVersion() {
        assertThrows(IllegalArgumentException.class, () -> new Version("invalid"));
        assertThrows(IllegalArgumentException.class, () -> new Version("1.2"));
        assertThrows(IllegalArgumentException.class, () -> new Version("1.2.3.4"));
    }

    @Test
    public void testNullVersion() {
        assertThrows(IllegalArgumentException.class, () -> new Version(null));
    }

    @Test
    public void testEmptyVersion() {
        assertThrows(IllegalArgumentException.class, () -> new Version(""));
    }

    @Test
    public void testToString() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.3-alpha");

        assertEquals("1.2.3", v1.toString());
        assertEquals("1.2.3-alpha", v2.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.3");
        Version v3 = new Version("1.2.4");
        Version v4 = new Version("1.2.3-alpha");

        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
        assertNotEquals(v1, v4);
        assertEquals(v1.hashCode(), v2.hashCode());
        assertNotEquals(v1.hashCode(), v3.hashCode());
    }

    @Test
    public void testCompareToWithNull() {
        Version v1 = new Version("1.2.3");
        assertThrows(NullPointerException.class, () -> v1.compareTo(null));
    }
}
