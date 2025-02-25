
package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Either;
import org.fungover.breeze.funclib.control.Try;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Represents a non-empty {@code Option} containing a value.
 * <p>
 * This class is part of the {@code Option} type, which is used to express
 * the presence or absence of a value in a safe and functional way. Unlike
 * {@code None}, which signifies the absence of a value, {@code Some} always
 * contains a non-null value.
 *
 * @param <T> the type of the value stored in this {@code Some}
 */
public final class Some<T extends Serializable> extends Option<T> {

    /**
     * Serial version UID for ensuring serialization compatibility.
     * This is required as {@code Some} implements {@code Serializable}.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The non-null value contained within this {@code Some} instance.
     * This value is immutable and cannot be {@code null}.
     */
    private final T value;


    /**
     * Constructs a {@code Some} instance containing the given non-null value.
     *
     * @param value the non-null value to be wrapped in {@code Some}
     * @throws NullPointerException if the provided value is {@code null}
     * @implNote If a {@code null} value is passed, an exception is thrown.
     *           To represent the absence of a value, use {@code None} instead.
     */
    public Some(final T value) {
        this.value = Objects.requireNonNull(value, "Cannot create Some with null value. Use None instead.");
    }


    /**
     * Checks if this {@code Some} instance is equal to another object.
     * <p>
     * Two {@code Some} instances are considered equal if they contain
     * the same value, as determined by {@link Objects#equals(Object, Object)}.
     *
     * @param obj the object to compare with this instance
     * @return {@code true} if the given object is a {@code Some} instance containing
     *         the same value; {@code false} otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Some<?> other)) {
            return false;
        }
        return Objects.equals(this.value, other.value);
    }

    /**
     * Computes the hash code for this {@code Some} instance.
     * <p>
     * The hash code is based on the contained value's hash code.
     *
     * @return the computed hash code for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Returns a string representation of this {@code Some} instance.
     * <p>
     * The string format includes the stored value and its type.
     *
     * @return a string representation of this {@code Some} instance, including
     *         the contained value and its class name
     */
    @Override
    public String toString() {
        return "Some(value=" + value + ", type= " + value.getClass().getSimpleName() + ")";
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
     * Returns the contained value since `Some<T>` always has a value.
     *
     * @param other ignored, as `Some<T>` does not use a fallback value.
     * @return the contained value.
     */
    @Override
    public T getOrElse(final T other) {
        return value;
    }

    /**
     * Returns the value if present, otherwise computes and returns a default value.
     *
     * @param supplier the supplier function to generate a default value
     * @return the contained value or a computed default
     */

    @Override
    public T getOrElseGet(final Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "Supplier must not be null");
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
     * @param <X>               the type of the exception to be thrown (ignored in Some)
     * @param exceptionSupplier a supplier function that provides an exception (ignored in Some)
     */

    @Override
    public <X extends Throwable> T orElseThrow(final Supplier<? extends X> exceptionSupplier) {
        Objects.requireNonNull(exceptionSupplier, "Exception supplier must not be null");
        return value;
    }

    /**
     * Applies the given mapping function to the contained value and returns a transformed {@code Option<U>}.
     * <p>
     * If the mapping function returns the same instance as the original value and the types are compatible,
     * the existing {@code Some<T>} instance is safely reused to avoid unnecessary object creation.
     * Otherwise, the result is wrapped in a new {@code Some<U>} or {@code None} if the mapping function returns {@code null}.
     * </p>
     *
     * <p><b>Type Safety:</b> If the mapping function returns the exact same object reference,
     * an unchecked cast is used to preserve immutability and avoid redundant object creation.
     * However, this relies on the assumption that the original value is safely castable to {@code U}.</p>
     *
     * @param <U>    the type of the transformed value, which must be {@link Serializable}
     * @param mapper a non-null function to apply to the contained value
     * @return the same {@code Some<T>} instance if the mapping function returns the original value (and types are compatible);
     * otherwise, a new {@code Some<U>} or {@code None} if the result is {@code null}.
     * @throws NullPointerException if the mapping function is {@code null}
     * @throws ClassCastException   if the original value cannot be safely cast to {@code U} when reused
     */
    @SuppressWarnings("unchecked")
    @Override
    public <U extends Serializable> Option<U> map(final Function<? super T, ? extends U> mapper) {
        U result = mapper.apply(value);

        return result == value ? (Option<U>) this : Option.ofNullable(result);
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
    public <U extends Serializable> Option<U> flatMap(final Function<? super T, Option<U>> mapper) {
        return Objects.requireNonNullElse(mapper.apply(value), None.getInstance());
    }


    /**
     * Filters the Option based on a predicate.
     *
     * @param predicate The condition to test.
     * @return This Option if it satisfies the predicate, otherwise None.
     */

    @Override
    public Option<T> filter(final Predicate<? super T> predicate) {
        return Option.ofNullable(predicate.test(value) ? value : null);
    }

    /**
     * Performs an action if a value is present.
     *
     * @param action The action to perform.
     */

    @Override
    public void forEach(final Consumer<? super T> action) {
        Objects.requireNonNull(action, "Action must not be null");
        action.accept(value);
    }

    /**
     * Peeks at the value without modifying the Option.
     *
     * @param action The action to perform.
     * @return This Option.
     */

    @Override
    public Option<T> peek(final Consumer<? super T> action) {
        Objects.requireNonNull(action, "Action must not be null");
        action.accept(value);

        return this;
    }

    /**
     * Folds the Option by applying a function to the value if present,
     * or returning a default computed by a Supplier.
     *
     * @param <U>       The return type.
     * @param ifNone    Supplier for default value if Option is None.
     * @param ifPresent Function applied to the value if present.
     * @return The computed value.
     */
    @Override
    public <U> U fold(final Supplier<U> ifNone, final Function<? super T, ? extends U> ifPresent) {
        Objects.requireNonNull(ifNone, "ifNone supplier cannot be null");
        Objects.requireNonNull(ifPresent, "ifPresent supplier cannot be null");
        return ifPresent.apply(value);
    }

    /**
     * Converts this {@code Some} to a {@link List} containing the value.
     *
     * @return a singleton list with the contained value.
     */
    @Override
    public List<T> toList() {
        return List.of(value);
    }

    /**
     * Converts this {@code Some} to a {@link Stream} containing the value.
     *
     * @return a single-element stream containing the value.
     */
    @Override
    public Stream<T> toStream() {
        return Stream.of(value);
    }

    /**
     * Converts this {@code Some} to a non-empty {@link Optional}.
     *
     * @return an {@code Optional} containing the value.
     */
    @Override
    public Optional<T> toOptional() {
        return Optional.of(value);
    }

    /**
     * Converts this {@code Some} to a right-biased {@link Either}, as it contains a valid value.
     *
     * @param leftSupplier ignored in this implementation, as {@code Some} always maps to {@code Either.right}.
     * @param <L>          the type of the left value, extending {@link Serializable}.
     * @return an {@code Either.Right} containing the value.
     */
    @Override
    public <L extends Serializable> Either<L, T> toEither(final Supplier<? extends L> leftSupplier) {
        return Either.right(value);
    }

    /**
     * Returns the wrapped value of this {@link Some<T>}.
     */
    @Override
    public void orElseThrow() {
    }

    /**
     * Converts this {@code Some} instance into a {@code Try} instance.
     * Since this instance contains a value, it returns a successful {@code Try}
     * wrapping the contained value.
     *
     * @param exceptionSupplier A supplier for an exception, which is ignored in this implementation
     *                          because {@code Some} always represents a present value.
     * @return A {@code Try} instance containing the stored value as a success.
     */
    @Override
    public Try<T> toTry(final Supplier<Exception> exceptionSupplier) {
        return Try.success(value);
    }

}
