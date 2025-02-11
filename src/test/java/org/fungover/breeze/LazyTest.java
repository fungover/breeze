package org.fungover.breeze;

import org.fungover.breeze.control.Lazy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    @DisplayName("value should return pre initialized lazy instance")
    void valueShouldReturnPreInitializedLazyInstance() {
        Integer value = 5;
        Lazy<Integer> lazy = Lazy.value(value);
        assertThat(lazy.get()).isEqualTo(5);
    }

    @Test
    @DisplayName("value should not be computed more than one time")
    void valueShouldNotBeComputedMoreThanOneTime() {
        AtomicInteger countedTimes = new AtomicInteger(0);
        Lazy<String> lazy = Lazy.of(() -> {
            countedTimes.incrementAndGet();
            return "computed value";
        });

        String value1 = lazy.get();
        String value2 = lazy.get();

        assertEquals(1, countedTimes.get());
        assertNotEquals(2, countedTimes.get());
        assertEquals("computed value", value1);
        assertEquals("computed value", value2);
    }

    @Test
    @DisplayName("Integer should transform to String with map")
    void integerShouldTransformToStringWithMap() {
        Lazy<Integer> lazyInt = Lazy.of(() -> 29);

        Lazy<String> lazyString = lazyInt.map(value -> "Value: " + value);

        String result = lazyString.get();

        assertEquals("Value: 29", result);
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
