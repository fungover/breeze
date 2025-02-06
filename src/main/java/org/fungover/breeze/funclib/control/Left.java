package org.fungover.breeze.funclib.control;

import java.util.Objects;
import java.util.function.Function;

/**
 * A concrete implementation of {@code Either} representing a left value.
 *
 * <p>By convention, a {@code Left} value represents an error or failure case
 * in computations that may return one of two possible results.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * Either<String, Integer> result = new Left<>("Error: Invalid input");
 * if (result.isLeft()) {
 *     System.out.println("Failure: " + result.getLeft());
 * }
 * </pre>
 *
 * <h2>Thread Safety:</h2>
 * <p>Instances of {@code Left} are immutable and therefore thread-safe.</p>
 *
 * @param <L> The type of the left value (typically an error type)
 * @param <R> The type of the right value (unused in this case)
 */
public final class Left<L, R> extends Either<L, R> {
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
    throw new IllegalStateException("Can not get right value on Left");
  }

  /**
   * Returns this instance unchanged, as mapping applies only to right values.
   *
   * @param mapper The function to apply to the right value (not used).
   * @param <U>    The new type of the right value.
   * @return This {@code Left} instance unchanged.
   */
  @Override
  public <U> Either<L, U> map(Function<R, U> mapper) {
    return new Left<>(value);
  }

  /**
   * Transforms the left value using the provided function.
   *
   * @param mapper The function to apply to the left value.
   * @param <U>    The new type of the left value.
   * @return A new {@code Left} instance with the transformed left value.
   */
  @Override
  public <U> Either<U, R> mapLeft(Function<L, U> mapper) {
    return new Left<>(mapper.apply(value));
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
    return "Left {" +
            "value = " + value +
            '}';
  }
}
