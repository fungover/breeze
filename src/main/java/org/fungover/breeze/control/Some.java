package org.fungover.breeze.control;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Some<T> extends Option<T> {

    private final T value;

    public Some(final T value) {
        this.value = value;
    }


}
