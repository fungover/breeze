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
        assertTrue(mapped instanceof Some);
        assertEquals("5", mapped.get());
    }

    @Test
    void flatMapTransformsToAnotherOption() {
        Some<Integer> some = new Some<>(10);
        Option<String> flatMapped = some.flatMap(val -> new Some<>("Value: " + val));
        assertTrue(flatMapped instanceof Some);
        assertEquals("Value: 10", flatMapped.get());
    }

    @Test
    void filterKeepsValueIfPredicateMatches() {
        Some<Integer> some = new Some<>(42);
        Option<Integer> filtered = some.filter(n -> n > 40);
        assertTrue(filtered instanceof Some);
        assertEquals(42, filtered.get());
    }

    @Test
    void filterRemovesValueIfPredicateFails() {
        Some<Integer> some = new Some<>(42);
        Option<Integer> filtered = some.filter(n -> n > 50);
        assertTrue(filtered instanceof None);
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
}