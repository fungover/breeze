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
    void testExecute_RetryOnException() throws InterruptedException {
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
    void testExecute_OnExhaustion() throws InterruptedException {
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
}



