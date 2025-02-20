package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    // ----------- Comparison Tests -----------

    @Test
    void isLessThan_WhenFirstVersionIsLess_ReturnsTrue() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.4");
        assertTrue(v1.isLessThan(v2));
    }

    @Test
    void isGreaterThan_WhenFirstVersionIsGreater_ReturnsTrue() {
        Version v1 = new Version("1.2.4");
        Version v2 = new Version("1.2.3");
        assertTrue(v1.isGreaterThan(v2));
    }

    @Test
    void isEqual_WhenVersionsAreIdentical_ReturnsTrue() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.3");
        assertTrue(v1.isEqual(v2));
    }

    @Test
    void isLessThan_WhenPreReleaseVersionIsComparedToRelease_ReturnsTrue() {
        Version release = new Version("1.2.3");
        Version preRelease = new Version("1.2.3-alpha");
        assertTrue(preRelease.isLessThan(release));
    }

    @Test
    void compareTo_WhenPreReleasesDiffer_ReturnsLexicographicalOrder() {
        Version alpha = new Version("1.2.3-alpha");
        Version beta = new Version("1.2.3-beta");
        assertTrue(alpha.compareTo(beta) < 0);
    }

    // ----------- Parameterized Comparison Tests -----------

    static Stream<Arguments> versionComparisonProvider() {
        return Stream.of(
                Arguments.of("1.2.3", "1.2.4", -1),
                Arguments.of("2.0.0", "1.2.4", 1),
                Arguments.of("1.2.3", "1.2.3", 0),
                Arguments.of("1.2.3-alpha", "1.2.3", -1),
                Arguments.of("1.2.3-beta", "1.2.3-alpha", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("versionComparisonProvider")
    void compareTo_WithVariousVersions_ReturnsExpectedResult(String v1, String v2, int expected) {
        Version version1 = new Version(v1);
        Version version2 = new Version(v2);
        assertEquals(expected, Integer.signum(version1.compareTo(version2)));
    }

    // ----------- Invalid Version Tests -----------

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "1.2", "1.2.3.4", "-1.2.3", "1.-2.3", "1.2.-3"})
    void constructor_WhenInvalidVersion_ThrowsIllegalArgumentException(String invalidVersion) {
        assertThrows(IllegalArgumentException.class, () -> new Version(invalidVersion));
    }

    @Test
    void constructor_WhenNullVersion_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Version(null));
    }

    @Test
    void constructor_WhenEmptyVersion_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Version(""));
    }

    // ----------- Equals & HashCode Tests -----------

    @Test
    void equals_WhenVersionsAreSame_ReturnsTrue() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.3");
        assertEquals(v1, v2);
    }

    @Test
    void equals_WhenPreReleasesDiffer_ReturnsFalse() {
        Version v1 = new Version("1.2.3-alpha");
        Version v2 = new Version("1.2.3-beta");
        assertNotEquals(v1, v2);
    }

    @Test
    void hashCode_WhenVersionsAreSame_ReturnsEqualHashCodes() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.3");
        assertEquals(v1.hashCode(), v2.hashCode());
    }

    // ----------- Other Tests -----------

    @Test
    void toString_ReturnsCorrectFormat() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.3-beta");
        assertEquals("1.2.3", v1.toString());
        assertEquals("1.2.3-beta", v2.toString());
    }

    @Test
    void compareTo_WhenOtherIsNull_ThrowsNullPointerException() {
        Version v = new Version("1.2.3");
        assertThrows(NullPointerException.class, () -> v.compareTo(null));
    }
}