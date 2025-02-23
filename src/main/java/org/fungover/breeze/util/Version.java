package org.fungover.breeze.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Semantic Versioning 2.0.0 (SemVer) compatible version number.
 * <p>
 * This class provides immutable version objects that support parsing, validation,
 * and comparison of version strings according to the SemVer 2.0.0 specification.
 *
 * <h3>Key Features:</h3>
 * <ul>
 *   <li>Strict validation of version format</li>
 *   <li>Case-sensitive comparison operations</li>
 *   <li>Support for pre-release identifiers</li>
 *   <li>Proper handling of numeric vs. alphanumeric identifiers</li>
 * </ul>
 *
 * <h3>Version Format:</h3>
 * <pre>
 * [major].[minor].[patch][-preRelease]
 * </pre>
 *
 * <h3>Example Usage:</h3>
 * <pre>{@code
 * // Create version instances
 * Version release = new Version("2.4.0");
 * Version prerelease = new Version("2.4.0-rc.1");
 *
 * // Compare versions
 * if (prerelease.isLessThan(release)) {
 *     System.out.println("Prerelease version is older");
 * }
 * }</pre>
 */
public final class Version implements Comparable<Version> {
    private final int major;
    private final int minor;
    private final int patch;
    private final String preRelease;

    private static final Pattern VERSION_PATTERN = Pattern.compile(
            "^(?<major>0|[1-9]\\d*+)\\.(?<minor>0|[1-9]\\d*+)\\.(?<patch>0|[1-9]\\d*+)" +
                    "(?:-(?<prerelease>[a-zA-Z0-9-]++(?:\\.[a-zA-Z0-9-]++)*+))?$"
    );

    /**
     * Constructs a new Version instance from a SemVer-compliant string.
     *
     * @param version The version string to parse
     * @throws IllegalArgumentException If:
     * <ul>
     *   <li>Input is null or empty</li>
     *   <li>Format doesn't match SemVer specifications</li>
     *   <li>Contains negative numbers</li>
     *   <li>Has numeric identifiers with leading zeros</li>
     *   <li>Contains invalid characters</li>
     *   <li>Has improper hyphen placement</li>
     * </ul>
     */
    public Version(String version) {
        validateInput(version);
        Matcher matcher = VERSION_PATTERN.matcher(version);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format: " + version);
        }

        try {
            this.major = Integer.parseInt(matcher.group("major"));
            this.minor = Integer.parseInt(matcher.group("minor"));
            this.patch = Integer.parseInt(matcher.group("patch"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Version component too large", e);
        }

        this.preRelease = matcher.group("prerelease");
        validatePreReleaseStructure();
    }

    private void validateInput(String version) {
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("Version string must not be null or empty");
        }
    }

    private void validatePreReleaseStructure() {
        if (preRelease == null) return;
        Arrays.stream(preRelease.split("\\.")).forEach(this::validatePreReleasePart);
    }

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

    private void validateNumericIdentifier(String part) {
        if (part.length() > 1 && part.charAt(0) == '0') {
            throw new IllegalArgumentException("Numeric identifier has leading zero: " + part);
        }
    }

    private void validateAlphanumericIdentifier(String part) {
        if (!part.matches("^[a-zA-Z0-9-]*$")) {
            throw new IllegalArgumentException("Invalid characters in: " + part);
        }
        if (!containsLetter(part)) {
            throw new IllegalArgumentException("Non-numeric identifier requires letter: " + part);
        }
    }

    private boolean isNumeric(String s) {
        return s.matches("\\d+");
    }

    private boolean containsLetter(String s) {
        return s.chars().anyMatch(Character::isLetter);
    }

    @Override
    public int compareTo(Version other) {
        if (other == null) throw new NullPointerException("Cannot compare with null version");

        int result = Integer.compare(major, other.major);
        if (result != 0) return result;

        result = Integer.compare(minor, other.minor);
        if (result != 0) return result;

        result = Integer.compare(patch, other.patch);
        if (result != 0) return result;

        return comparePreReleaseIdentifiers(this.preRelease, other.preRelease);
    }

    private int comparePreReleaseIdentifiers(String a, String b) {
        if (a == null) return b == null ? 0 : 1;
        if (b == null) return -1;

        String[] partsA = a.split("\\.");
        String[] partsB = b.split("\\.");
        int minLength = Math.min(partsA.length, partsB.length);

        for (int i = 0; i < minLength; i++) {
            int comparison = compareIdentifier(partsA[i], partsB[i]);
            if (comparison != 0) return comparison;
        }

        return Integer.compare(partsA.length, partsB.length);
    }

    private int compareIdentifier(String a, String b) {
        boolean aNumeric = isNumeric(a);
        boolean bNumeric = isNumeric(b);

        if (aNumeric && bNumeric) {
            return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
        }

        if (aNumeric) return -1;
        if (bNumeric) return 1;
        return a.compareTo(b);
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

    @Override
    public String toString() {
        return preRelease == null ?
                String.format("%d.%d.%d", major, minor, patch) :
                String.format("%d.%d.%d-%s", major, minor, patch, preRelease);
    }

    public boolean isGreaterThan(Version other) {
        return compareTo(other) > 0;
    }

    public boolean isLessThan(Version other) {
        return compareTo(other) < 0;
    }

    public boolean isEqualTo(Version other) {
        return compareTo(other) == 0;
    }
}