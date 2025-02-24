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

    private static final Pattern VERSION_PATTERN = Pattern.compile(
            "^(?<major>0|[1-9]\\d*+)\\." +
                    "(?<minor>0|[1-9]\\d*+)\\." +
                    "(?<patch>0|[1-9]\\d*+)" +
                    "(?:-(?<prerelease>[a-zA-Z0-9-]++(?:\\.[a-zA-Z0-9-]++)*+))?" +
                    "(?:\\+(?<build>[0-9A-Za-z-]++(?:\\.[0-9A-Za-z-]++)*+))?$"
    );

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

    private void validateInput(String version) {
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("Version cannot be null or empty");
        }
    }

    private void validatePreReleaseStructure() {
        if (preRelease != null) {
            Arrays.stream(preRelease.split("\\.", -1))
                    .forEach(this::validatePreReleasePart);
        }
    }

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

    private void validateBuildMetadata() {
        if (buildMetadata != null) {
            Arrays.stream(buildMetadata.split("\\.", -1))
                    .forEach(this::validateBuildIdentifier);
        }
    }

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

    private boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

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

    private void validateAlphanumericIdentifier(String part) {
        if (!isValidAlphanumeric(part)) {
            throw new IllegalArgumentException("Invalid characters in identifier: " + part);
        }
    }

    private boolean isValidAlphanumeric(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '-') {
                return false;
            }
        }
        return true;
    }

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
        StringBuilder sb = new StringBuilder();
        sb.append(major).append(".").append(minor).append(".").append(patch);
        if (preRelease != null) sb.append("-").append(preRelease);
        if (buildMetadata != null) sb.append("+").append(buildMetadata);
        return sb.toString();
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

    public String getBuildMetadata() {
        return buildMetadata;
    }
}