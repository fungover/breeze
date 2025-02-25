package org.fungover.breeze.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Version implements Comparable<Version> {
    private final int major;
    private final int minor;
    private final int patch;
    private final String preRelease;
    private final String buildMetadata;

    /**
     * Regular expression pattern to match valid version strings.
     * The pattern includes groups for major, minor, patch, pre-release, and build metadata.
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
     * @throws IllegalArgumentException if the version string is invalid.
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
     * Parses a numeric component of the version string.
     *
     * @param component The numeric component to parse.
     * @return The parsed integer value.
     * @throws IllegalArgumentException if the component is invalid.
     */
    private int parseNumericComponent(String component) {
        try {
            int value = Integer.parseInt(component);
            if (value < 0) {
                throw new IllegalArgumentException("Negative version component: " + component);
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
     * @throws IllegalArgumentException if the version string is null or empty.
     */
    private void validateInput(String version) {
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("Version cannot be null or empty");
        }
    }

    /**
     * Validates the structure of the pre-release identifier.
     *
     * @throws IllegalArgumentException if the pre-release identifier is invalid.
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
     * @throws IllegalArgumentException if the part is invalid.
     */
    private void validatePreReleasePart(String part) {
        if (part.isEmpty()) {
            throw new IllegalArgumentException("Empty pre-release component");
        }
        if (isNumeric(part)) {
            validateNumericIdentifier(part);
        } else {
            validateAlphanumericIdentifier(part);
        }
    }

    /**
     * Validates the structure of the build metadata.
     *
     * @throws IllegalArgumentException if the build metadata is invalid.
     */
    private void validateBuildMetadata() {
        if (buildMetadata != null) {
            Arrays.stream(buildMetadata.split("\\.", -1))
                    .forEach(this::validateBuildIdentifier);
        }
    }

    /**
     * Validates a build metadata identifier.
     *
     * @param identifier The build metadata identifier to validate.
     * @throws IllegalArgumentException if the identifier is invalid.
     */
    private void validateBuildIdentifier(String identifier) {
        if (identifier.isEmpty()) {
            throw new IllegalArgumentException("Empty build metadata component");
        }
        for (char c : identifier.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '-') {
                throw new IllegalArgumentException("Invalid build metadata character: " + c);
            }
        }
    }

    /**
     * Checks if a string is numeric.
     *
     * @param s The string to check.
     * @return True if the string is numeric, false otherwise.
     */
    private boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * Validates a numeric identifier.
     *
     * @param part The numeric identifier to validate.
     * @throws IllegalArgumentException if the identifier is invalid.
     */
    private void validateNumericIdentifier(String part) {
        if (part.length() > 1 && part.startsWith("0")) {
            throw new IllegalArgumentException("Numeric identifier contains leading zeros: " + part);
        }
        try {
            Integer.parseInt(part);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Numeric component too large: " + part, e);
        }
    }

    /**
     * Validates an alphanumeric identifier.
     *
     * @param part The alphanumeric identifier to validate.
     * @throws IllegalArgumentException if the identifier is invalid.
     */
    private void validateAlphanumericIdentifier(String part) {
        if (!isValidAlphanumeric(part)) {
            throw new IllegalArgumentException("Invalid characters in identifier: " + part);
        }
    }

    /**
     * Checks if a string is a valid alphanumeric identifier.
     *
     * @param input The string to check.
     * @return True if the string is a valid alphanumeric identifier, false otherwise.
     */
    private boolean isValidAlphanumeric(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '-') {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares this version with another version.
     *
     * @param other The version to compare to.
     * @return A negative integer, zero, or a positive integer as this version is less than, equal to, or greater than the specified version.
     * @throws NullPointerException if the specified version is null.
     */
    @Override
    public int compareTo(Version other) {
        if (other == null) throw new NullPointerException("Cannot compare to null version");

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

        String[] partsA = a.split("\\.");
        String[] partsB = b.split("\\.");
        int minLength = Math.min(partsA.length, partsB.length);

        for (int i = 0; i < minLength; i++) {
            int cmp = compareIdentifier(partsA[i], partsB[i]);
            if (cmp != 0) return cmp;
        }

        return Integer.compare(partsA.length, partsB.length);
    }

    /**
     * Compares two identifiers.
     *
     * @param a The first identifier.
     * @param b The second identifier.
     * @return A negative integer, zero, or a positive integer as the first identifier is less than, equal to, or greater than the second identifier.
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
     * @param o The object to compare to.
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
     * @return The hash code of this version.
     */
    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, preRelease);
    }

    /**
     * Returns the string representation of this version.
     *
     * @return The string representation of this version.
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
     * @param other The version to compare to.
     * @return True if this version is greater than the other version, false otherwise.
     */
    public boolean isGreaterThan(Version other) {
        return compareTo(other) > 0;
    }

    /**
     * Checks if this version is less than another version.
     *
     * @param other The version to compare to.
     * @return True if this version is less than the other version, false otherwise.
     */
    public boolean isLessThan(Version other) {
        return compareTo(other) < 0;
    }

    /**
     * Checks if this version is equal to another version.
     *
     * @param other The version to compare to.
     * @return True if this version is equal to the other version, false otherwise.
     */
    public boolean isEqualTo(Version other) {
        return compareTo(other) == 0;
    }

    /**
     * Returns the build metadata of this version.
     *
     * @return The build metadata of this version.
     */
    public String getBuildMetadata() {
        return buildMetadata;
    }
}
