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

    /**
     * Generates multiple passwords.
     *
     * @param count Number of passwords to generate.
     * @return List of generated passwords.
     */
    public List<String> generateMultiple(int count) {
        List<String> passwords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            passwords.add(generate());
        }
        return passwords;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int length = 12;
        private boolean includeUppercase = true;
        private boolean includeLowercase = true;
        private boolean includeNumbers = true;
        private boolean includeSymbols = true;
        private boolean excludeAmbiguous = false;
        private int minUppercase = 0;
        private int minNumbers = 0;
        private int minSymbols = 0;

        public Builder length(int length) {
            this.length = length;
            return this;
        }

        public Builder includeUppercase() {
            this.includeUppercase = true;
            return this;
        }

        public Builder includeLowercase() {
            this.includeLowercase = true;
            return this;
        }

        public Builder includeNumbers() {
            this.includeNumbers = true;
            return this;
        }

        public Builder includeSymbols() {
            this.includeSymbols = true;
            return this;
        }

        public Builder excludeAmbiguous() {
            this.excludeAmbiguous = true;
            return this;
        }

        public Builder minUppercase(int count) {
            this.minUppercase = count;
            return this;
        }

        public Builder minNumbers(int count) {
            this.minNumbers = count;
            return this;
        }

        public Builder minSymbols(int count) {
            this.minSymbols = count;
            return this;
        }

        public Builder includeAll() {
            return this.includeUppercase().includeLowercase().includeNumbers().includeSymbols();
        }

        public PasswordGenerator build() {
            return new PasswordGenerator(this);
        }
    }

    /**
     * Estimates password strength based on length and character diversity.
     *
     * @param password The password to analyze.
     * @return Strength score from 1 (weak) to 5 (very strong).
     */
    public static int estimateStrength(String password) {
        int score = 1;
        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;
        if (password.chars().anyMatch(Character::isUpperCase)) score++;
        if (password.chars().anyMatch(Character::isDigit)) score++;
        if (password.chars().anyMatch(ch -> SYMBOLS.indexOf(ch) >= 0)) score++;
        return score;
    }
}
