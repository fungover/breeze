package org.fungover.breeze.control;

import java.util.function.Function;
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

    /**
     * Throws an exception provided by the supplier since None<T> has no value.
     *
     * @param <X>               the type of the exception to be thrown
     * @param exceptionSupplier a supplier function that provides the exception to throw
     * @return nothing, always throws an exception
     * @throws X the provided exception
     */

    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        throw exceptionSupplier.get();
    }

    /**
     * Returns None since there is no value to transform.
     * <p>
     * The function is ignored because None<T> represents an empty state.
     *
     * @param <U>    the type of the would-be transformed value
     * @param mapper a function to apply (ignored)
     * @return None<U>, since mapping an empty Option still results in None
     */

    @Override
    public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
        return None.getInstance();
    }

    /**
     * Returns None since there is no value to transform.
     * <p>
     * The function is ignored because None<T> represents an empty state.
     *
     * @param <U>    the type of the would-be transformed Option
     * @param mapper a function that takes the contained value and returns an Option<U> (ignored)
     * @return None<U>, since flat-mapping an empty Option still results in None
     */

    @Override
    public <U> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
        return None.getInstance();
    }
}
