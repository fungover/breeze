package org.fungover.breeze.util;

/**
 * PasswordGenerator is a utility class for generating secure passwords based on configurable criteria.
 * It supports various character sets, minimum requirements, and excludes ambiguous characters.
 */
public class PasswordGenerator {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String AMBIGUOUS = "l1O0";
}
