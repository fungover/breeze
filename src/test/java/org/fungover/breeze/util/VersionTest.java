package org.fungover.breeze.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for verifying Semantic Versioning 2.0.0 compliance
 * and edge case handling in the Version class.
 */
class VersionTest {

    /**
     * Tests core version comparison logic between different version numbers
     */
    @Nested
    class CoreVersionComparisons {

        @Test
        void higherMajorVersionShouldBeGreater() {
            Version v1 = new Version("2.0.0");
            Version v2 = new Version("1.999.9999");
            assertTrue(v1.isGreaterThan(v2));
        }

        @Test
        void equalVersionsWithoutPreReleaseShouldBeEqual() {
            Version v1 = new Version("3.14.159");
            Version v2 = new Version("3.14.159");
            assertTrue(v1.isEqualTo(v2));
        }
    }

    /**
     * Tests specific pre-release comparison rules as defined by SemVer 2.0.0
     */
    @Nested
    class PreReleaseComparisons {

        /**
         * Parameterized test for various pre-release comparison scenarios
         * @param version1 First version string (without prefix)
         * @param version2 Second version string (without prefix)
         * @param expected Expected comparison result (-1, 0, 1)
         */
        @ParameterizedTest(name = "{0} vs {1} → {2}")
        @MethodSource("providePreReleaseComparisonCases")
        void comparePreReleaseVersions(String version1, String version2, int expected) {
            Version v1 = new Version("1.0.0-" + version1);
            Version v2 = new Version("1.0.0-" + version2);
            assertEquals(expected, Integer.signum(v1.compareTo(v2)));
        }

        private static Stream<Arguments> providePreReleaseComparisonCases() {
            return Stream.of(
                    // Basic ordering
                    Arguments.of("alpha", "beta", -1),
                    Arguments.of("beta", "alpha", 1),

                    // Numeric comparisons
                    Arguments.of("alpha.2", "alpha.10", -1),
                    Arguments.of("alpha.10", "alpha.2", 1),

                    // Mixed type comparisons
                    Arguments.of("alpha.1", "alpha.beta", -1),
                    Arguments.of("alpha.beta", "alpha.1", 1),

                    // Different segment lengths
                    Arguments.of("alpha.1", "alpha.1.1", -1),
                    Arguments.of("alpha.1.1", "alpha.1", 1),

                    // Equal comparisons
                    Arguments.of("123", "123", 0),
                    Arguments.of("alpha.1.beta", "alpha.1.beta", 0)
            );
        }

        @Test
        void versionWithPreReleaseShouldBeLessThanReleaseVersion() {
            Version prerelease = new Version("1.0.0-alpha");
            Version release = new Version("1.0.0");
            assertTrue(prerelease.isLessThan(release));
        }
    }

    /**
     * Tests validation of version string formats and error conditions
     */
    @Nested
    class VersionValidation {

        @ParameterizedTest(name = "Reject invalid version: {0}")
        @ValueSource(strings = {
                "1.2.3-alpha..beta",
                "1.2.3-alpha.01",
                "1.2.3-alpha!",
                "1.2.3-alpha/beta",
                "1.2.3-alpha-",
                "1.2.3--beta"
        })
        void rejectInvalidVersionFormats(String invalidVersion) {
            assertThrows(IllegalArgumentException.class, () ->
                    new Version(invalidVersion));
        }

        @Test
        void acceptValidVersionWithMixedCaseIdentifiers() {
            Version version = new Version("1.2.3-RC.1-BUILD.123");
            assertEquals("1.2.3-RC.1-BUILD.123", version.toString());
        }
    }

    /**
     * Tests security-related edge cases and potential vulnerabilities
     */
    @Nested
    class SecurityTests {

        @Test
        void rejectExcessivelyLongVersionStrings() {
            String longVersion = "1".repeat(1000) + ".0.0";
            assertThrows(IllegalArgumentException.class, () ->
                    new Version(longVersion));
        }
    }

    /**
     * Tests equality contracts and hash code consistency
     */
    @Nested
    class EqualityTests {

        @Test
        void identicalVersionsShouldBeEqual() {
            Version v1 = new Version("1.2.3-alpha.1");
            Version v2 = new Version("1.2.3-alpha.1");
            assertEquals(v1, v2);
            assertEquals(v1.hashCode(), v2.hashCode());
        }

        @Test
        void differentPreReleasesShouldNotBeEqual() {
            Version v1 = new Version("1.0.0-alpha");
            Version v2 = new Version("1.0.0-beta");
            assertNotEquals(v1, v2);
        }

        @Test
        void nullShouldNotBeEqual() {
            Version version = new Version("1.0.0");
            assertNotEquals(null, version);
        }
    }
}