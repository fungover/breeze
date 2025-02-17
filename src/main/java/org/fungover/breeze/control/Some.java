package org.fungover.breeze.control;
import org.fungover.breeze.funclib.control.Either;

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

    public Some(final T value) {
        this.value = Objects.requireNonNull(value, "Cannot create Some with null value. Use None instead.");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Some<?> other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Some(value=" + value + ", type= "+ value.getClass().getSimpleName() + ")";
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
     * @param <X> the type of the exception to be thrown (ignored in Some)
     * @param exceptionSupplier a supplier function that provides an exception (ignored in Some)
     * @return the contained value
     */

    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        Objects.requireNonNull(exceptionSupplier, "Exception supplier must not be null");
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
    public <U extends Serializable> Option<U> map(Function<? super T, ? extends U> mapper) {
        return Option.ofNullable(mapper.apply(value));
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
    public <U extends Serializable> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
        return Objects.requireNonNullElse(mapper.apply(value), None.getInstance());
    }


    /**
     * Filters the Option based on a predicate.
     *
     * @param predicate The condition to test.
     * @return This Option if it satisfies the predicate, otherwise None.
     */

    @Override
    public Option<T> filter(Predicate<? super T> predicate) {
        return Option.ofNullable(predicate.test(value) ? value : null);
    }

    /**
     * Performs an action if a value is present.
     *
     * @param action The action to perform.
     */

    @Override
    public void forEach(Consumer<? super T> action) {

        action.accept(value);

    }

    /**
     * Peeks at the value without modifying the Option.
     *
     * @param action The action to perform.
     * @return This Option.
     */

    @Override
    public Option<T> peek(Consumer<? super T> action) {
        Objects.requireNonNull(action, "Action must not be null");
        action.accept(value);

        return this;
    }

    /**
     * Folds the Option by applying a function to the value if present,
     * or returning a default computed by a Supplier.
     *
     * @param <U> The return type.
     * @param ifNone Supplier for default value if Option is None.
     * @param ifPresent Function applied to the value if present.
     * @return The computed value.
     */

    @Override
    public <U> U fold(Supplier<U> ifNone, Function<? super T, ? extends U> ifPresent) {
        Objects.requireNonNull(ifNone, "ifNone supplier cannot be null");
        Objects.requireNonNull(ifPresent, "ifPresent supplier cannot be null");
        return ifPresent.apply(value);
    }

    @Override
    public List<T> toList() {
        return List.of();
    }

    @Override
    public Stream<T> toStream() {
        return Stream.empty();
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    @Override
    public <L extends Serializable> Either<L, T> toEither(Supplier<? extends L> leftSupplier) {
        return null;
    }
}
