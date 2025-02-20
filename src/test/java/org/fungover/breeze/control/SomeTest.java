package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Either;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SomeTest {


    // ==============================
    // Basic Properties & Existence Tests
    // ==============================
    @Test
    void someIsDefined() {
        Some<Integer> some = new Some<>(5);
        assertTrue(some.isDefined());
    }

    @Test
    void someIsNotEmpty() {
        Some<Integer> some = new Some<>(5);
        assertFalse(some.isEmpty());
    }



    // ==============================
    // Value Retrieval Tests
    // ==============================
    @Test
    void getReturnsValue() {
        Some<String> some = new Some<>("Hello");
        assertEquals("Hello", some.get());
    }

    @Test
    void getOrElseReturnsValue() {
        Some<Integer> some = new Some<>(10);
        assertEquals(10, some.getOrElse(20));
    }

    @Test
    void getOrElseGetReturnsValue() {
        Some<Integer> some = new Some<>(15);
        assertEquals(15, some.getOrElseGet(() -> 25));
    }

    @Test
    void getOrNullReturnsValue() {
        Some<Integer> some = new Some<>(30);
        assertEquals(30, some.getOrNull());
    }

    @Test
    void orElseThrowShouldReturnValueForSome() {
        Option<Integer> some = Option.some(10);

        assertThat(some.orElseThrow(IllegalStateException::new)).isEqualTo(10);
    }

    @Test
    void forEachShouldExecuteConsumer() {
        Option<Integer> some = Option.some(10);
        final int[] capturedValue = {0};

        some.forEach(val -> capturedValue[0] = val);

        assertThat(capturedValue[0]).isEqualTo(10);
    }

    @Test
    void peekShouldPerformActionAndReturnSameInstance() {
        Option<Integer> some = Option.some(10);
        final int[] capturedValue = {0};

        Option<Integer> result = some.peek(val -> capturedValue[0] = val);

        assertThat(result).isSameAs(some);
        assertThat(capturedValue[0]).isEqualTo(10);
    }

    @Test
    void foldShouldReturnMappedValueForSome() {
        Option<Integer> some = Option.some(10);

        String result = some.fold(() -> "Default Value", val -> "Value: " + val);

        assertThat(result).isEqualTo("Value: 10");
    }


    // ==============================
    // Transformation Tests (map, flatMap)
    // ==============================
    @Test
    void mapTransformsValue() {
        Some<Integer> some = new Some<>(5);
        Option<String> mapped = some.map(Object::toString);
        assertInstanceOf(Some.class, mapped);
        assertEquals("5", mapped.get());
    }

    @Test
    void flatMapTransformsToAnotherOption() {
        Some<Integer> some = new Some<>(10);
        Option<String> flatMapped = some.flatMap(val -> new Some<>("Value: " + val));
        assertInstanceOf(Some.class, flatMapped);
        assertEquals("Value: 10", flatMapped.get());
    }


    // ==============================
    // Filtering Tests
    // ==============================
    @Test
    void filterKeepsValueIfPredicateMatches() {
        Some<Integer> some = new Some<>(42);
        Option<Integer> filtered = some.filter(n -> n > 40);
        assertInstanceOf(Some.class, filtered);
        assertEquals(42, filtered.get());
    }

    @Test
    void filterRemovesValueIfPredicateFails() {
        Some<Integer> some = new Some<>(42);
        Option<Integer> filtered = some.filter(n -> n > 50);
        assertInstanceOf(None.class, filtered);
    }


    // ==============================
    // Conversion Tests (List, Stream, Optional, Either)
    // ==============================
    @Test
    void toListContainsValue() {
        Some<Integer> some = new Some<>(7);
        List<Integer> list = some.toList();
        assertEquals(1, list.size());
        assertEquals(7, list.get(0));
    }

    @Test
    void toStreamContainsValue() {
        Some<Integer> some = new Some<>(7);
        Stream<Integer> stream = some.toStream();
        assertEquals(1, stream.count());
    }

    @Test
    void toOptionalContainsValue() {
        Some<Integer> some = new Some<>(7);
        Optional<Integer> optional = some.toOptional();
        assertTrue(optional.isPresent());
        assertEquals(7, optional.get());
    }

    @Test
    void toEitherReturnsRight() {
        Some<Integer> some = new Some<>(7);
        Either<String, Integer> either = some.toEither(() -> "Error");
        assertTrue(either.isRight());
        assertEquals(7, either.getRight());
    }

    @Test
    void toOptionShouldReturnSameInstance() {
        Option<String> some = Option.some("test");
        assertThat(some.toOption()).isSameAs(some);
    }


    // ==============================
    // Equality & HashCode Tests
    // ==============================
    @Test
    void equalsReturnsTrueForSameValue() {
        Some<Integer> some1 = new Some<>(10);
        Some<Integer> some2 = new Some<>(10);
        assertTrue(some1.equals(some2));
    }

    @Test
    void equalsReturnsFalseForDifferentValue() {
        Some<Integer> some1 = new Some<>(10);
        Some<Integer> some2 = new Some<>(20);
        assertNotEquals(some1, some2);
    }

    @Test
    void hashCodeIsSameForEqualValues() {
        Some<Integer> some1 = new Some<>(10);
        Some<Integer> some2 = new Some<>(10);
        assertEquals(some1.hashCode(), some2.hashCode());
    }


    // ==============================
    // Exception Handling Tests
    // ==============================
    @Test
    void nullValueThrowsException() {
        assertThrows(NullPointerException.class, () -> new Some<>(null));
    }

    @Test
    void mapWithNullFunctionThrowsException() {
        Some<Integer> some = new Some<>(5);
        assertThrows(NullPointerException.class, () -> some.map(null));
    }

    @Test
    void flatMapWithNullFunctionThrowsException() {
        Some<Integer> some = new Some<>(5);
        assertThrows(NullPointerException.class, () -> some.flatMap(null));
    }


    @Test
    void orElseThrowShouldThrowIfSupplierIsNull() {
        Option<Integer> some = Option.some(10);

        assertThrows(NullPointerException.class, () -> some.orElseThrow(null));
    }

    @Test
    void peekShouldThrowIfActionIsNull() {
        Option<Integer> some = Option.some(10);

        assertThrows(NullPointerException.class, () -> some.peek(null));
    }
    @Test
    void foldShouldThrowIfPresentFunctionIsNull() {
        Option<Integer> some = Option.some(10);

        assertThrows(NullPointerException.class, () -> some.fold(() -> "Default Value", null));
    }

    @Test
    void foldShouldThrowIfNoneSupplierIsNull() {
        Option<Integer> some = Option.some(10);

        assertThrows(NullPointerException.class, () -> some.fold(null, val -> "Value: " + val));
    }




    // ==============================
    // Extreme Value Tests
    // ==============================
    @Test
    void someHandlesIntegerMaxValue() {
        Some<Integer> some = new Some<>(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, some.get());
    }

    @Test
    void someHandlesIntegerMinValue() {
        Some<Integer> some = new Some<>(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, some.get());
    }

    @Test
    void someHandlesNaN() {
        Some<Double> some = new Some<>(Double.NaN);
        assertTrue(Double.isNaN(some.get()));
    }

    // ==============================
    // Edge Case Tests
    // ==============================

    @Test
    void someHandlesEmptyString() {
        Some<String> some = new Some<>("");
        assertEquals("", some.get());
    }

    @Test
    void someHandlesWhitespaceString() {
        Some<String> some = new Some(" ");
        assertEquals(" ", some.get());
    }

    @Test
    void orElseThrowShouldDoNothingForSome() {
        Option<Integer> some = Option.some(42);

        assertDoesNotThrow(() -> some.orElseThrow()); // Ensures it doesn't throw an exception
    }


    // ==============================
    // String Representation
    // ==============================
    @Test
    void toStringShouldReturnFormattedStringForSome() {
        Option<Integer> some = Option.some(42);
        assertThat(some.toString()).isEqualTo("Some(value=42, type= Integer)");

        Option<String> someString = Option.some("test");
        assertThat(someString.toString()).isEqualTo("Some(value=test, type= String)");
    }

}