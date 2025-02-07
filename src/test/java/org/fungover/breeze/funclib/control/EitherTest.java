package org.fungover.breeze.funclib.control;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class EitherTest {

  @Nested
  @DisplayName("ToString tests")
  class ToStringTests {
    @Test
    @DisplayName("Calling toString on Right returns correct value")
    void callingToStringOnRightReturnsCorrectValue() {
      Either<String, Integer> right = Either.right(42);

      assertThat(right.toString()).hasToString("Right { value = 42 }");
    }

    @Test
    @DisplayName("toString on Right with null value should be correct")
    void toStringOnRightWithNullValueShouldBeCorrect() {
      Either<String, Integer> right = Either.right(null);

      assertThat(right.toString()).hasToString("Right { value = null }");
    }

    @Test
    @DisplayName("Calling toString on Left returns correct value")
    void callingToStringOnLeftReturnsCorrectValue() {
      Either<String, Integer> left = Either.left("error");

      assertThat(left.toString()).hasToString("Left { value = error }");
    }

    @Test
    @DisplayName("toString on Left with null value should be correct")
    void toStringOnLeftWithNullValueShouldBeCorrect() {
      Either<String, Integer> left = Either.left(null);

      assertThat(left.toString()).hasToString("Left { value = null }");
    }
  }

  @Nested
  @DisplayName("HashCode tests")
  class HashCodeTests {
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
    @DisplayName("HashCode handles null values")
    void hashCodeHandlesNullValues() {
      Either<Boolean, String> right = Either.right(null);
      Either<Boolean, String> rightTwo = Either.right(null);

      assertThat(right.hashCode()).hasSameHashCodeAs(rightTwo.hashCode());
    }
  }

  @Nested
  @DisplayName("Equals tests")
  class EqualsTests {
    @Test
    @DisplayName("Equals returns true for same values")
    void equalsReturnsTrueForSameValues() {
      Either<Integer, Boolean> right = Either.right(true);
      Either<Integer, Boolean> rightTwo = Either.right(true);

      assertThat(right.equals(rightTwo)).isTrue();
    }

    @Test
    @DisplayName("Equals can handle null values")
    void equalsCanHandleNullValues() {
      Either<Integer, Boolean> right = Either.right(null);
      Either<Integer, Boolean> rightTwo = Either.right(null);

      assertThat(right.equals(rightTwo)).isTrue();
    }

    @Test
    @DisplayName("Left equals returns false for non equal values")
    void leftEqualsReturnsFalseForNonEqualValues() {
      Either<Integer, Integer> left = Either.left(42);
      Either<Integer, Integer> leftTwo = Either.left(84);

      assertThat(left.equals(leftTwo)).isFalse();
    }

    @Test
    @DisplayName("Right equals returns false for non equal values")
    void rightEqualsReturnsFalseForNonEqualValues() {
      Either<Integer, Integer> right = Either.right(42);
      Either<Integer, Integer> rightTwo = Either.right(84);

      assertThat(right.equals(rightTwo)).isFalse();
    }
  }

  @Nested
  @DisplayName("Basic Operations Tests")
  class BasicOperationsTests {
    @Test
    @DisplayName("Left should handle null values")
    void leftShouldHandleNullValues() {
      Either<String, Integer> left = Either.left(null);

      assertThat(left.getLeft()).isNull();
    }

    @Test
    @DisplayName("Setting left to a value returns correct value")
    void settingLeftToAValueReturnsCorrectValue() {
      Either<String, Integer> left = Either.left("error");

      assertThat(left.getLeft()).isEqualTo("error");
    }

    @Test
    @DisplayName("Setting right to a value returns correct value")
    void settingRightToAValueReturnsCorrectValue() {
      Either<String, Integer> right = Either.right(42);

      assertThat(right.getRight()).isEqualTo(42);
    }

    @Test
    @DisplayName("Right should handle null values")
    void rightShouldHandleNullValues() {
      Either<String, Integer> right = Either.right(null);

      assertThat(right.getRight()).isNull();
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
  }

  @Nested
  @DisplayName("Map tests")
  class MapTests {
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
    @DisplayName("Right Identity law: map() on Right with identity function should not modify the Right value")
    void rightIdentityLaw() {
      Either<String, Integer> right = Either.right(42);

      Either<String, Integer> result = right.map(x -> x);

      assertThat(result).isEqualTo(right);
    }

    @Test
    @DisplayName("Left Identity law: calling map on Left returns the same instance")
    void callingMapOnLeftReturnsSameInstance() {
      Either<String, Integer> left = Either.left("error");
      var result = left.map(x -> x);

      assertThat(result).isEqualTo(left);
    }

    @Test
    @DisplayName("Map should handle null returned from transformation safely")
    void mapShouldHandleNullTransformationSafely() {
      Either<String, Integer> right = Either.right(42);

      Either<String, String> result = right.map(_ -> null);

      assertAll(
              () -> assertThat(result.isRight()).isTrue(),
              () -> assertThat(result.getRight()).isNull()
      );
    }

    @Test
    @DisplayName("Associativity law: chaining map() should result in the same value regardless of the order")
    void associativityLaw() {
      Either<String, Integer> right = Either.right(42);

      Either<String, String> result1 = right.map(x -> "First " + x).map(x -> x + " Processed");
      Either<String, String> result2 = right.map(x -> "First " + x + " Processed");

      assertThat(result1).isEqualTo(result2);
    }
  }

  @Nested
  @DisplayName("FlatMap tests")
  class FlatMapTests {
    @Test
    @DisplayName("Calling flatMap on Right transforms the value")
    void callingFlatMapOnRightTransformsTheValue() {
      Either<String, Integer> right = Either.right(42);

      Either<String, String> result = right.flatMap(value -> Either.right("Processed " + value));

      assertAll(
              () -> assertThat(result.isRight()).isTrue(),
              () -> assertThat(result.getRight()).isEqualTo("Processed 42")
      );
    }

    @Test
    @DisplayName("Calling flatMap on Left keeps the same value")
    void callingFlatMapOnLeftKeepsTheSameValue() {
      Either<String, Integer> right = Either.left("Error");

      Either<String, String> result = right.flatMap(value -> Either.right("Processed " + value));

      assertAll(
              () -> assertThat(result.isLeft()).isTrue(),
              () -> assertThat(result.getLeft()).isEqualTo("Error")
      );
    }

    @Test
    @DisplayName("FlatMap should handle null returned from transformation safely")
    void flatMapShouldHandleNullTransformationSafely() {
      Either<String, Integer> right = Either.right(42);

      Either<String, String> result = right.flatMap(_ -> Either.right(null));

      assertAll(
              () -> assertThat(result.isRight()).isTrue(),
              () -> assertThat(result.getRight()).isNull()
      );
    }
  }

  @Nested
  @DisplayName("Swap tests")
  class SwapTests {
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
    @DisplayName("Calling swap on Left with null value should result in Right")
    void swapWithNullLeftValue() {
      Either<String, Integer> left = Either.left(null);

      Either<Integer, String> result = left.swap();

      assertThat(result.getRight()).isNull();
    }
  }

  @Nested
  @DisplayName("Fold tests")
  class FoldTests {
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

    @Test
    @DisplayName("Fold handles null values")
    void foldHandlesNullValues() {
      Either<Integer, String> right = Either.right(null);

      String result = right.fold(
              error -> "Left: " + error,
              success -> "Right: " + success
      );

      assertThat(result).isEqualTo("Right: null");
    }

    @Test
    @DisplayName("Fold handles null transformation functions")
    void foldHandlesNullTransformationFunctions() {
      Either<String, Integer> right = Either.right(42);

      String result = right.fold(
              error -> "Left: " + error,
              _ -> null
      );

      assertThat(result).isNull();
    }

    @Test
    @DisplayName("Fold handles exception throwing transformation functions")
    void foldHandlesExceptionTransformationFunctions() {
      Either<Exception, String> left = Either.left(new RuntimeException("left"));

      assertThatThrownBy(() -> left.fold(
              error -> {
                throw new RuntimeException(error.getMessage());
              },
              success -> "Right: " + success
      )).isInstanceOf(RuntimeException.class).hasMessage("left");
    }
  }
}
