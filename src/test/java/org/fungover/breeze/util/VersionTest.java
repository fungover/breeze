package org.fungover.breeze.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    @Nested
    class EdgeCaseTests {
        @ParameterizedTest
        @ValueSource(strings = {"2147483647.2147483647.2147483647"})
        void shouldHandleMaxIntegerValues(String version) {
            assertDoesNotThrow(() -> new Version(version));
        }

        @ParameterizedTest
        @ValueSource(strings = {"2147483648.0.0"})
        void shouldRejectOverflowValues(String version) {
            assertThrows(IllegalArgumentException.class, () -> new Version(version));
        }

        @ParameterizedTest
        @MethodSource("provideLongPreReleaseIdentifiers")
        void shouldHandleLongPreReleaseIdentifiers(String v1, String v2) {
            Version version1 = new Version(v1);
            Version version2 = new Version(v2);
            assertTrue(version1.isGreaterThan(version2));
        }

        private static Stream<Arguments> provideLongPreReleaseIdentifiers() {
            return Stream.of(
                    Arguments.of("1.0.0-a.b.c.d.e.f.g", "1.0.0-a.b.c.d.e.f")
            );
        }

        @ParameterizedTest
        @MethodSource("provideCompareWithoutPreRelease")
        void shouldCompareVersionsWithoutPreReleaseCorrectly(String release, String prerelease) {
            Version version1 = new Version(release);
            Version version2 = new Version(prerelease);
            assertTrue(version1.isGreaterThan(version2));
        }

        private static Stream<Arguments> provideCompareWithoutPreRelease() {
            return Stream.of(
                    Arguments.of("2.0.0", "2.0.0-rc")
            );
        }
    }

    @Nested
    class VersionEqualityTests {
        @ParameterizedTest
        @MethodSource("provideEqualVersions")
        void versionsShouldBeEqual(String v1, String v2) {
            Version version1 = new Version(v1);
            Version version2 = new Version(v2);
            assertEquals(version1, version2);
            assertEquals(version1.hashCode(), version2.hashCode());
        }

        private static Stream<Arguments> provideEqualVersions() {
            return Stream.of(
                    Arguments.of("1.2.3-rc.1", "1.2.3-rc.1"),
                    Arguments.of("2.1.3-alpha+001", "2.1.3-alpha+002"),
                    Arguments.of("1.0.0+meta1", "1.0.0+meta2")
            );
        }

        @ParameterizedTest
        @MethodSource("provideNotEqualVersions")
        void versionsShouldNotBeEqual(String v1, String v2) {
            Version version1 = new Version(v1);
            Version version2 = new Version(v2);
            assertNotEquals(version1, version2);
        }

        private static Stream<Arguments> provideNotEqualVersions() {
            return Stream.of(
                    Arguments.of("1.2.3", "1.2.4"),
                    Arguments.of("1.0.0-alpha", "1.0.0-beta")
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"1.0.0"})
        void shouldNotEqualDifferentTypes(String version) {
            Version versionObj = new Version(version);
            assertNotEquals(versionObj, version);
        }
    }

    @Nested
    class StringRepresentationTests {
        @ParameterizedTest
        @MethodSource("provideStringRepresentations")
        void shouldFormatVersionCorrectly(String version) {
            Version versionObj = new Version(version);
            assertEquals(version, versionObj.toString());
        }

        private static Stream<Arguments> provideStringRepresentations() {
            return Stream.of(
                    Arguments.of("1.2.3"),
                    Arguments.of("2.0.0-rc.1"),
                    Arguments.of("1.0.0-alpha.beta.1+sha.1234"),
                    Arguments.of("2.0.0+exp.sha.5114f85")
            );
        }
    }

    @Nested
    class BuildMetadataTests {
        @ParameterizedTest
        @MethodSource("provideBuildMetadata")
        void shouldReturnBuildMetadata(String version, String expectedMetadata) {
            Version versionObj = new Version(version);
            assertEquals(expectedMetadata, versionObj.getBuildMetadata());
        }

        private static Stream<Arguments> provideBuildMetadata() {
            return Stream.of(
                    Arguments.of("1.0.0-rc.1+sha.1234", "sha.1234"),
                    Arguments.of("2.1.0", null)
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"1.0.0+meta", "1.0.0-beta+sha.1234"})
        void buildMetadataShouldNotAffectComparisons(String version) {
            Version v1 = new Version(version);
            Version v2 = new Version(version.replace("+meta", "+different"));
            assertEquals(v1, v2);
        }
    }

    @Nested
    class PreReleaseComparisonTests {
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

        private static Stream<Arguments> provideNumericPreReleaseComparisons() {
            return Stream.of(
                    Arguments.of("1.0.0-alpha.2", "1.0.0-alpha.10", true),
                    Arguments.of("2.1.0-beta.15", "2.1.0-beta.3", false),
                    Arguments.of("3.0.0-rc.1", "3.0.0-rc.2", true)
            );
        }

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

        private static Stream<Arguments> provideDifferentLengthPreReleaseComparisons() {
            return Stream.of(
                    Arguments.of("1.2.3-alpha.1", "1.2.3-alpha.1.1", true),
                    Arguments.of("2.0.0-beta.1.2", "2.0.0-beta.1", false),
                    Arguments.of("3.1.4-rc.1.0", "3.1.4-rc.1.0.1", true)
            );
        }
    }

    @Nested
    class NegativeValueTests {
        @ParameterizedTest
        @ValueSource(strings = {"-1.0.0", "1.-1.0", "1.0.-1"})
        void shouldRejectNegativeVersionComponents(String version) {
            assertThrows(IllegalArgumentException.class, () -> new Version(version));
        }
    }

    @Nested
    class ValidAlphanumericIdentifiersTests {
        @ParameterizedTest
        @ValueSource(strings = {"a", "0a", "a0", "a-0", "valid"})
        void validAlphanumericIdentifiersShouldPass(String identifier) {
            assertDoesNotThrow(() -> new Version("1.0.0-" + identifier));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "---", "123-", "-456"})
        void numericIdentifiersWithoutLettersShouldThrow(String identifier) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("1.0.0-" + identifier));
        }

        @ParameterizedTest
        @ValueSource(strings = {"0", "123", "999"})
        void validNumericIdentifiersShouldNotThrow(String identifier) {
            assertDoesNotThrow(() -> new Version("1.0.0-" + identifier));
        }

        @ParameterizedTest
        @ValueSource(strings = {"a!", "test@", "space ", "ünicode"})
        void invalidCharactersShouldThrow(String identifier) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("1.0.0-" + identifier));
        }

        @Test
        void shouldCompareDifferentLengthPreReleases() {
            Version v1 = new Version("1.0.0-alpha.1");
            Version v2 = new Version("1.0.0-alpha.1.2");
            assertTrue(v1.isLessThan(v2));
        }

        @Test
        void shouldCompareNumericVsAlphanumeric() {
            Version v1 = new Version("1.0.0-999");
            Version v2 = new Version("1.0.0-alpha");
            assertTrue(v1.isLessThan(v2));
        }

        @Test
        void shouldRejectEmptyPreReleaseComponent() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("1.0.0-alpha..1"));
        }
    }
}