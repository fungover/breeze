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

    // Builder class for RetryExecutor configuration
    public static class Builder {
        private int maxAttempts = 3;
        private long initialDelay = 100;
        private long maxDelay = 1000;
        private Class<? extends Throwable> retryOn = Exception.class;
        private Function<Throwable, Boolean> onFailure = null;

        public Builder maxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
            return this;
        }

        public Builder exponentialBackoff(long initialDelay, long maxDelay) {
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

    public <T> T execute(RiskyOperation<T> operation) throws InterruptedException {
        int attempts = 0;
        long delay = initialDelay;

        while (attempts < maxAttempts) {
            try {
                return operation.run();
            } catch (Throwable ex) {
                if (!retryOn.isAssignableFrom(ex.getClass())) {
                    throw new RuntimeException("Operation failed", ex);
                }
                attempts++;
                if (attempts >= maxAttempts) {
                    if (onFailure != null) {
                        onFailure.apply(ex);
                    }
                    throw new RetryExhaustedException("Max retry attempts reached", ex);
                }
                Thread.sleep(delay);
                delay = Math.min(maxDelay, delay * 2);  // Exponential backoff
            }
        }
        return null;
    }

    // Functional interface for risky operation
    public interface RiskyOperation<T> {
        T run() throws Throwable;
    }

    // Exception for when retry attempts are exhausted
    public static class RetryExhaustedException extends RuntimeException {
        public RetryExhaustedException(String message, Throwable cause) {
            super(message, cause);
        }
    }








}