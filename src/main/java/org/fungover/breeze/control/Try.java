package org.fungover.breeze.control;

class Try<T> {
    private final T value;
    private final Exception exception;

    private Try(T value, Exception exception) {
        this.value = value;
        this.exception = exception;
    }

    public static <T> Try<T> success(T value) {
        return new Try<>(value, null);
    }

    public static <T> Try<T> failure(Exception exception) {
        return new Try<>(null, exception);
    }

    public boolean isSuccess() {
        return exception == null;
    }

    public T get() {
        if (exception != null) {
            throw new RuntimeException(exception);
        }
        return value;
    }
}
