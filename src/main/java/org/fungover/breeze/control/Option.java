
package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Either;
import org.fungover.breeze.funclib.control.Try;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Represents an optional value that may either contain a non-null value (Some) or be empty (None).
 * This class provides a functional approach to handling optional values without using null.
 *
 * @param <T> the type of the contained value, which must be serializable.
 */
public abstract class Option<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Compares this option with another object for equality.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal, otherwise {@code false}
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Computes the hash code for this option.
     *
     * @return the hash code
     */
    @Override
    public abstract int hashCode();

    /**
     * Returns a string representation of this option.
     *
     * @return a string describing this option
     */
    @Override
    public abstract String toString();


    /**
     * Creates an {@code Option} instance containing the given value, or {@code None} if the value is null.
     *
     * @param value the value to wrap
     * @param <T> the type of the value
     * @return an {@code Option} containing the value, or {@code None} if the value is null
     */
    public static <T extends Serializable> Option<T> of(final T value) {
        return value != null ? new Some<>(value) : None.getInstance();
    }

    /**
     * Creates a {@code Some} instance containing the given non-null value.
     *
     * @param value the non-null value to wrap
     * @param <T> the type of the value
     * @return a {@code Some} instance
     * @throws NullPointerException if the value is null
     */
    public static <T extends Serializable> Option<T> some(final T value) {
        if (value == null) {
            throw new NullPointerException("Cannot create 'Some' with null");
        }
        return new Some<>(value);
    }

    /**
     * Returns a singleton instance of {@code None}.
     *
     * @param <T> the type parameter
     * @return a {@code None} instance
     */
    public static <T extends Serializable> Option<T> none() {
        return None.getInstance();
    }

    /**
     * Checks if this option is empty.
     *
     * @return {@code true} if empty, otherwise {@code false}
     */
    public abstract boolean isEmpty();


    /**
     * Checks if this option contains a value.
     *
     * @return {@code true} if a value is present, otherwise {@code false}
     */
    public final boolean isDefined() {
        return !isEmpty();

    }

    /**
     * Retrieves the value contained in this option.
     *
     * @return the contained value
     * @throws NoSuchElementException if this option is empty
     */
    public abstract T get();

    /**
     * Retrieves the value or returns the specified alternative if empty.
     *
     * @param other the alternative value
     * @return the contained value or {@code other} if empty
     */
    public abstract T getOrElse(T other);

    /**
     * Retrieves the value or supplies an alternative if empty.
     *
     * @param supplier a supplier of an alternative value
     * @return the contained value or the supplied value if empty
     */
    public abstract T getOrElseGet(Supplier<? extends T> supplier);

    /**
     * Retrieves the value or returns {@code null} if empty.
     *
     * @return the contained value or {@code null}
     */
    public abstract T getOrNull();

    /**
     * Retrieves the value or throws an exception supplied by the given supplier.
     *
     * @param exceptionSupplier a supplier of an exception to be thrown
     * @param <X> the type of the exception
     * @return the contained value
     * @throws X if the option is empty
     */
    public abstract <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * Transforms the contained value using the given function.
     *
     * @param mapper the mapping function
     * @param <U> the type of the result
     * @return an {@code Option} containing the transformed value or {@code None} if empty
     */
    public abstract <U extends Serializable> Option<U> map(Function<? super T, ? extends U> mapper);


    /**
     * Transforms the contained value into another option using the given function.
     *
     * @param mapper the mapping function
     * @param <U> the type of the result
     * @return the resulting option or {@code None} if empty
     */
    public abstract <U extends Serializable> Option<U> flatMap(Function<? super T, Option<U>> mapper);

    /**
     * Filters the contained value based on a predicate.
     *
     * @param predicate the filtering predicate
     * @return the same option if it matches the predicate, otherwise {@code None}
     */
    public abstract Option<T> filter(Predicate<? super T> predicate);


    /**
     * Performs an action on the contained value if present.
     *
     * @param action the action to perform
     */
    public abstract void forEach(Consumer<? super T> action);

    /**
     * Peeks at the contained value without modifying it.
     *
     * @param action the action to perform
     * @return this option
     */
    public abstract Option<T> peek(Consumer<? super T> action);

    /**
     * Applies a function to the present value or returns a default value
     * if none is present.
     *
     * @param <U>       the type of the result
     * @param ifNone    a {@code Supplier} providing a default value if none is present
     * @param ifPresent a {@code Function} to apply to the present value
     * @return the result of applying {@code ifPresent} to the value,
     *         or the result of {@code ifNone} if no value is present
     * @throws NullPointerException if {@code ifNone} or {@code ifPresent} is null
     */
    public abstract <U> U fold(Supplier<U> ifNone, Function<? super T, ? extends U> ifPresent);

    /**
     * Creates an {@code Option} from a nullable value.
     * If the value is non-null, it returns an instance of {@code Some};
     * otherwise, it returns {@code None}.
     *
     * @param <T>   the type of the value
     * @param value the nullable value to wrap
     * @return an {@code Option} containing the value if non-null,
     *         or {@code None} if the value is null
     */
    public static <T extends Serializable> Option<T> ofNullable(final T value) {
        return value != null ? new Some<>(value) : None.getInstance();
    }

    /**
     * Converts the Option to a list containing the value if present,
     * or an empty list if none.
     *
     * @return a {@code List} containing the present value or an empty list
     */
    public abstract List<T> toList();

    /**
     * Converts the Option to a {@code Stream} containing the value if present,
     * or an empty stream if none.
     *
     * @return a {@code Stream} containing the present value or an empty stream
     */
    public abstract Stream<T> toStream();

    /**
     * Converts this option to a Java {@link Optional}.
     *
     * @return an {@code Optional} instance
     */
    public abstract Optional<T> toOptional();

    /**
     * Converts this {@code Option} to an {@link Either}.
     *
     * @param leftSupplier supplies the left value if empty.
     * @param <L> the left type.
     * @return an {@code Either}.
     */
    public abstract <L extends Serializable> Either<L, T> toEither(Supplier<? extends L> leftSupplier);



    /**
     * Returns this option itself.
     *
     * @return this option.
     */
    public Option<T> toOption() {
        return this;
    }


    /**
     * Converts this option to a {@code Try} instance.
     *
     * @param exceptionSupplier a supplier for an exception if empty
     * @return a {@code Try} instance
     */
    public abstract Try<T> toTry(Supplier<Exception> exceptionSupplier);


    /**
     * Throws an exception if this {@code Option} is empty.
     *
     * @throws NoSuchElementException if empty.
     */
    public abstract void orElseThrow();


    /**
     * Creates an {@code Option} from an {@code Either}, returning {@code Some} if the right value exists.
     *
     * @param either the {@code Either} instance.
     * @param <L> the left type.
     * @param <R> the right type.
     * @return an {@code Option} wrapping the right value.
     */
    public static <L extends Serializable, R extends Serializable> Option<R> fromEither(final Either<L, R> either) {
        return either.isRight() ? Option.some(either.getRight()) : Option.none();
    }
}