package org.fungover.breeze.control;

import java.util.function.Function;

/**
 * A utility class for executing operations with retry logic.
 * <p>
 * This class allows execution of a risky operation (e.g., network calls, file I/O)
 * with configurable retry behavior, including exponential backoff with jitter and custom failure handling.
 * </p>
 * Example usage:
 * <pre>{@code
 * org.fungover.breeze.control.RetryExecutor executor = org.fungover.breeze.control.RetryExecutor.builder()
 *     .maxAttempts(5)
 *     .exponentialBackoff(100, 1000)
 *     .retryOn(IOException.class)
 *     .build();
 *
 * String result = executor.execute(() -> {
 *     // Risky operation here
 *     return fetchDataFromServer();
 * });
 * }</pre>
 */
public class RetryExecutor {
    private final int maxAttempts;
    private final long initialDelay;
    private final long maxDelay;
    private final Class<? extends Throwable> retryOn;
    private final Function<Throwable, Boolean> onFailure;

    /**
     * Private constructor that initializes the retry executor with builder parameters.
     *
     * @param builder The builder instance used to configure this executor.
     */
    private RetryExecutor(Builder builder) {
        this.maxAttempts = builder.maxAttempts;
        this.initialDelay = builder.initialDelay;
        this.maxDelay = builder.maxDelay;
        this.retryOn = builder.retryOn;
        this.onFailure = builder.onFailure;
    }

    /**
     * Creates a new builder instance for configuring {@link RetryExecutor}.
     *
     * @return A new instance of {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Executes the provided risky operation with retry logic.
     *
     * @param <T>       The type of the return value from the operation.
     * @param operation The operation to execute.
     * @return The result of the operation if successful.
     * @throws InterruptedException          If the thread sleep is interrupted.
     * @throws RetryExhaustedException       If the maximum number of retry attempts is reached.
     * @throws IllegalArgumentException      If the exception is not retryable.
     */
    public <T> T execute(RiskyOperation<T> operation) throws InterruptedException {
        int attempts = 0;
        long delay = initialDelay;

        while (attempts < maxAttempts) {
            try {
                return operation.run(); // Return the result if the operation succeeds
            } catch (Exception ex) {  // Narrowed to Exception
                if (!retryOn.isAssignableFrom(ex.getClass())) {
                    throw new IllegalArgumentException("Operation failed", ex); // Throw if the exception is not retryable
                }
                attempts++;
                if (attempts >= maxAttempts) {
                    if (onFailure != null) {
                        onFailure.apply(ex); // Call the failure handler if provided
                    }
                    throw new RetryExhaustedException("Max retry attempts reached", ex); // Throw if retries are exhausted
                }
                Thread.sleep(delay); // Wait before retrying
                delay = Math.min(maxDelay, (long) (delay * 1.5 + Math.random() * 500));  // Exponential backoff with jitter
            }
        }
        return null;
    }

    /**
     * Builder class for configuring {@link RetryExecutor}.
     */
    public static class Builder {
        private int maxAttempts = 3;
        private long initialDelay = 100;
        private long maxDelay = 1000;
        private Class<? extends Throwable> retryOn = Exception.class;
        private Function<Throwable, Boolean> onFailure = null;

        /**
         * Sets the maximum number of retry attempts.
         *
         * @param maxAttempts The maximum retry attempts.
         * @return The updated builder instance.
         */
        public Builder maxAttempts(int maxAttempts) {
            if (maxAttempts <= 0) {
                throw new IllegalArgumentException("maxAttempts must be positive");
            }
            this.maxAttempts = maxAttempts;
            return this;
        }

        /**
         * Configures exponential backoff with initial and maximum delays.
         *
         * @param initialDelay The initial delay in milliseconds.
         * @param maxDelay     The maximum delay in milliseconds.
         * @return The updated builder instance.
         */
        public Builder exponentialBackoff(long initialDelay, long maxDelay) {
            if (initialDelay <= 0 || maxDelay <= 0) {
                throw new IllegalArgumentException("delays must be positive");
            }
            if (maxDelay < initialDelay) {
                throw new IllegalArgumentException("maxDelay must be greater than or equal to initialDelay");
            }
            this.initialDelay = initialDelay;
            this.maxDelay = maxDelay;
            return this;
        }

        /**
         * Specifies the type of exception that should trigger a retry.
         *
         * @param retryOn The exception class that should be retried.
         * @return The updated builder instance.
         */
        public Builder retryOn(Class<? extends Throwable> retryOn) {
            this.retryOn = retryOn;
            return this;
        }

        /**
         * Sets a failure handler to execute when all retry attempts are exhausted.
         *
         * @param onFailure A function that accepts the thrown exception and returns a boolean.
         * @return The updated builder instance.
         */
        public Builder onExhaustion(Function<Throwable, Boolean> onFailure) {
            this.onFailure = onFailure;
            return this;
        }

        /**
         * Builds and returns a configured instance of {@link RetryExecutor}.
         *
         * @return A configured {@link RetryExecutor} instance.
         */
        public RetryExecutor build() {
            return new RetryExecutor(this);
        }
    }

    /**
     * Functional interface representing a risky operation that may fail.
     *
     * @param <T> The return type of the operation.
     */
    public interface RiskyOperation<T> {
        /**
         * Executes the risky operation.
         *
         * @return The result of the operation.
         * @throws Exception If an exception occurs during execution.
         */
        T run() throws Exception;
    }

    /**
     * Exception thrown when the maximum number of retry attempts is exhausted.
     */
    public static class RetryExhaustedException extends RuntimeException {
        /**
         * Constructs a {@code RetryExhaustedException} with the given message and cause.
         *
         * @param message The error message.
         * @param cause   The original exception that caused the failure.
         */
        public RetryExhaustedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Static helper method for executing an operation with predefined retry logic.
     *
     * @param <T>       The type of the return value from the operation.
     * @param operation The operation to execute.
     * @return The result of the operation if successful.
     * @throws Exception If the operation fails after all retry attempts.
     */
    public static <T> T executeWithRetry(RiskyOperation<T> operation) throws Exception {
        return builder()
                .maxAttempts(5)
                .exponentialBackoff(1000, 16000) // 1s, 2s, 4s, 8s, 16s
                .retryOn(ServerBusyException.class)
                .onExhaustion(ex -> {
                    System.out.println("Final attempt failed after exponential backoff");
                    return true;
                })
                .build()
                .execute(operation);
    }
}
