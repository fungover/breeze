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
