package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Either;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SomeTest {
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

    @Test
    void toStringReturnsExpectedFormat() {
        Some<Integer> some = new Some<>(99);
        assertEquals("Some(value=99, type= Integer)", some.toString());
    }

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
    void filterWithNullPredicateThrowsException() {
        Some<Integer> some = new Some<>(5);
        assertThrows(NullPointerException.class, () -> some.filter(null));
    }

    @Test
    void peekWithNullConsumerThrowsException() {
        Some<Integer> some = new Some<>(5);
        assertThrows(NullPointerException.class, () -> some.peek(null));
    }

    @Test
    void orElseThrowWithNullSupplierThrowsException() {
        Some<Integer> some = new Some<>(5);
        assertThrows(NullPointerException.class, () -> some.orElseThrow(null));
    }

    @Test
    void foldWithNullIfNoneSupplierThrowsException() {
        Some<Integer> some = new Some<>(5);
        assertThrows(NullPointerException.class, () -> some.fold(null, val -> val));
    }

    @Test
    void foldWithNullIfPresentFunctionThrowsException() {
        Some<Integer> some = new Some<>(5);
        assertThrows(NullPointerException.class, () -> some.fold(() -> 0, null));
    }

    // Extreme Values Test Cases

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
    void someHandlesPositiveInfinity() {
        Some<Double> some = new Some<>(Double.POSITIVE_INFINITY);
        assertEquals(Double.POSITIVE_INFINITY, some.get());
    }

    @Test
    void someHandlesNegativeInfinity() {
        Some<Double> some = new Some<>(Double.NEGATIVE_INFINITY);
        assertEquals(Double.NEGATIVE_INFINITY, some.get());
    }


    @Test
    void someHandlesNaN() {
        Some<Double> some = new Some<>(Double.NaN);
        assertTrue(Double.isNaN(some.get()));
    }

    @Test
    void someHandlesEmptyString() {
        Some<String> some = new Some<>("");
        assertEquals("", some.get());
    }

    @Test
    void mapOnEmptyStringRetainsEmptyString() {
        Some<String> some = new Some<>("");
        Option<String> mapped = some.map(str -> str.toUpperCase());
        assertInstanceOf(Some.class, mapped);
        assertEquals("", mapped.get());
    }

    @Test
    void flatMapOnEmptyStringRetainsEmptyString() {
        Some<String> some = new Some<>("");
        Option<String> flatMapped = some.flatMap(str -> new Some<>(str + " modified"));
        assertInstanceOf(Some.class, flatMapped);
        assertEquals(" modified", flatMapped.get());
    }

    @Test
    void filterOnEmptyStringFails() {
        Some<String> some = new Some<>("");
        Option<String> filtered = some.filter(str -> !str.isEmpty());
        assertInstanceOf(None.class, filtered);
    }

    @Test
    void someHandlesWhitespaceString() {
        Some<String> some = new Some<>(" ");
        assertEquals(" ", some.get());
    }

    @Test
    void filterOnWhitespaceStringFails() {
        Some<String> some = new Some<>(" ");
        Option<String> filtered = some.filter(str -> !str.isBlank());
        assertInstanceOf(None.class, filtered);
    }
}