package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Try;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LazyTest {


    @Nested
    class TestsForPublicMethods {
        @Test
        @DisplayName("Of method should return new Lazy supplier")
        void ofMethodShouldReturnNewLazySupplier() {
            Supplier<String> supplier = () -> "Hello";

            Lazy<String> lazy = Lazy.of(supplier);
            assertNotNull(lazy);
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
        @DisplayName("flatMap should retrieve element from lazy list")
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
        @DisplayName("filter value calculates only once")
        void filterValueCalculatesOnlyOnce() {
            AtomicInteger counter = new AtomicInteger(0);
            Lazy<Optional<Integer>> lazyOptional = Lazy.of(() -> {
                counter.incrementAndGet();
                return 89;
            }).filter(i -> i > 88);

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
        @DisplayName("lazy toOption should return present when present and empty when empty")
        void lazyToOptionShouldReturnPresentWhenPresentAndEmptyWhenEmpty() {
            Lazy<Integer> lazy = Lazy.of(() -> 10);
            lazy.get();
            Optional<Integer> option = lazy.toOption();

            assertTrue(option.isPresent(), "Option should be present");
            assertEquals(10, option.get(), "Option should contain value 10");
            assertNotEquals(11, option.get());
            assertNotEquals(9, option.get());

        }

        @Test
        @DisplayName("toOption should return empty with null value")
        void toOptionShouldReturnEmpty() {
            Lazy<Integer> lazyWithNullValue = Lazy.of(() -> null);
            Optional<Integer> option = lazyWithNullValue.toOption();

            assertThat(option).isNotPresent();
            assertTrue(option.isEmpty());
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
        @DisplayName("toTry should return success when success")
        void toTryShouldReturnSuccessWhenSuccess() throws Throwable {
            Lazy<Integer> lazy = Lazy.of(() -> 96);
            Try<Integer> result = lazy.toTry();
            assertTrue(result.isSuccess());
            assertEquals(96, result.get());
        }

        @Test
        @DisplayName("toTry should return failure when failure")
        void toTryShouldReturnFailureWhenFailure() {
            Lazy<Integer> lazy = Lazy.of(() -> {
                throw new RuntimeException("Computation failed");
            });

            Try<Integer> result = lazy.toTry();

            assertTrue(result.isFailure());
            assertThrows(RuntimeException.class, result::get);
        }
    }

    @Nested
    class TestsForThreadSafety {
        private final AtomicInteger calculationCount = new AtomicInteger(0);
        private static final long HEAVY_COMPUTATION_DELAY_NANOS = 1_000_000_000L;

        @Test
        @DisplayName("Thread safety when counting value in multiple threads")
        void threadSafetyWhenCountingValueInMultipleThreads() throws InterruptedException, ExecutionException {

            Lazy<Integer> lazy = Lazy.of(() -> {
                calculationCount.incrementAndGet();
                long start = System.nanoTime();

                //Simulates a heavy calculation
                while (System.nanoTime() - start < HEAVY_COMPUTATION_DELAY_NANOS) {
                    LockSupport.parkNanos(1);
                }
                return 123;
            });

            try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
                List<Callable<Integer>> tasks = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    tasks.add(lazy::get);
                }

                // Threads runs parallel and should return the same value
                List<Future<Integer>> results = executor.invokeAll(tasks);

                Integer expectedValue = 123;
                for (Future<Integer> result : results) {
                    assertEquals(expectedValue, result.get(), "Thread didn't return the correct value");
                }

                assertEquals(1, calculationCount.get(), "Value should only be calculated once");
            }
        }

        @Test
        @DisplayName("Memory consistency when multiple threads access Lazy value")
        void memoryConsistencyWhenMultipleThreadsAccessLazyValue() throws InterruptedException, ExecutionException {

            Lazy<Integer> lazy = Lazy.of(() -> {
                calculationCount.incrementAndGet();
                long start = System.nanoTime();

                while (System.nanoTime() - start < HEAVY_COMPUTATION_DELAY_NANOS) {
                    LockSupport.parkNanos(1);
                }

                return 123;
            });

            try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
                List<Callable<Integer>> tasks = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    tasks.add(lazy::get);
                }

                List<Future<Integer>> results = executor.invokeAll(tasks);

                Integer expectedValue = 123;
                for (Future<Integer> result : results) {
                    assertEquals(expectedValue, result.get(), "Thread didn't return the correct value");
                }

                assertSame(lazy.get(), expectedValue, "All threads should see the same value");
            }
        }


    }

    @Nested
    class TestsNullValues {

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

        @Test
        @DisplayName("map handles null values correctly")
        void mapHandlesNullValuesCorrectly() {
            Lazy<String> lazy = Lazy.of(() -> null);
            Lazy<Integer> mapped = lazy.map(str -> str == null ? 0 : str.length());
            assertEquals(0, mapped.get());
        }

    }

}
