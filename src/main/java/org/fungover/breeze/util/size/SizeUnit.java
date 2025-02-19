package org.fungover.breeze.util.size;

import java.math.BigDecimal;
import java.math.MathContext;

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

    SizeUnit(String suffix, boolean isBit, int base, int exponent) {
        this.suffix = suffix;
        this.isBit = isBit;
        this.base = base;
        this.exponent = exponent;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getBase() {
        return base;
    }

    public int getExponent() {
        return exponent;
    }

    public BigDecimal toBytes(BigDecimal quantity) {
        BigDecimal baseFactor = BigDecimal.valueOf(base).pow(exponent);
        BigDecimal value = quantity.multiply(baseFactor);
        return isBit ? value.divide(BigDecimal.valueOf(8), MathContext.DECIMAL128) : value;
    }

    public BigDecimal fromBytes(BigDecimal bytes) {
        BigDecimal baseFactor = BigDecimal.valueOf(base).pow(exponent);
        BigDecimal value = isBit ? bytes.multiply(BigDecimal.valueOf(8)) : bytes;
        return value.divide(baseFactor, MathContext.DECIMAL128);
    }
}
