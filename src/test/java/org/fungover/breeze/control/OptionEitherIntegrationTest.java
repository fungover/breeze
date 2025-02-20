package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Either;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class OptionEitherIntegrationTest {

    @Test
    void someToEitherShouldReturnRight() {
        Option<Integer> some = Option.some(42);
        Either<String, Integer> result = some.toEither(() -> "No value present");

        assertThat(result).isInstanceOf(Either.Right.class);
        assertThat(result.getRight()).isEqualTo(42);
    }

    @Test
    void noneToEitherShouldReturnLeft() {
        Option<Integer> none = Option.none();
        Either<String, Integer> result = none.toEither(() -> "No value present");

        assertThat(result).isInstanceOf(Either.Left.class);
        assertThat(result.getLeft()).isEqualTo("No value present");
    }

    @Test
    void noneToEitherWithNullSupplierShouldThrow() {
        Option<Integer> none = Option.none();

        assertThatThrownBy(() -> none.toEither(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void noneToEitherWithNullLeftValueShouldThrow() {
        Option<Integer> none = Option.none();

        assertThatThrownBy(() -> none.toEither(() -> null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void rightToOptionShouldReturnSome() {
        Either<String, Integer> right = Either.right(42);
        Option<Integer> result = Option.fromEither(right);

        assertThat(result).isInstanceOf(Some.class);
        assertThat(result.get()).isEqualTo(42);
    }

    @Test
    void leftToOptionShouldReturnNone() {
        Either<String, Integer> left = Either.left("Error");
        Option<Integer> result = Option.fromEither(left);

        assertThat(result).isInstanceOf(None.class);
    }

}