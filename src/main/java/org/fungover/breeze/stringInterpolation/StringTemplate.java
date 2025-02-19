package org.fungover.breeze.stringInterpolation;

import java.lang.reflect.Constructor;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringTemplate {

    // Constant for placeholder pattern: {name[:format]}
    static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{(\\w+)(?::([^}]+))?\\}");

    // Instance fields
    private final String template;
    private final Map<String, Object> values;

    // Private constructor: use builder or static factory methods
    private StringTemplate(String template, Map<String, Object> values) {
        this.template = template;
        this.values = values;
    }

    // Formats a template using indexed placeholders.
    public static String format(String template, Object... args) {
        // Build a map from indices (as String) to argument values.
        Map<String, Object> argMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            argMap.put(String.valueOf(i), args[i]);
        }
        return new StringTemplate(template, argMap).render();
    }


    // Formats a template using named placeholders.
    public static String format(String template, Map<String, Object> values) {
        return new StringTemplate(template, values).render();
    }


    // Creates a new Builder instance for constructing a StringTemplate.
    public static Builder builder() {
        return new Builder();
    }

     // Renders the template by replacing placeholders with corresponding values.
    public String render() {
        StringBuilder result = new StringBuilder();
        List<Token> tokens = parseTemplate(template);
        for (Token token : tokens) {
            if (token.isLiteral()) {
                result.append(token.getContent());
            } else {
                // token is a placeholder; get the corresponding value
                String key = token.getContent();
                Object value = values.get(key);
                if (value == null) {
                    throw new IllegalArgumentException("Missing value for placeholder: " + key);
                }
                String replacement = applyFormat(value, token.getFormat());
                result.append(replacement);
            }
        }
        return result.toString();
    }


    static String applyFormat(Object value, String format) {
        if (format == null || format.isEmpty()) {
            return String.valueOf(value);
        }

        // Handle date/time formatting for TemporalAccessor instances.
        if (value instanceof TemporalAccessor) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format, Locale.US);
                return dtf.format((TemporalAccessor) value);
            } catch (Exception e) {

            }
        }
        try {
            return String.format(Locale.US, format, value);
        } catch (Exception e) {

            return String.valueOf(value);
        }
    }


    // Parses the template string into a list of tokens.
    static List<Token> parseTemplate(String template) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        int lastIndex = 0;
        while (matcher.find()) {
            // Add literal token for text before the placeholder.
            if (matcher.start() > lastIndex) {
                tokens.add(new Token(template.substring(lastIndex, matcher.start())));
            }
            String key = matcher.group(1);
            String format = matcher.group(2);
            tokens.add(new Token(key, format, false));
            lastIndex = matcher.end();
        }
        // Add remaining literal if any.
        if (lastIndex < template.length()) {
            tokens.add(new Token(template.substring(lastIndex)));
        }
        return tokens;
    }


    // Represents a token in the template.
    static class Token {
        private final String content;
        private final String format;
        private final boolean isPlaceholder;

        Token(String content) {
            this.content = content;
            this.format = null;
            this.isPlaceholder = false;
        }


        // Constructor for placeholder tokens.
        Token(String content, String format, boolean isPlaceholder) {
            this.content = content;
            this.format = format;
            this.isPlaceholder = isPlaceholder;
        }

        // Factory method for placeholder tokens.
        static Token placeholder(String key, String format) {
            return new Token(key, format, true);
        }

        public String getContent() {
            return content;
        }

        public String getFormat() {
            return format;
        }

        public boolean isLiteral() {
            return !isPlaceholder;
        }
    }

    // Builder for constructing a StringTemplate with named placeholders.
    public static class Builder {
        private String template;
        private final Map<String, Object> values = new HashMap<>();



        public Builder template(String template) {
            this.template = template;
            return this;
        }


        public Builder put(String key, Object value) {
            values.put(key, value);
            return this;
        }


        public StringTemplate build() {
            if (template == null) {
                throw new IllegalStateException("Template is not set");
            }
            return new StringTemplate(template, values);
        }
    }

    @Override
    public String toString() {
        return "StringTemplate{" +
                "template='" + template + '\'' +
                ", values=" + values +
                '}';
    }
}
