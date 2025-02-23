package org.fungover.breeze.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Semantic Versioning 2.0.0 compatible version number
 * with support for pre-release identifiers and build metadata.
 *
 * <p>Implements natural version ordering with the following precedence rules:
 * <ol>
 *   <li>Major, minor, and patch versions compared numerically</li>
 *   <li>Pre-release versions have lower precedence than normal versions</li>
 *   <li>Pre-release identifiers compare with ASCII ordering except for numeric values</li>
 * </ol>
 *
 * @see <a href="https://semver.org">Semantic Versioning 2.0.0</a>
 */
public final class Version implements Comparable<Version> {

    /**
     * Core version components (major.minor.patch)
     */
    private final int major;
    private final int minor;
    private final int patch;

    /**
     * Optional pre-release identifiers (hyphen-separated)
     */
    private final String preRelease;

    /**
     * Pattern for valid semantic versions:
     * - Major, minor, patch as non-negative integers
     * - Optional pre-release identifiers containing [0-9A-Za-z-]
     * - No leading zeros in numeric identifiers
     */
    private static final Pattern VERSION_PATTERN = Pattern.compile(
            "^(?<major>\\d+)\\.(?<minor>\\d+)\\.(?<patch>\\d+)" +
                    "(?:-(?<prerelease>[0-9A-Za-z-]+(?:\\.[0-9A-Za-z-]+)*))?$"
    );

    /**
     * Constructs a Version instance from a semantic version string
     *
     * @param version The version string to parse
     * @throws IllegalArgumentException If version format is invalid
     * @throws NullPointerException If version is null
     */
    public Version(String version) {
        validateInput(version);
        Matcher matcher = VERSION_PATTERN.matcher(version);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format: " + version);
        }

        this.major = parseComponent(matcher.group("major"), "major");
        this.minor = parseComponent(matcher.group("minor"), "minor");
        this.patch = parseComponent(matcher.group("patch"), "patch");
        this.preRelease = matcher.group("prerelease");

        validatePreRelease();
    }

    /**
     * Core version comparison algorithm following SemVer 2.0.0 rules
     *
     * @param other The version to compare against
     * @return Negative, zero, or positive if this version is less than,
     *         equal to, or greater than the specified version
     */
    @Override
    public int compareTo(Version other) {
        if (other == null) throw new NullPointerException("Cannot compare with null version");

        int comparison = Integer.compare(major, other.major);
        if (comparison != 0) return comparison;

        comparison = Integer.compare(minor, other.minor);
        if (comparison != 0) return comparison;

        comparison = Integer.compare(patch, other.patch);
        if (comparison != 0) return comparison;

        return comparePreReleases(this.preRelease, other.preRelease);
    }

    // Region: Comparison helper methods
    // ================================

    /**
     * Compares pre-release identifiers according to SemVer 2.0.0 rules
     *
     * @return Comparison result based on identifier precedence rules
     */
    private int comparePreReleases(String a, String b) {
        if (a == null) return b == null ? 0 : 1;
        if (b == null) return -1;

        String[] partsA = a.split("\\.");
        String[] partsB = b.split("\\.");
        int minLength = Math.min(partsA.length, partsB.length);

        for (int i = 0; i < minLength; i++) {
            int result = compareIdentifierParts(partsA[i], partsB[i]);
            if (result != 0) return result;
        }

        return Integer.compare(partsA.length, partsB.length);
    }

    /**
     * Compares individual pre-release identifier parts with
     * numeric/non-numeric handling rules
     */
    private int compareIdentifierParts(String a, String b) {
        boolean aNumeric = isNumeric(a);
        boolean bNumeric = isNumeric(b);

        if (aNumeric && bNumeric) {
            return compareNumeric(a, b);
        }

        // Numeric identifiers have lower precedence than non-numeric
        if (aNumeric) return -1;
        if (bNumeric) return 1;
        return a.compareTo(b);
    }
    // End region

    // Region: Validation and parsing
    // ==============================

    /**
     * Validates version string basic requirements
     */
    private void validateInput(String version) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("Version string must not be null or empty");
        }
    }

    /**
     * Parses and validates version number components
     *
     * @throws IllegalArgumentException For invalid numeric formats
     */
    private int parseComponent(String value, String componentName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + componentName + " format: " + value);
        }
    }

    /**
     * Validates pre-release identifier rules
     * - No empty identifiers
     * - No leading/trailing hyphens
     * - No leading zeros in numeric identifiers
     */
    private void validatePreRelease() {
        if (preRelease == null) return;

        String[] parts = preRelease.split("\\.");
        for (String part : parts) {
            if (part.isEmpty()) {
                throw new IllegalArgumentException("Empty pre-release part");
            }
            if (part.startsWith("-") || part.endsWith("-")) {
                throw new IllegalArgumentException("Invalid hyphen placement: " + part);
            }
            if (isNumeric(part) && part.length() > 1 && part.charAt(0) == '0') {
                throw new IllegalArgumentException("Numeric identifier with leading zero: " + part);
            }
        }
    }
    // End region

    // Region: Utility methods
    // ======================

    /**
     * Checks if a string represents a numeric identifier
     */
    private boolean isNumeric(String s) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares numeric identifiers as zero-prefixed strings
     */
    private int compareNumeric(String a, String b) {
        // Direct length comparison for different digit counts
        if (a.length() != b.length()) {
            return a.length() - b.length();
        }
        // Lexicographical compare for same length numbers
        return a.compareTo(b);
    }
    // End region

    // Region: Standard Java methods
    // =============================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return major == version.major &&
                minor == version.minor &&
                patch == version.patch &&
                Objects.equals(preRelease, version.preRelease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, preRelease);
    }

    /**
     * Returns the string representation in SemVer 2.0.0 format
     */
    @Override
    public String toString() {
        return preRelease == null ?
                String.format("%d.%d.%d", major, minor, patch) :
                String.format("%d.%d.%d-%s", major, minor, patch, preRelease);
    }

    /**
     * @return true if this version is newer than the other version
     */
    public boolean isGreaterThan(Version other) {
        return compareTo(other) > 0;
    }

    /**
     * @return true if this version is older than the other version
     */
    public boolean isLessThan(Version other) {
        return compareTo(other) < 0;
    }

    /**
     * @return true if both versions have identical version components
     */
    public boolean isEqualTo(Version other) {
        return compareTo(other) == 0;
    }
}