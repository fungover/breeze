package org.fungover.breeze.circular.buffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


class CircularBufferTest {

    @Test
    @DisplayName("Default constructor initiates fields correctly")
    void defaultConstructorInitiatesFieldsCorrectly() {
        CircularBuffer<String> cb = new CircularBuffer<>();

        int capacity = cb.capacity();
        int count = cb.count();
        boolean isThreadSafe = cb.isThreadSafe();
        OverflowStrategy overflowStrategy = cb.getOverflowStrategy();

        assertEquals(10, capacity, "Default capacity should be 10");
        assertEquals(0, count, "Default buffer should be empty (count=0)");
        assertTrue(isThreadSafe, "Default buffer should be thread safe");
        assertEquals(OverflowStrategy.OVERWRITE, overflowStrategy, "Default buffer should have OVERWRITE overflow strategy");
    }

    @Test
    @DisplayName("Parameterized constructor initiates fields correctly")
    void parameterizedConstructorInitiatesFieldsCorrectly() {
        CircularBuffer<String> cb = new CircularBuffer<>(5, OverflowStrategy.REJECT, false);

        int capacity = cb.capacity();
        int count = cb.count();
        boolean isThreadSafe = cb.isThreadSafe();
        OverflowStrategy overflowStrategy = cb.getOverflowStrategy();

        assertEquals(5, capacity, "Custom capacity should be 5");
        assertEquals(0, count, "Custom buffer should be empty (count=0)");
        assertFalse(isThreadSafe, "Custom buffer should not be thread safe");
        assertEquals(OverflowStrategy.REJECT, overflowStrategy, "Custom buffer should have REJECT overflow strategy");
    }

