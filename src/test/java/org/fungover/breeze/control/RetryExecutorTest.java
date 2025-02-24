package org.fungover.breeze.control;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RetryExecutorTest {
    private static final int TEST_MAX_ATTEMPTS = 3;
    private static final long TEST_INITIAL_DELAY = 100;
    private static final long TEST_MAX_DELAY = 1000;

    private RetryExecutor createDefaultExecutor(Class<? extends Throwable> retryOn) {
        return RetryExecutor.builder()
                .maxAttempts(TEST_MAX_ATTEMPTS)
                .exponentialBackoff(TEST_INITIAL_DELAY, TEST_MAX_DELAY)
                .retryOn(retryOn)
                .build();
    }

    @Test
    void executeShouldSucceedOnFirstAttempt() throws Exception {
        RetryExecutor executor = createDefaultExecutor(IOException.class);
        String result = executor.execute(() -> "Success");
        assertEquals("Success", result);
    }

    @Test
    void executeShouldThrowWhenAllRetriesFail() {
        RetryExecutor executor = createDefaultExecutor(IOException.class);
        assertThrows(RetryExecutor.RetryExhaustedException.class, () ->
                executor.execute(() -> {
                    throw new IOException("Connection failed");
                })
        );
    }

    @Test
    void executeShouldCallOnExhaustionHandler() {
        AtomicBoolean handlerCalled = new AtomicBoolean(false);
        RetryExecutor executor = RetryExecutor.builder()
                .maxAttempts(1)
                .onExhaustion(ex -> {
                    handlerCalled.set(true);
                    return true;
                })
                .build();

        assertThrows(RetryExecutor.RetryExhaustedException.class, () ->
                executor.execute(() -> {
                    throw new ServerBusyException();
                })
        );
        assertTrue(handlerCalled.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void executeShouldRetryCorrectNumberOfTimes(int failedAttempts) throws Exception {
        AtomicInteger attemptCounter = new AtomicInteger(0);
        RetryExecutor executor = createDefaultExecutor(ServerBusyException.class);

        if (failedAttempts >= TEST_MAX_ATTEMPTS) {
            assertThrows(RetryExecutor.RetryExhaustedException.class, () ->
                    executor.execute(() -> {
                        attemptCounter.incrementAndGet();
                        throw new ServerBusyException();
                    })
            );
        } else {
            assertDoesNotThrow(() ->
                    executor.execute(() -> {
                        if (attemptCounter.getAndIncrement() < failedAttempts) {
                            throw new ServerBusyException();
                        }
                        return "Success";
                    })
            );
        }
        assertEquals(Math.min(failedAttempts + 1, TEST_MAX_ATTEMPTS), attemptCounter.get());
    }

    @Test
    void executeShouldNotRetryNonRetryableExceptions() {
        RetryExecutor executor = createDefaultExecutor(ServerBusyException.class);
        assertThrows(IllegalArgumentException.class, () ->
                executor.execute(() -> {
                    throw new IOException("Non-retryable error");
                })
        );
    }

    @Test
    void executeWithRetryShouldUseDefaultConfiguration() {
        assertThrows(RetryExecutor.RetryExhaustedException.class, () ->
                RetryExecutor.executeWithRetry(() -> {
                    throw new ServerBusyException();
                })
        );
    }

    @Test
    void executeWithRetryShouldSucceedAfterRetries() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);
        String result = RetryExecutor.executeWithRetry(() -> {
            if (attempts.getAndIncrement() < 2) throw new ServerBusyException();
            return "Success after " + attempts.get() + " tries";
        });
        assertTrue(result.contains("Success after 3"));
    }

    @Test
    void executeShouldRespectMaxDelay() throws Exception {
        long start = System.currentTimeMillis();
        RetryExecutor executor = RetryExecutor.builder()
                .maxAttempts(4)
                .exponentialBackoff(500, 2000)
                .retryOn(ServerBusyException.class)
                .build();

        try {
            executor.execute(() -> {
                throw new ServerBusyException();
            });
        } catch (RetryExecutor.RetryExhaustedException ignored) {}

        long duration = System.currentTimeMillis() - start;
        assertTrue(duration >= 500 + 1000 + 2000, "Should respect max delay growth");
    }
}