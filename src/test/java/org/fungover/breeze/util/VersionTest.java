package org.fungover.breeze.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * VersionTest-klassen innehåller enhetstester för Version-klassen.
 * Dessa tester verifierar funktionaliteten för versionsparsning, jämförelse och felhantering.
 */
public class VersionTest {

    /**
     * Testar jämförelse av versioner med och utan pre-release-identifierare.
     */
    @Test
    public void testVersionComparison() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.4");
        Version v3 = new Version("1.2.3-alpha");
        Version v4 = new Version("1.2.3-beta");

        assertTrue(v1.isLessThan(v2));
        assertTrue(v2.isGreaterThan(v1));
        assertTrue(v1.isGreaterThan(v3));
        assertTrue(v3.isLessThan(v4));
        assertTrue(v1.isEqual(new Version("1.2.3")));
    }

    /**
     * Testar hanteringen av ogiltiga versionssträngar.
     * Förväntar sig att IllegalArgumentException kastas för ogiltiga format.
     */
    @Test
    public void testInvalidVersion() {
        assertThrows(IllegalArgumentException.class, () -> new Version("invalid"));
        assertThrows(IllegalArgumentException.class, () -> new Version("1.2"));
        assertThrows(IllegalArgumentException.class, () -> new Version("1.2.3.4"));
    }

    /**
     * Testar om toString-metoden returnerar korrekt formaterade versionssträngar.
     */
    @Test
    public void testToString() {
        Version v1 = new Version("1.2.3");
        Version v2 = new Version("1.2.3-alpha");

        assertEquals("1.2.3", v1.toString());
        assertEquals("1.2.3-alpha", v2.toString());
    }
}
