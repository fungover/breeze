package org.fungover.breeze.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Version class represents a semantic versioning system.
 * It includes major, minor, patch, pre-release, and build metadata components.
 */
public final class Version implements Comparable<Version> {
    /**
     * The major version number.
     */
    private final int major;

    /**
     * The minor version number.
     */
    private final int minor;

    /**
     * The patch version number.
     */
    private final int patch;

    /**
     * The pre-release version identifier.
     */
    private final String preRelease;

    /**
     * The build metadata identifier.
     */
    private final String buildMetadata;

    /**
     * The pattern used to validate and parse version strings.
     */
    private static final Pattern VERSION_PATTERN = Pattern.compile(
            "^(?<major>0|[1-9]\\d*+)\\." +
                    "(?<minor>0|[1-9]\\d*+)\\." +
                    "(?<patch>0|[1-9]\\d*+)" +
                    "(?:-(?<prerelease>[a-zA-Z0-9-]++(?:\\.[a-zA-Z0-9-]++)*+))?" +
                    "(?:\\+(?<build>[0-9A-Za-z-]++(?:\\.[0-9A-Za-z-]++)*+))?$"
    );

    /**
     * Constructs a Version object from a version string.
     *
     * @param version The version string to parse.
     * @throws IllegalArgumentException If the version string is invalid.
     */
    public Version(String version) {
        validateInput(version);
        Matcher matcher = VERSION_PATTERN.matcher(version);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format: " + version);
        }

