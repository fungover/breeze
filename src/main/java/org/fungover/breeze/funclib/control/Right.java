package org.fungover.breeze.funclib.control;

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
}
