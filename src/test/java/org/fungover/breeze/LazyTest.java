package org.fungover.breeze;

import org.fungover.breeze.control.Lazy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    @DisplayName("flatMap should retriece element from lazy list")
    void flatMapShouldRetrieveElementFromLazyList() {
        Lazy<List<String>> lazyList = Lazy.of(() -> Arrays.asList("a", "b", "c"));
        Lazy<String> flatMapped = lazyList.flatMap(list -> Lazy.of(() -> list.get(2)));

        String result = flatMapped.get();
        assertEquals("c", result);
    }

    @Test
    @DisplayName("Lazy flatmap should return chain of transformed values")
    void lazyFlatmapShouldReturnChainOfTransformedValues() {
        Lazy<Integer> lazyInt = Lazy.of(() -> 666);
        Lazy<String> lazyString = lazyInt
                .flatMap(i -> Lazy.of(() -> "The answer is " + i))
                .flatMap(s -> Lazy.of(s::toUpperCase));
        assertEquals("THE ANSWER IS 666", lazyString.get());
    }

    @Test
    @DisplayName("filer value calculates only once")
    void filterValueCalculatesOnlyOnce() {
        AtomicInteger counter = new AtomicInteger(0);
        Lazy<Optional<Integer>> lazyOptional = Lazy.of(() -> {
            counter.incrementAndGet();
            return 89;
        }).filter(i -> i > 88);

        lazyOptional.get();
        lazyOptional.get();
        assertTrue(lazyOptional.get().isPresent());
        assertEquals(1, counter.get());
        assertEquals(89, lazyOptional.get().get().intValue());
    }

    @Test
    @DisplayName("filter with predicate should return true")
    void filterWithPredicateTrue() {
        Lazy<Optional<Integer>> lazyOptional = Lazy.of(() -> 12)
                .filter(i -> i < 13);
        assertTrue(lazyOptional.get().isPresent());
        assertEquals(12, lazyOptional.get().get().intValue());
    }

    @Test
    @DisplayName("lazy to option should return present when present and empty when empty")
    void lazyToOptionShouldReturnPresentWhenPresentAndEmptyWhenEmpty() {
        Lazy<Integer> lazy = Lazy.of(() -> 10);
        lazy.get();
        Optional<Integer> option = lazy.toOption();

        assertTrue(option.isPresent(), "Option should be present");
        assertEquals(10, option.get(), "Option should contain value 10");
        assertNotEquals(11, option.get());
        assertNotEquals(9, option.get());
        Lazy<Integer> lazy2 = Lazy.value(null);
        assertFalse(lazy2.toOption().isPresent(), "Option should be empty");
    }

    @Test
    @DisplayName("forEach processes lazy value correctly")
    void forEachProcessesLazyValueCorrectly() {
        Lazy<String> lazy = Lazy.value("Hello");
        StringBuilder result = new StringBuilder();

        lazy.forEach(result::append);

        assertEquals("Hello", result.toString());
    }

    @Test
    @DisplayName("forEach when not evaluated should return isEvaluated")
    void forEachWhenNotEvaluated() {
        Lazy<String> lazy = Lazy.of(() -> "Lazy Evaluation");
        StringBuilder result = new StringBuilder();

        lazy.forEach(result::append);

        assertEquals("Lazy Evaluation", result.toString());
        assertTrue(lazy.isEvaluated(), "Value should be evaluated after forEach is called");
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
