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
}
