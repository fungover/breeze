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
    class ValidVersionTests {
        @ParameterizedTest
        @ValueSource(strings = {
                "0.0.0",
                "1.2.3+sha.1234",
                "10.20.30-beta.1+20240321.1337",
                "1.0.0-alpha+001",
                "1.0.0-0valid+exp.sha.5114f85",
                "2147483647.2147483647.2147483647+meta",
                "1.2.3",
                "1.0.0-alpha",
                "1.0.0-alpha.1",
                "1.0.0-0valid",
                "1.0.0-rc.1",
                "2147483647.2147483647.2147483647"
        })
        void should_accept_valid_semver_formats(String version) {
            assertDoesNotThrow(() -> new Version(version));
        }
    }

    @Nested
    class InvalidVersionTests {
        @ParameterizedTest
        @ValueSource(strings = {
                "1.0.0+",                 // Empty build metadata
                "1.0.0+_bad",             // Invalid build character
                "1.0.0-alpha+!",          // Invalid build metadata
                "1.0.0+meta+another",     // Multiple build metadata
                "1.0.0-alpha.01+valid",   // Leading zero in pre-release
                "1.0.0+-beta",            // Empty build metadata with hyphen
                "", "  ", "1.2", "1.2.x",
                "-1.2.3", "1.-2.3", "1.2.-3",
                "01.1.1", "1.02.3", "1.2.03",
                "1.2.3-alpha.01", "1.2.3-alpha..beta",
                "1.2.3-alpha!", "1.2.3-alpha@beta",
                "1.2.3-alpha-", "1.2.3--beta",
                "2147483648.0.0", "1.0.0-alpha_beta",
                "99999999999999999999.0.0", "1.0.0-rc..1",
                "1.0.0-rc.01", "1.0.0-.beta"
        })
        void should_reject_invalid_formats(String version) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version(version),
                    "Should reject invalid version: " + version
            );
        }

        @Test
        void should_throw_on_null_input() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version(null)
            );
        }
    }

    @Nested
    class ComparisonTests {
        @ParameterizedTest
        @MethodSource("provideComparisonCases")
        void should_correctly_compare_versions(String v1, String v2, int expectedSign) {
            Version version1 = new Version(v1);
            Version version2 = new Version(v2);

            int result = version1.compareTo(version2);

            if (expectedSign > 0) {
                assertTrue(result > 0,
                        v1 + " should be greater than " + v2 + " (got: " + result + ")");
            } else if (expectedSign < 0) {
                assertTrue(result < 0,
                        v1 + " should be less than " + v2 + " (got: " + result + ")");
            } else {
                assertEquals(0, result,
                        "Versions should be equal: " + v1 + " vs " + v2);
            }

            assertEquals(expectedSign > 0, version1.isGreaterThan(version2));
            assertEquals(expectedSign < 0, version1.isLessThan(version2));
            assertEquals(expectedSign == 0, version1.isEqualTo(version2));
        }

        private static Stream<Arguments> provideComparisonCases() {
            return Stream.of(
                    // Core version comparisons
                    Arguments.of("2.0.0", "1.9.9", 1),
                    Arguments.of("1.1.0", "1.2.0", -1),
                    Arguments.of("1.0.0", "1.0.0", 0),

                    // Pre-release comparisons
                    Arguments.of("1.0.0", "1.0.0-alpha", 1),
                    Arguments.of("1.0.0-beta", "1.0.0-alpha", 1),
                    Arguments.of("1.0.0-alpha.1", "1.0.0-alpha", 1),
                    Arguments.of("1.0.0-alpha.10", "1.0.0-alpha.2", 1),
                    Arguments.of("1.0.0-alpha.1", "1.0.0-alpha.1.1", -1),
                    Arguments.of("1.0.0-20231231", "1.0.0-20230101", 1),
                    Arguments.of("1.0.0-rc.1", "1.0.0-beta.2", 1),

                    // Numeric vs non-numeric
                    Arguments.of("1.0.0-1", "1.0.0-alpha", -1),
                    Arguments.of("1.0.0-999", "1.0.0-beta", -1),

                    // Case sensitivity
                    Arguments.of("1.0.0-alpha", "1.0.0-ALPHA", 1),
                    Arguments.of("1.0.0-BETA", "1.0.0-beta", -1),

                    // Identifier lengths
                    Arguments.of("1.0.0-alpha.beta", "1.0.0-alpha.alpha", 1),
                    Arguments.of("1.0.0-alpha", "1.0.0-alpha.1", -1),
                    Arguments.of("1.0.0-x.7.z.92", "1.0.0-x.7.z.92", 0),

                    // Build metadata comparisons
                    Arguments.of("1.0.0+sha.1234", "1.0.0+9876.abc", 0),
                    Arguments.of("1.0.0-beta+meta", "1.0.0-beta+other", 0),
                    Arguments.of("1.2.3-rc.1+001", "1.2.3-rc.1+002", 0)
            );
        }
    }

    @Nested
    class EdgeCaseTests {
        @Test
        void should_handle_max_integer_values() {
            assertDoesNotThrow(() ->
                    new Version("2147483647.2147483647.2147483647")
            );
        }

        @Test
        void should_reject_overflow_values() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Version("2147483648.0.0")
            );
        }

        @Test
        void should_handle_long_pre_release_identifiers() {
            Version v1 = new Version("1.0.0-a.b.c.d.e.f.g");
            Version v2 = new Version("1.0.0-a.b.c.d.e.f");
            assertTrue(v1.isGreaterThan(v2));
        }

        @Test
        void should_compare_versions_without_pre_release_correctly() {
            Version release = new Version("2.0.0");
            Version prerelease = new Version("2.0.0-rc");
            assertTrue(release.isGreaterThan(prerelease));
        }
    }

    @Nested
    class EqualityTests {
        @Test
        void identical_versions_should_be_equal() {
            Version v1 = new Version("1.2.3-rc.1");
            Version v2 = new Version("1.2.3-rc.1");
            assertEquals(v1, v2);
            assertEquals(v1.hashCode(), v2.hashCode());
        }

        @Test
        void different_patch_versions_should_not_be_equal() {
            Version v1 = new Version("1.2.3");
            Version v2 = new Version("1.2.4");
            assertNotEquals(v1, v2);
        }

        @Test
        void different_pre_releases_should_not_be_equal() {
            Version v1 = new Version("1.0.0-alpha");
            Version v2 = new Version("1.0.0-beta");
            assertNotEquals(v1, v2);
        }

        @Test
        void should_not_equal_different_types() {
            Version version = new Version("1.0.0");
            assertNotEquals(version, "1.0.0");
        }

        @Test
        void equal_versions_with_different_build_metadata() {
            Version v1 = new Version("2.1.3-alpha+001");
            Version v2 = new Version("2.1.3-alpha+002");

            assertEquals(v1, v2);
            assertEquals(v1.hashCode(), v2.hashCode());
        }

        @Test
        void different_build_metadata_should_not_affect_equality() {
            Version v1 = new Version("1.0.0+meta1");
            Version v2 = new Version("1.0.0+meta2");

            assertTrue(v1.isEqualTo(v2));
        }
    }

    @Nested
    class StringRepresentationTests {
        @Test
        void should_format_core_version_correctly() {
            Version version = new Version("1.2.3");
            assertEquals("1.2.3", version.toString());
        }

        @Test
        void should_include_pre_release_in_string() {
            Version version = new Version("2.0.0-rc.1");
            assertEquals("2.0.0-rc.1", version.toString());
        }

        @Test
        void should_handle_complex_pre_release_format() {
            Version version = new Version("1.0.0-alpha.beta.1+sha.1234");
            assertEquals("1.0.0-alpha.beta.1+sha.1234", version.toString());
        }

        @Test
        void should_include_build_metadata_in_string() {
            Version version = new Version("1.2.3-rc.1+sha.1234");
            assertEquals("1.2.3-rc.1+sha.1234", version.toString());
        }

        @Test
        void should_handle_multiple_build_identifiers() {
            Version version = new Version("2.0.0+exp.sha.5114f85");
            assertEquals("2.0.0+exp.sha.5114f85", version.toString());
        }

        @Test
        void should_format_core_version_with_build_metadata() {
            Version version = new Version("3.1.4+001");
            assertEquals("3.1.4+001", version.toString());
        }
    }

    @Nested
    class BuildMetadataTests {
        @Test
        void should_return_build_metadata() {
            Version version = new Version("1.0.0-rc.1+sha.1234");
            assertEquals("sha.1234", version.getBuildMetadata());
        }

        @Test
        void should_return_null_when_no_build_metadata() {
            Version version = new Version("2.1.0");
            assertNull(version.getBuildMetadata());
        }
    }
}
