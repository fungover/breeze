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
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Can not get left value on Right");
  }

  @Test
  @DisplayName("Can not access right value on Left")
  void canNotAccessRightValueOnLeft() {
    Either<String, Integer> left = Either.left("error");

    assertThatThrownBy(left::getRight)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Can not get right value on Left");
  }
}
