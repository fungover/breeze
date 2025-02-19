package org.fungover.breeze.control;

import org.fungover.breeze.funclib.control.Either;
import org.junit.jupiter.api.Test;

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

}