package org.fungover.breeze.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Version-klassen är ett verktyg för att tolka, jämföra och hantera versionsnummer.
 * Den stöder standardversionsnummer och versioner med pre-release-identifierare,
 * enligt semantiska versionsprinciper.
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
     * Skapar en ny Version-instans baserat på en versionssträng.
     */
    public Version(String version) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("Versionssträngen får inte vara null eller tom");
        }

        Matcher matcher = VERSION_PATTERN.matcher(version);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Ogiltigt versionsformat: " + version);
        }

        this.major = parseNonNegative(matcher.group(GROUP_MAJOR), "major");
        this.minor = parseNonNegative(matcher.group(GROUP_MINOR), "minor");
        this.patch = parseNonNegative(matcher.group(GROUP_PATCH), "patch");
        this.preRelease = matcher.group(GROUP_PRE_RELEASE);
    }

    /**
     * Jämför denna version med en annan version.
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
     * Kontrollerar om denna version är större än en annan version.
     */
    public boolean isGreaterThan(Version other) {
        return this.compareTo(other) > 0;
    }

    /**
     * Kontrollerar om denna version är mindre än en annan version.
     */
    public boolean isLessThan(Version other) {
        return this.compareTo(other) < 0;
    }

    /**
     * Kontrollerar om denna version är lika med en annan version.
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
     * Returnerar strängrepresentationen av versionen.
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
     * Parsar ett värde och kontrollerar att det inte är negativt.
     */
    private int parseNonNegative(String value, String component) {
        return Integer.parseInt(value);
    }
}