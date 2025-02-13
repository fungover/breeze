package org.fungover.breeze.control;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.bytebuddy.dynamic.scaffold.TypeInitializer;
import java.util.Optional;

/**
 * A generic container type that represents an optional value.
 * It can either contain a value (Some) or be empty (None).
 * @param <T> the type of the contained value
 */


public abstract class Option<T> {

    /**
     * Creates a Some instance if the value is non-null, otherwise returns None.
     * @param value the value to wrap
     * @param <T> the type of the value
     * @return an Option containing the value or None
     */

    public static <T> Option<T> of(T value) {

        return value != null ? new Some<>(value) : None.getInstance();

    }


    public static <T> Option<T> some(T value) {

        if (value == null) {
            throw new NullPointerException("Cannot create 'Some' with null");

        }

        return new Some<>(value);

    }



}
