package org.fungover.breeze.control;

import java.util.function.Function;

public class RetryExecutor {
    private final int maxAttempts;
    private final long initialDelay;
    private final long maxDelay;
    private final Class<? extends Throwable> retryOn;
    private final Function<Throwable, Boolean> onFailure;

    private RetryExecutor(Builder builder) {
        this.maxAttempts = builder.maxAttempts;
        this.initialDelay = builder.initialDelay;
        this.maxDelay = builder.maxDelay;
        this.retryOn = builder.retryOn;
        this.onFailure = builder.onFailure;
    }

    public static Builder builder() {
        return new Builder();
    }

    public <T> T execute(RiskyOperation<T> operation) throws InterruptedException {
        int attempts = 0;
        long delay = initialDelay;

        while (attempts < maxAttempts) {
            try {
                System.out.println("Attempt " + (attempts + 1) + " to execute operation");
                return operation.run();
            } catch (Exception ex) {
                if (!retryOn.isAssignableFrom(ex.getClass())) {
                    System.out.println("Operation failed with non-retryable exception: " + ex.getMessage());
                    throw new IllegalArgumentException("Operation failed", ex);
                }
                attempts++;
                if (attempts >= maxAttempts) {
                    if (onFailure != null) {
                        onFailure.apply(ex);
                    }
                    System.err.println("[WARN] Max retry attempts reached: " + ex.getMessage());
                    throw new RetryExhaustedException("Max retry attempts reached", ex);
                }
                System.out.println("Retrying after " + delay + " ms");
                Thread.sleep(delay);
                delay = Math.min(maxDelay, (long) (delay * 1.5 + Math.random() * 500));
            }
        }
        return null;
    }

    public static class Builder {
        private int maxAttempts = 3;
        private long initialDelay = 100;
        private long maxDelay = 1000;
        private Class<? extends Throwable> retryOn = Exception.class;
        private Function<Throwable, Boolean> onFailure = null;

        public Builder maxAttempts(int maxAttempts) {
            if (maxAttempts <= 0) {
                throw new IllegalArgumentException("maxAttempts must be positive");
            }
            this.maxAttempts = maxAttempts;
            return this;
        }

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

        public Builder retryOn(Class<? extends Throwable> retryOn) {
            this.retryOn = retryOn;
            return this;
        }

        public Builder onExhaustion(Function<Throwable, Boolean> onFailure) {
            this.onFailure = onFailure;
            return this;
        }

        public RetryExecutor build() {
            return new RetryExecutor(this);
        }
    }

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
                    System.err.println("[WARN] Final attempt failed: " + ex.getMessage());
                    return true;
                })
                .build()
                .execute(operation);
    }
}
