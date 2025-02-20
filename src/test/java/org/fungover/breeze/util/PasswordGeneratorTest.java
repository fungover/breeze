package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PasswordGeneratorTest {

    @Test
    public void testGenerateDefaultPassword() {
        PasswordGenerator generator = new PasswordGenerator().builder().buld();
        String password = generator.generate();
        assertNotNull(password);
        assertEquals(12, password.length());
    }

    @Test
    public void testCustomLengthPassword() {
        PasswordGenerator generator = PasswordGenerator.builder().length(16).build();
        String password = generator.generate();
        assertNotNull(password);
        assertEquals(16, password.length());
    }

    @Test
    public void testIncludeUppercase() {
        PasswordGenerator generator = PasswordGenerator.builder().includeUppercase().minUppercase(2).build();
        String password = generator.generate();
        assertTrue(password.chars().filter(Character::isUpperCase).count() >= 2);
    }

    @Test
    public void testIncludeNumbers() {
        PasswordGenerator generator = PasswordGenerator.builder().includeNumbers().minNumbers(2).build();
        String password = generator.generate();
        assertTrue(password.chars().filter(Character::isDigit).count() >= 2);
    }

    @Test
    public void testIncludeSymbols() {
        PasswordGenerator generator = PasswordGenerator.builder().includeSymbols().minSymbols(2).build();
        String password = generator.generate();
        assertTrue(password.chars().filter(ch -> PasswordGenerator.SYMBOLS.indexOf(ch) >= 0).count() >= 2);
    }

    @Test
    public void testExcludeAmbiguous() {
        PasswordGenerator generator = PasswordGenerator.builder().excludeAmbiguous().build();
        String password = generator.generate();
        for (char ch : "l1O0".toCharArray()) {
            assertFalse(password.contains(String.valueOf(ch)));
        }
    }
}
