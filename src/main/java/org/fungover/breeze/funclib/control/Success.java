package org.fungover.breeze.funclib.control;

import java.io.Serializable;

/**
 * Represents a successful computation result.
 */
final class Success<T> extends Try<T> implements Serializable {
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
        return this.value;
    }
}
