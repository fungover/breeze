package org.fungover.breeze.funclib.control;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;


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
        assertThrows(NullPointerException.class, () -> Try.failure(null));
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
        Try<Integer> result = Try.of(() -> {
            throw new RuntimeException("Test Exception");
        });
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
        Callable<Integer> callable = () -> {
            throw new RuntimeException("Callable Exception");
        };
        Try<Integer> result = Try.ofCallable(callable);
        assertTrue(result.isFailure());
        assertFalse(result.isSuccess());
        assertThrows(RuntimeException.class, result::get);
    }

    @Test
    void testGetOrElseSuccess() {
        Try<String> success = Try.success("Hello");
        String result = success.getOrElse("Default");
        assertEquals("Hello", result);  // Should return the value inside Success
    }

    @Test
    void testGetOrElseFailure() {
        Try<String> failure = Try.failure(new Exception("Error"));
        String result = failure.getOrElse("Default");
        assertEquals("Default", result);  // Should return the default value on Failure
    }

    @Test
    void testGetOrElseGetSuccess() {
        Try<String> success = Try.success("Hello");
        String result = success.getOrElseGet(() -> "Computed Default");
        assertEquals("Hello", result);  // Should return the value inside Success
    }

    @Test
    void testGetOrElseGetFailure() {
        Try<String> failure = Try.failure(new Exception("Error"));
        String result = failure.getOrElseGet(() -> "Computed Default");
        assertEquals("Computed Default", result);  // Should call supplier and return computed value on Failure
    }

    @Test
    void testGetOrElseThrowSuccess() {
        Try<String> success = Try.success("Hello");
        String result = success.getOrElseThrow(t -> new IllegalStateException("Error occurred"));
        assertEquals("Hello", result);  // Should return the value inside Success
    }

    @Test
    void testGetOrElseThrowFailure() {
        Try<String> failure = Try.failure(new Exception("Error"));
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            failure.getOrElseThrow(t -> new IllegalStateException("Error occurred"));
        });
        assertEquals("Error occurred", exception.getMessage());  // Should throw the mapped exception
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
            Throwable thrown = ((Failure<?>) failure).exception;
            assertEquals(cause, thrown.getCause());
        }
    }

    @Test
    void testToEitherWithSuccess() {
        Try<String> successTry = Try.success("Success value");
        Either<Throwable, String> result = successTry.toEither();
        assertTrue(result.isRight());
        assertEquals("Success value", result.getRight());
    }

    @Test
    void testToEitherWithFailure() {
        Exception exception = new Exception("Failure message");
        Try<String> failureTry = Try.failure(exception);
        Either<Throwable, String> result = failureTry.toEither();
        assertTrue(result.isLeft());
        assertEquals(exception, result.getLeft());
    }

    @Test
    void testToEitherWithGenericType() {
        Try<Integer> successTry = Try.success(42);
        Either<Throwable, Integer> result = successTry.toEither();

        assertTrue(result.isRight());
        assertEquals(42, result.getRight());
    }

    @Test
    void testToEitherWithThrowableInLeft() {
        Exception exception = new Exception("Test failure");
        Try<String> failureTry = Try.failure(exception);
        Either<Throwable, String> result = failureTry.toEither();

        assertTrue(result.isLeft());
        assertEquals(exception, result.getLeft());
    }

    @Test
    void testToOptionalWithSuccess() {
        Try<String> successTry = Try.success("Success value");
        Optional<String> result = successTry.toOptional();
        assertTrue(result.isPresent());
        assertEquals("Success value", result.get());
    }

    @Test
    void testToOptionalWithSuccessNullValue() {
        Try<String> successTry = Try.success(null);
        Optional<String> result = successTry.toOptional();
        assertFalse(result.isPresent());
    }

    @Test
    void testToOptionalWithFailure() {
        Exception exception = new Exception("Failure message");
        Try<String> failureTry = Try.failure(exception);
        Optional<String> result = failureTry.toOptional();
        assertFalse(result.isPresent());
    }

    @Test
    void testToOptionalWithGenericType() {
        Try<Integer> successTry = Try.success(42);
        Optional<Integer> result = successTry.toOptional();
        assertTrue(result.isPresent());
        assertEquals(42, result.get());
    }
}
