package org.fungover.breeze.control;

import java.util.function.Supplier;

public final class None<T> extends Option<T> {

private static final None<?> INSTANCE = new None<>();

private None() {}



    @SuppressWarnings("unchecked")
    public static <T> None<T> getInstance() {
    return (None<T>) INSTANCE;
    }


    /**
     * Returns true if this Option is empty (None), false otherwise.
     *
     * @return true if None, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * Gets the contained value.
     *
     * @return the contained value
     * @throws UnsupportedOperationException if called on None
     */
    @Override
    public T get() {
        throw new UnsupportedOperationException("Cannot call get() on None");
    }

    /**
     * @param other
     * @return
     */
    @Override
    public T getOrElse(T other) {
        return other;
    }

    /**
     * Returns the value if present, otherwise computes and returns a default value.
     *
     * @param supplier the supplier function to generate a default value
     * @return the contained value or a computed default
     */
    @Override
    public T getOrElseGet(Supplier<? extends T> supplier) {
        return supplier.get();
    }

    /**
     * Returns the contained value or null if None.
     *
     * @return the contained value or null
     */
    @Override
    public T getOrNull() {
        return null;
    }
}
