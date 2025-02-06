package org.fungover.breeze.funclib.control;

import java.util.Objects;
import java.util.function.Function;

public final class Right<L, R> extends Either<L, R> {
  private final R value;

  public Right(R value)
  {
    this.value = value;
  }

  @Override
  public boolean isLeft() {
    return false;
  }

  @Override
  public boolean isRight() {
    return true;
  }

  @Override
  public L getLeft() {
    throw new IllegalStateException("Can not get left value on Right");
  }

  @Override
  public R getRight() {
    return value;
  }

  @Override
  public <T> Either<L, T> map(Function<R, T> mapper) {
    return new Right<>(mapper.apply(value));
  }

  @Override
  public <T> Either<T, R> mapLeft(Function<L, T> mapper) {
    return new Right<>(value);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Right<?, ?> right)) return false;
    return Objects.equals(value, right.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }
}
