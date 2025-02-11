import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class RetryExecutorTest {

    @Test
    public void testRetryWithExponentialBackoff() throws InterruptedException {
        RetryExecutor retryExecutor = RetryExecutor.builder()
                .maxAttempts(3)
                .exponentialBackoff(100, 500)
                .retryOn(IOException.class)
                .build();

        RetryExecutor.RiskyOperation<Boolean> operation = () -> {
            throw new IOException("Simulated failure");
        };

        // Förvänta dig att RetryExecutor misslyckas efter max attempts
        Exception exception = assertThrows(RetryExecutor.RetryExhaustedException.class, () -> {
            retryExecutor.execute(operation);
        });

        assertEquals("Max retry attempts reached", exception.getMessage());
    }





}
