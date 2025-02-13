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
}
