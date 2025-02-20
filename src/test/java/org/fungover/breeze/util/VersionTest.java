package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The VersionTest class contains unit tests for the Version class.
 * These tests verify the functionality of version parsing, comparison, and error handling.
 */
public class VersionTest {

    /**
     * Tests the comparison of versions with and without pre-release identifiers.
     * This test ensures that versions are correctly compared based on their major, minor, patch,
     * and pre-release components.
     */
    @Test
    public void testVersionComparison() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.4");
        Version v3 = new Version("1.2.3-alpha");
        Version v4 = new Version("1.2.3-beta");

        // v1 (1.2.3) should be less than v2 (1.2.4)
        assertTrue(v1.isLessThan(v2));

        // v2 (1.2.4) should be greater than v1 (1.2.3)
        assertTrue(v2.isGreaterThan(v1));

        // v1 (1.2.3) should be greater than v3 (1.2.3-alpha) because v3 has a pre-release identifier
        assertTrue(v1.isGreaterThan(v3));

        // v3 (1.2.3-alpha) should be less than v4 (1.2.3-beta) because 'alpha' comes before 'beta'
        assertTrue(v3.isLessThan(v4));

        // v1 (1.2.3) should be equal to another instance of Version("1.2.3")
        assertTrue(v1.isEqual(new Version("1.2.3")));
    }

    /**
     * Tests the handling of invalid version strings.
     * This test ensures that IllegalArgumentException is thrown for invalid version formats.
     */
    @Test
    public void testInvalidVersion() {
        // Invalid version string "invalid" should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Version("invalid"));

        // Version string "1.2" is missing the patch component and should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Version("1.2"));

        // Version string "1.2.3.4" has an extra component and should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Version("1.2.3.4"));

        // Additional test cases for invalid versions
        assertThrows(IllegalArgumentException.class, () -> new Version(null));
        assertThrows(IllegalArgumentException.class, () -> new Version(""));
        assertThrows(IllegalArgumentException.class, () -> new Version("-1.2.3"));
        assertThrows(IllegalArgumentException.class, () -> new Version("1.2.x"));
    }

    /**
     * Tests if the toString method returns correctly formatted version strings.
     * This test ensures that the version string is formatted as "major.minor.patch(-preRelease)".
     */
    @Test
    public void testToString() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.3-alpha");

        // The toString method should return "1.2.3" for v1
        assertEquals("1.2.3", v1.toString());

        // The toString method should return "1.2.3-alpha" for v2
        assertEquals("1.2.3-alpha", v2.toString());
    }
}
