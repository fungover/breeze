
package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Either;
import org.fungover.breeze.funclib.control.Try;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A generic container type representing an optional value.
 * <p>
 * This abstract class serves as the base for {@code Some<T>} (which contains a value)
 * and {@code None<T>} (which represents the absence of a value).
 * It provides a functional alternative to {@code null}, ensuring safer handling
 * of optional values.
 *
 * <p>This class is {@link Serializable}, allowing instances to be persisted or transmitted.
 *
 * @param <T> the type of the contained value
 */

public abstract class Option<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Checks whether this {@code Option} is equal to another object.
     * <p>
     * Two {@code Option} instances are considered equal if:
     * <ul>
     *   <li>Both are instances of {@code Some<T>} and contain equal values (based on {@code equals()}).</li>
     *   <li>Both are instances of {@code None<T>} (as {@code None} is a singleton).</li>
     * </ul>
     *
     * @param obj the object to compare for equality
     * @return {@code true} if the given object is an equivalent {@code Option}, otherwise {@code false}
     */

    @Override
    public abstract boolean equals(Object obj);

    /**
     * Computes the hash code for this {@code Option}.
     * <p>
     * The hash code follows these rules:
     * <ul>
     *   <li>For {@code Some<T>}, it is derived from the contained value's hash code.</li>
     *   <li>For {@code None<T>}, it returns a constant hash code (as {@code None} is a singleton).</li>
     * </ul>
     *
     * @return the hash code of this {@code Option}
     */

    @Override
    public abstract int hashCode();

    /**
     * Returns a string representation of this {@code Option}.
     * <p>
     * The format follows these conventions:
     * <ul>
     *   <li>For {@code Some<T>}, it returns {@code "Some(value)"} where {@code value} is the string representation of the contained value.</li>
     *   <li>For {@code None<T>}, it returns {@code "None"}.</li>
     * </ul>
     *
     * @return a string representation of this {@code Option}
     */

    @Override
    public abstract String toString();


    /**
     * Creates an {@code Option<T>} instance based on the provided value.
     * <p>
     * If the value is non-null, it returns a {@code Some<T>} instance wrapping the value.
     * Otherwise, it returns the singleton instance of {@code None<T>}.
     *
     * @param value the value to wrap in an {@code Option}
     * @param <T>   the type of the value
     * @return an {@code Option<T>} containing the value, or {@code None<T>} if the value is null
     */

    public static <T extends Serializable> Option<T> of(final T value) {
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

    public static <T extends Serializable> Option<T> some(final T value) {
        if (value == null) {
            throw new NullPointerException("Cannot create 'Some' with null");
        }
        return new Some<>(value);
    }

    /**
     * Returns the singleton instance of {@code None<T>}, representing the absence of a value.
     * <p>
     * This method provides a type-safe way to obtain an empty {@code Option<T>}. Since {@code None}
     * is a singleton, all calls to this method return the same instance.
     * </p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Option<String> emptyOption = Option.none();
     * System.out.println(emptyOption.isEmpty()); // true
     * }</pre>
     *
     * @param <T> the type of the (non-existent) value, restricted to {@link Serializable}
     * @return the singleton {@code None<T>} instance
     */
    public static <T extends Serializable> Option<T> none() {
        return None.getInstance();
    }

    /**
     * Checks if this Option is empty (i.e., an instance of {@code None}).
     *
     * @return {@code true} if this is None, otherwise {@code false}.
     */

    public abstract boolean isEmpty();


    /**
     * Checks if this Option contains a value (i.e., an instance of {@code Some}).
     *
     * <p>This is a convenience method that returns the opposite of {@link #isEmpty()}.
     * It improves readability in functional-style code.
     *
     * @return {@code true} if this is Some, otherwise {@code false}.
     */

    public final boolean isDefined() {
        return !isEmpty();

    }

    /**
     * Retrieves the contained value.
     *
     * @return the contained value
     * @throws UnsupportedOperationException if this is {@code None}
     */
    public abstract T get();

    /**
     * Returns the contained value if present, otherwise returns the provided default value.
     *
     * @param other the default value
     * @return the contained value or {@code other} if this is {@code None}
     */
    public abstract T getOrElse(T other);

    /**
     * Returns the contained value if present; otherwise, computes and returns a default value using the provided supplier.
     *
     * <p>This method ensures lazy evaluation: the supplier is only invoked if this is {@code None}.
     *
     * @param supplier a {@link Supplier} providing a default value if this is {@code None}
     * @return the contained value if present, otherwise the computed default value
     * @throws NullPointerException if {@code supplier} is null
     */
    public abstract T getOrElseGet(Supplier<? extends T> supplier);

    /**
     * Returns the contained value or null if None.
     *
     * @return the contained value or null
     */

    public abstract T getOrNull();

    /**
     * @return the contained value if present, otherwise throws an exception provided by the supplier.
     *
     * @param <X>               the type of the exception to be thrown
     * @param exceptionSupplier a supplier function that provides the exception to throw
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

    public abstract <U extends Serializable> Option<U> map(Function<? super T, ? extends U> mapper);

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

    public abstract <U extends Serializable> Option<U> flatMap(Function<? super T, Option<U>> mapper);

    /**
     * Returns this Option if the contained value satisfies the given predicate;
     * otherwise, returns {@code None}.
     *
     * <p>If this is {@code None}, the predicate is not applied, and {@code None} is returned.
     * If this is {@code Some}, the predicate is applied to the contained value:
     * <ul>
     *   <li>If the predicate returns {@code true}, this Option is returned unchanged.</li>
     *   <li>If the predicate returns {@code false}, {@code None} is returned.</li>
     * </ul>
     *
     * <p>Example usage:
     * <pre>{@code
     * Option<Integer> number = Option.some(42);
     * Option<Integer> evenNumber = number.filter(n -> n % 2 == 0); // Retains Some(42)
     * Option<Integer> oddNumber = number.filter(n -> n % 2 != 0);  // Becomes None
     * }</pre>
     *
     * @param predicate The condition to test against the value.
     * @return This Option if the predicate is satisfied, otherwise {@code None}.
     * @throws NullPointerException if {@code predicate} is null.
     */

    public abstract Option<T> filter(Predicate<? super T> predicate);

    /**
     * Performs the given action if a value is present in this Option.
     * <p>
     * If this is a {@code Some}, the action is executed with the contained value.
     * If this is {@code None}, no action is performed.
     *
     * <p>Example usage:
     * <pre>{@code
     * Option<String> name = Option.some("Alice");
     * name.forEach(System.out::println); // Prints "Alice"
     *
     * Option<String> empty = Option.of(null);
     * empty.forEach(System.out::println); // Does nothing
     * }</pre>
     *
     * @param action The action to perform on the contained value.
     * @throws NullPointerException if {@code action} is null.
     */

    public abstract void forEach(Consumer<? super T> action);

    /**
     * Applies the given action to the value if present, and returns the same Option.
     * <p>
     * This method is useful for debugging or logging without modifying the Option itself.
     * If this is {@code None}, the action is not executed, and the Option remains unchanged.
     *
     * <p>Example usage:
     * <pre>{@code
     * Option<String> name = Option.some("Alice");
     * name.peek(value -> System.out.println("Debug: " + value));
     * // Output: Debug: Alice
     *
     * Option<String> empty = Option.none();
     * empty.peek(value -> System.out.println("This won't be printed"));
     * }</pre>
     *
     * @param action The action to perform on the contained value.
     * @return This Option instance, unchanged.
     * @throws NullPointerException if {@code action} is null.
     */

    public abstract Option<T> peek(Consumer<? super T> action);

    /**
     * Transforms this Option by applying a function to its value if present,
     * or returns a default value if it is None.
     *
     * <p>Behavior:
     * <ul>
     *   <li>If this is {@code Some}, applies {@code ifPresent} to the value and returns the result.</li>
     *   <li>If this is {@code None}, returns the result of {@code ifNone.get()}.</li>
     * </ul>
     *
     * @param <U>       The return type.
     * @param ifNone    A {@link Supplier} providing a default value if this is None.
     * @param ifPresent A {@link Function} applied to the value if this is Some.
     * @return The computed value from either {@code ifNone} or {@code ifPresent}.
     * @throws NullPointerException if {@code ifNone} or {@code ifPresent} is null.
     */
    public abstract <U> U fold(Supplier<U> ifNone, Function<? super T, ? extends U> ifPresent);

    /**
     * Creates an {@link Option} instance based on the given value.
     * If the value is non-null, returns a {@link Some} instance containing the value.
     * Otherwise, returns the singleton {@link None} instance.
     *
     * @param <T> the type of the value, which must be {@link Serializable}
     * @param value the value to wrap in an {@link Option}
     * @return a {@link Some} containing the value if non-null, otherwise {@link None}
     */
    public static <T extends Serializable> Option<T> ofNullable(final T value) {
        return value != null ? new Some<>(value) : None.getInstance();
    }

    /**
     * Converts this {@code Option<T>} into a {@code List<T>}.
     * <ul>
     *   <li>If this is {@code Some<T>}, returns a singleton list containing the value.</li>
     *   <li>If this is {@code None}, returns an empty list.</li>
     * </ul>
     *
     * @return a list containing the value if present, otherwise an empty list
     */
    public abstract List<T> toList();

    /**
     * Converts this {@code Option<T>} into a {@code Stream<T>}.
     * <ul>
     *   <li>If this is {@code Some<T>}, returns a stream containing the value.</li>
     *   <li>If this is {@code None}, returns an empty stream.</li>
     * </ul>
     *
     * @return a stream containing the value if present, otherwise an empty stream
     */
    public abstract Stream<T> toStream();

    /**
     * Converts this {@code Option<T>} into a {@code java.util.Optional<T>}.
     * <ul>
     *   <li>If this is {@code Some<T>}, returns an {@code Optional} containing the value.</li>
     *   <li>If this is {@code None}, returns {@code Optional.empty()}.</li>
     * </ul>
     *
     * @return an {@code Optional} containing the value if present, otherwise an empty {@code Optional}
     */
    public abstract Optional<T> toOptional();

    /**
     * Converts this {@code Option<T>} into an {@code Either<L, T>}.
     * <ul>
     *   <li>If this is {@code Some<T>}, returns a {@code Right<T>} containing the value.</li>
     *
     *   <li>If this is {@code None}, invokes the given {@code leftSupplier} and returns a {@code Left<L>}.</li>
     * </ul>
     *
     * @param leftSupplier a supplier function that provides a {@code Left<L>} value when this is {@code None}
     * @param <L>          the type of the left value, which must be serializable
     * @return an {@code Either<L, T>} with {@code Right<T>} if value is present, otherwise {@code Left<L>}
     * @throws NullPointerException if {@code leftSupplier} is null or returns null
     */
    public abstract <L extends Serializable> Either<L, T> toEither(Supplier<? extends L> leftSupplier);


    /**
     * Returns this instance as an {@link Option}.
     * <p>
     * This method is useful when working with APIs expecting an {@code Option<T>}.
     * Since the implementing class is already an {@code Option<T>}, it simply returns itself.
     * </p>
     *
     * @return This {@code Option<T>} instance.
     */
    public Option<T> toOption() {
        return this;
    }


    /**
     * Converts this value into a {@link Try}, wrapping any potential failure scenario.
     * <p>
     * If this instance represents an absent value (e.g., {@code None}), the provided exception
     * supplier is used to generate an exception, which is wrapped inside a failed {@code Try}.
     * </p>
     *
     * @param exceptionSupplier A supplier providing the exception in case of absence.
     * @return A successful {@code Try<T>} if this instance contains a value,
     * otherwise a failed {@code Try} with the supplied exception.
     * @throws NullPointerException if {@code exceptionSupplier} is null or produces a null exception.
     */
    public abstract Try<T> toTry(Supplier<Exception> exceptionSupplier);


    /**
     * Throws an exception if the value is absent.
     * <p>
     * This method enforces strict handling of missing values, ensuring that users explicitly
     * deal with cases where the value may not be present.
     * </p>
     *
     * @throws UnsupportedOperationException if called on a {@code None} instance.
     */
    public abstract void orElseThrow();


    /**
     * Converts an {@code Either<L, R>} to an {@code Option<R>}.
     * <p>
     * If the {@code Either} contains a right value, it returns {@code Some<R>} with that value.
     * If the {@code Either} contains a left value, it returns {@code None}.
     * </p>
     *
     * @param either the {@code Either} instance to convert
     * @param <L>    the type of the left value (must be {@link Serializable})
     * @param <R>    the type of the right value (must be {@link Serializable})
     * @return an {@code Option<R>} containing the right value if present, otherwise {@code None}
     */
    public static <L extends Serializable, R extends Serializable> Option<R> fromEither(final Either<L, R> either) {
        return either.isRight() ? Option.some(either.getRight()) : Option.none();
    }
}
