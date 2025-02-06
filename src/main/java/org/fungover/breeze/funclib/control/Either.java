package org.fungover.breeze.funclib.control;

import java.util.function.Function;

/**
 * A generic sealed class representing a value that can be either {@code Left} or {@code Right}.
 *
 * <p>This class is useful for handling computations that may result in one of two possible outcomes:
 * an error or a success. By convention, {@code Left} represents failure, and {@code Right} represents success.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 *   Either<String, Integer> result = Either.right(42)
 *   if (result.isRight()) {
 *     System.out.println("Success: " + result.getRight())
 *   } else {
 *     System.out.println("Error: " + result.getLeft())
 *   }
 * </pre>
 *
 * <h2>Thread Safety:</h2>
 * <p>Instances of {@code Either} are immutable and therefore thread-safe</p>
 *
 * @param <L> The type of the left value (typically an error type)
 * @param <R> The type of the right value (typically a success type)
 */
public abstract sealed class Either<L, R> permits Left, Right {

  /**
   * Checks if this instance represents a left value.
   *
   * @return {@code true} if this is a left value, {@code false} otherwise.
   */
  public abstract boolean isLeft();

  /**
   * Checks if this instance represents a right value.
   *
   * @return {@code true} if this is a right value, {@code false} otherwise.
   */
  public abstract boolean isRight();

  /**
   * Retrieves the left value.
   *
   * @return The left value.
   * @throws UnsupportedOperationException if this is a right value.
   */
  public abstract L getLeft();

  /**
   * Retrieves the right value.
   *
   * @return The right value.
   * @throws UnsupportedOperationException if this is a left value.
   */
  public abstract R getRight();

  /**
   * Transforms the right value using the provided function if this is a {@code Right},
   * otherwise returns the unchanged {@code Left}.
   *
   * @param mapper The function to apply to the right value.
   * @param <U>    The new type of the right value.
   * @return A new {@code Either} instance with the transformed right value.
   */
  public abstract <U> Either<L, U> map(Function<R, U> mapper);

  /**
   * Transforms the left value using the provided function if this is a {@code Left},
   * otherwise returns the unchanged {@code Right}.
   *
   * @param mapper The function to apply to the left value.
   * @param <U>    The new type of the left value.
   * @return A new {@code Either} instance with the transformed left value.
   */
  public abstract <U> Either<U, R> mapLeft(Function<L, U> mapper);

  /**
   * Swaps a {@code Right} to a {@code Left} and vice versa.
   *
   * @return A new {@code Either} instance with the value swapped
   */
  public abstract Either<R, L> swap();

  /** Transforms and flattens the right value using the provided function if this is a ${@code Right}
   * otherwise returns the unchanged {@code Left}
   *
   * @param mapper The function to apply to the right value
   * @param <U> The new type of the right value
   *
   * @return A new {@code Either} instance with the transformed right value
   */
  public abstract <U> Either<L, U> flatMap(Function<R, Either<L, U>> mapper);

  /** Transforms both the left and right values using the provided functions
   *
   * @param leftMapper The function to apply to the left value
   * @param rightMapper The function to apply to the right value
   * @param <U> The new type of the left and right values
   *
   * @return A new {@code Either} instance with the transformed value
   */
  public abstract <U> U fold(Function<L, U> leftMapper, Function<R, U> rightMapper);

  /**
   * Creates a new {@code Either} instance representing a left value.
   *
   * @param value The left value.
   * @param <L>   The type of the left value.
   * @param <R>   The type of the right value.
   * @return An {@code Either} instance containing the left value.
   */
  public static <L, R> Either<L, R> left(L value) {
    return new Left<>(value);
  }

  /**
   * Creates a new {@code Either} instance representing a right value.
   *
   * @param value The right value.
   * @param <L>   The type of the left value.
   * @param <R>   The type of the right value.
   * @return An {@code Either} instance containing the right value.
   */
  public static <L, R> Either<L, R> right(R value) {
    return new Right<>(value);
  }
}