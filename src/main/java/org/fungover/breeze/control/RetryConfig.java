package org.fungover.breeze.control;

public final class RetryConfig {
    public static final int DEFAULT_MAX_ATTEMPTS = 5;
    public static final long DEFAULT_INITIAL_DELAY = 1000;
    public static final long DEFAULT_MAX_DELAY = 16000;

    private RetryConfig() {
        throw new AssertionError("Utility class cannot be instantiated");
    }
}