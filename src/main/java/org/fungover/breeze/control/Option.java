package org.fungover.breeze.control;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.bytebuddy.dynamic.scaffold.TypeInitializer;

import java.util.Optional;

public abstract class Option<T> {


    public static <T> Option<T> of(T value) {

        return value != null ? new Some<>(value) : None.getInstance();

    }




}
