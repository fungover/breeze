package org.fungover.breeze.control;

public final class None<T> extends Option<T> {

private static final None<?> INSTANCE = new None<>();

private None() {}




    public static <T> None<T> getInstance() {
    return (None<T>) INSTANCE;
    }


}
