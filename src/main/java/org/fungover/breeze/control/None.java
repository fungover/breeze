package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Either;
import org.fungover.breeze.funclib.control.Try;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;


/**
 * Represents an empty {@link Option} instance, indicating the absence of a value.
 * This class is a singleton and is used to represent the "None" case in an optional value context.
 *
 * @param <T> the type of the value that this {@code None} represents (though it holds no value).
 */
public final class None<T extends Serializable> extends Option<T> {



    @Serial
    private static final long serialVersionUID = 1L;


    private static final None<?> INSTANCE = new None<>();

    /**
     * Private constructor to enforce singleton pattern.
     */
    private None() {
    }

    /**
     * Returns the singleton instance of {@code None}.
     *
     * @param <T> the type parameter for the {@code None} instance.
     * @return the singleton instance of {@code None}.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> None<T> getInstance() {
        return (None<T>) INSTANCE;
    }

    /**
     * Compares this {@code None} instance with another object for equality.
     * All instances of {@code None} are considered equal.
     *
     * @param obj the object to compare with.
     * @return {@code true} if the object is an instance of {@code None}, otherwise {@code false}.
     */
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof None; // Ensures correct behavior even if different instances exist due to serialization issues.
    }

    /**
     * Returns the hash code for this {@code None} instance.
     * All instances of {@code None} have the same hash code.
     *
     * @return the hash code, which is always 0.
     */
    @Override
    public int hashCode() {
        return 0; // All instances of None have the same hash code since they are the same instance.
    }

    /**
     * Returns a string representation of this {@code None} instance.
     *
     * @return the string "None()".
     */
    @Override
    public String toString() {
        return "None()";
    }

    /**
     * Indicates whether this {@code Option} is empty.
     *
     * @return {@code true} since {@code None} represents an empty value.
     */
    @Override
    public boolean isEmpty() {
        return true;
    }


    /**
     * Throws an {@link UnsupportedOperationException} since {@code None} does not contain a value.
     *
     * @return never returns, always throws an exception.
     * @throws UnsupportedOperationException always thrown when called.
     */
    @Override
    public T get() {
        throw new UnsupportedOperationException("Cannot call get() on None");
    }

    /**
     * Returns the provided alternative value since {@code None} does not contain a value.
     *
     * @param other the alternative value to return.
     * @return the provided alternative value.
     */
    @Override
    public T getOrElse(final T other) {
        return other;
    }

    /**
     * Returns the result of invoking the provided supplier since {@code None} does not contain a value.
     *
     * @param supplier the supplier to invoke.
     * @return the result of the supplier.
     * @throws NullPointerException if the supplier is {@code null}.
     */
    @Override
    public T getOrElseGet(final Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "Supplier must not be null");
        return supplier.get();
    }

    /**
     * Returns {@code null} since {@code None} does not contain a value.
     *
     * @return {@code null}.
     */
    @Override
    public T getOrNull() {

        return null;
    }

    /**
     * Throws the exception provided by the supplier since {@code None} does not contain a value.
     *
     * @param <X> the type of the exception to throw.
     * @param exceptionSupplier the supplier of the exception to throw.
     * @return never returns, always throws an exception.
     * @throws X the exception provided by the supplier.
     * @throws NullPointerException if the exception supplier is {@code null}.
     */
    @Override
    public <X extends Throwable> T orElseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
        throw exceptionSupplier.get(); // Throw the provided exception
    }

    /**
     * Returns a {@code None} instance since mapping over an empty value results in no value.
     *
     * @param mapper the mapping function (ignored).
     * @param <U> the type of the mapped value.
     * @return a {@code None} instance.
     * @throws NullPointerException if the mapper is {@code null}.
     */
    @Override
    public <U extends Serializable> Option<U> map(final Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper, "Mapper function cannot be null");
        return None.getInstance();
    }


    /**
     * Returns a {@code None} instance since flat-mapping over an empty value results in no value.
     *
     * @param mapper the flat-mapping function (ignored).
     * @param <U> the type of the mapped value.
     * @return a {@code None} instance.
     * @throws NullPointerException if the mapper is {@code null}.
     */
    @Override
    public <U extends Serializable> Option<U> flatMap(final Function<? super T, Option<U>> mapper) {
        Objects.requireNonNull(mapper, "Mapper function cannot be null");
        return None.getInstance();
    }

    /**
     * Returns this {@code None} instance since filtering an empty value results in no value.
     *
     * @param predicate the predicate to apply (ignored).
     * @return this {@code None} instance.
     */
    @Override
    public Option<T> filter(final Predicate<? super T> predicate) {
        return this;
    }

    /**
     * Does nothing since {@code None} does not contain a value to consume.
     *
     * @param action the action to perform (ignored).
     */
    @Override
    public void forEach(final Consumer<? super T> action) {

    }

    /**
     * Returns this {@code None} instance since peeking into an empty value results in no value.
     *
     * @param action the action to perform (ignored).
     * @return this {@code None} instance.
     */
    @Override
    public Option<T> peek(final Consumer<? super T> action) {
        return this;
    }

    /**
     * Returns the result of invoking the {@code ifNone} supplier since {@code None} does not contain a value.
     *
     * @param ifNone the supplier to invoke if this is {@code None}.
     * @param ifPresent the function to apply if this is {@code Some} (ignored).
     * @param <U> the type of the result.
     * @return the result of the {@code ifNone} supplier.
     * @throws NullPointerException if either {@code ifNone} or {@code ifPresent} is {@code null}.
     */
    @Override
    public <U> U fold(final Supplier<U> ifNone, final Function<? super T, ? extends U> ifPresent) {

        Objects.requireNonNull(ifNone, "ifNone supplier cannot be null");
        Objects.requireNonNull(ifPresent, "ifPresent supplier cannot be null");

        return ifNone.get();
    }

    /**
     * Returns an empty list since {@code None} does not contain a value.
     *
     * @return an empty list.
     */
    @Override
    public List<T> toList() {
        return List.of();
    }

    /**
     * Returns an empty stream since {@code None} does not contain a value.
     *
     * @return an empty stream.
     */
    @Override
    public Stream<T> toStream() {
        return Stream.empty();
    }

    /**
     * Returns an empty {@link Optional} since {@code None} does not contain a value.
     *
     * @return an empty {@link Optional}.
     */
    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    /**
     * Returns a {@link Either} instance representing the left side since {@code None} does not contain a value.
     *
     * @param leftSupplier the supplier to provide the left value.
     * @param <L> the type of the left value.
     * @return a {@link Either} instance representing the left side.
     * @throws NullPointerException if the left supplier is {@code null} or provides a {@code null} value.
     */
    @Override
    public <L extends Serializable> Either<L, T> toEither(final Supplier<? extends L> leftSupplier) {
        if (leftSupplier == null) {
            throw new NullPointerException("Left supplier must not be null");
        }
        L leftValue = leftSupplier.get();
        if (leftValue == null) {
            throw new NullPointerException("Left value must not be null");
        }

        return Either.left(leftValue);
    }


    @Serial
    private Object readResolve() {
        return INSTANCE; // Ensure deserialized None is the same singleton instance
    }

    /**
     * Throws an {@link UnsupportedOperationException} since {@code None} does not contain a value.
     *
     * @throws UnsupportedOperationException always thrown when called.
     */
    @Override
    public void orElseThrow() {
        throw new UnsupportedOperationException("Cannot get value from None");
    }


    /**
     * Returns a {@link Try} instance representing a failure since {@code None} does not contain a value.
     *
     * @param exceptionSupplier the supplier to provide the exception.
     * @return a {@link Try} instance representing a failure.
     * @throws NullPointerException if the exception supplier is {@code null}.
     */
    public Try<T> toTry(final Supplier<Exception> exceptionSupplier) {
        if (exceptionSupplier == null) {
            return Try.failure(new NoSuchElementException("No value present"));
        }

        Exception exception = exceptionSupplier.get();
        if (exception == null) {
            exception = new NoSuchElementException("No value present");
        }

        return Try.failure(exception);
    }
}