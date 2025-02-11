package org.fungover.breeze.funclib.control;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.Callable;


class TryTest {

    @Test
    void testSuccess() {
        Try<Integer> result = Try.success(42);
        assertTrue(result.isSuccess());
        assertFalse(result.isFailure());
        assertDoesNotThrow(() -> assertEquals(42, result.get()));
    }

    @Test
    void testFailure() {
        Exception exception = new RuntimeException("Test Exception");
        Try<Integer> result = Try.failure(exception);
        assertTrue(result.isFailure());
        assertFalse(result.isSuccess());
        assertThrows(RuntimeException.class, result::get);
    }


    @Test
    void testNullValueSuccess() {
        Try<String> success = Try.success(null);
        assertTrue(success.isSuccess());
        assertDoesNotThrow(() -> assertNull(success.get()));
    }


    @Test
    void testNullValueFailure() {
        Try<String> failure = Try.failure(null);
        assertTrue(failure.isFailure());
        assertThrows(NullPointerException.class, failure::get);
    }

    @Test
    void testOfSuccess() {
        Try<Integer> result = Try.of(() -> 42);
        assertTrue(result.isSuccess());
        assertFalse(result.isFailure());
        assertDoesNotThrow(() -> assertEquals(42, result.get()));
    }

    @Test
    void testOfFailure() {
        Try<Integer> result = Try.of(() -> { throw new RuntimeException("Test Exception"); });
        assertTrue(result.isFailure());
        assertFalse(result.isSuccess());
        assertThrows(RuntimeException.class, result::get);
    }


    @Test
    void testOfCallableSuccess() {
        Callable<Integer> callable = () -> 42;
        Try<Integer> result = Try.ofCallable(callable);
        assertTrue(result.isSuccess());
        assertFalse(result.isFailure());
        assertDoesNotThrow(() -> assertEquals(42, result.get()));
    }

    @Test
    void testOfCallableFailure() {
        Callable<Integer> callable = () -> { throw new RuntimeException("Callable Exception"); };
        Try<Integer> result = Try.ofCallable(callable);
        assertTrue(result.isFailure());
        assertFalse(result.isSuccess());
        assertThrows(RuntimeException.class, result::get);
    }

    @Test
    void testMapSuccess() {
        Try<Integer> success = Try.success(10);
        Try<Integer> mapped = success.map(x -> x * 2);
        assertTrue(mapped.isSuccess());
        assertDoesNotThrow(() -> assertEquals(20, mapped.get()));
    }


    @Test
    void testMapFailure() {
        Try<Integer> failure = Try.failure(new RuntimeException("error"));
        Try<Integer> mapped = failure.map(x -> x * 2);
        assertTrue(mapped.isFailure());
        assertThrows(RuntimeException.class, mapped::get);
    }

    @Test
    void testFlatMapSuccess() {
        Try<Integer> success = Try.success(10);
        Try<Integer> flatMapped = success.flatMap(x -> Try.success(x * 2));
        assertTrue(flatMapped.isSuccess());
        assertDoesNotThrow(() -> assertEquals(20, flatMapped.get()));
    }


    @Test
    void testFlatMapFailure() {
        Try<Integer> failure = Try.failure(new RuntimeException("error"));
        Try<Integer> flatMapped = failure.flatMap(x -> Try.success(x * 2));
        assertTrue(flatMapped.isFailure());
        assertThrows(RuntimeException.class, flatMapped::get);
    }

    @Test
    void testFilterSuccess() {
        Try<Integer> success = Try.success(10);
        Try<Integer> filter = success.filter(x -> x % 2 == 0);
        assertTrue(filter.isSuccess());
    }

    @Test
    void testFilterFailure() {
        Try<Integer> failure = Try.failure(new RuntimeException("error"));
        Try<Integer> filter = failure.filter(x -> x % 2 == 0);
        assertTrue(filter.isFailure());
    }

    @Test
    void testRecover() {
        Try<Integer> failure = Try.failure(new RuntimeException("error"));
        Try<Integer> recovered = failure.recover(ex -> 42);
        assertTrue(recovered.isSuccess());
        assertDoesNotThrow(() -> assertEquals(42, recovered.get()));
    }

    @Test
    void testRecoverWith() {
        Try<Integer> failure = Try.failure(new RuntimeException("error"));
        Try<Integer> recovered = failure.recoverWith(ex -> Try.success(42));
        assertTrue(recovered.isSuccess());
        assertDoesNotThrow(() -> assertEquals(42, recovered.get()));
    }

    @Test
    void testFoldSuccess() {
        Try<Integer> success = Try.success(10);
        int result = success.fold(Throwable::hashCode, x -> x * 2);
        assertEquals(20, result);
    }

    @Test
    void testFoldFailure() {
        Try<Integer> failure = Try.failure(new RuntimeException("error"));
        int result = failure.fold(ex -> -1, x -> x * 2);
        assertEquals(-1, result);
    }

    @Test
    void testExceptionChaining() {
        Exception cause = new IllegalArgumentException("cause");
        Exception exception = new RuntimeException("error", cause);
        Try<Integer> failure = Try.failure(exception);

        assertTrue(failure.isFailure());
        assertThrows(RuntimeException.class, failure::get);

        if (failure instanceof Failure) {
            Throwable thrown = ((Failure<?>) failure).throwable();
            assertEquals(cause, thrown.getCause());
        }
    }

}
