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
            "^([-+]?(?:\\d+\\.\\d+|\\.\\d+|\\d+))(\\s*)([a-z]+)$", // Simplified to [a-z]
            Pattern.CASE_INSENSITIVE // Flag handles case insensitivity
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
     * @throws IllegalArgumentException if decimal places are negative
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
     * Converts a size from one unit to another.
     *
     * @param value     the size value to convert
     * @param fromUnit the unit of the input value
     * @param toUnit   the unit to convert to
     * @return the converted size value
     * @throws IllegalArgumentException if any unit is null
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
     * @throws IllegalArgumentException if the input string is null, empty, or improperly formatted
     * @throws ArithmeticException if the parsed size exceeds the maximum or minimum Long value
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
            throw new ArithmeticException(
                    "Parsed size exceeds maximum Long value: " + bytes + ". Ensure the size is within [-9,223,372,036,854,775,808 to 9,223,372,036,854,775,807]"
            );
        }
        if (bytes.compareTo(BigDecimal.valueOf(Long.MIN_VALUE)) < 0) {
            throw new ArithmeticException(
                    "Parsed size exceeds minimum Long value: " + bytes + ". Ensure the size is within [-9,223,372,036,854,775,808 to 9,223,372,036,854,775,807]"
            );
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
     * @throws IllegalArgumentException if the time unit is null or decimal places are negative
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
                String format = String.format(Locale.US, "%%.%df %%sps", decimalPlaces);
                return String.format(Locale.US, format,
                        converted.setScale(decimalPlaces, RoundingMode.HALF_UP), unit.getSuffix());
            }
        }
        String formatBps = String.format(Locale.US, "%%.%df bps", decimalPlaces);
        return String.format(Locale.US, formatBps,
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
     * @param unit           the unit of the value
     * @param decimalPlaces  the number of decimal places
     * @return the formatted string
     */
    private static String format(BigDecimal value, SizeUnit unit, int decimalPlaces) {
        String format = String.format(Locale.US, "%%.%df %%s", decimalPlaces);
        return String.format(Locale.US, format,
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
}
