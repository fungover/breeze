package org.fungover.breeze.control;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Some<T> extends Option<T> {

    private final T value;

    public Some(final T value) {
        this.value = value;
    }


    /**
     * Returns true if this Option is empty (None), false otherwise.
     *
     * @return true if None, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Gets the contained value.
     *
     * @return the contained value
     * @throws UnsupportedOperationException if called on None
     */

    @Override
    public T get() {
        return value;
    }

    /**
     * @param other
     * @return
     */

    @Override
    public T getOrElse(T other) {
        return value;
    }

    /**
     * Returns the value if present, otherwise computes and returns a default value.
     *
     * @param supplier the supplier function to generate a default value
     * @return the contained value or a computed default
     */

    @Override
    public T getOrElseGet(Supplier<? extends T> supplier) {
        return value;
    }

    /**
     * Returns the contained value or null if None.
     *
     * @return the contained value or null
     */
    @Override
    public T getOrNull() {
        return value;
    }
}