        try {
            this.major = parseNumericComponent(matcher.group("major"));
            this.minor = parseNumericComponent(matcher.group("minor"));
            this.patch = parseNumericComponent(matcher.group("patch"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Version component exceeds integer limits", e);
        }

        this.preRelease = matcher.group("prerelease");
        this.buildMetadata = matcher.group("build");

        validatePreReleaseStructure();
        validateBuildMetadata();
    }

    /**
     * Parses a numeric component from a string.
     *
     * @param component The numeric component as a string.
     * @return The parsed integer value.
     * @throws IllegalArgumentException If the component is not a valid integer or is negative.
     */
    private int parseNumericComponent(String component) {
        try {
            int value = Integer.parseInt(component);
            if (value < 0) {
                throw new IllegalArgumentException("Version components cannot be negative: " + component);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric component: " + component, e);
        }
    }

    /**
     * Validates the input version string.
     *
     * @param version The version string to validate.
     * @throws IllegalArgumentException If the version string is null or blank.
     */
    private void validateInput(String version) {
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("Version string must not be null or empty");
        }
    }

    /**
     * Validates the structure of the pre-release identifier.
     *
     * @throws IllegalArgumentException If the pre-release identifier is invalid.
     */
    private void validatePreReleaseStructure() {
        if (preRelease != null) {
            Arrays.stream(preRelease.split("\\.", -1))
                    .forEach(this::validatePreReleasePart);
        }
    }

    /**
     * Validates a part of the pre-release identifier.
     *
     * @param part The pre-release part to validate.
     * @throws IllegalArgumentException If the pre-release part is invalid.
     */
    private void validatePreReleasePart(String part) {
        if (part.isEmpty()) {
            throw new IllegalArgumentException("Empty pre-release identifier");
        }
        if (part.startsWith("-") || part.endsWith("-")) {
            throw new IllegalArgumentException("Invalid hyphen placement in: " + part);
        }
        if (isNumeric(part)) {
            validateNumericIdentifier(part);
        } else {
            validateAlphanumericIdentifier(part);
        }
    }

    /**
     * Validates the build metadata identifier.
     *
     * @throws IllegalArgumentException If the build metadata identifier is invalid.
     */
    private void validateBuildMetadata() {
        if (buildMetadata != null) {
            Arrays.stream(buildMetadata.split("\\.", -1))
                    .forEach(this::validateBuildIdentifier);
        }
    }

    /**
     * Validates a build metadata identifier part.
     *
     * @param identifier The build metadata identifier part to validate.
     * @throws IllegalArgumentException If the build metadata identifier part is invalid.
     */
    private void validateBuildIdentifier(String identifier) {
        if (identifier.isEmpty()) {
            throw new IllegalArgumentException("Empty build metadata identifier");
        }
        if (identifier.startsWith("-") || identifier.endsWith("-")) {
            throw new IllegalArgumentException("Invalid hyphen placement in build metadata: " + identifier);
        }
        if (!identifier.matches("[0-9A-Za-z-]+")) {
            throw new IllegalArgumentException("Invalid build metadata format: " + identifier);
        }
    }

    /**
     * Checks if a string is numeric.
     *
     * @param s The string to check.
     * @return True if the string is numeric, false otherwise.
     */
    private boolean isNumeric(String s) {
        return s.matches("\\d++");
    }

    /**
     * Validates a numeric identifier part.
     *
     * @param part The numeric identifier part to validate.
     * @throws IllegalArgumentException If the numeric identifier part is invalid.
     */
    private void validateNumericIdentifier(String part) {
        try {
            Integer.parseInt(part);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Numeric component too large: " + part, e);
        }
        if (part.length() > 1 && part.startsWith("0")) {
            throw new IllegalArgumentException("Numeric identifier contains leading zeros: " + part);
        }
    }

    /**
     * Validates an alphanumeric identifier part.
     *
     * @param part The alphanumeric identifier part to validate.
     * @throws IllegalArgumentException If the alphanumeric identifier part is invalid.
     */
    private void validateAlphanumericIdentifier(String part) {
        if (!part.matches("[a-zA-Z0-9-]++")) {
            throw new IllegalArgumentException("Invalid characters in: " + part);
        }
        if (!hasAtLeastOneLetter(part)) {
            throw new IllegalArgumentException("Non-numeric identifier requires at least one letter: " + part);
        }
    }

    /**
     * Checks if a string contains at least one letter.
     *
     * @param input The string to check.
     * @return True if the string contains at least one letter, false otherwise.
     */
    private boolean hasAtLeastOneLetter(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares this version with another version.
     *
     * @param other The other version to compare to.
     * @return A negative integer, zero, or a positive integer as this version is less than, equal to, or greater than the specified version.
     * @throws NullPointerException If the specified version is null.
     */
    @Override
    public int compareTo(Version other) {
        if (other == null) throw new NullPointerException("Cannot compare with null version");

        int result = Integer.compare(major, other.major);
        if (result != 0) return result;

        result = Integer.compare(minor, other.minor);
        if (result != 0) return result;

        result = Integer.compare(patch, other.patch);
        if (result != 0) return result;

        return comparePreRelease(this.preRelease, other.preRelease);
    }

    /**
     * Compares two pre-release identifiers.
     *
     * @param a The first pre-release identifier.
     * @param b The second pre-release identifier.
     * @return A negative integer, zero, or a positive integer as the first pre-release identifier is less than, equal to, or greater than the second pre-release identifier.
     */
    private int comparePreRelease(String a, String b) {
        if (a == null && b == null) return 0;
        if (a == null) return 1;
        if (b == null) return -1;

        String[] partsA = a.split("\\.", -1);
        String[] partsB = b.split("\\.", -1);
        int minLength = Math.min(partsA.length, partsB.length);

        for (int i = 0; i < minLength; i++) {
            int cmp = compareIdentifier(partsA[i], partsB[i]);
            if (cmp != 0) return cmp;
        }

        return Integer.compare(partsA.length, partsB.length);
    }

    /**
     * Compares two identifier parts.
     *
     * @param a The first identifier part.
     * @param b The second identifier part.
     * @return A negative integer, zero, or a positive integer as the first identifier part is less than, equal to, or greater than the second identifier part.
     */
    private int compareIdentifier(String a, String b) {
        boolean aNumeric = isNumeric(a);
        boolean bNumeric = isNumeric(b);

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

    /**
     * Checks if this version is equal to another version.
     *
     * @param o The other version to compare to.
     * @return True if the versions are equal, false otherwise.
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
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, preRelease);
    }

    /**
     * Returns the string representation of this version.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(major).append(".").append(minor).append(".").append(patch);
        if (preRelease != null) sb.append("-").append(preRelease);
        if (buildMetadata != null) sb.append("+").append(buildMetadata);
        return sb.toString();
    }

    /**
     * Checks if this version is greater than another version.
     *
     * @param other The other version to compare to.
     * @return True if this version is greater, false otherwise.
     */
    public boolean isGreaterThan(Version other) {
        return this.compareTo(other) > 0;
    }

    /**
     * Checks if this version is less than another version.
     *
     * @param other The other version to compare to.
     * @return True if this version is less, false otherwise.
     */
    public boolean isLessThan(Version other) {
        return this.compareTo(other) < 0;
    }

    /**
     * Checks if this version is equal to another version.
     *
     * @param other The other version to compare to.
     * @return True if the versions are equal, false otherwise.
     */
    public boolean isEqualTo(Version other) {
        return this.compareTo(other) == 0;
    }

    /**
     * Returns the build metadata of this version.
     *
     * @return The build metadata.
     */
    public String getBuildMetadata() {
        return buildMetadata;
    }
}
