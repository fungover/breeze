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
  @DisplayName("Calling toString on Right returns correct value")
  void callingToStringOnRightReturnsCorrectValue() {
    Either<String, Integer> right = Either.right(42);

    assertThat(right.toString()).hasToString("Right { value = 42 }");
  }

  @Test
  @DisplayName("Calling toString on Left returns correct value")
  void callingToStringOnLeftReturnsCorrectValue() {
    Either<String, Integer> left = Either.left("error");

    assertThat(left.toString()).hasToString("Left { value = error }");
  }

  @Test
  @DisplayName("isLeft returns true for Left")
  void isLeftReturnsTrueForLeft() {
    Either<String, Integer> left = Either.left("error");

    assertThat(left.isLeft()).isTrue();
  }

  @Test
  @DisplayName("Calling hashCode on Right with same value should be equal")
  void callingHashCodeOnRightWithSameValueShouldBeEqual() {
    Either<String, Integer> right = Either.right(42);
    Either<String, Integer> rightTwo = Either.right(42);

    assertThat(right.hashCode()).hasSameHashCodeAs(rightTwo.hashCode());
  }

  @Test
  @DisplayName("Calling hashCode on Left with same value should be equal")
  void callingHashCodeOnLeftWithSameValueShouldBeEqual() {
    Either<String, Integer> left = Either.left("error");
    Either<String, Integer> leftTwo = Either.left("error");

    assertThat(left.hashCode()).hasSameHashCodeAs(leftTwo.hashCode());
  }

  @Test
  @DisplayName("Two different Right values should not have equal hashcode")
  void twoDifferentRightValuesShouldNotHaveEqualHashCode() {
    Either<String, Integer> right = Either.right(42);
    Either<String, Integer> rightTwo = Either.right(84);

    assertThat(right.hashCode()).isNotEqualTo(rightTwo.hashCode());
  }

  @Test
  @DisplayName("Two different Left values should not have equal hashcode")
  void twoDifferentLeftValuesShouldNotHaveEqualHashCode() {
    Either<String, Integer> left = Either.left("error");
    Either<String, Integer> leftTwo = Either.left("success");

    assertThat(left.hashCode()).isNotEqualTo(leftTwo.hashCode());
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

  @Test
  @DisplayName("Swap converts Left to Right")
  void swapLeftToRight() {
    Either<String, Integer> left = Either.left("error");
    var result = left.swap();

    assertThat(result).isInstanceOf(Right.class);
  }

  @Test
  @DisplayName("Swap converts Right to Left")
  void swapRightToLeft() {
    Either<String, Integer> right = Either.right(42);
    var result = right.swap();

    assertThat(result).isInstanceOf(Left.class);
  }

  @Test
  @DisplayName("Calling fold on Left transforms the value")
  void callingFoldOnLeftTransformsTheValue() {
    Either<String, Integer> left = Either.left("Error");

    String result = left.fold(
            error -> "Left: " + error,
            success -> "Right: " + success
    );

    assertThat(result).isEqualTo("Left: Error");
  }

  @Test
  @DisplayName("Calling fold on Right transforms the value")
  void callingFoldOnRightTransformsTheValue() {
    Either<String, Integer> right = Either.right(42);

    String result = right.fold(
            error -> "Left: " + error,
            success -> "Right: " + success
    );

    assertThat(result).isEqualTo("Right: 42");
  }
}
