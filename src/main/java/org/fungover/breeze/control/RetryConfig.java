// RetryConfig.java
package org.fungover.breeze.control;

public final class RetryConfig {
    public static final int DEFAULT_MAX_ATTEMPTS = 5;
    public static final long DEFAULT_INITIAL_DELAY = 1000; // 1 second
    public static final long DEFAULT_MAX_DELAY = 16000; // 16 seconds

    private RetryConfig() { // Add private constructor
        throw new AssertionError("Utility class should not be instantiated");
    }
}