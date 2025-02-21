package org.fungover.breeze.util.size;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Enum representing different size units for bytes and bits,
 * both in decimal and binary systems.
 */
public enum SizeUnit {

    // Byte-based decimal units
    BYTES("B", false, 1000, 0),
    KILOBYTES("KB", false, 1000, 1),
    MEGABYTES("MB", false, 1000, 2),
    GIGABYTES("GB", false, 1000, 3),
    TERABYTES("TB", false, 1000, 4),

    // Byte-based binary units
    KIBIBYTES("KiB", false, 1024, 1),
    MEBIBYTES("MiB", false, 1024, 2),
    GIBIBYTES("GiB", false, 1024, 3),
    TEBIBYTES("TiB", false, 1024, 4),

    // Bit-based decimal units
    BITS("b", true, 1000, 0),
    KILOBITS("Kb", true, 1000, 1),
    MEGABITS("Mb", true, 1000, 2),
    GIGABITS("Gb", true, 1000, 3),
    TERABITS("Tb", true, 1000, 4);

    private final String suffix;
    private final boolean isBit;
    private final int base;
    private final int exponent;
    private final BigDecimal baseFactor;

    /**
     * Constructor for SizeUnit.
     *
     * @param suffix    the suffix representing the unit (e.g., "KB", "MiB")
     * @param isBit    true if the unit is bit-based, false if byte-based
     * @param base     the base of the unit (e.g., 1000 for decimal, 1024 for binary)
     * @param exponent the exponent for the unit (e.g., 0 for bytes, 1 for kilobytes)
     */
    SizeUnit(String suffix, boolean isBit, int base, int exponent) {
        this.suffix = suffix;
        this.isBit = isBit;
        this.base = base;
        this.exponent = exponent;
        this.baseFactor = BigDecimal.valueOf(base).pow(exponent);
    }

    /**
     * Returns the suffix of the unit (e.g., "KB", "MiB").
     *
     * @return the unit suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Returns the base of the unit (e.g., 1000 for decimal, 1024 for binary).
     *
     * @return the unit base
     */
    public int getBase() {
        return base;
    }

    /**
     * Returns the exponent of the unit (e.g., 0 for bytes, 1 for kilobytes).
     *
     * @return the unit exponent
     */
    public int getExponent() {
        return exponent;
    }

    /**
     * Converts a quantity from this unit to bytes.
     *
     * @param quantity the quantity to convert
     * @return the converted value in bytes
     */
    public BigDecimal toBytes(BigDecimal quantity) {
        BigDecimal value = quantity.multiply(baseFactor);
        return isBit ? value.divide(BigDecimal.valueOf(8), MathContext.DECIMAL128) : value;
    }

    /**
     * Converts a quantity from bytes to this unit.
     *
     * @param bytes the quantity in bytes
     * @return the converted value in this unit
     */
    public BigDecimal fromBytes(BigDecimal bytes) {
        BigDecimal value = isBit ? bytes.multiply(BigDecimal.valueOf(8)) : bytes;
        return value.divide(baseFactor, MathContext.DECIMAL128);
    }

    /**
     * Returns the SizeUnit corresponding to the given suffix.
     *
     * @param suffix the suffix to match
     * @return the matching SizeUnit
     * @throws IllegalArgumentException if the suffix is unrecognized
     */
    public static SizeUnit fromSuffix(String suffix) {
        for (SizeUnit unit : values()) {
            if (unit.getSuffix().equalsIgnoreCase(suffix)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown size unit: " + suffix);
    }

    /**
     * Chooses an appropriate SizeUnit for a given size.
     *
     * @param size           the size in bytes
     * @param useBinaryUnits true to use binary units, false to use decimal units
     * @return the chosen SizeUnit
     */
    public static SizeUnit chooseUnit(long size, boolean useBinaryUnits) {
        if (useBinaryUnits) {
            if (size < 1024) return BYTES;
            if (size < 1024 * 1024) return KIBIBYTES;
            if (size < 1024L * 1024 * 1024) return MEBIBYTES;
            return GIBIBYTES;
        } else {
            if (size < 1_000) return BYTES;
            if (size < 1_000_000) return KILOBYTES;
            if (size < 1_000_000_000) return MEGABYTES;
            return GIGABYTES;
        }
    }

    /**
     * Chooses an appropriate SizeUnit for a given bit rate.
     *
     * @param bitsPerSecond the bit rate in bits per second
     * @return the chosen SizeUnit
     */
    public static SizeUnit chooseRateUnit(long bitsPerSecond) {
        if (bitsPerSecond < 1_000) return BITS;
        if (bitsPerSecond < 1_000_000) return KILOBITS;
        if (bitsPerSecond < 1_000_000_000) return MEGABITS;
        return GIGABITS;
    }
}
