package org.fungover.breeze.funclib.control;


import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
/*
*
* Represents a computation that may either result in a success or a Failure
*
*/
public abstract class Try <T> implements Serializable {
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
     * Retrieves the value if present, otherwise returns the default value.
     * @param defaultValue the value to return if the computation failed.
     * @return the computed value or the default value.
     */
    public abstract T getOrElse(T defaultValue);

    /**
     * Retrieves the value if present, otherwise computes and returns a default value.
     * @param supplier the supplier function to compute the default value.
     * @return the computed value or the supplied default value.
     */
    public abstract T getOrElseGet(Supplier<? extends T> supplier);

    /**
     * Retrieves the value if present, otherwise throws a mapped exception.
     * @param exceptionMapper function to map the Throwable to an exception of type X.
     * @param <X> the exception type to be thrown.
     * @return the computed value.
     * @throws X if the computation failed.
     */
    public abstract <X extends Throwable> T getOrElseThrow(Function<? super Throwable, ? extends X> exceptionMapper) throws X;




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
     * @param exception the exception that caused the Failure.
     * @return a Failure instance containing the exception.
     */
    public static <T> Try<T> failure(Exception exception) {
        Objects.requireNonNull(exception, "Throwable must not be null");
        return new Failure<>(exception);
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
            return failure(t instanceof Exception ? (Exception) t : new Exception(t));
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
            return failure(t instanceof Exception ? (Exception) t : new Exception(t));
        }
    }

    /**
     * Transforms the value inside Try using the provided function if it is a success.
     * @param mapper the function to apply.
     * @param <U> the new type of the transformed value.
     * @return a new Try instance containing the transformed value or the original Failure.
     */
    public <U> Try<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (isSuccess()) {
            try {
                return success(mapper.apply(get()));
            } catch (Throwable t) {
                return failure(new Exception(t));
            }
        } else {
            return failure(((Failure<T>) this).exception);
        }
    }

    /**
     * Applies a function that returns a Try instance if the computation was successful.
     * @param mapper the function to apply.
     * @param <U> the new type of the transformed value.
     * @return the Try instance returned by the function or the original failure.
     */
    public <U> Try<U> flatMap(Function<? super T, Try<U>> mapper) {
        Objects.requireNonNull(mapper);

        if (isSuccess()) {
            try {
                return mapper.apply(get());
            } catch (Throwable t) {
                return failure(new Exception(t));
            }
        } else {
            return failure(((Failure<T>) this).exception);
        }
    }


    /**
     * Filters the value inside Try using the provided predicate.
     * If the value does not satisfy the predicate, it returns a Failure.
     * @param predicate the condition to check.
     * @return the same Try instance if the value satisfies the predicate, otherwise a Failure.
     */
    public Try<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (isSuccess()) {
            try {
                T value = get();
                if (!predicate.test(value))  return failure(new NoSuchElementException("The value does not satisfy the predicate"));
                return this;
            } catch (Throwable t) {
                return failure(new Exception(t));
            }
        } else {
            return this;
        }
    }

    /**
     * Handles the failure case by recovering from the Throwable and returning a successful Try.
     * If the Try is a success, it returns the same instance.
     * @param recoverFunction the function that handles the failure and returns a value.
     * @return a Try instance containing the recovered value or the same success.
     */
    public Try<T> recover(Function<? super Exception, ? extends T> recoverFunction) {
        Objects.requireNonNull(recoverFunction);

        if (isSuccess()) {
            return this;
        } else {
            try {
                Exception cause = ((Failure<T>) this).exception;
                if (cause != null) {
                    T recoveredValue = recoverFunction.apply((cause));
                    return success(recoveredValue);
                } else {
                    return failure(cause);
                }
            } catch (Exception e) {
                return failure(e);
            }
        }
    }



    /**
     * Monadic failure handling: If the Try is a failure, this method applies a function
     * that returns another Try. If it is a success, it returns the same instance.
     * @param recoverWithFunction the function that handles the failure and returns a new Try.
     * @return a new Try instance containing the recovered value or the same success.
     */
    public Try<T> recoverWith(Function<? super Throwable, Try<T>> recoverWithFunction) {
        Objects.requireNonNull(recoverWithFunction);

        if (isSuccess()) {
            return this;
        } else {
            try {
                Exception cause = ((Failure<T>) this).exception;
                if (cause != null) {
                    return recoverWithFunction.apply(cause);
                } else {
                    return failure(cause);
                }
            } catch (Throwable t) {
                return failure(new Exception(t));
            }
        }
    }


    /**
     * Handles both the failure and success cases by applying different functions to each.
     * @param failureFunction the function to apply to the Throwable in case of failure.
     * @param successFunction the function to apply to the value in case of success.
     * @param <U> the result type after folding.
     * @return the result of applying the corresponding function based on the Try instance.
     */
    public <U> U fold(Function<? super Throwable, ? extends U> failureFunction, Function<? super T, ? extends U> successFunction) {
        Objects.requireNonNull(failureFunction);
        Objects.requireNonNull(successFunction);

        if (isSuccess()) {
            try {
                return successFunction.apply(get());
            } catch (Throwable t) {
                return failureFunction.apply(new Exception(t));
            }
        } else {
            try {
                Exception cause = ((Failure<T>) this).exception;
                return failureFunction.apply(cause);
            } catch (Throwable t) {
                return failureFunction.apply(new Exception(t));
            }
        }
    }

}


/**
 * Represents a failed computation result.
 */
 final class Failure<T> extends Try<T> implements Serializable {
    final Exception exception;

    public Failure(Exception exception) {
        this.exception = exception;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public T get() throws Exception {
        throw exception;
    }

    @Override
    public T getOrElse(T defaultValue) {
        return defaultValue;
    }

    @Override
    public T getOrElseGet(Supplier<? extends T> supplier) {
        return supplier.get();
    }

    @Override
    public <X extends Throwable> T getOrElseThrow(Function<? super Throwable, ? extends X> exceptionMapper) throws X {
        throw exceptionMapper.apply(exception);
    }
}

/**
 * Represents a successful computation result.
 */
final class Success<T> extends Try<T> implements Serializable {
    private final T value;

    public Success(T value) {
        this.value = value;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public T get() {
        return this.value;
    }

    @Override
    public T getOrElse(T defaultValue) {
        return value;
    }

    @Override
    public T getOrElseGet(Supplier<? extends T> supplier) {
        return value;
    }

    @Override
    public <X extends Throwable> T getOrElseThrow(Function<? super Throwable, ? extends X> exceptionMapper) throws X {
        return value;
    }


}