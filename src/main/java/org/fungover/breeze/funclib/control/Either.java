package org.fungover.breeze.funclib.control;

import java.util.function.Function;

public abstract class Either<L, R> {
  public abstract boolean isLeft();

  public abstract boolean isRight();

  public abstract L getLeft();

  public abstract R getRight();

  public abstract <U> Either<L, U> map(Function<R, U> mapper);
  public abstract <U> Either<U, R> mapLeft(Function<L, U> mapper);

  public static <L, R> Either<L, R> left(L value) {
    return new Left<>(value);
  }

  public static <L, R> Either<L, R> right(R value) {
    return new Right<>(value);
  }
}

