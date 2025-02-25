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
    class CoreFunctionalityTests {
        /**
         * Test to ensure that the constructor does not throw an exception
         * when provided with a valid version string.
         */
        @Test
        void constructor_WithValidVersion_ShouldNotThrow() {
            assertDoesNotThrow(() -> new Version("1.2.3"));
        }

        /**
         * Test to ensure that the constructor throws an IllegalArgumentException
         * when provided with a null input.
         */
        @Test
        void constructor_WithNullInput_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> new Version(null));
        }

        /**
         * Test to ensure that the constructor throws an IllegalArgumentException
         * when provided with an empty input string.
         */
        @Test
        void constructor_WithEmptyInput_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> new Version(""));
        }
    }

    @Nested
    class ValidationTests {
        /**
         * Parameterized test to ensure that the constructor throws an IllegalArgumentException
         * when provided with various invalid version formats.
         *
         * @param invalidVersion The invalid version string to test.
         */
        @ParameterizedTest
        @ValueSource(strings = {
                "v1.0.0", "1.0", "1.0.0.0", "1.0.0-+meta",
                "01.0.0", "1.00.0", "1.0.00", "1.0.0-01",
                "1.0.0- ", "1.0.0+ ", "1.0.0-alpha..beta"
        })
        void constructor_WithInvalidFormats_ShouldThrowIllegalArgumentException(String invalidVersion) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version(invalidVersion));
        }

        /**
         * Parameterized test to ensure that the constructor does not throw an exception
         * when provided with various valid pre-release version formats.
         *
         * @param version The valid pre-release version string to test.
         */
        @ParameterizedTest
        @ValueSource(strings = {
                "1.0.0-rc.1",
                "1.0.0-alpha.beta",
                "1.0.0-0.3.7",
                "1.0.0-x.7.z.92",
                "1.0.0-x.y.z",      // Valid dot-separated identifiers
                "1.0.0-alpha1",     // Alphanumeric without hyphen
                "1.0.0-beta2",      // Alphanumeric without hyphen
                "1.0.0-20231231"    // Numeric without hyphen
        })
        void constructor_WithValidPreReleaseFormats_ShouldNotThrow(String version) {
            assertDoesNotThrow(() -> new Version(version));
        }

        /**
         * Parameterized test to ensure that the constructor does not throw an exception
         * when provided with various valid build metadata formats.
         *
         * @param version The valid build metadata version string to test.
         */
        @ParameterizedTest
        @ValueSource(strings = {
                "1.0.0+20130313144700",
                "1.0.0+exp.sha.5114f85",
                "1.0.0+21AF26D3-117B344092BD",
                "1.0.0+-hyphen",
                "1.0.0+meta-hyphen"
        })
        void constructor_WithValidBuildMetadataFormats_ShouldNotThrow(String version) {
            assertDoesNotThrow(() -> new Version(version));
        }
    }

    @Nested
    class ComparisonTests {
        /**
         * Test to ensure that the isLessThan method correctly orders versions
         * with different numerical values.
         */
        @Test
        void isLessThan_WithDifferentVersions_ShouldOrderCorrectly() {
            Version v1 = new Version("1.0.0");
            Version v2 = new Version("1.0.1");
            Version v3 = new Version("1.1.0");
            Version v4 = new Version("2.0.0");

            assertTrue(v1.isLessThan(v2));
            assertTrue(v2.isLessThan(v3));
            assertTrue(v3.isLessThan(v4));
        }

        /**
         * Test to ensure that the isLessThan method correctly orders versions
         * with different pre-release identifiers.
         */
        @Test
        void isLessThan_WithPreReleases_ShouldOrderCorrectly() {
            Version v1 = new Version("1.0.0-alpha");
            Version v2 = new Version("1.0.0-alpha.1");
            Version v3 = new Version("1.0.0-beta");
            Version v4 = new Version("1.0.0-beta.2");
            Version v5 = new Version("1.0.0-beta.11");
            Version v6 = new Version("1.0.0-rc.1");
            Version v7 = new Version("1.0.0");

            assertTrue(v1.isLessThan(v2));
            assertTrue(v2.isLessThan(v3));
            assertTrue(v4.isLessThan(v5));
            assertTrue(v5.isLessThan(v6));
            assertTrue(v6.isLessThan(v7));
        }

        /**
         * Parameterized test to ensure that the isLessThan method correctly compares
         * versions with mixed types (pre-release and build metadata).
         *
         * @param lower  The lower version string to test.
         * @param higher The higher version string to test.
         */
        @ParameterizedTest
        @MethodSource("provideMixedComparisons")
        void isLessThan_WithMixedTypes_ShouldCompareCorrectly(String lower, String higher) {
            Version v1 = new Version(lower);
            Version v2 = new Version(higher);
            assertTrue(v1.isLessThan(v2));
        }

        /**
         * Provides a stream of arguments for the mixed comparison tests.
         *
         * @return A stream of arguments for the parameterized test.
         */
        private static Stream<Arguments> provideMixedComparisons() {
            return Stream.of(
                    Arguments.of("1.0.0-alpha", "1.0.0-alpha.1"),
                    Arguments.of("1.0.0-alpha.1", "1.0.0-alpha.beta"),
                    Arguments.of("1.0.0-alpha.beta", "1.0.0-beta"),
                    Arguments.of("1.0.0-beta", "1.0.0-beta.2"),
                    Arguments.of("1.0.0-beta.11", "1.0.0-rc.1"),
                    Arguments.of("1.0.0-rc.1", "1.0.0")
            );
        }
    }

    @Nested
    class EdgeCaseTests {
        /**
         * Test to ensure that the constructor does not throw an exception
         * when provided with the maximum integer values for version components.
         */
        @Test
        void constructor_WithMaxValues_ShouldNotThrow() {
            assertDoesNotThrow(() ->
                    new Version("2147483647.2147483647.2147483647"));
        }

        /**
         * Test to ensure that the constructor throws an IllegalArgumentException
         * when provided with overflow integer values for version components.
         */
        @Test
        void constructor_WithOverflowValues_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("2147483648.0.0"));
        }

        /**
         * Test to ensure that versions with different build metadata are considered equal.
         */
        @Test
        void equals_WithBuildMetadata_ShouldCompareAsEqual() {
            Version v1 = new Version("1.0.0+sha.1234");
            Version v2 = new Version("1.0.0+sha.5678");
            assertEquals(v1, v2);
        }

        /**
         * Test to ensure that the constructor does not throw an exception
         * when provided with long pre-release identifiers.
         */
        @Test
        void constructor_WithLongIdentifiers_ShouldNotThrow() {
            assertDoesNotThrow(() ->
                    new Version("1.0.0-a.long.identifier.1234567890"));
        }
    }

    @Nested
    class IdentifierValidationTests {
        /**
         * Parameterized test to ensure that the constructor throws an IllegalArgumentException
         * when provided with pre-release identifiers containing leading zeros.
         *
         * @param identifier The invalid identifier string to test.
         */
        @ParameterizedTest
        @ValueSource(strings = {"01", "00", "0123456789"})
        void constructor_WithLeadingZeros_ShouldThrowIllegalArgumentException(String identifier) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("1.0.0-" + identifier));
        }

        /**
         * Parameterized test to ensure that the constructor throws an IllegalArgumentException
         * when provided with pre-release identifiers containing special characters.
         *
         * @param identifier The invalid identifier string to test.
         */
        @ParameterizedTest
        @ValueSource(strings = {"alpha!", "beta@1", "rc#", "meta space"})
        void constructor_WithSpecialCharacters_ShouldThrowIllegalArgumentException(String identifier) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("1.0.0-" + identifier));
        }

        /**
         * Test to ensure that the constructor throws an IllegalArgumentException
         * when provided with empty components in pre-release or build metadata.
         */
        @Test
        void constructor_WithEmptyComponents_ShouldThrowIllegalArgumentException() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Version("1.0.0-..empty")),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Version("1.0.0+..empty"))
            );
        }
    }

    @Nested
    class StringRepresentationTests {
        /**
         * Test to ensure that the toString method maintains the original format
         * of the version string.
         */
        @Test
        void toString_ShouldMaintainOriginalFormat() {
            String versionStr = "1.2.3-rc.1+sha.1234";
            Version version = new Version(versionStr);
            assertEquals(versionStr, version.toString());
        }

        /**
         * Test to ensure that the toString method omits empty parts of the version string.
         */
        @Test
        void toString_WithEmptyParts_ShouldOmitEmptyParts() {
            Version version = new Version("2.0.0");
            assertEquals("2.0.0", version.toString());
        }

        /**
         * Test to ensure that the toString method includes build metadata in the version string.
         */
        @Test
        void toString_WithBuildMetadata_ShouldIncludeBuildMetadata() {
            Version version = new Version("3.1.4+meta");
            assertEquals("3.1.4+meta", version.toString());
        }

        /**
         * Test to ensure that the toString method maintains the format of the version string
         * with both pre-release and build metadata.
         */
        @Test
        void toString_WithPreReleaseAndBuildMetadata_ShouldMaintainFormat() {
            Version version = new Version("1.0.0-alpha+meta");
            assertEquals("1.0.0-alpha+meta", version.toString());
        }
    }

    @Nested
    class EqualityTests {
        /**
         * Test to ensure that versions with the same pre-release identifiers are considered equal.
         */
        @Test
        void equals_WithSamePreRelease_ShouldBeEqualWithSamePreRelease() {
            Version v1 = new Version("1.0.0-rc.1");
            Version v2 = new Version("1.0.0-rc.1");
            assertEquals(v1, v2);
        }

        /**
         * Test to ensure that versions with different pre-release identifiers are not considered equal.
         */
        @Test
        void equals_WithDifferentPreRelease_ShouldDifferWithDifferentPreRelease() {
            Version v1 = new Version("1.0.0-alpha");
            Version v2 = new Version("1.0.0-beta");
            assertNotEquals(v1, v2);
        }

        /**
         * Test to ensure that equal versions have the same hash code.
         */
        @Test
        void hashCode_WithEqualVersions_ShouldHaveSameHashCodeForEqualVersions() {
            Version v1 = new Version("2.1.3");
            Version v2 = new Version("2.1.3");
            assertEquals(v1.hashCode(), v2.hashCode());
        }

        /**
         * Test to ensure that a version is not equal to an object of a different class.
         */
        @Test
        void equals_WithDifferentClass_ShouldNotEqualDifferentClass() {
            Version version = new Version("1.0.0");
            assertNotEquals(version, new Object());
        }
    }
}
