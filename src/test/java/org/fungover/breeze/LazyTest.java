package org.fungover.breeze;

import org.fungover.breeze.control.Lazy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class LazyTest {

    @Test
    @DisplayName("Of method should return new Lazy supplier")
    void ofMethodShouldReturnNewLazySupplier() {
        Supplier<String> supplier = () -> "Hej";

        Lazy<String> lazy = Lazy.of(supplier);

        assertNotNull(lazy);
    }

    @Test
    @DisplayName("Supports null values")
    void supportsNullValue() {
        Supplier<String> supplier = () -> null;
        Lazy<String> lazy = Lazy.of(supplier);
        String value = lazy.get();

        assertTrue(lazy.isEvaluated());
        assertNull(value);
    }

}
