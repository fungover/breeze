package org.fungover.breeze.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Version class is a tool for interpreting, comparing, and managing version numbers.
 * It supports standard version numbers and versions with pre-release identifiers,
 * following semantic versioning principles.
 * <p>
 * The version format is MAJOR.MINOR.PATCH[-PRE-RELEASE], where each component is a non-negative integer,
 * and the optional pre-release identifier is a string composed of alphanumeric characters, hyphens, and dots.
 * </p>
 * <p>
 * Valid version examples:
 * <ul>
 *   <li>1.0.0</li>
 *   <li>2.3.4-beta</li>
 *   <li>5.6.7-alpha.1</li>
 * </ul>
 * Invalid version examples:
 * <ul>
 *   <li>1.0 (missing components)</li>
 *   <li>-1.0.0 (negative component)</li>
 *   <li>1.0.0.0 (extra component)</li>
 * </ul>
 * </p>
 */
public class Version implements Comparable<Version> {
    private final int major;
    private final int minor;
    private final int patch;
    private final String preRelease;

    // Regular expression pattern to match version strings
    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(?:-([0-9A-Za-z.-]+))?");
    private static final int GROUP_MAJOR = 1;
    private static final int GROUP_MINOR = 2;
    private static final int GROUP_PATCH = 3;
    private static final int GROUP_PRE_RELEASE = 4;

    /**
     * Creates a new Version instance based on a version string.
     *
     * @param version The version string to parse.
     * @throws IllegalArgumentException if the version string is null, empty, or invalid.
     */
    public Version(String version) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("Version string must not be null or empty");
        }

        Matcher matcher = VERSION_PATTERN.matcher(version);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format: " + version);
        }

        this.major = parseNonNegative(matcher.group(GROUP_MAJOR), "major");
        this.minor = parseNonNegative(matcher.group(GROUP_MINOR), "minor");
        this.patch = parseNonNegative(matcher.group(GROUP_PATCH), "patch");
        this.preRelease = matcher.group(GROUP_PRE_RELEASE);
    }

    /**
     * Compares this version with another version following semantic versioning rules.
     * <p>
     * The comparison is performed in the following order:
     * <ol>
     *   <li>Major version number</li>
     *   <li>Minor version number</li>
     *   <li>Patch version number</li>
     *   <li>Pre-release identifiers (if present)</li>
     * </ol>
     *
     * Pre-release versions have lower precedence than normal versions. When comparing pre-release
     * versions with the same major, minor, and patch versions, lexicographical ASCII comparison
     * is used for the pre-release identifiers. Note that this comparison treats identifiers as
     * strings, which may not handle numeric identifiers as specified by SemVer (e.g., "alpha.11"
     * is considered greater than "alpha.2" lexicographically, even though 11 > 2 numerically).
     * <p>
     * Examples:
     * <ul>
     *   <li>{@code new Version("1.0.0").compareTo(new Version("1.0.0-beta"))}
     *       returns positive value (1.0.0 &gt; 1.0.0-beta)</li>
     *   <li>{@code new Version("1.0.0-alpha").compareTo(new Version("1.0.0-beta"))}
     *       returns negative value (alpha &lt; beta)</li>
     *   <li>{@code new Version("1.2.3").compareTo(new Version("1.2.3"))}
     *       returns 0 (equal)</li>
     *   <li>{@code new Version("1.0.0-alpha.11").compareTo(new Version("1.0.0-alpha.2"))}
     *       returns positive value ("alpha.11" is lexicographically greater than "alpha.2")</li>
     * </ul>
     *
     * @param other The other version to compare to
     * @return A negative integer, zero, or a positive integer as this version is
     *         less than, equal to, or greater than the specified version
     * @throws NullPointerException if the other version is null
     */
    @Override
    public int compareTo(Version other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare with null version");
        }
        int comparison = Integer.compare(this.major, other.major);
        if (comparison != 0) return comparison;

        comparison = Integer.compare(this.minor, other.minor);
        if (comparison != 0) return comparison;

        comparison = Integer.compare(this.patch, other.patch);
        if (comparison != 0) return comparison;

        if (this.preRelease == null && other.preRelease == null) return 0;
        if (this.preRelease == null) return 1;
        if (other.preRelease == null) return -1;

        return this.preRelease.compareTo(other.preRelease);
    }

    /**
     * Checks if this version is greater than another version.
     *
     * @param other The other version to compare to.
     * @return True if this version is greater than the other version, false otherwise.
     */
    public boolean isGreaterThan(Version other) {
        return this.compareTo(other) > 0;
    }

    /**
     * Checks if this version is less than another version.
     *
     * @param other The other version to compare to.
     * @return True if this version is less than the other version, false otherwise.
     */
    public boolean isLessThan(Version other) {
        return this.compareTo(other) < 0;
    }

    /**
     * Checks if this version is equal to another version.
     *
     * @param other The other version to compare to.
     * @return True if this version is equal to the other version, false otherwise.
     */
    public boolean isEqual(Version other) {
        return this.compareTo(other) == 0;
    }

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
     * Returns the string representation of the version.
     *
     * @return The version string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(major)
                .append('.')
                .append(minor)
                .append('.')
                .append(patch);
        return preRelease == null ? sb.toString() : sb.append('-').append(preRelease).toString();
    }

    /**
     * Parses a value and ensures it is non-negative.
     *
     * @param value The value to parse.
     * @param component The component name (major, minor, patch).
     * @return The parsed integer value.
     * @throws NumberFormatException if the value is not a valid integer.
     */
    private int parseNonNegative(String value, String component) {
        int parsedValue = Integer.parseInt(value);
        if (parsedValue < 0) {
            throw new IllegalArgumentException(component + " version component must be non-negative");
        }
        return parsedValue;
    }
}
