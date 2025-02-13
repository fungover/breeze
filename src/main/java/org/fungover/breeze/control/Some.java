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

    /**
     * Returns the contained value since Some<T> is never empty.
     *
     * @param <X> the type of the exception to be thrown (ignored in Some)
     * @param exceptionSupplier a supplier function that provides an exception (ignored in Some)
     * @return the contained value
     */

    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        return value;
    }

    /**
     * Transforms the contained value using the provided mapping function.
     * <p>
     * Since Some<T> contains a value, the function is applied to the value, and the result
     * is wrapped in a new Some<U>.
     *
     * @param <U>    the type of the transformed value
     * @param mapper a function to apply to the contained value
     * @return a new Some<U> containing the transformed value
     */

    @Override
    public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
        return new Some<>(mapper.apply(value));
    }

    /**
     * Transforms the contained value using a function that returns an Option.
     * <p>
     * This enables chaining multiple Option-based computations while preserving Some.
     * If the function returns None, the result will be None.
     *
     * @param <U>    the type of the resulting Option
     * @param mapper a function that takes the contained value and returns an Option<U>
     * @return the mapped Option<U> resulting from applying the function
     */

    @Override
    public <U> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
        return mapper.apply(value);
    }
}
