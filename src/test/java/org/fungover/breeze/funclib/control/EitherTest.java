package org.fungover.breeze.funclib.control;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EitherTest {

  @Test
  @DisplayName("Setting right to a value returns correct value")
  void settingRightToAValueReturnsCorrectValue() {
    Either<String, Integer> right = Either.right(42);

    assertThat(right.getRight()).isEqualTo(42);
  }

  @Test
  @DisplayName("Setting left to a value returns correct value")
  void settingLeftToAValueReturnsCorrectValue() {
    Either<String, Integer> left = Either.left("error");

    assertThat(left.getLeft()).isEqualTo("error");
  }

  @Test
  @DisplayName("Can not access left value on Right")
  void canNotAccessLeftValueOnRight() {
    Either<String, Integer> right = Either.right(42);

    assertThatThrownBy(right::getLeft)
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("Can not get left value on Right");
  }

  @Test
  @DisplayName("Can not access right value on Left")
  void canNotAccessRightValueOnLeft() {
    Either<String, Integer> left = Either.left("error");

    assertThatThrownBy(left::getRight)
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("Can not get right value on Left");
  }

  @Test
  @DisplayName("isLeft returns true for Left")
  void isLeftReturnsTrueForLeft() {
    Either<String, Integer> left = Either.left("error");

    assertThat(left.isLeft()).isTrue();
  }

  @Test
  @DisplayName("isLeft returns false for Right")
  void isLeftReturnsFalseForRight() {
    Either<String, Integer> right = Either.right(42);

    assertThat(right.isLeft()).isFalse();
  }

  @Test
  @DisplayName("isRight returns true for Right")
  void isRightReturnsTrueForRight() {
    Either<String, Integer> right = Either.right(42);

    assertThat(right.isRight()).isTrue();
  }

  @Test
  @DisplayName("isRight returns false for Left")
  void isRightReturnsFalseForLeft() {
    Either<String, Integer> left = Either.left("error");

    assertThat(left.isRight()).isFalse();
  }

  @Test
  @DisplayName("Map updates right value")
  void mapUpdatesRightValue() {
    Either<String, Integer> right = Either.right(42);
    var result = right.map(x -> x * 2);

    assertThat(result.getRight()).isEqualTo(84);
  }

  @Test
  @DisplayName("MapLeft updates left value")
  void mapLeftUpdatesLeftValue() {
    Either<Integer, String> left = Either.left(10);
    var result = left.mapLeft(x -> x * 2);

    assertThat(result.getLeft()).isEqualTo(20);
  }

  @Test
  @DisplayName("Calling mapLeft on Right returns the same instance")
  void callingMapLeftOnRightReturnsSameInstance() {
    Either<String, Integer> right = Either.right(42);
    var result = right.mapLeft(x -> x);

    assertThat(result).isEqualTo(right);
  }

  @Test
  @DisplayName("Calling map on Left returns the same instance")
  void callingMapOnLeftReturnsSameInstance() {
    Either<String, Integer> left = Either.left("error");
    var result = left.map(x -> x);

    assertThat(result).isEqualTo(left);
  }
}
