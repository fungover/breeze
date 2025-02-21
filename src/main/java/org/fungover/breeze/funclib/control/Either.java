package org.fungover.breeze.funclib.control;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 * A generic sealed class representing a value that can be either {@code Left} or {@code Right}.
 *
 * <p>This class is useful for handling computations that may result in one of two possible outcomes:
 * an error or a success. By convention, {@code Left} represents failure, and {@code Right} represents success.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 *   {@code Either<String, Integer> result = Either.right(42)
 *   if (result.isRight()) {
 *     System.out.println("Success: " + result.getRight())
 *   } else {
 *     System.out.println("Error: " + result.getLeft())
 *   }}
 * </pre>
 *
 * <h2>Thread Safety:</h2>
 * <p>Instances of {@code Either} are immutable and therefore thread-safe</p>
 *
 * @param <L> The type of the left value (typically an error type)
 * @param <R> The type of the right value (typically a success type)
 */
public sealed interface Either<L extends Serializable, R extends Serializable> extends Serializable permits Either.Left, Either.Right {

  /**
   * Checks if this instance represents a left value.
   *
   * @return {@code true} if this is a left value, {@code false} otherwise.
   */
  boolean isLeft();

  /**
   * Checks if this instance represents a right value.
   *
   * @return {@code true} if this is a right value, {@code false} otherwise.
   */
  boolean isRight();

  /**
   * Retrieves the left value.
   *
   * @return The left value.
   * @throws UnsupportedOperationException if this is a right value.
   */
  L getLeft();

  /**
   * Retrieves the right value.
   *
   * @return The right value.
   * @throws UnsupportedOperationException if this is a left value.
   */
  R getRight();

  /**
   * Transforms the right value using the provided function if this is a {@code Right},
   * otherwise returns the unchanged {@code Left}.
   *
   * @param mapper The function to apply to the right value.
   * @param <U>    The new type of the right value.
   * @return A new {@code Either} instance with the transformed right value.
   */
  <U extends Serializable> Either<L, U> map(Function<R, U> mapper);

  /**
   * Transforms the left value using the provided function if this is a {@code Left},
   * otherwise returns the unchanged {@code Right}.
   *
   * @param mapper The function to apply to the left value.
   * @param <U>    The new type of the left value.
   * @return A new {@code Either} instance with the transformed left value.
   */
  <U extends Serializable> Either<U, R> mapLeft(Function<L, U> mapper);

  /**
   * Swaps a {@code Right} to a {@code Left} and vice versa.
   *
   * @return A new {@code Either} instance with the value swapped
   */
  Either<R, L> swap();

  /**
   * Transforms and flattens the right value using the provided function if this is a ${@code Right}
   * otherwise returns the unchanged {@code Left}
   *
   * @param mapper The function to apply to the right value
   * @param <U>    The new type of the right value
   * @return A new {@code Either} instance with the transformed right value
   */
  <U extends Serializable> Either<L, U> flatMap(Function<R, Either<L, U>> mapper);

  /**
   * Transforms both the left and right values using the provided functions
   *
   * @param leftMapper  The function to apply to the left value
   * @param rightMapper The function to apply to the right value
   * @param <U>         The new type of the left and right values
   * @return The result of applying the corresponding mapper function
   */
  <U> U fold(Function<L, U> leftMapper, Function<R, U> rightMapper);

