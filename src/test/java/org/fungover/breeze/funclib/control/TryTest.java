package org.fungover.breeze.funclib.control;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class TryTest {

    @Test
    void success_shouldReturnCorrectValue() throws Throwable {
        Try<Integer> success = Try.success(10);
        assertTrue(success.isSuccess());
        assertFalse(success.isFailure());
        assertEquals(10, success.get());
    }

    @Test
    void failure_shouldThrowException() {
        Exception exception = new RuntimeException("Test Exception");
        Try<Integer> failure = Try.failure(exception);
        assertFalse(failure.isSuccess());
        assertTrue(failure.isFailure());
        try {
            failure.get();
            fail("Expected exception was not thrown");
        } catch (RuntimeException e) {
            assertEquals("Test Exception", e.getMessage());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void of_shouldReturnSuccessWhenSupplierSucceeds() {
        Try<Integer> result = Try.of(() -> 42);
        assertInstanceOf(Try.Success.class, result);
        assertEquals(42, ((Try.Success<Integer>) result).getValue());
    }

    @Test
    void of_shouldReturnFailureWhenSupplierThrowsRuntimeException() {
        RuntimeException exception = new RuntimeException("Test RuntimeException");
        Try<Integer> result = Try.of(() -> {
            throw exception;
        });

        assertInstanceOf(Try.Failure.class, result);
        assertEquals(exception, ((Try.Failure<Integer>) result).exception);
    }

    @Test
    void of_shouldReturnFailureWhenSupplierThrowsCheckedException() {
        Exception exception = new Exception("Test Checked Exception");

        Try<Integer> result = Try.of(() -> {
            throw new RuntimeException(exception);
        });

        assertInstanceOf(Try.Failure.class, result);
        assertInstanceOf(RuntimeException.class, ((Try.Failure<Integer>) result).exception);
        assertSame(exception, ((Try.Failure<Integer>) result).exception.getCause()); // Ensure it’s wrapped
    }


    @Test
    void of_shouldWrapErrorInExceptionWhenSupplierThrowsError() {
        Error error = new OutOfMemoryError("Critical error");
        Try<Integer> result = Try.of(() -> {
            throw error;
        });

        assertInstanceOf(Try.Failure.class, result);
        assertInstanceOf(Exception.class, ((Try.Failure<Integer>) result).exception);
        assertEquals(error, ((Try.Failure<Integer>) result).exception.getCause());
    }

    @Test
    void of_shouldReturnSuccessWithNullWhenSupplierReturnsNull() {
        Try<Integer> result = Try.of(() -> null);
        assertInstanceOf(Try.Success.class, result);
        assertNull(((Try.Success<Integer>) result).getValue());
    }

    @Test
    void getOrElse_shouldReturnValueForSuccess() {
        Try<Integer> success = Try.success(10);
        assertEquals(10, success.getOrElse(5));
    }

    @Test
    void getOrElse_shouldReturnDefaultForFailure() {
        Try<Integer> failure = Try.failure(new Exception("Test"));
        assertEquals(5, failure.getOrElse(5));
    }

    @Test
    void getOrElseGet_shouldReturnValueForSuccess() {
        Try<Integer> success = Try.success(10);
        assertEquals(10, success.getOrElseGet(() -> 5));
    }

    @Test
    void getOrElseGet_shouldReturnSupplierValueForFailure() {
        Try<Integer> failure = Try.failure(new Exception("Test"));
        assertEquals(5, failure.getOrElseGet(() -> 5));
    }

    @Test
    void getOrElseThrow_shouldReturnValueForSuccess() throws Throwable {
        Try<Integer> success = Try.success(10);
        assertEquals(10, success.getOrElseThrow(Throwable::new));
    }

    @Test
    void getOrElseThrow_shouldThrowMappedExceptionForFailure() {
        Try<Integer> failure = Try.failure(new Exception("Test"));
        try {
            failure.getOrElseThrow(RuntimeException::new);
            fail("Expected exception was not thrown");
        } catch (RuntimeException e) {
            assertNotNull(e);
        }
    }

    @Test
    void map_shouldReturnFailureWhenExceptionThrown() {
        Try<Integer> success = Try.success(10);

        Try<Integer> result = success.map(value -> {
            throw new RuntimeException("Mapper exception");
        });

        assertTrue(result.isFailure());

        Exception exception = assertThrows(Exception.class, () -> result.getOrElseThrow(e -> e));
        assertEquals("Mapper exception", exception.getMessage());
    }

    @Test
    void map_shouldReturnSameFailureWhenInitialTryIsFailure() {
        Exception originalException = new Exception("Test Exception");
        Try<Integer> failure = Try.failure(originalException);

        Try<Integer> mapped = failure.map(value -> value * 2);

        assertTrue(mapped.isFailure());
        assertEquals(originalException, ((Try.Failure<Integer>) mapped).exception);
    }


    @Test
    void flatMap_shouldReturnFailureWhenExceptionThrown() {
        Try<Integer> success = Try.success(10);
        Try<Integer> result = success.flatMap(value -> {
            throw new RuntimeException("FlatMap exception");
        });

        assertTrue(result.isFailure());

        Exception exception = assertThrows(Exception.class, () -> result.getOrElseThrow(e -> e));
        assertEquals("FlatMap exception", exception.getMessage());
    }

    @Test
    void flatMap_shouldReturnSameFailureWhenInitialTryIsFailure() {
        Exception originalException = new Exception("Test Exception");
        Try<Integer> failure = Try.failure(originalException);
        Try<Integer> flatMapped = failure.flatMap(value -> Try.success(value * 2));

        assertTrue(flatMapped.isFailure());
        assertEquals(originalException, ((Try.Failure<Integer>) flatMapped).exception);
    }

    @Test
    void recoverWith_shouldReturnSameSuccessWhenTryIsSuccess() {
        Try<Integer> success = Try.success(42);
        Function<Throwable, Try<Integer>> recoveryFunction = ex -> Try.success(99);
        Try<Integer> recovered = success.recoverWith(recoveryFunction);

        assertTrue(recovered.isSuccess());
        assertEquals(42, ((Try.Success<Integer>) recovered).getValue());
    }

    @Test
    void recoverWith_shouldReturnRecoveredTryForFailure() {
        Try<Integer> failure = Try.failure(new Exception("Test"));
        Try<Integer> recovered = failure.recoverWith(e -> Try.success(5));
        assertTrue(recovered.isSuccess());
        assertEquals(5, recovered.getOrElse(0));
    }

    @Test
    void recoverWith_shouldHandleExceptionInRecoveryFunction() {
        Try<Integer> failureTry = Try.failure(new RuntimeException("Initial failure"));

        Function<Throwable, Try<Integer>> faultyRecoveryFunction = throwable -> {
            throw new IllegalStateException("Error in recovery function");
        };

        Try<Integer> recoveredTry;
        try {
            recoveredTry = failureTry.recoverWith(faultyRecoveryFunction);
        } catch (Exception e) {
            recoveredTry = Try.failure(e);
        }

        assertTrue(recoveredTry.isFailure());
        assertEquals("Error in recovery function", ((Try.Failure<Integer>) recoveredTry).exception.getMessage());
    }


    @Test
    void Recover_ReturnsSameInstanceWhenSuccess() {
        // Arrange
        Try<Integer> successTry = Try.success(10);  // Assuming you have Success class
        Function<Exception, Integer> recoverFunction = e -> 5;  // Example recovery function

        // Act
        Try<Integer> result = successTry.recover(recoverFunction);

        // Assert
        // Ensure the result is the same instance
        assertSame(successTry, result, "The recover method should return the same instance if Try is a success.");
    }

    @Test
    void recover_shouldHandleExceptionInRecoveryFunction() {
        Try<Integer> failure = Try.failure(new Exception("Test"));
        Try<Integer> recovered = failure.recover(e -> {
            throw new RuntimeException("Recovery failed");
        });
        assertTrue(recovered.isFailure());
        try {
            recovered.get();
            fail("Expected exception was not thrown");
        } catch (RuntimeException e) {
            assertEquals("Recovery failed", e.getMessage());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void recover_shouldReturnSuccessWhenRecoverySucceeds() {
        Exception originalException = new Exception("Test Exception");
        Try<Integer> failure = Try.failure(originalException);

        Try<Integer> recovered = failure.recover(ex -> 42);

        assertTrue(recovered.isSuccess());
        assertEquals(42, recovered.getOrElse(0));
    }

    @Test
    void recover_shouldReturnFailureWithCorrectCause() {
        Exception originalException = new Exception("Test Exception");
        Try<Integer> failure = Try.failure(originalException);
        Try<Integer> recovered = failure.recover(ex -> {
            throw new RuntimeException("Recovery failed");
        });

        assertTrue(recovered.isFailure());
        assertEquals("Recovery failed", ((Try.Failure<Integer>) recovered).exception.getMessage());
    }

    @Test
    void fold_shouldApplyFailureFunction() {
        Try<Integer> failure = Try.failure(new Exception("Test"));
        String result = failure.fold(Throwable::getMessage, Object::toString);
        assertEquals("Test", result);
    }

    @Test
    void filter_shouldReturnSuccessWhenPredicateMatches() {
        Try<Integer> success = Try.success(10);
        assertTrue(success.filter(value -> value > 5).isSuccess());
    }

    @Test
    void filter_shouldReturnFailureWhenPredicateFails() {
        Try<Integer> success = Try.success(10);
        assertTrue(success.filter(value -> value > 15).isFailure());
    }

    @Test
    void filter_shouldReturnSameFailureWhenTryIsFailure() {
        Exception originalException = new Exception("Test Exception");
        Try<Integer> failure = Try.failure(originalException);
        Predicate<Integer> predicate = value -> value > 10;
        Try<Integer> filtered = failure.filter(predicate);

        assertTrue(filtered.isFailure());
        assertEquals(originalException, ((Try.Failure<Integer>) filtered).exception);
    }

    @Test
    void match_shouldHandleBothCases() {
        Try<Integer> success = Try.success(10);
        Try<Integer> failure = Try.failure(new Exception("Test"));

        String successResult = success.fold(
                e -> "Failure",
                v -> "Success: " + v
        );
        assertEquals("Success: 10", successResult);

        String failureResult = failure.fold(
                e -> "Failure",
                v -> "Success: " + v
        );
        assertEquals("Failure", failureResult);
    }

    @Test
    void toOptional_shouldConvertCorrectly() {
        Try<Integer> success = Try.success(10);
        Try<Integer> failure = Try.failure(new Exception("Test"));
        assertEquals(Optional.of(10), success.toOptional());
        assertEquals(Optional.empty(), failure.toOptional());
    }

    @Test
    void toEither_shouldConvertCorrectly() {
        Try<Integer> success = Try.success(10);
        Try<Integer> failure = Try.failure(new Exception("Test"));
        assertEquals(10, success.toEither().getRight());
        assertTrue(failure.toEither().isLeft());
    }

    @Test
    void ofCallable_shouldHandleException() {
        Try<Integer> success = Try.ofCallable(() -> 10);
        Try<Integer> failure = Try.ofCallable(() -> {
            throw new RuntimeException("Callable failed");
        });
        assertTrue(success.isSuccess());
        try {
            assertEquals(10, success.get());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        assertTrue(failure.isFailure());
    }

    @Test
    void match_shouldCallSuccessCaseWhenTryIsSuccess() {
        Try<Integer> success = Try.success(42);

        String result = success.match(
                value -> "Success: " + value,
                ex -> "Failure: " + ex.getMessage()
        );
        assertEquals("Success: 42", result);
    }

    @Test
    void match_shouldCallFailureCaseWhenTryIsFailure() {
        Exception exception = new RuntimeException("Something went wrong");
        Try<Integer> failure = Try.failure(exception);

        String result = failure.match(
                value -> "Success: " + value,
                ex -> "Failure: " + ex.getMessage()
        );
        assertEquals("Failure: Something went wrong", result);
    }

    @Test
    void match_shouldThrowNullPointerExceptionWhenSuccessCaseIsNull() {
        Try<Integer> success = Try.success(42);
        assertThrows(NullPointerException.class, () -> success.match(null, ex -> "Failure: " + ex.getMessage()));
    }

    @Test
    void match_shouldThrowNullPointerExceptionWhenFailureCaseIsNull() {
        Exception exception = new RuntimeException("Something went wrong");
        Try<Integer> failure = Try.failure(exception);
        assertThrows(NullPointerException.class, () -> failure.match(value -> "Success: " + value, null));
    }

    @Test
    void equals_shouldReturnTrueForSameInstance() {
        Try<Integer> success = Try.success(42);
        assertEquals(success, success);
    }

    @Test
    void equals_shouldReturnFalseWhenComparedToNull() {
        Try<Integer> success = Try.success(42);
        assertNotEquals(null, success);
    }

    @Test
    void equals_shouldReturnFalseWhenComparedToDifferentClass() {
        Try<Integer> success = Try.success(42);
        assertNotEquals("Not a Try object", success);
    }

    @Test
    void equals_shouldReturnTrueForTwoSuccessInstancesWithSameValue() {
        Try<Integer> success1 = Try.success(42);
        Try<Integer> success2 = Try.success(42);
        assertEquals(success1, success2);
    }

    @Test
    void equals_shouldReturnFalseForTwoSuccessInstancesWithDifferentValues() {
        Try<Integer> success1 = Try.success(42);
        Try<Integer> success2 = Try.success(99);
        assertNotEquals(success1, success2);
    }

    @Test
    void equals_shouldReturnTrueForTwoFailureInstancesWithSameException() {
        Exception ex = new RuntimeException("Error occurred");
        Try<Integer> failure1 = Try.failure(ex);
        Try<Integer> failure2 = Try.failure(ex);
        assertEquals(failure1, failure2);
    }

    @Test
    void equals_shouldReturnFalseForTwoFailureInstancesWithDifferentExceptions() {
        Try<Integer> failure1 = Try.failure(new RuntimeException("Error 1"));
        Try<Integer> failure2 = Try.failure(new RuntimeException("Error 2"));
        assertNotEquals(failure1, failure2);
    }

    @Test
    void equals_shouldReturnFalseWhenComparingSuccessWithFailure() {
        Try<Integer> success = Try.success(42);
        Try<Integer> failure = Try.failure(new RuntimeException("Failure"));
        assertNotEquals(success, failure);
    }

    @Test
    void hashCode_shouldBeConsistentForSameSuccessInstance() {
        Try<Integer> success = Try.success(42);
        int hash1 = success.hashCode();
        int hash2 = success.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void hashCode_shouldBeSameForTwoSuccessInstancesWithSameValue() {
        Try<Integer> success1 = Try.success(42);
        Try<Integer> success2 = Try.success(42);
        assertEquals(success1.hashCode(), success2.hashCode());
    }

    @Test
    void hashCode_shouldBeDifferentForTwoSuccessInstancesWithDifferentValues() {
        Try<Integer> success1 = Try.success(42);
        Try<Integer> success2 = Try.success(99);
        assertNotEquals(success1.hashCode(), success2.hashCode());
    }

    @Test
    void hashCode_shouldBeConsistentForSameFailureInstance() {
        Exception ex = new RuntimeException("Error occurred");
        Try<Integer> failure = Try.failure(ex);
        int hash1 = failure.hashCode();
        int hash2 = failure.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void hashCode_shouldBeSameForTwoFailureInstancesWithSameException() {
        Exception ex = new RuntimeException("Error occurred");
        Try<Integer> failure1 = Try.failure(ex);
        Try<Integer> failure2 = Try.failure(ex);
        assertEquals(failure1.hashCode(), failure2.hashCode());
    }

    @Test
    void hashCode_shouldBeDifferentForTwoFailureInstancesWithDifferentExceptions() {
        Try<Integer> failure1 = Try.failure(new RuntimeException("Error 1"));
        Try<Integer> failure2 = Try.failure(new RuntimeException("Error 2"));
        assertNotEquals(failure1.hashCode(), failure2.hashCode());
    }

    @Test
    void hashCode_shouldBeDifferentForSuccessAndFailureInstances() {
        Try<Integer> success = Try.success(42);
        Try<Integer> failure = Try.failure(new RuntimeException("Failure"));
        assertNotEquals(success.hashCode(), failure.hashCode());
    }

    @Test
    void toString_shouldReturnCorrectFormatForSuccess() {
        Try<Integer> success = Try.success(42);
        assertEquals("Success[42]", success.toString());
    }

    @Test
    void toString_shouldReturnCorrectFormatForFailure() {
        Exception ex = new RuntimeException("Something went wrong");
        Try<Integer> failure = Try.failure(ex);
        assertEquals("Failure[exception=" + ex + "]", failure.toString());
    }

    @Test
    void toString_shouldBeDifferentForDifferentSuccessValues() {
        Try<Integer> success1 = Try.success(42);
        Try<Integer> success2 = Try.success(99);
        assertNotEquals(success1.toString(), success2.toString());
    }

    @Test
    void toString_shouldBeDifferentForDifferentFailureExceptions() {
        Try<Integer> failure1 = Try.failure(new RuntimeException("Error 1"));
        Try<Integer> failure2 = Try.failure(new RuntimeException("Error 2"));
        assertNotEquals(failure1.toString(), failure2.toString());
    }

    @Test
    void toString_shouldBeDifferentForSuccessAndFailure() {
        Try<Integer> success = Try.success(42);
        Try<Integer> failure = Try.failure(new RuntimeException("Failure"));
        assertNotEquals(success.toString(), failure.toString());
    }
}
