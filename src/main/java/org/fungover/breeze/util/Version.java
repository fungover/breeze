package org.fungover.breeze.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Version class is a tool for parsing, comparing, and managing version numbers.
 * It supports standard version numbers and versions with pre-release identifiers,
 * following semantic versioning principles.
 */
public class Version implements Comparable<Version> {
    private final int major;
    private final int minor;
    private final int patch;
    private final String preRelease;

    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(?:-([0-9A-Za-z.-]+))?");
    private static final int GROUP_MAJOR = 1;
    private static final int GROUP_MINOR = 2;
    private static final int GROUP_PATCH = 3;
    private static final int GROUP_PRE_RELEASE = 4;

    /**
     * Creates a new Version instance based on a version string.
     *
     * @param version The version string to parse.
     * @throws IllegalArgumentException if the version string is null, empty, or does not match the expected format.
     */
    public Version(String version) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("Version string cannot be null or empty");
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
     * Compares this version with another version.
     *
     * @param other The version to compare with.
     * @return A negative integer, zero, or a positive integer as this version is less than, equal to, or greater than the specified version.
     * @throws NullPointerException if the specified version is null.
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
     * @param other The version to compare with.
     * @return True if this version is greater than the specified version, false otherwise.
     */
    public boolean isGreaterThan(Version other) {
        return this.compareTo(other) > 0;
    }

    /**
     * Checks if this version is less than another version.
     *
     * @param other The version to compare with.
     * @return True if this version is less than the specified version, false otherwise.
     */
    public boolean isLessThan(Version other) {
        return this.compareTo(other) < 0;
    }

    /**
     * Checks if this version is equal to another version.
     *
     * @param other The version to compare with.
     * @return True if this version is equal to the specified version, false otherwise.
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
     * @return The version as a string in the format "major.minor.patch(-preRelease)".
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
     * Parses a version component and ensures it is not negative.
     *
     * @param value The string value to parse.
     * @param component The name of the version component (e.g., "major", "minor", "patch").
     * @return The parsed integer value.
     * @throws IllegalArgumentException if the value is negative.
     */
    private int parseNonNegative(String value, String component) {
        int result = Integer.parseInt(value);
        if (result < 0) {
            throw new IllegalArgumentException(component + " version number cannot be negative: " + result);
        }
        return result;
    }
}