  /**
   * Creates a new {@code Either} instance representing a left value.
   *
   * @param value The left value.
   * @param <L>   The type of the left value.
   * @param <R>   The type of the right value.
   * @return An {@code Either} instance containing the left value.
   */
  static <L extends Serializable, R extends Serializable> Either<L, R> left(L value) {
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
  static <L extends Serializable, R extends Serializable> Either<L, R> right(R value) {
    return new Right<>(value);
  }

  /**
   * A concrete implementation of {@code Either} representing a left value.
   *
   * <p>By convention, a {@code Left} value represents an error or failure case
   * in computations that may return one of two possible results.</p>
   *
   * <h2>Usage Example:</h2>
   * <pre>
   * {@code Either<String, Integer> result = Either.left("Error: Invalid input");
   * if (result.isLeft()) {
   *     System.out.println("Failure: " + result.getLeft());
   * }}
   * </pre>
   *
   * <h2>Thread Safety:</h2>
   * <p>Instances of {@code Left} are immutable and therefore thread-safe.</p>
   *
   * @param <L> The type of the left value (typically an error type)
   * @param <R> The type of the right value (unused in this case)
   */
  final class Left<L extends Serializable, R extends Serializable> implements Either<L, R> {
    /**
     * It represents the value inside the {@code Left}'s instance
     */
    private final L value;

    /**
     * Constructs a new {@code Left} instance with the specified value.
     *
     * @param value The left value, representing a failure or error.
     */
    public Left(L value) {
      this.value = value;
    }

    /**
     * Checks if this instance represents a left value.
     *
     * @return {@code true}, since this is always a left value.
     */
    @Override
    public boolean isLeft() {
      return true;
    }

    /**
     * Checks if this instance represents a right value.
     *
     * @return {@code false}, since this is always a left value.
     */
    @Override
    public boolean isRight() {
      return false;
    }

    /**
     * Retrieves the left value.
     *
     * @return The left value.
     */
    @Override
    public L getLeft() {
      return value;
    }

    /**
     * Throws an exception because a {@code Left} does not contain a right value.
     *
     * @return Never returns normally.
     * @throws UnsupportedOperationException Always thrown when called.
     */
    @Override
    public R getRight() {
      throw new UnsupportedOperationException("Can not get right value on Left");
    }

    /**
     * Returns this instance unchanged, as mapping applies only to right values.
     *
     * @param mapper The function to apply to the right value (not used).
     * @param <U>    The new type of the right value.
     * @return This {@code Left} instance unchanged.
     */
    @Override
    public <U extends Serializable> Either<L, U> map(Function<R, U> mapper) {
      return Either.left(value);
    }

    /**
     * Transforms the left value using the provided function.
     *
     * @param mapper The function to apply to the left value.
     * @param <U>    The new type of the left value.
     * @return A new {@code Left} instance with the transformed left value.
     */
    @Override
    public <U extends Serializable> Either<U, R> mapLeft(Function<L, U> mapper) {
      return Either.left(mapper.apply(value));
    }

    /**
     * Swaps the value from {@code Left} to {@code Right}
     *
     * @return A new {@code Right} instance with the Left's value
     */
    @Override
    public Either<R, L> swap() {
      return Either.right(value);
    }

    /**
     * @param mapper The function to apply to the right value
     * @param <U>    The new type of the right value
     * @return A new {@code Left} instance with unchanged value
     */
    @Override
    public <U extends Serializable> Either<L, U> flatMap(Function<R, Either<L, U>> mapper) {
      return Either.left(value);
    }

    /**
     * @param leftMapper  The function to apply to the left value
     * @param rightMapper The function to apply to the right value
     * @param <U>         The new type of the left value
     * @return The result of applying the corresponding mapper function
     */
    @Override
    public <U> U fold(Function<L, U> leftMapper, Function<R, U> rightMapper) {
      return leftMapper.apply(value);
    }

    /**
     * Compares this instance with another for equality.
     *
     * @param o The object to compare with.
     * @return {@code true} if the other object is also a {@code Left} with the same value, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Left<?, ?> left)) return false;
      return Objects.equals(value, left.value);
    }

    /**
     * Computes the hash code for this instance.
     *
     * @return The hash code of the left value.
     */
    @Override
    public int hashCode() {
      return Objects.hashCode(value);
    }

    /**
     * Returns a string representation of this instance.
     *
     * @return A string describing this {@code Left} instance.
     */
    @Override
    public String toString() {
      return "Left { " +
              "value = " + value +
              " }";
    }
  }

  /**
   * A concrete implementation of {@code Either} representing a right value.
   *
   * <p>By convention, a {@code Right} value represents a success case
   * in computations that may return one of two possible results.</p>
   *
   * <h2>Usage Example:</h2>
   * <pre>
   * {@code Either<String, Integer> result = Either.right(42);
   * if (result.isRight()) {
   *     System.out.println("Success: " + result.getRight());
   * }}
   * </pre>
   *
   * <h2>Thread Safety:</h2>
   * <p>Instances of {@code Right} are immutable and therefore thread-safe.</p>
   *
   * @param <L> The type of the left value (unused in this case)
   * @param <R> The type of the right value (typically a success type)
   */
  final class Right<L extends Serializable, R extends Serializable> implements Either<L, R> {
    /**
     * It represents the value inside the {@code Right}'s instance
     */
    private final R value;

    /**
     * Constructs a new {@code Right} instance with the specified value.
     *
     * @param value The right value, representing a success.
     */
    public Right(R value) {
      this.value = value;
    }

    /**
     * Checks if this instance represents a left value.
     *
     * @return {@code false}, since this is always a right value.
     */
    @Override
    public boolean isLeft() {
      return false;
    }

    /**
     * Checks if this instance represents a right value.
     *
     * @return {@code true}, since this is always a right value.
     */
    @Override
    public boolean isRight() {
      return true;
    }

    /**
     * Throws an exception because a {@code Right} does not contain a left value.
     *
     * @return Never returns normally.
     * @throws UnsupportedOperationException Always thrown when called.
     */
    @Override
    public L getLeft() {
      throw new UnsupportedOperationException("Can not get left value on Right");
    }

    /**
     * Retrieves the right value.
     *
     * @return The right value.
     */
    @Override
    public R getRight() {
      return value;
    }

    /**
     * Transforms the right value using the provided function.
     *
     * @param mapper The function to apply to the right value.
     * @param <U>    The new type of the right value.
     * @return A new {@code Right} instance with the transformed right value.
     */
    @Override
    public <U extends Serializable> Either<L, U> map(Function<R, U> mapper) {
      return Either.right(mapper.apply(value));
    }

    /**
     * Returns this instance unchanged, as mapLeft applies only to left values.
     *
     * @param mapper The function to apply to the left value (not used).
     * @param <U>    The new type of the left value.
     * @return This {@code Right} instance unchanged.
     */
    @Override
    public <U extends Serializable> Either<U, R> mapLeft(Function<L, U> mapper) {
      return Either.right(value);
    }

    /**
     * Swaps the value from {@code Right} to {@code Left}
     *
     * @return A new {@code Left} instance with the Right's value
     */
    @Override
    public Either<R, L> swap() {
      return Either.left(value);
    }

    /**
     * @param mapper The function to apply to the right value
     * @param <U>    The new type of the right value
     * @return A new {@code Right} instance with the transformed right value.
     */
    @Override
    public <U extends Serializable> Either<L, U> flatMap(Function<R, Either<L, U>> mapper) {
      return mapper.apply(value);
    }

    /**
     * @param leftMapper  The function to apply to the left value
     * @param rightMapper The function to apply to the right value
     * @param <U>         The new type of the right value
     * @return The result of applying the corresponding mapper function
     */
    @Override
    public <U> U fold(Function<L, U> leftMapper, Function<R, U> rightMapper) {
      return rightMapper.apply(value);
    }

    /**
     * Compares this instance with another for equality.
     *
     * @param o The object to compare with.
     * @return {@code true} if the other object is also a {@code Right} with the same value, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Right<?, ?> right)) return false;
      return Objects.equals(value, right.value);
    }

    /**
     * Computes the hash code for this instance.
     *
     * @return The hash code of the right value.
     */
    @Override
    public int hashCode() {
      return Objects.hashCode(value);
    }

    /**
     * Returns a string representation of this instance.
     *
     * @return A string describing this {@code Right} instance.
     */
    @Override
    public String toString() {
      return "Right { " +
              "value = " + value +
              " }";
    }
  }
}