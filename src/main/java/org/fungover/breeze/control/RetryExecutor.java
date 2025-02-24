package org.fungover.breeze.control;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.function.Function;

public class RetryExecutor {
    private static final SecureRandom JITTER = new SecureRandom();
    private final int maxAttempts;
    private final long initialDelay;
    private final long maxDelay;
    private final Class<? extends Throwable> retryOn;
    private final Function<Throwable, Boolean> onExhaustion;

    private RetryExecutor(Builder builder) {
        this.maxAttempts = builder.maxAttempts;
        this.initialDelay = builder.initialDelay;
        this.maxDelay = builder.maxDelay;
        this.retryOn = builder.retryOn;
        this.onExhaustion = builder.onExhaustion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public <T> T execute(RiskyOperation<T> operation) throws InterruptedException {
        int attemptCount = 0;
        long currentDelay = initialDelay;

        while (attemptCount < maxAttempts) {
            try {
                logAttempt(attemptCount + 1);
                return operation.run();
            } catch (Exception ex) {
                handleOperationError(ex, attemptCount, currentDelay);
                attemptCount++;
                currentDelay = calculateNextDelay(currentDelay);
                Thread.sleep(currentDelay);
            }
        }
        throw new IllegalStateException("Unexpected execution path reached");
    }

    private void logAttempt(int attemptNumber) {
        System.out.printf("Attempt %d/%d%n", attemptNumber, maxAttempts);
    }

    private void handleOperationError(Exception ex, int attemptCount, long delay) {
        if (!retryOn.isAssignableFrom(ex.getClass())) {
            throw new IllegalArgumentException("Non-retryable exception occurred", ex);
        }

        if (attemptCount + 1 >= maxAttempts) {
            handleFinalFailure(ex);
        }

        System.err.printf("Retry %d/%d failed. Next attempt in %dms%n",
                attemptCount + 1, maxAttempts - 1, delay);
    }

    private void handleFinalFailure(Throwable ex) {
        if (onExhaustion != null) {
            onExhaustion.apply(ex);
        }
        throw new RetryExhaustedException(
                String.format("Operation failed after %d attempts", maxAttempts), ex
        );
    }

    private long calculateNextDelay(long currentDelay) {
        long nextDelay = (long) (currentDelay * 1.5 + JITTER.nextDouble() * 500);
        return Math.min(maxDelay, nextDelay);
    }

    public static class Builder {
        private int maxAttempts = 3;
        private long initialDelay = 100;
        private long maxDelay = 1000;
        private Class<? extends Throwable> retryOn = Exception.class;
        private Function<Throwable, Boolean> onExhaustion;

        public Builder maxAttempts(int maxAttempts) {
            validatePositive(maxAttempts, "Max attempts");
            this.maxAttempts = maxAttempts;
            return this;
        }

        public Builder exponentialBackoff(long initial, long max) {
            validatePositive(initial, "Initial delay");
            validatePositive(max, "Max delay");
            if (max < initial) throw new IllegalArgumentException("Max delay < Initial delay");
            this.initialDelay = initial;
            this.maxDelay = max;
            return this;
        }

        public Builder retryOn(Class<? extends Throwable> exceptionType) {
            this.retryOn = Objects.requireNonNull(exceptionType, "Exception type cannot be null");
            return this;
        }

        public Builder onExhaustion(Function<Throwable, Boolean> handler) {
            this.onExhaustion = handler;
            return this;
        }

        private void validatePositive(long value, String name) {
            if (value <= 0) throw new IllegalArgumentException(name + " must be positive");
        }

        public RetryExecutor build() {
            return new RetryExecutor(this);
        }
    }

    @FunctionalInterface
    public interface RiskyOperation<T> {
        T run() throws Exception;
    }

    public static class RetryExhaustedException extends RuntimeException {
        public RetryExhaustedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static <T> T executeWithRetry(RiskyOperation<T> operation) throws Exception {
        return builder()
                .maxAttempts(5)
                .exponentialBackoff(1000, 16000)
                .retryOn(ServerBusyException.class)
                .onExhaustion(ex -> {
                    System.err.println("Operation failed permanently: " + ex.getMessage());
                    return true;
                })
                .build()
                .execute(operation);
    }
}