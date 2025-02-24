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
        @Test
        void shouldCreateSimpleVersion() {
            assertDoesNotThrow(() -> new Version("1.2.3"));
        }

        @Test
        void shouldThrowOnNullInput() {
            assertThrows(IllegalArgumentException.class, () -> new Version(null));
        }

        @Test
        void shouldThrowOnEmptyInput() {
            assertThrows(IllegalArgumentException.class, () -> new Version(""));
        }
    }

    @Nested
    class ValidationTests {
        @ParameterizedTest
        @ValueSource(strings = {
                "v1.0.0", "1.0", "1.0.0.0", "1.0.0-+meta",
                "01.0.0", "1.00.0", "1.0.00", "1.0.0-01",
                "1.0.0- ", "1.0.0+ ", "1.0.0-alpha..beta"
        })
        void shouldRejectInvalidFormats(String invalidVersion) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version(invalidVersion));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "1.0.0-rc.1",
                "1.0.0-alpha.beta",
                "1.0.0-0.3.7",
                "1.0.0-x.7.z.92",
                "1.0.0-x-y-z.-",    // Valid hyphen-only identifier
                "1.0.0--hyphen",    // Leading hyphen
                "1.0.0-hyphen-",    // Trailing hyphen
                "1.0.0-2023-12-31"  // Numeric-hyphen combo
        })
        void shouldValidatePreReleaseFormats(String version) {
            assertDoesNotThrow(() -> new Version(version));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "1.0.0+20130313144700",
                "1.0.0+exp.sha.5114f85",
                "1.0.0+21AF26D3-117B344092BD",
                "1.0.0+-hyphen",
                "1.0.0+meta-hyphen"
        })
        void shouldValidateBuildMetadataFormats(String version) {
            assertDoesNotThrow(() -> new Version(version));
        }
    }

    @Nested
    class ComparisonTests {
        @Test
        void shouldOrderCorrectly() {
            Version v1 = new Version("1.0.0");
            Version v2 = new Version("1.0.1");
            Version v3 = new Version("1.1.0");
            Version v4 = new Version("2.0.0");

            assertTrue(v1.isLessThan(v2));
            assertTrue(v2.isLessThan(v3));
            assertTrue(v3.isLessThan(v4));
        }

        @Test
        void shouldOrderPreReleasesCorrectly() {
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

        @ParameterizedTest
        @MethodSource("provideMixedComparisons")
        void shouldCompareMixedTypes(String lower, String higher) {
            Version v1 = new Version(lower);
            Version v2 = new Version(higher);
            assertTrue(v1.isLessThan(v2));
        }

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
        @Test
        void shouldHandleMaxValues() {
            assertDoesNotThrow(() ->
                    new Version("2147483647.2147483647.2147483647"));
        }

        @Test
        void shouldRejectOverflowValues() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("2147483648.0.0"));
        }

        @Test
        void shouldCompareBuildMetadataAsEqual() {
            Version v1 = new Version("1.0.0+sha.1234");
            Version v2 = new Version("1.0.0+sha.5678");
            assertEquals(v1, v2);
        }

        @Test
        void shouldHandleLongIdentifiers() {
            assertDoesNotThrow(() ->
                    new Version("1.0.0-a.long.identifier.1234567890"));
        }
    }

    @Nested
    class IdentifierValidationTests {
        @ParameterizedTest
        @ValueSource(strings = {"01", "00", "0123456789"})
        void shouldRejectLeadingZeros(String identifier) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("1.0.0-" + identifier));
        }

        @ParameterizedTest
        @ValueSource(strings = {"alpha!", "beta@1", "rc#", "meta space"})
        void shouldRejectSpecialCharacters(String identifier) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("1.0.0-" + identifier));
        }

        @Test
        void shouldRejectEmptyComponents() {
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
        @Test
        void shouldMaintainOriginalFormat() {
            String versionStr = "1.2.3-rc.1+sha.1234";
            Version version = new Version(versionStr);
            assertEquals(versionStr, version.toString());
        }

        @Test
        void shouldOmitEmptyParts() {
            Version version = new Version("2.0.0");
            assertEquals("2.0.0", version.toString());
        }

        @Test
        void shouldIncludeBuildMetadata() {
            Version version = new Version("3.1.4+meta");
            assertEquals("3.1.4+meta", version.toString());
        }

        @Test
        void shouldHandleHyphenOnlyIdentifier() {
            Version version = new Version("1.0.0--+meta");
            assertEquals("1.0.0--+meta", version.toString());
        }
    }

    @Nested
    class EqualityTests {
        @Test
        void shouldBeEqualWithSamePreRelease() {
            Version v1 = new Version("1.0.0-rc.1");
            Version v2 = new Version("1.0.0-rc.1");
            assertEquals(v1, v2);
        }

        @Test
        void shouldDifferWithDifferentPreRelease() {
            Version v1 = new Version("1.0.0-alpha");
            Version v2 = new Version("1.0.0-beta");
            assertNotEquals(v1, v2);
        }

        @Test
        void shouldHaveSameHashCodeForEqualVersions() {
            Version v1 = new Version("2.1.3");
            Version v2 = new Version("2.1.3");
            assertEquals(v1.hashCode(), v2.hashCode());
        }

        @Test
        void shouldNotEqualDifferentClass() {
            Version version = new Version("1.0.0");
            assertNotEquals(version, new Object());
        }
    }
}