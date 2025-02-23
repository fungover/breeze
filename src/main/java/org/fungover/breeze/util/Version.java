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

    private static final Pattern VERSION_PATTERN = Pattern.compile(
            "^(?<major>0|[1-9]\\d*)\\.(?<minor>0|[1-9]\\d*)\\.(?<patch>0|[1-9]\\d*)" +
                    "(?:-(?<prerelease>[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*))?$"
    );

    public Version(String version) {
        validateInput(version);
        Matcher matcher = VERSION_PATTERN.matcher(version);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format: " + version);
        }

        this.major = Integer.parseInt(matcher.group("major"));
        this.minor = Integer.parseInt(matcher.group("minor"));
        this.patch = Integer.parseInt(matcher.group("patch"));
        this.preRelease = matcher.group("prerelease");

        validatePreReleaseStructure();
    }

    private void validateInput(String version) {
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("Version string must not be null or empty");
        }
    }

    private void validatePreReleaseStructure() {
        if (preRelease != null) {
            Arrays.stream(preRelease.split("\\.")).forEach(this::validatePreReleasePart);
        }
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

    private boolean isNumeric(String s) {
        return s.matches("\\d+");
    }

    private void validateNumericIdentifier(String part) {
        if (part.length() > 1 && part.startsWith("0")) {
            throw new IllegalArgumentException("Numeric identifier contains leading zeros: " + part);
        }
    }

    private void validateAlphanumericIdentifier(String part) {
        if (!part.matches("[a-zA-Z0-9-]*")) {
            throw new IllegalArgumentException("Invalid characters in: " + part);
        }
        if (!part.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("Non-numeric identifier requires letter: " + part);
        }
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

        return comparePreRelease(this.preRelease, other.preRelease);
    }

    private int comparePreRelease(String a, String b) {
        if (a == null && b == null) return 0;
        if (a == null) return 1;  // No pre-release has higher precedence
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

    private int compareIdentifier(String a, String b) {
        boolean aNumeric = a.matches("\\d+");
        boolean bNumeric = b.matches("\\d+");

        if (aNumeric && bNumeric) {
            return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
        } else if (aNumeric) {
            return -1;  // Numeric identifiers have lower precedence
        } else if (bNumeric) {
            return 1;   // Non-numeric has higher precedence
        } else {
            return a.compareTo(b);  // ASCII order comparison
        }
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
        return this.compareTo(other) > 0;
    }

    public boolean isLessThan(Version other) {
        return this.compareTo(other) < 0;
    }

    public boolean isEqualTo(Version other) {
        return this.compareTo(other) == 0;
    }
}