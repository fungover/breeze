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

public class SizeFormatter {

    public static String autoFormat(long bytes, boolean useBinaryBase, int decimalPlaces) {
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

    public static String autoFormat(long bytes) {
        return autoFormat(bytes, false, 2);
    }

    public static double convert(long value, SizeUnit fromUnit, SizeUnit toUnit) {
        BigDecimal bytes = fromUnit.toBytes(BigDecimal.valueOf(value));
        return toUnit.fromBytes(bytes).setScale(4, RoundingMode.HALF_UP).doubleValue();
    }

    public static long parse(String sizeString) {
        if (sizeString == null || sizeString.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        // Optimized regex to prevent invalid decimal formats
        Matcher matcher = Pattern.compile("^([-+]?(?:\\d+\\.\\d+|\\d+|\\.\\d+))(\\s*)([a-zA-Z]+)$", Pattern.CASE_INSENSITIVE)
                .matcher(sizeString.trim());

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid size format: " + sizeString);
        }

        BigDecimal number;
        try {
            number = new BigDecimal(matcher.group(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value: " + matcher.group(1), e);
        }

        SizeUnit unit = parseUnit(matcher.group(3));
        BigDecimal bytes = unit.toBytes(number);

        if (bytes.compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) > 0) {
            throw new ArithmeticException("Size exceeds maximum Long value");
        }

        return bytes.longValueExact();
    }

    public static String formatRate(long bits, TimeUnit timeUnit, int decimalPlaces) {
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
                return String.format(Locale.US,
                        "%." + decimalPlaces + "f %sps",
                        converted.setScale(decimalPlaces, RoundingMode.HALF_UP),
                        unit.getSuffix());
            }
        }
        return String.format(Locale.US,
                "%." + decimalPlaces + "f bps",
                bitsPerSecond.setScale(decimalPlaces, RoundingMode.HALF_UP));
    }

    private static List<SizeUnit> getDecimalByteUnits() {
        return Arrays.asList(
                SizeUnit.TERABYTES,
                SizeUnit.GIGABYTES,
                SizeUnit.MEGABYTES,
                SizeUnit.KILOBYTES,
                SizeUnit.BYTES
        );
    }

    private static List<SizeUnit> getBinaryByteUnits() {
        return Arrays.asList(
                SizeUnit.TEBIBYTES,
                SizeUnit.GIBIBYTES,
                SizeUnit.MEBIBYTES,
                SizeUnit.KIBIBYTES,
                SizeUnit.BYTES
        );
    }

    private static String format(BigDecimal value, SizeUnit unit, int decimalPlaces) {
        return value.setScale(decimalPlaces, RoundingMode.HALF_UP)
                .toString()
                .replace(",", ".") + " " + unit.getSuffix();
    }

    private static SizeUnit parseUnit(String unitPart) {
        for (SizeUnit unit : SizeUnit.values()) {
            if (unit.getSuffix().equalsIgnoreCase(unitPart)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unrecognized unit: " + unitPart);
    }
}
