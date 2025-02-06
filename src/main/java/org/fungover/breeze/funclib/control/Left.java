package org.fungover.breeze.funclib.control;

import java.util.Objects;
import java.util.function.Function;

public final class Left<L, R> extends Either<L, R> {
  private final L value;

  public Left(L value) {
    this.value = value;
  }

  @Override
  public boolean isLeft() {
    return true;
  }

  @Override
  public boolean isRight() {
    return false;
  }

  @Override
  public L getLeft() {
    return value;
  }

  @Override
  public R getRight() {
    throw new IllegalStateException("Can not get right value on Left");
  }

  @Override
  public <U> Either<L, U> map(Function<R, U> mapper) {
    return new Left<>(value);
  }

  @Override
  public <U> Either<U, R> mapLeft(Function<L, U> mapper) {
    return new Left<>(mapper.apply(value));
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Left<?, ?> left)) return false;
    return Objects.equals(value, left.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  @Override
  public String toString() {
    return "Left {" +
            "value = " + value +
            '}';
  }
}
