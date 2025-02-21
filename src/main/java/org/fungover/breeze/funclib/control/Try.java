package org.fungover.breeze.funclib.control;

import java.io.Serializable;



// STUB CLASS //

// STUB CLASS //

// STUB CLASS //

public abstract class Try<T> implements Serializable {
    @SuppressWarnings("unused")
    public abstract boolean isSuccess();
    @SuppressWarnings("unused")
    public abstract boolean isFailure();

    public abstract T get() throws Exception;

    public static <T> Try<T> success(T value) {
        return new Success<>(value);
    }

    public static <T> Try<T> failure(Exception exception) {
        return new Failure<>(exception);
    }

    public static final class Success<T> extends Try<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public T get() {
            return value;
        }
    }

    public static final class Failure<T> extends Try<T> {
        private final Exception exception;

        public Failure(Exception exception) {
            this.exception = exception;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public T get() throws Exception {
            throw exception;
        }
    }
}