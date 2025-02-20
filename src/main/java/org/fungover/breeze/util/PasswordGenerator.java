package org.fungover.breeze.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PasswordGenerator is a utility class for generating secure passwords based on configurable criteria.
 * It supports various character sets, minimum requirements, and excludes ambiguous characters.
 */
public class PasswordGenerator {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String AMBIGUOUS = "l1O0";

    private final int length;
    private final boolean includeUppercase;
    private final boolean includeLowercase;
    private final boolean includeNumbers;
    private final boolean includeSymbols;
    private final boolean excludeAmbiguous;
    private final int minUppercase;
    private final int minNumbers;
    private final int minSymbols;
    private final SecureRandom random;

    private PasswordGenerator(Builder builder) {
        this.length = builder.length;
        this.includeUppercase = builder.includeUppercase;
        this.includeLowercase = builder.includeLowercase;
        this.includeNumbers = builder.includeNumbers;
        this.includeSymbols = builder.includeSymbols;
        this.excludeAmbiguous = builder.excludeAmbiguous;
        this.minUppercase = builder.minUppercase;
        this.minNumbers = builder.minNumbers;
        this.minSymbols = builder.minSymbols;
        this.random = new SecureRandom();
    }

    /**
     * Generates a single password based on the configured criteria.
     *
     * @return A randomly generated secure password
     */
    public String generate() {
        if (length < minUppercase + minNumbers + minSymbols) {
            throw new IllegalArgumentException("Password length too short for specified minimum requirements");
        }
        List<Character> passwordChars = new ArrayList<>();
        String availableCharacters = "";

        if (includeUppercase) availableCharacters += UPPERCASE;
        if (includeLowercase) availableCharacters += LOWERCASE;
        if (includeNumbers) availableCharacters += NUMBERS;
        if (includeSymbols) availableCharacters += SYMBOLS;
        if (excludeAmbiguous) {
            availableCharacters = availableCharacters.chars()
                    .filter(c -> AMBIGUOUS.indexOf(c) == -1)
                    .mapToObj(c -> String.valueOf((char) c))
                    .collect(Collectors.joining());
        }
        if (availableCharacters.isEmpty()) {
            throw new IllegalArgumentException("No valid character sets selected");
        }
        for (int i = 0; i < minUppercase; i++) passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        for (int i = 0; i < minNumbers; i++) passwordChars.add(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        for (int i = 0; i < minSymbols; i++) passwordChars.add(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));

        while (passwordChars.size() < length) {
            passwordChars.add(availableCharacters.charAt(random.nextInt(availableCharacters.length())));
        }

        Collections.shuffle(passwordChars);
        return passwordChars.stream().map(String::valueOf).collect(Collectors.joining());
    }
}
