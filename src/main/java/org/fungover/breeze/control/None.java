package org.fungover.breeze.control;

import com.sun.net.httpserver.Authenticator;
import org.fungover.breeze.funclib.control.Either;
import org.fungover.breeze.funclib.control.Try;

import java.io.ObjectStreamException;
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
 * Represents the absence of a value in the {@link Option} type.
 * <p>
 * This is a singleton class that represents an empty state, similar to {@code Optional.empty()} in Java.
 * Any operations performed on this instance will result in a no-op or return a default value.
 * </p>
 *
 * @param <T> The type of the non-existent value.
 */
public final class None<T extends Serializable> extends Option<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Singleton instance of {@code None}.
     */
    private static final None<?> INSTANCE = new None<>();

    /**
     * Private constructor to enforce singleton pattern.
     */
    private None() {}

    /**
     * Returns the singleton instance of {@code None}.
     *
     * @param <T> The type parameter.
     * @return The singleton instance of {@code None}.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> None<T> getInstance() {
    return (None<T>) INSTANCE;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        return 0; // All instances of None have the same hash code since they are the same instance.
    }

    @Override
    public String toString() {
        return "None()";
    }

    /**
     * Always returns {@code true} as this represents an empty Option.
     *
     * @return {@code true} since this is {@code None}.
     */
    @Override
    public boolean isEmpty() {
        return true;
    }


    /**
     * Throws an exception since {@code None} does not contain a value.
     *
     * @return never returns a value.
     * @throws UnsupportedOperationException always.
     */
    @Override
    public T get() {
        throw new UnsupportedOperationException("Cannot call get() on None");
    }

    /**
     * Returns the provided alternative value since {@code None} has no value.
     *
     * @param other The alternative value to return.
     * @return The provided {@code other} value.
     */
    @Override
    public T getOrElse(T other) {
        return other;
    }

    /**
     * Returns a computed default value since {@code None} has no value.
     *
     * @param supplier The supplier function that provides a default value.
     * @return The computed default value.
     */
    @Override
    public T getOrElseGet(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "Supplier must not be null");
        return supplier.get();
    }

    /**
     * Returns {@code null} since {@code None} has no value.
     *
     * @return {@code null}.
     */
    @Override
    public T getOrNull() {

        return null;
    }

    /**
     * Throws an exception provided by the supplier since {@code None} has no value.
     *
     * @param <X>               The type of the exception to be thrown.
     * @param exceptionSupplier The supplier function that provides the exception.
     * @throws X the provided exception.
     */
    @Override
    public <X extends Throwable> void orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        throw exceptionSupplier.get();
    }

    /**
     * Returns {@code None} since there is no value to transform.
     *
     * @param <U> The type of the would-be transformed value.
     * @param mapper A function to apply (ignored).
     * @return {@code None<U>}.
     */
    @Override
    public <U extends Serializable> Option<U> map(Function<? super T, ? extends U> mapper) {
        return None.getInstance();
    }

    /**
     * Returns {@code None} since there is no value to transform.
     *
     * @param <U> The type of the resulting Option.
     * @param mapper A function that returns an {@code Option<U>} (ignored).
     * @return {@code None<U>}.
     */
    @Override
    public <U extends Serializable> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
        return None.getInstance();
    }

    /**
     * Always returns {@code None} since there is no value to filter.
     *
     * @param predicate The condition to test (ignored).
     * @return This {@code None} instance.
     */
    @Override
    public Option<T> filter(Predicate<? super T> predicate) {
        return this;
    }

    /**
     * Does nothing since there is no value.
     *
     * @param action The action to perform (ignored).
     */
    @Override
    public void forEach(Consumer<? super T> action) {

    }

    /**
     * Does nothing and returns this {@code None} instance.
     *
     * @param action The action to perform (ignored).
     * @return This {@code None} instance.
     */
    @Override
    public Option<T> peek(Consumer<? super T> action) {
        return this;
    }

    /**
     * Folds the Option, returning a default value since {@code None} contains no value.
     *
     * @param <U> The return type.
     * @param ifNone Supplier for the default value.
     * @param ifPresent Function applied to the value if present (ignored).
     * @return The default value from {@code ifNone}.
     */
    @Override
    public <U> U fold(Supplier<U> ifNone, Function<? super T, ? extends U> ifPresent) {

        Objects.requireNonNull(ifNone, "ifNone supplier cannot be null");
        Objects.requireNonNull(ifPresent, "ifPresent supplier cannot be null");

        return ifNone.get();
    }

    /**
     * Converts this {@code None} to an empty {@link List}.
     *
     * @return an empty list, as {@code None} contains no value.
     */
    @Override
    public List<T> toList() {
        return List.of();
    }

    /**
     * Converts this {@code None} to an empty {@link Stream}.
     *
     * @return an empty stream, as {@code None} contains no value.
     */
    @Override
    public Stream<T> toStream() {
        return Stream.empty();
    }

    /**
     * Converts this {@code None} to an empty {@link Optional}.
     *
     * @return {@code Optional.empty()}, as {@code None} represents the absence of a value.
     */
    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    /**
     * Converts this {@code None} into a left-biased {@link Either}.
     * <p>
     * Since {@code None} has no value, it calls the provided supplier to generate a left value.
     * </p>
     *
     * @param leftSupplier a supplier providing the left value if this is {@code None}.
     * @param <L>          the type of the left value, extending {@link Serializable}.
     * @return an {@code Either.Left} containing the supplied value.
     * @throws NullPointerException if {@code leftSupplier} is null or if it returns null.
     */
    @Override
    public <L extends Serializable> Either<L, T> toEither(Supplier<? extends L> leftSupplier) {
        if (leftSupplier == null) {
            throw new NullPointerException("Left supplier must not be null");
        }
        L leftValue = leftSupplier.get();
        if (leftValue == null) {
            throw new NullPointerException("Left value must not be null");
        }

        return Either.left(leftValue);
    }

    /**
     * Ensures that deserialization of {@code None} always returns the singleton instance.
     * <p>
     * This method is called automatically during deserialization.
     * Instead of creating a new instance, it ensures that {@code Option.none()} is used,
     * maintaining the singleton property of {@code None}.
     *
     * @return The singleton instance of {@code None}.
     * @see java.io.Serializable
     */
    @Serial
    private Object readResolve() throws ObjectStreamException {
        return INSTANCE; // Ensure deserialized None is the same singleton instance
    }

    /**
     * Throws an {@link UnsupportedOperationException} when attempting to retrieve a value.
     * This method should only be called on {@link Some<T>}, as {@link None<T>} does not contain a value.
     *
     * @throws UnsupportedOperationException always, since there is no value to retrieve.
     */
    @Override
    public void orElseThrow() {
        throw new UnsupportedOperationException("Cannot get value from None");
    }



    public Try<T> toTry(Supplier<Exception> exceptionSupplier) {
        return Try.failure(exceptionSupplier != null ? exceptionSupplier.get() : new NoSuchElementException("No value present"));
    }
}
