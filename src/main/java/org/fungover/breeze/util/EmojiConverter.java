package org.fungover.breeze.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for converting between UTF-8 emoticons and ASCII emoticons.
 * Supports transformations similar to ASCIIEmoji.
 * <p>
 * Thread-safe and immutable.
 * </p>
 *
 * <pre>
 *     String utf = "(yay)";
 *     String ascii = "\\( ﾟヮﾟ)/";
 *
 *     EmojiConverter.toAscii(utf);  // "¯\\_(ツ)_/¯"
 *     EmojiConverter.toUtf(ascii);  // "(shrug)"
 * </pre>
 */
public final class EmojiConverter {

    private static final Map<String, String> UTF_TO_ASCII;
    private static final Map<String, String> ASCII_TO_UTF;

    static {
        Map<String, String> utfToAscii = new HashMap<>();
        utfToAscii.put("(shrug)", "¯\\_(ツ)_/¯");
        utfToAscii.put("(yay)", "\\( ﾟヮﾟ)/");

        UTF_TO_ASCII = Collections.unmodifiableMap(utfToAscii);

        Map<String, String> asciiToUtf = new HashMap<>();
        utfToAscii.forEach((utf, ascii) -> asciiToUtf.put(ascii, utf));
        ASCII_TO_UTF = Collections.unmodifiableMap(asciiToUtf);
    }

    private EmojiConverter() {
        // Prevent instantiation
    }

    /**
     * Checks if the given string is an ASCII emoticon.
     *
     * @param emoji the emoji to check
     * @return true if it is an ASCII emoticon, false otherwise
     */
    public static boolean isAscii(String emoji) {
        return ASCII_TO_UTF.containsKey(Objects.toString(emoji, ""));
    }

    /**
     * Checks if the given string is a UTF-8 emoticon.
     *
     * @param emoji the emoji to check
     * @return true if it is a UTF-8 emoticon, false otherwise
     */
    public static boolean isUtf(String emoji) {
        return UTF_TO_ASCII.containsKey(Objects.toString(emoji, ""));
    }

    /**
     * Converts a UTF-8 emoticon to its ASCII equivalent.
     *
     * @param emoji the UTF-8 emoticon
     * @return the ASCII equivalent, or the original input if no match is found
     */
    public static String toAscii(String emoji) {
        return UTF_TO_ASCII.getOrDefault(Objects.toString(emoji, ""), emoji);
    }

    /**
     * Converts an ASCII emoticon to its UTF-8 equivalent.
     *
     * @param emoji the ASCII emoticon
     * @return the UTF-8 equivalent, or the original input if no match is found
     */
    public static String toUtf(String emoji) {
        return ASCII_TO_UTF.getOrDefault(Objects.toString(emoji, ""), emoji);
    }
}
