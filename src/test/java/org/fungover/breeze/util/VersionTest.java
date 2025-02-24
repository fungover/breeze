package org.fungover.breeze.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The VersionTest class contains unit tests for the Version class.
 */
class VersionTest {

    /**
     * Nested class for testing edge cases in the Version class.
     */
    @Nested
    class EdgeCaseTests {
        /**
         * Tests that the Version class can handle the maximum integer values for version components.
         *
         * @param version the version string to test
         */
        @ParameterizedTest
        @ValueSource(strings = {"2147483647.2147483647.2147483647"})
        void shouldHandleMaxIntegerValues(String version) {
            assertDoesNotThrow(() -> new Version(version));
        }

        /**
         * Tests that the Version class rejects overflow values for version components.
         *
         * @param version the version string to test
         */
        @ParameterizedTest
        @ValueSource(strings = {"2147483648.0.0"})
        void shouldRejectOverflowValues(String version) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version(version)
            );
        }

        /**
         * Provides long pre-release identifiers for testing.
         *
         * @return a stream of arguments representing long pre-release identifiers
         */
        private static Stream<Arguments> provideLongPreReleaseIdentifiers() {
            return Stream.of(
                    Arguments.of("1.0.0-a.b.c.d.e.f.g", "1.0.0-a.b.c.d.e.f") // Test case with long pre-release identifier
            );
        }

        /**
         * Tests that the Version class can handle long pre-release identifiers.
         *
         * @param v1 the first version string to compare
         * @param v2 the second version string to compare
         */
        @ParameterizedTest
        @MethodSource("provideLongPreReleaseIdentifiers")
        void shouldHandleLongPreReleaseIdentifiers(String v1, String v2) {
            Version version1 = new Version(v1);
            Version version2 = new Version(v2);
            assertTrue(version1.isGreaterThan(version2));
        }

        /**
         * Provides versions without pre-release for comparison testing.
         *
         * @return a stream of arguments representing versions without pre-release
         */
        private static Stream<Arguments> provideCompareWithoutPreRelease() {
            return Stream.of(
                    Arguments.of("2.0.0", "2.0.0-rc") // Test case comparing version without pre-release
            );
        }

        /**
         * Tests that the Version class correctly compares versions without pre-release.
         *
         * @param release the release version string
         * @param prerelease the pre-release version string
         */
        @ParameterizedTest
        @MethodSource("provideCompareWithoutPreRelease")
        void shouldCompareVersionsWithoutPreReleaseCorrectly(String release, String prerelease) {
            Version version1 = new Version(release);
            Version version2 = new Version(prerelease);
            assertTrue(version1.isGreaterThan(version2));
        }
    }

    /**
     * Nested class for testing version equality in the Version class.
     */
    @Nested
    class VersionEqualityTests {
        /**
         * Provides equal versions for testing.
         *
         * @return a stream of arguments representing equal versions
         */
        private static Stream<Arguments> provideEqualVersions() {
            return Stream.of(
                    Arguments.of("1.2.3-rc.1", "1.2.3-rc.1"), // Equal versions with pre-release
                    Arguments.of("2.1.3-alpha+001", "2.1.3-alpha+002"), // Equal versions with build metadata
                    Arguments.of("1.0.0+meta1", "1.0.0+meta2") // Equal versions with different build metadata
            );
        }

        /**
         * Tests that the Version class correctly identifies equal versions.
         *
         * @param v1 the first version string to compare
         * @param v2 the second version string to compare
         */
        @ParameterizedTest
        @MethodSource("provideEqualVersions")
        void versionsShouldBeEqual(String v1, String v2) {
            Version version1 = new Version(v1);
            Version version2 = new Version(v2);
            assertEquals(version1, version2);
            assertEquals(version1.hashCode(), version2.hashCode());
        }

        /**
         * Provides non-equal versions for testing.
         *
         * @return a stream of arguments representing non-equal versions
         */
        private static Stream<Arguments> provideNotEqualVersions() {
            return Stream.of(
                    Arguments.of("1.2.3", "1.2.4"), // Different patch versions
                    Arguments.of("1.0.0-alpha", "1.0.0-beta") // Different pre-release versions
            );
        }

        /**
         * Tests that the Version class correctly identifies non-equal versions.
         *
         * @param v1 the first version string to compare
         * @param v2 the second version string to compare
         */
        @ParameterizedTest
        @MethodSource("provideNotEqualVersions")
        void versionsShouldNotBeEqual(String v1, String v2) {
            Version version1 = new Version(v1);
            Version version2 = new Version(v2);
            assertNotEquals(version1, version2);
        }

        /**
         * Tests that the Version class does not consider different types as equal.
         *
         * @param version the version string to test
         */
        @ParameterizedTest
        @ValueSource(strings = {"1.0.0"})
        void shouldNotEqualDifferentTypes(String version) {
            Version versionObj = new Version(version);
            assertNotEquals(versionObj, version);
        }
    }

    /**
     * Nested class for testing string representations of versions in the Version class.
     */
    @Nested
    class StringRepresentationTests {
        /**
         * Provides string representations of versions for testing.
         *
         * @return a stream of arguments representing string representations of versions
         */
        private static Stream<Arguments> provideStringRepresentations() {
            return Stream.of(
                    Arguments.of("1.2.3"), // Simple version
                    Arguments.of("2.0.0-rc.1"), // Version with pre-release
                    Arguments.of("1.0.0-alpha.beta.1+sha.1234"), // Version with pre-release and build metadata
                    Arguments.of("1.2.3-rc.1+sha.1234"), // Version with pre-release and build metadata
                    Arguments.of("2.0.0+exp.sha.5114f85"), // Version with build metadata
                    Arguments.of("3.1.4+001") // Version with build metadata
            );
        }

        /**
         * Tests that the Version class correctly formats the string representation of versions.
         *
         * @param version the version string to test
         */
        @ParameterizedTest
        @MethodSource("provideStringRepresentations")
        void shouldFormatVersionCorrectly(String version) {
            Version versionObj = new Version(version);
            assertEquals(version, versionObj.toString());
        }
    }

    /**
     * Nested class for testing build metadata in the Version class.
     */
    @Nested
    class BuildMetadataTests {
        /**
         * Provides build metadata for testing.
         *
         * @return a stream of arguments representing build metadata
         */
        private static Stream<Arguments> provideBuildMetadata() {
            return Stream.of(
                    Arguments.of("1.0.0-rc.1+sha.1234", "sha.1234"), // Version with build metadata
                    Arguments.of("2.1.0", null) // Version without build metadata
            );
        }

        /**
         * Tests that the Version class correctly returns the build metadata.
         *
         * @param version the version string to test
         * @param expectedMetadata the expected build metadata
         */
        @ParameterizedTest
        @MethodSource("provideBuildMetadata")
        void shouldReturnBuildMetadata(String version, String expectedMetadata) {
            Version versionObj = new Version(version);
            assertEquals(expectedMetadata, versionObj.getBuildMetadata());
        }
    }

    /**
     * Nested class for testing pre-release comparisons in the Version class.
     */
    @Nested
    class PreReleaseComparisonTests {
        /**
         * Provides numeric pre-release comparisons for testing.
         *
         * @return a stream of arguments representing numeric pre-release comparisons
         */
        private static Stream<Arguments> provideNumericPreReleaseComparisons() {
            return Stream.of(
                    Arguments.of("1.0.0-alpha.2", "1.0.0-alpha.10", true), // Numeric pre-release comparison
                    Arguments.of("2.1.0-beta.15", "2.1.0-beta.3", false), // Numeric pre-release comparison
                    Arguments.of("3.0.0-rc.1", "3.0.0-rc.2", true) // Numeric pre-release comparison
            );
        }

        /**
         * Tests that the Version class correctly compares numeric pre-release versions.
         *
         * @param v1 the first version string to compare
         * @param v2 the second version string to compare
         * @param isLessThan indicates if v1 should be less than v2
         */
        @ParameterizedTest
        @MethodSource("provideNumericPreReleaseComparisons")
        void shouldCompareNumericPreReleasesCorrectly(String v1, String v2, boolean isLessThan) {
            Version version1 = new Version(v1);
            Version version2 = new Version(v2);
            if (isLessThan) {
                assertTrue(version1.isLessThan(version2));
            } else {
                assertTrue(version1.isGreaterThan(version2));
            }
        }

        /**
         * Provides different length pre-release comparisons for testing.
         *
         * @return a stream of arguments representing different length pre-release comparisons
         */
        private static Stream<Arguments> provideDifferentLengthPreReleaseComparisons() {
            return Stream.of(
                    Arguments.of("1.2.3-alpha.1", "1.2.3-alpha.1.1", true), // Different length pre-release comparison
                    Arguments.of("2.0.0-beta.1.2", "2.0.0-beta.1", false), // Different length pre-release comparison
                    Arguments.of("3.1.4-rc.1.0", "3.1.4-rc.1.0.1", true) // Different length pre-release comparison
            );
        }

        /**
         * Tests that the Version class correctly compares pre-release versions of different lengths.
         *
         * @param v1 the first version string to compare
         * @param v2 the second version string to compare
         * @param isLessThan indicates if v1 should be less than v2
         */
        @ParameterizedTest
        @MethodSource("provideDifferentLengthPreReleaseComparisons")
        void shouldCompareDifferentLengthPreReleasesCorrectly(String v1, String v2, boolean isLessThan) {
            Version version1 = new Version(v1);
            Version version2 = new Version(v2);
            if (isLessThan) {
                assertTrue(version1.isLessThan(version2));
            } else {
                assertTrue(version1.isGreaterThan(version2));
            }
        }
    }

    /**
     * Nested class for testing negative value handling in the Version class.
     */
    @Nested
    class NegativeValueTests {
        /**
         * Tests that the Version class rejects negative version components.
         *
         * @param version the version string to test
         */
        @ParameterizedTest
        @ValueSource(strings = {"-1.0.0", "1.-1.0", "1.0.-1"})
        void shouldRejectNegativeVersionComponents(String version) {
            assertThrows(IllegalArgumentException.class, () -> new Version(version));
        }
    }
}
