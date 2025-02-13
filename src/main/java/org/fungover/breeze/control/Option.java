package org.fungover.breeze.control;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;

/**
 * A generic container type that represents an optional value.
 * <p>
 * This class follows the functional programming paradigm, providing a safer alternative
 * to {@code null} by representing an optional value. It can either contain a value
 * ({@code Some<T>}) or be empty ({@code None<T>}).
 *
 * @param <T> the type of the contained value
 */

public abstract class Option<T> {

    /**
     * Creates an {@code Option<T>} instance based on the provided value.
     * <p>
     * If the value is non-null, it returns a {@code Some<T>} instance wrapping the value.
     * Otherwise, it returns a singleton instance of {@code None<T>}.
     *
     * @param value the value to wrap in an {@code Option}
     * @param <T>   the type of the value
     * @return an {@code Option<T>} containing the value, or {@code None<T>} if the value is null
     */

    public static <T> Option<T> of(T value) {
        return value != null ? new Some<>(value) : None.getInstance();
    }

    /**
     * Creates a {@code Some<T>} instance containing the given value.
     * <p>
     * Unlike {@code of()}, this method does not return {@code None<T>};
     * it strictly requires a non-null value. If {@code null} is passed, a {@code NullPointerException}
     * is thrown to enforce non-null constraints.
     *
     * @param value the non-null value to wrap in {@code Some<T>}
     * @param <T>   the type of the value
     * @return an instance of {@code Some<T>} containing the provided value
     * @throws NullPointerException if {@code value} is {@code null}
     */

    public static <T> Option<T> some(T value) {
        if (value == null) {
            throw new NullPointerException("Cannot create 'Some' with null");
        }
        return new Some<>(value);
    }

    /**
     * Returns true if this Option is empty (None), false otherwise.
     * @return true if None, false otherwise
     */

    public abstract boolean isEmpty();

    /**
     * Returns true if this Option contains a value (Some), false otherwise.
     * @return true if Some, false if None
     */

    public boolean isDefined(){
        return !isEmpty();

    }

    /**
     * Gets the contained value.
     * @return the contained value
     * @throws UnsupportedOperationException if called on None
     */

    public abstract T get();

    /**
     * Returns the value if present, otherwise returns the provided default value.
     * @param other the default value
     * @return the contained value or other
     */

    public abstract T getOrElse(T other);

    /**
     * Returns the value if present, otherwise computes and returns a default value.
     * @param supplier the supplier function to generate a default value
     * @return the contained value or a computed default
     */

    public abstract T getOrElseGet(Supplier<? extends T> supplier);

    /**
     * Returns the contained value or null if None.
     * @return the contained value or null
     */

    public abstract T getOrNull();

    /**
     * Returns the contained value if present, otherwise throws an exception provided by the supplier.
     *
     * @param <X>               the type of the exception to be thrown
     * @param exceptionSupplier a supplier function that provides the exception to throw
     * @return the contained value if present
     * @throws X if the Option is None, the supplied exception is thrown
     */

    public abstract <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * Transforms the contained value using the provided mapping function.
     * <p>
     * If the Option is Some, applies the function and wraps the result in a new Some.
     * If the Option is None, returns None.
     *
     * @param <U>    the type of the transformed value
     * @param mapper a function to apply to the contained value
     * @return an Option containing the transformed value, or None if empty
     */


    public abstract <U> Option<U> map(Function<? super T, ? extends U> mapper);

    /**
     * Transforms the contained value using a function that returns an Option.
     * <p>
     * This allows chaining multiple computations while preserving None.
     * If the Option is Some, applies the function and returns the result.
     * If the Option is None, returns None.
     *
     * @param <U>    the type of the resulting Option
     * @param mapper a function that takes the contained value and returns an Option<U>
     * @return the mapped Option<U> or None if empty
     */


    public abstract <U> Option<U> flatMap(Function<? super T, Option<U>> mapper);
}
