package org.fungover.breeze;

import org.fungover.breeze.control.Lazy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
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
    @DisplayName("map should transform values")
    void mapShouldTransformValues() {
        Lazy<Integer> lazyInt = Lazy.of(() -> 100);
        Lazy<String> lazyString = lazyInt.map(value -> "Number: " + value);
        assertEquals("Number: 100", lazyString.get());

        Lazy<Double> lazyDouble = Lazy.of(() -> 3.14);
        Lazy<Integer> lazyIntFromDouble = lazyDouble.map(value -> (int) Math.round(value));
        assertEquals(3, lazyIntFromDouble.get());
    }

    @Test
    @DisplayName("Lazy method should not compute before call")
    void lazyMethodShouldNotComputeBeforeCall() {
        AtomicBoolean evaluated = new AtomicBoolean(false);

        Lazy<Integer> lazyInt = Lazy.of(() -> {
            evaluated.set(true);
            return 42;
        });

        assertFalse(evaluated.get());

        lazyInt.get();

        assertTrue(evaluated.get());
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
