package org.fungover.breeze.funclib.control;

import java.util.Objects;
import java.util.function.Function;

/**
 * A concrete implementation of {@code Either} representing a right value.
 *
 * <p>By convention, a {@code Right} value represents an success case
 * in computations that may return one of two possible results.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * Either<String, Integer> result = new Right<>(42);
 * if (result.isRight()) {
 *     System.out.println("Success: " + result.getRight());
 * }
 * </pre>
 *
 * <h2>Thread Safety:</h2>
 * <p>Instances of {@code Right} are immutable and therefore thread-safe.</p>
 *
 * @param <L> The type of the left value (unused in this case)
 * @param <R> The type of the right value (typically a success type)
 */
public final class Right<L, R> extends Either<L, R> {
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
    throw new IllegalStateException("Can not get left value on Right");
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
  public <U> Either<L, U> map(Function<R, U> mapper) {
    return new Right<>(mapper.apply(value));
  }

  /**
   * Returns this instance unchanged, as mapLeft applies only to left values.
   *
   * @param mapper The function to apply to the left value (not used).
   * @param <U>    The new type of the left value.
   * @return This {@code Right} instance unchanged.
   */
  @Override
  public <U> Either<U, R> mapLeft(Function<L, U> mapper) {
    return new Right<>(value);
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
    return "Right {" +
            "value = " + value +
            '}';
  }
}
