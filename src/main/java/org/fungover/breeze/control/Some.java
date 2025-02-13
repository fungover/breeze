
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


public final class Some<T extends Serializable> extends Option<T> {


    @Serial
    private static final long serialVersionUID = 1L;


    private final T value;


    /**
     * Constructs a {@code Some} instance with a non-null value.
     *
     * @param value the value to store, must not be null
     * @throws NullPointerException if the provided value is null
     */
    public Some(final T value) {
        this.value = Objects.requireNonNull(value, "Cannot create Some with null value. Use None instead.");
    }


    /**
     * Checks if this {@code Some} instance is equal to another object.
     *
     * @param obj the object to compare
     * @return {@code true} if the given object is also a {@code Some} with the same value, otherwise {@code false}
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
     * Computes the hash code of this {@code Some} instance based on its value.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Returns a string representation of this {@code Some} instance.
     *
     * @return a string describing this instance
     */
    @Override
    public String toString() {
        return "Some(value=" + value + ", type= " + value.getClass().getSimpleName() + ")";
    }

    /**
     * Checks if the option is empty.
     *
     * @return {@code false} since {@code Some} always contains a value
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Returns the contained value.
     *
     * @return the stored value
     */
    @Override
    public T get() {
        return value;
    }

    /**
     * Returns the contained value, ignoring the provided fallback.
     *
     * @param other the fallback value (ignored)
     * @return the stored value
     */
    @Override
    public T getOrElse(final T other) {
        return value;
    }

    /**
     * Returns the contained value, ignoring the supplier function.
     *
     * @param supplier the fallback supplier (ignored)
     * @return the stored value
     */
    @Override
    public T getOrElseGet(final Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "Supplier must not be null");
        return value;
    }

    /**
     * Returns the contained value, never returning {@code null}.
     *
     * @return the stored value
     */
    @Override
    public T getOrNull() {
        return value;
    }

    /**
     * Returns the contained value, ignoring the exception supplier.
     *
     * @param exceptionSupplier the exception supplier (ignored)
     * @return the stored value
     */
    @Override
    public <X extends Throwable> T orElseThrow(final Supplier<? extends X> exceptionSupplier) {
        Objects.requireNonNull(exceptionSupplier, "Exception supplier must not be null");
        return value;
    }


    /**
     * Transforms the contained value using the given mapping function.
     *
     * @param <U> the result type
     * @param mapper the function to apply to the contained value
     * @return an {@code Option} containing the mapped value
     */
    @Override
    public <U extends Serializable> Option<U> map(final Function<? super T, ? extends U> mapper) {
        U result = mapper.apply(value);

        return result == value ? (Option<U>) this : Option.ofNullable(result);
    }

    /**
     * Transforms the contained value using a function that returns an {@code Option}.
     *
     * @param <U> the result type
     * @param mapper the function to apply
     * @return the result of applying the function
     */
    @Override
    public <U extends Serializable> Option<U> flatMap(final Function<? super T, Option<U>> mapper) {
        return Objects.requireNonNullElse(mapper.apply(value), None.getInstance());
    }


    /**
     * Filters the contained value based on the given predicate.
     *
     * @param predicate the condition to test
     * @return {@code Some} if the value matches, otherwise {@code None}
     */
    @Override
    public Option<T> filter(final Predicate<? super T> predicate) {
        return Option.ofNullable(predicate.test(value) ? value : null);
    }

    /**
     * Applies an action to the contained value.
     *
     * @param action the action to perform
     */
    @Override
    public void forEach(final Consumer<? super T> action) {
        Objects.requireNonNull(action, "Action must not be null");
        action.accept(value);
    }

    /**
     * Applies an action to the contained value and returns the same {@code Some} instance.
     *
     * @param action the action to perform
     * @return this {@code Some} instance
     */
    @Override
    public Option<T> peek(final Consumer<? super T> action) {
        Objects.requireNonNull(action, "Action must not be null");
        action.accept(value);

        return this;
    }

    /**
     * Folds the contained value into a result using the given functions.
     *
     * @param <U> the result type
     * @param ifNone the function to apply if the value is absent (ignored)
     * @param ifPresent the function to apply if the value is present
     * @return the result of applying {@code ifPresent}
     */
    @Override
    public <U> U fold(final Supplier<U> ifNone, final Function<? super T, ? extends U> ifPresent) {
        Objects.requireNonNull(ifNone, "ifNone supplier cannot be null");
        Objects.requireNonNull(ifPresent, "ifPresent supplier cannot be null");
        return ifPresent.apply(value);
    }

    /**
     * Converts this {@code Some} instance into a {@link List} containing the stored value.
     *
     * @return a list containing the single value
     */
    @Override
    public List<T> toList() {
        return List.of(value);
    }

    /**
     * Converts this {@code Some} instance into a {@link Stream} containing the stored value.
     *
     * @return a stream containing the single value
     */
    @Override
    public Stream<T> toStream() {
        return Stream.of(value);
    }

    /**
     * Converts this {@code Some} instance into an {@link Optional} containing the stored value.
     *
     * @return an {@code Optional} containing the value
     */
    @Override
    public Optional<T> toOptional() {
        return Optional.of(value);
    }

    /**
     * Converts this {@code Some} instance into an {@link Either}, representing a successful outcome.
     *
     * @param <L> the type of the left side, which represents an error case
     * @param leftSupplier a supplier that would provide a left value if needed (ignored in this case)
     * @return an {@code Either} containing the right value
     */
    @Override
    public <L extends Serializable> Either<L, T> toEither(final Supplier<? extends L> leftSupplier) {
        return Either.right(value);
    }

    /**
     * Ensures compatibility with the {@code Option} API but does nothing since {@code Some} always contains a value.
     */
    @Override
    public void orElseThrow() {
    }

    /**
     * Converts this {@code Some} instance into a {@link Try} representing a successful outcome.
     *
     * @param exceptionSupplier a supplier for an exception in case of failure (ignored in this case)
     * @return a successful {@code Try} containing the value
     */
    @Override
    public Try<T> toTry(final Supplier<Exception> exceptionSupplier) {
        return Try.success(value);
    }

}