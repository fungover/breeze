package org.fungover.breeze.funclib.control;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;
/*
*
* Represents a computation that may either result in a success or a Failure
*
*/
public abstract class Try <T>{
    /**
     * Checks if the computation was successful.
     * @return true if successful, false otherwise.
     */
    public abstract boolean isSuccess();

    /**
     * Checks if the computation failed.
     * @return true if failed, false otherwise.
     */
    public abstract boolean isFailure();

    /**
     * Retrieves the computed value or throws the exception if failed.
     * @return the computed value.
     * @throws Throwable if the computation failed.
     */
    public abstract T get() throws Throwable;

    /**
     * Creates a successful Try instance.
     * @param value the value of the successful computation.
     * @return a Success instance containing the value.
     */
    public static <T> Try<T> success(T value) {
        return new Success<>(value);
    }

    /**
     * Creates a failed Try instance.
     * @param throwable the exception that caused the Failure.
     * @return a Failure instance containing the throwable.
     */
    public static <T> Try<T> failure(Throwable throwable) {
        return new Failure<>(throwable);
    }

    /**
     * Executes a supplier function and returns a Try instance capturing success or Failure.
     * @param supplier the function to execute.
     * @return a Success instance if the function executes successfully, otherwise a Failure instance.
     */
    public static  <T>Try <T> of(Supplier<T> supplier) {
        try {
            return success(supplier.get());
        } catch (Throwable t) {
            return failure(t);
        }
    }


    /**
     * Executes a Callable function and returns a Try instance capturing success or Failure.
     * @param callable the function to execute.
     * @return a Success instance if the function executes successfully, otherwise a Failure instance.
     */
    public static <T> Try <T> ofCallable(Callable<T> callable) {
        try {
            return success(callable.call());
        } catch (Throwable t) {
            return failure(t);
        }
    }



}
