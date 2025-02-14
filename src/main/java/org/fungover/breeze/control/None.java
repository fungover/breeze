package org.fungover.breeze.control;

import java.io.Serial;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class None<T> extends Option<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final None<?> INSTANCE = new None<>();

    private None() {}


    @SuppressWarnings("unchecked")
    public static <T> None<T> getInstance() {
    return (None<T>) INSTANCE;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof None;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "None";
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

    /**
     * Always returns None since there is no value to filter.
     *
     * @param predicate The condition to test.
     * @return This None instance.
     */

    @Override
    public Option<T> filter(Predicate<? super T> predicate) {
        return this;
    }

    /**
     * Does nothing since there is no value.
     *
     * @param action The action to perform.
     */

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    /**
     * Does nothing and returns this None instance.
     *
     * @param action The action to perform.
     * @return This None instance.
     */

    @Override
    public Option<T> peek(Consumer<? super T> action) {
        return this;
    }

    /**
     * Folds the Option, returning a default value since None contains no value.
     *
     * @param <U> The return type.
     * @param ifNone Supplier for the default value.
     * @param ifPresent Function applied to the value if present.
     * @return The default value from ifNone.
     */

    @Override
    public <U> U fold(Supplier<U> ifNone, Function<? super T, ? extends U> ifPresent) {

        Objects.requireNonNull(ifNone, "ifNone supplier cannot be null");
        Objects.requireNonNull(ifPresent, "ifPresent supplier cannot be null");

        return ifNone.get();
    }


}
