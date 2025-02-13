package org.fungover.breeze.funclib.control;

import java.io.Serializable;

/**
 * Represents a failed computation result.
 */
final class Failure<T> extends Try<T>  implements Serializable {
    private final Throwable throwable;

    public Failure(Throwable throwable) {
        this.throwable = throwable;
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
    public T get() throws Throwable {
        throw throwable;
    }

    public Throwable throwable() {
        return throwable;
    }
}
