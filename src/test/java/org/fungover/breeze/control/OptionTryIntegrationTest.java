package org.fungover.breeze.control;

import org.fungover.breeze.control.Option;
import org.fungover.breeze.funclib.control.Try;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class OptionTryIntegrationTest {

    @Test
    void someToTryShouldReturnSuccess() throws Exception {
        Option<Integer> some = Option.some(42);
        Try<Integer> result = some.toTry(() -> new Exception("Value was None"));

        assertThat(result).isInstanceOf(Try.Success.class);
        assertThat(result.get()).isEqualTo(42);
    }

    @Test
    void noneToTryShouldReturnFailure() {
        Option<Integer> none = Option.none();
        Try<Integer> result = none.toTry(() -> new Exception("No value present"));

        assertThat(result).isInstanceOf(Try.Failure.class);
        assertThatExceptionOfType(Exception.class).isThrownBy(result::get)
                .withMessage("No value present");
    }

    @Test
    void noneToTryWithCustomExceptionShouldReturnFailureWithGivenException() {
        Exception customException = new IllegalStateException("Custom Error");
        Option<Integer> none = Option.none();
        Try<Integer> result = none.toTry(() -> customException);

        assertThat(result).isInstanceOf(Try.Failure.class);
        assertThatThrownBy(result::get).isInstanceOf(IllegalStateException.class)
                .hasMessage("Custom Error");
    }

    @Test
    void someToTryShouldIgnoreExceptionSupplier() throws Exception {
        Option<Integer> some = Option.some(42);
        Try<Integer> result = some.toTry(() -> new Exception("This should not be used"));

        assertThat(result).isInstanceOf(Try.Success.class);
        assertThat(result.get()).isEqualTo(42);
    }

    @Test
    void noneToTryWithNullSupplierShouldReturnDefaultException() {
        Option<Integer> none = Option.none();
        Try<Integer> result = none.toTry(null);

        assertThat(result).isInstanceOf(Try.Failure.class);
        assertThatThrownBy(result::get)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    void noneToTryWithNullExceptionFromSupplierShouldThrow() {
        Option<Integer> none = Option.none();

        assertThatThrownBy(() -> none.toTry(() -> null).get())
                .isInstanceOf(NoSuchElementException.class) // Expect NoSuchElementException instead
                .hasMessage("No value present");
    }

    @Test
    void someToTryShouldMaintainReferenceEquality() throws Exception {
        String value = "Hello";
        Option<String> some = Option.some(value);
        Try<String> result = some.toTry(() -> new Exception("Unused"));

        assertThat(result).isInstanceOf(Try.Success.class);
        assertThat(result.get()).isSameAs(value);
    }

}
