package org.fungover.breeze.util.size;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Locale;

/**
 * Utility class for formatting and converting sizes and rates.
 * This class provides methods to format byte sizes and bit rates,
 * convert between different size units, and parse size strings.
 */
public class SizeFormatter {

    private static final Pattern SIZE_PATTERN = Pattern.compile(
            "^([-+]?(?:\\d+\\.\\d+|\\.\\d+|\\d+))(\\s*)([a-zA-Z]+)$",
            Pattern.CASE_INSENSITIVE
    );

    // Private constructor to prevent instantiation of this utility class
    private SizeFormatter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Formats a byte size into a human-readable string with appropriate units.
     *
     * @param bytes         the size in bytes
     * @param useBinaryBase whether to use binary (KiB, MiB) or decimal (KB, MB) units
     * @param decimalPlaces the number of decimal places to display
     * @return a formatted string representing the size
     */
    public static String autoFormat(long bytes, boolean useBinaryBase, int decimalPlaces) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException(String.format("Decimal places must be non-negative: %d", decimalPlaces));
        }

        List<SizeUnit> units = useBinaryBase ? getBinaryByteUnits() : getDecimalByteUnits();
        BigDecimal size = BigDecimal.valueOf(bytes);

        for (SizeUnit unit : units) {
            BigDecimal converted = unit.fromBytes(size);
            if (converted.abs().compareTo(BigDecimal.ONE) >= 0) {
                return format(converted, unit, decimalPlaces);
            }
        }
        return format(size, SizeUnit.BYTES, decimalPlaces);
    }

    /**
     * Formats a byte size into a human-readable string with default settings.
     *
     * @param bytes the size in bytes
     * @return a formatted string representing the size
     */
    public static String autoFormat(long bytes) {
        return autoFormat(bytes, false, 2);
    }

    /**
     * Converts a size from one unit to another.
     *
     * @param value     the size value to convert
     * @param fromUnit the unit of the input value
     * @param toUnit   the unit to convert to
     * @return the converted size value
     */
    public static double convert(long value, SizeUnit fromUnit, SizeUnit toUnit) {
        if (fromUnit == null || toUnit == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        BigDecimal bytes = fromUnit.toBytes(BigDecimal.valueOf(value));
        return toUnit.fromBytes(bytes).setScale(4, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Parses a size string into a byte value.
     *
     * @param sizeString the size string to parse
     * @return the size in bytes
     */
    public static long parse(String sizeString) {
        if (sizeString == null || sizeString.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        Matcher matcher = SIZE_PATTERN.matcher(sizeString.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("Invalid size format: %s", sizeString));
        }

        BigDecimal number;
        try {
            number = new BigDecimal(matcher.group(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid numeric value: %s", matcher.group(1)), e);
        }

        SizeUnit unit = parseUnit(matcher.group(3));
        BigDecimal bytes = unit.toBytes(number);

        if (bytes.compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) > 0) {
            throw new ArithmeticException("Size exceeds maximum Long value");
        }
        if (bytes.compareTo(BigDecimal.valueOf(Long.MIN_VALUE)) < 0) {
            throw new ArithmeticException("Size exceeds minimum Long value");
        }

        return bytes.longValueExact();
    }

    /**
     * Formats a bit rate into a human-readable string with appropriate units.
     *
     * @param bits         the bit rate in bits per second
     * @param timeUnit     the time unit for the rate
     * @param decimalPlaces the number of decimal places to display
     * @return a formatted string representing the bit rate
     */
    public static String formatRate(long bits, TimeUnit timeUnit, int decimalPlaces) {
        if (timeUnit == null) {
            throw new IllegalArgumentException("Time unit cannot be null");
        }
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("Decimal places must be non-negative");
        }

        BigDecimal seconds = BigDecimal.valueOf(timeUnit.toSeconds(1));
        BigDecimal bitsPerSecond = BigDecimal.valueOf(bits).divide(seconds, MathContext.DECIMAL128);

        List<SizeUnit> rateUnits = Arrays.asList(
                SizeUnit.TERABITS,
                SizeUnit.GIGABITS,
                SizeUnit.MEGABITS,
                SizeUnit.KILOBITS,
                SizeUnit.BITS
        );

        for (SizeUnit unit : rateUnits) {
            BigDecimal divisor = BigDecimal.valueOf(unit.getBase()).pow(unit.getExponent());
            BigDecimal converted = bitsPerSecond.divide(divisor, MathContext.DECIMAL128);
            if (converted.compareTo(BigDecimal.ONE) >= 0) {
                return String.format(Locale.US, "%." + decimalPlaces + "f %sps",
                        converted.setScale(decimalPlaces, RoundingMode.HALF_UP), unit.getSuffix());
            }
        }
        return String.format(Locale.US, "%." + decimalPlaces + "f bps",
                bitsPerSecond.setScale(decimalPlaces, RoundingMode.HALF_UP));
    }

    /**
     * Returns a list of decimal byte units (e.g., KB, MB, GB).
     *
     * @return list of decimal byte units
     */
    private static List<SizeUnit> getDecimalByteUnits() {
        return Arrays.asList(
                SizeUnit.TERABYTES,
                SizeUnit.GIGABYTES,
                SizeUnit.MEGABYTES,
                SizeUnit.KILOBYTES,
                SizeUnit.BYTES
        );
    }

    /**
     * Returns a list of binary byte units (e.g., KiB, MiB, GiB).
     *
     * @return list of binary byte units
     */
    private static List<SizeUnit> getBinaryByteUnits() {
        return Arrays.asList(
                SizeUnit.TEBIBYTES,
                SizeUnit.GIBIBYTES,
                SizeUnit.MEBIBYTES,
                SizeUnit.KIBIBYTES,
                SizeUnit.BYTES
        );
    }

    /**
     * Formats a BigDecimal value with a specified number of decimal places and unit suffix.
     *
     * @param value          the value to format
     * @param unit          the unit of the value
     * @param decimalPlaces the number of decimal places
     * @return the formatted string
     */
    private static String format(BigDecimal value, SizeUnit unit, int decimalPlaces) {
        return String.format(Locale.US, "%." + decimalPlaces + "f %s",
                value.setScale(decimalPlaces, RoundingMode.HALF_UP), unit.getSuffix());
    }

    /**
     * Parses a unit suffix and returns the corresponding SizeUnit.
     *
     * @param unitPart the unit suffix to parse
     * @return the corresponding SizeUnit
     * @throws IllegalArgumentException if the unit suffix is unrecognized
     */
    private static SizeUnit parseUnit(String unitPart) {
    return SizeUnit.fromSuffix(unitPart);
        }

    /**
     * Chooses the appropriate size unit based on the size and whether to use binary units.
     *
     * @param size           the size in bytes
     * @param useBinaryUnits whether to use binary units
     * @return the appropriate SizeUnit
     */
    public static SizeUnit chooseUnit(long size, boolean useBinaryUnits) {
        if (useBinaryUnits) {
            if (size < 1024) return SizeUnit.BYTES;
            if (size < 1024L * 1024L) return SizeUnit.KIBIBYTES;
            if (size < 1024L * 1024L * 1024L) return SizeUnit.MEBIBYTES;
            return SizeUnit.GIBIBYTES;
        } else {
            if (size < 1_000) return SizeUnit.BYTES;
            if (size < 1_000_000L) return SizeUnit.KILOBYTES;
            if (size < 1_000_000_000L) return SizeUnit.MEGABYTES;
            return SizeUnit.GIGABYTES;
        }
    }

    /**
     * Chooses the appropriate rate unit based on the bit rate.
     *
     * @param bitsPerSecond the bit rate in bits per second
     * @return the appropriate SizeUnit
     */
    public static SizeUnit chooseRateUnit(long bitsPerSecond) {
        if (bitsPerSecond < 1_000) return SizeUnit.BITS;
        if (bitsPerSecond < 1_000_000L) return SizeUnit.KILOBITS;
        if (bitsPerSecond < 1_000_000_000L) return SizeUnit.MEGABITS;
        return SizeUnit.GIGABITS;
    }
}
