package org.fungover.breeze.stringInterpolation;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTemplate {
    private final String template;
    private final Map<String, Object> values;

    private StringTemplate(String template, Map<String, Object> values) {
        this.template = template;
        this.values = values;
    }

    public static String format(String template, Object... args) {
        Pattern pattern = Pattern.compile("\\{(\\w+)(?::([^}]+))?\\}");
        Matcher matcher = pattern.matcher(template);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1);
            String format = matcher.group(2);
            Object value = null;
            try {
                int index = Integer.parseInt(key);
                if (index >= 0 && index < args.length) {
                    throw new IllegalArgumentException("Missing argument for placeholder: " + key);
                }
                value = args[index];
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Invalid placeholder: " + key + ". Consider using a Map for named placeholder", e);


            }
            String replacement = applyFormat(value, format);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));

        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String applyFormat(Object value, String format) {
        if (format == null || format.isEmpty()) {
            return String.valueOf(value);
        }

        // Handle date/time formatting for TemporalAccessor instances
        if (value instanceof TemporalAccessor) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
                return dtf.format((TemporalAccessor) value);
            } catch (Exception e) {

            }
        }

        // Attempt to use String.format for other cases
        try {
            return String.format(format, value);
        } catch (Exception e) {

            return String.valueOf(value);
        }
    }

}