    @Test
    @DisplayName("Invalid constructor parameters throws IllegalArgumentException")
    void invalidConstructorParametersThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> new CircularBuffer<String>(0, OverflowStrategy.OVERWRITE, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Capacity must be greater than 0");
        assertThatThrownBy(() -> new CircularBuffer<String>(5, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("OverflowStrategy cannot be null");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Reject overflow strategy does not add element to buffer")
    void rejectOverflowStrategyDoesNotAddElementToBuffer(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(2, OverflowStrategy.REJECT, threadSafe);
        cb.add(1);
        cb.add(2);
        assertThatThrownBy(() -> cb.add(3))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Buffer is full");
        assertThat(cb.getAt(0)).isNotEqualTo(3);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Overwrite overflow strategy overwrites oldest element in buffer")
    void overwriteOverflowStrategyOverwritesOldestElementInBuffer(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(2, OverflowStrategy.OVERWRITE, threadSafe);
        cb.add(1);
        cb.add(2);
        cb.add(3);
        assertThat(cb.getAt(0)).isEqualTo(3);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("addAll adds multiple elements to buffer")
    void addAllAddsMultipleElementsToBuffer(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(5, OverflowStrategy.OVERWRITE, threadSafe);
        cb.addAll(1, 2, 3, 4, 5);
        List<Integer> list = Arrays.asList(6, 7, 8, 9, 10);
        cb.addAll(list);

        assertThat(cb.getAt(0)).isEqualTo(6);
        assertThat(cb.getAt(4)).isEqualTo(10);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("addAll with reject overflow strategy does not add any element to buffer")
    void addAllWithRejectOverflowStrategyDoesNotAddAnyElementToBuffer(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(5, OverflowStrategy.REJECT, threadSafe);
        cb.addAll(1, 2, 3, 4, 5);
        assertThatThrownBy(() -> cb.addAll(6, 7, 8, 9, 10))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Buffer is full");
        assertThat(cb.getAt(0)).isEqualTo(1);
        assertThat(cb.getAt(4)).isEqualTo(5);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Peek returns oldest element")
    void peekReturnsOldestElement(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(5, OverflowStrategy.OVERWRITE, threadSafe);
        cb.addAll(1, 2, 3, 4, 5, 6);
        assertThat(cb.peek()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Peek empty buffer returns null")
    void peekEmptyBufferReturnsNull(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(5, OverflowStrategy.OVERWRITE, threadSafe);
        assertNull(cb.peek());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Remove from empty buffer throws NoSuchElementException")
    void removeFromEmptyBufferThrowsNoSuchElementException(boolean threadSafe) {
        CircularBuffer<String> cb = new CircularBuffer<>(3, OverflowStrategy.OVERWRITE, threadSafe);
        assertThatThrownBy(cb::remove)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Buffer is empty");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Remove returns the oldest element")
    void removeReturnsOldestElement(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(5, OverflowStrategy.OVERWRITE, threadSafe);
        cb.addAll(1, 2, 3); // oldest is '1'
        Integer removed = cb.remove();

        assertThat(removed).isEqualTo(1);
        assertThat(cb.remove()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Removing oldest element reduces count by 1")
    void removingOldestElementReducesCountBy1(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(5, OverflowStrategy.OVERWRITE, threadSafe);
        cb.addAll(1, 2, 3);
        cb.remove();

        assertThat(cb.count()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("removeBatch throws IllegalArgumentException when exceeding capacity")
    void removeBatchThrowsIllegalArgumentExceptionWhenExceedingCapacity(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(3, OverflowStrategy.OVERWRITE, threadSafe);
        assertThatThrownBy(() -> cb.removeBatch(4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Count cannot be greater than capacity");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("removeBatch with empty buffer throws NoSuchElementException")
    void removeBatchWithEmptyBufferThrowsNoSuchElementException(boolean threadSafe) {
        CircularBuffer<String> cb = new CircularBuffer<>(3, OverflowStrategy.OVERWRITE, threadSafe);
        assertThatThrownBy(() -> cb.removeBatch(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Buffer is empty");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("removeBatch returns oldest elements")
    void removeBatchReturnsOldestElements(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(5, OverflowStrategy.OVERWRITE, threadSafe);
        cb.addAll(1, 2, 3, 4);
        Collection<Integer> removed = cb.removeBatch(3);
        assertThat(removed).containsExactly(1, 2, 3);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("clear empties buffer")
    void clearEmptiesBuffer(boolean threadSafe) {
        CircularBuffer<String> cb = new CircularBuffer<>(3, OverflowStrategy.OVERWRITE, threadSafe);
        cb.addAll("Apple", "Banana", "Cherry");
        cb.clear();
        assertThat(cb).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("getAt throws IndexOutOfBoundsException with invalid parameters")
    void getAtThrowsIndexOutOfBoundsExceptionWithInvalidParameters(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(5, OverflowStrategy.OVERWRITE, threadSafe);
        assertThatThrownBy(() -> cb.getAt(-1))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Index does not exist");
        assertThatThrownBy(() -> cb.getAt(6))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Index does not exist");
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    @DisplayName("getTail is 0 when buffer is empty")
    void getTailIs0WhenEmpty(boolean threadSafe) {
        CircularBuffer<String> cb = new CircularBuffer<>(5, OverflowStrategy.OVERWRITE, threadSafe);
        assertThat(cb.getTail()).isZero();
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    @DisplayName("getTail reflects position after adding elements")
    void getTailReflectsPositionAfterAddingElement(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(3, OverflowStrategy.OVERWRITE, threadSafe);

        assertThat(cb.getTail()).isEqualTo(0);
        cb.add(10);
        assertThat(cb.getTail()).isEqualTo(1);
        cb.add(20);
        assertThat(cb.getTail()).isEqualTo(2);
        cb.add(30);
        assertThat(cb.getTail()).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    @DisplayName("hasNext on empty buffer is false")
    void hasNextOnEmptyBufferIsFalse(boolean threadSafe) {
        CircularBuffer<Integer> cb = new CircularBuffer<>(3, OverflowStrategy.OVERWRITE, threadSafe);
        Iterator<Integer> it = cb.iterator();
        assertThat(it.hasNext()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    @DisplayName("Iterator returns elements in FIFO order")
    void iteratorReturnsElementsInFifoOrder(boolean threadSafe) {
        CircularBuffer<String> cb = new CircularBuffer<>(5, OverflowStrategy.OVERWRITE, threadSafe);
        cb.addAll("Apple", "Banana", "Citrus");

        Iterator<String> it = cb.iterator();

        assertThat(it.hasNext()).isTrue();
        assertThat(it.next()).isEqualTo("Apple");

        assertThat(it.hasNext()).isTrue();
        assertThat(it.next()).isEqualTo("Banana");

        assertThat(it.hasNext()).isTrue();
        assertThat(it.next()).isEqualTo("Citrus");

        assertThat(it.hasNext()).isFalse();
        assertThatThrownBy(it::next).isInstanceOf(NoSuchElementException.class);
    }
}
