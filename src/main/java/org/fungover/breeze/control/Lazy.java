package org.fungover.breeze.control;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Lazy<T> {
    private final Supplier<? extends T> supplier;
    private T value;
    private volatile boolean evaluated;

    /// Private constructor for lazy initialization
    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
        this.evaluated = false;
    }

    /// Static factory method to create a new lazy instance
    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        return new Lazy<>(supplier);
    }

    /// Static factory method to create a pre-initialized lazy instance
    public static <T> Lazy<T> value(T value) {
        Lazy<T> lazy = new Lazy<>(() -> value);
        lazy.evaluated = true;
        lazy.value = value;
        return lazy;
    }

    /// Retrieves or computes the value
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

    /// Checks if the value has been evaluated
    public boolean isEvaluated() {
        return evaluated;
    }

    /// Transforms value lazily
    public <U> Lazy<U> map(Function<? super T, ? extends U> mapper) {
        return Lazy.of(() -> mapper.apply(get()));
    }

    /// Monadic bind operation
    public <U> Lazy<U> flatMap(Function<? super T, Lazy<U>> mapper) {
        return Lazy.of(() -> mapper.apply(get()).get());
    }

    /// Returns Option lazily
    public Lazy<Optional<T>> filter(Predicate<? super T> predicate) {
        return Lazy.of(() -> {
            T val = get();
            return predicate.test(val) ? Optional.of(val) : Optional.empty();
        });
    }

    /// Converts to Option
    public Optional<T> toOption() {
        return isEvaluated() ? Optional.ofNullable(value) : Optional.empty();
    }

    /// Converts to Try
    public Try<T> toTry() {
        try {
            return Try.success(get());
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    /// Performs action when evaluated
    public void forEach(Consumer<? super T> action) {
        if (isEvaluated()) {
            action.accept(value);
        } else {
            action.accept(get());
        }
    }

}

