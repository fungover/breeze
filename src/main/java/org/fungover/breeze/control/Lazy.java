package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Try;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A class that represents a lazily evaluated value of type `T`.
 * The value is computed only when it is first accessed, and the result is cached for subsequent accesses.
 *
 * @param <T> the type of the value
 */
public final class Lazy<T> {
    private final Supplier<? extends T> supplier;
    private T value;
    private volatile boolean evaluated;

    /**
     * Private constructor for lazy initialization.
     *
     * @param supplier the supplier function that computes the value
     */
    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
        this.value = null;
        this.evaluated = false;
    }

    /**
     * Static factory method to create a new lazy instance.
     *
     * @param <T>      the type of the value
     * @param supplier the supplier function that computes the value
     * @return a new lazy instance
     */
    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        return new Lazy<>(supplier);
    }

    /**
     * Static factory method to create a pre-initialized lazy instance.
     *
     * @param <T>   the type of the value
     * @param value the value to be stored in the lazy instance
     * @return a new lazy instance with the given value
     */
    public static <T> Lazy<T> value(T value) {
        Lazy<T> lazy = new Lazy<>(() -> value);
        lazy.evaluated = true;
        lazy.value = value;
        return lazy;
    }

    /**
     * Retrieves or computes the value.
     *
     * @return the value
     */
    public T get() {
        if (!evaluated) {
            synchronized (this) {
                if (!evaluated) {
                    value = supplier.get();
                    evaluated = true;
                }
            }
        }
        return value;
    }

    /**
     * Checks if the value has been evaluated.
     *
     * @return true if the value has been evaluated, false otherwise
     */
    public boolean isEvaluated() {
        return evaluated;
    }

    /**
     * Transforms the value lazily using the given mapper function.
     *
     * @param <U>    the type of the transformed value
     * @param mapper the function to transform the value
     * @return a new lazy instance with the transformed value
     */
    public <U> Lazy<U> map(Function<? super T, ? extends U> mapper) {
        return Lazy.of(() -> mapper.apply(get()));
    }

    /**
     * Performs a monadic bind operation on the lazy value.
     *
     * @param <U>    the type of the transformed value
     * @param mapper the function that maps the value to a new lazy instance
     * @return a new lazy instance with the transformed value
     */
    public <U> Lazy<U> flatMap(Function<? super T, Lazy<U>> mapper) {
        return Lazy.of(() -> mapper.apply(get()).get());
    }

    /**
     * Returns an optional value lazily based on the given predicate.
     *
     * @param predicate the predicate to filter the value
     * @return a new lazy instance with an optional value
     */
    public Lazy<Optional<T>> filter(Predicate<? super T> predicate) {
        return Lazy.of(() -> {
            T val = get();
            return predicate.test(val) ? Optional.of(val) : Optional.empty();
        });
    }

    /**
     * Converts the lazy value to an optional value.
     *
     * @return an optional value
     */
    public Optional<T> toOption() {
        return isEvaluated() ? Optional.ofNullable(value) : Optional.empty();
    }

    /**
     * Converts the lazy value to a `Try` instance.
     *
     * @return a `Try` instance
     */
    public Try<T> toTry() {
        try {
            return Try.success(get());
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /**
     * Performs the given action when the value is evaluated.
     *
     * @param action the action to perform
     */
    public void forEach(Consumer<? super T> action) {
        action.accept(get());
    }
}
