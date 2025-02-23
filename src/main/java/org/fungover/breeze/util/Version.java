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
 * and the optional pre-release identifier is a string composed of alphanumeric characters and dots.
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
 *   <li>1.2.3-alpha..beta (consecutive dots)</li>
 *   <li>1.2.3-alpha- (trailing hyphen)</li>
 * </ul>
 */
public class Version implements Comparable<Version> {
    private final int major;
    private final int minor;
    private final int patch;
    private final String preRelease;

    private static final Pattern VERSION_PATTERN = Pattern.compile(
            "(\\d++)\\.(\\d++)\\.(\\d++)(?:-([0-9A-Za-z]++(?:\\.[0-9A-Za-z]++)*+))?"
    );
    private static final int GROUP_MAJOR = 1;
    private static final int GROUP_MINOR = 2;
    private static final int GROUP_PATCH = 3;
    private static final int GROUP_PRE_RELEASE = 4;

    /**
     * Constructs a Version object by parsing a version string.
     *
     * @param version the version string to parse
     * @throws IllegalArgumentException if the version string is invalid
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
        validatePreRelease(this.preRelease);
    }

    /**
     * Compares this version with another version.
     *
     * @param other the version to compare to
     * @return a negative integer, zero, or a positive integer as this version is less than, equal to, or greater than the specified version
     * @throws NullPointerException if the specified version is null
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

        return comparePreRelease(this.preRelease, other.preRelease);
    }

    private int comparePreRelease(String a, String b) {
        if (a == null && b == null) return 0;
        if (a == null) return 1;
        if (b == null) return -1;

        String[] partsA = a.split("\\.");
        String[] partsB = b.split("\\.");

        int maxLength = Math.max(partsA.length, partsB.length);

        for (int i = 0; i < maxLength; i++) {
            String partA = i < partsA.length ? partsA[i] : "";
            String partB = i < partsB.length ? partsB[i] : "";

            int result = comparePreReleasePart(partA, partB);
            if (result != 0) return result;
        }
        return 0;
    }

    private int comparePreReleasePart(String a, String b) {
        boolean aNumeric = a.matches("\\d+");
        boolean bNumeric = b.matches("\\d+");

        if (aNumeric && bNumeric) {
            return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
        } else if (aNumeric) {
            return -1;
        } else if (bNumeric) {
            return 1;
        } else {
            return a.compareTo(b);
        }
    }

    private void validatePreRelease(String preRelease) {
        if (preRelease == null) return;

        if (preRelease.split("\\.").length == 0) {
            throw new IllegalArgumentException("Empty pre-release identifier");
        }

        for (String part : preRelease.split("\\.")) {
            if (part.isEmpty()) {
                throw new IllegalArgumentException("Empty pre-release part");
            }
            if (part.matches("^0\\d+")) {
                throw new IllegalArgumentException(
                        "Numeric pre-release identifier contains leading zeros: " + preRelease
                );
            }
            if (part.matches(".*-.*")) {
                throw new IllegalArgumentException("Hyphens not allowed in pre-release identifiers: " + preRelease);
            }
        }
    }

    /**
     * Checks if this version is greater than another version.
     *
     * @param other the version to compare to
     * @return true if this version is greater than the specified version, false otherwise
     */
    public boolean isGreaterThan(Version other) {
        return this.compareTo(other) > 0;
    }

    /**
     * Checks if this version is less than another version.
     *
     * @param other the version to compare to
     * @return true if this version is less than the specified version, false otherwise
     */
    public boolean isLessThan(Version other) {
        return this.compareTo(other) < 0;
    }

    /**
     * Checks if this version is equal to another version.
     *
     * @param other the version to compare to
     * @return true if this version is equal to the specified version, false otherwise
     */
    public boolean isEqual(Version other) {
        return this.compareTo(other) == 0;
    }

    /**
     * Checks if this version is equal to another object.
     *
     * @param o the object to compare to
     * @return true if the object is a Version and is equal to this version, false otherwise
     */
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

    /**
     * Returns the hash code of this version.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, preRelease);
    }

    /**
     * Returns the string representation of this version.
     *
     * @return the string representation
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

    private int parseNonNegative(String value, String component) {
        int parsedValue = Integer.parseInt(value);
        if (parsedValue < 0) {
            throw new IllegalArgumentException(component + " version component must be non-negative");
        }
        return parsedValue;
    }
}
