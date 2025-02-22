package org.fungover.breeze.control;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RetryExecutorTest {

    // Test successful execution on the first attempt
    @Test
    void testExecute_SuccessOnFirstAttempt() throws InterruptedException {
        RetryExecutor executor = RetryExecutor.builder()
                .maxAttempts(3)
                .exponentialBackoff(100, 1000)
                .retryOn(IOException.class)
                .build();

        String result = executor.execute(() -> "Success");
        assertEquals("Success", result);
    }

    // Test retry logic for exception
    @Test
    void testExecute_RetryOnException() {
        RetryExecutor executor = RetryExecutor.builder()
                .maxAttempts(3)
                .exponentialBackoff(100, 1000)
                .retryOn(IOException.class)
                .build();

        // Vi förväntar oss att ett undantag kastas eftersom alla retries misslyckas
        assertThrows(RetryExecutor.RetryExhaustedException.class, () -> {
            executor.execute(() -> {
                throw new IOException("Failed");
            });
        });
    }

    // Test exhaustion handler
    @Test
    void testExecute_OnExhaustion() {
        RetryExecutor executor = RetryExecutor.builder()
                .maxAttempts(3)
                .exponentialBackoff(100, 1000)
                .retryOn(IOException.class)
                .onExhaustion(ex -> {
                    System.out.println("Retry exhausted");
                    return true;
                })
                .build();

        assertThrows(RetryExecutor.RetryExhaustedException.class, () -> {
            executor.execute(() -> {
                throw new IOException("Failed");
            });
        });
    }

    // Test for retry logic with ServerBusyException
    @Test
    void testServerBusyRetry() {
        assertThrows(RetryExecutor.RetryExhaustedException.class, () -> {
            RetryExecutor.executeWithRetry(() -> {
                throw new ServerBusyException();
            });
        });
    }
    // New test case
    @Test
    void testSuccessfulRetry() throws Exception {
        final int[] attempts = {0};
        String result = RetryExecutor.executeWithRetry(() -> {
            if (attempts[0]++ < 2) throw new ServerBusyException();
            return "Success after " + attempts[0] + " tries";
        });
        assertTrue(result.contains("Success after 3"));
    }
}
