package org.fungover.breeze.util;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FDequeTest {

    @Test
    void testEnqueueFront() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueFront("A");
        assertThat(deque.peekFront()).isEqualTo("A");
    }

    @Test
    void testEnqueueBack() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueBack("B");
        assertThat(deque.peekBack()).isEqualTo("B");
    }

    @Test
    void testDequeueFront() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueFront("A").enqueBack("B");
        deque = deque.dequeFront();
        assertThat(deque.peekFront()).isEqualTo("B");
    }

    @Test
    void testDequeueBack() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueBack("B").enqueFront("A");
        deque = deque.dequeBack();
        assertThat(deque.peekBack()).isEqualTo("A");
    }

    @Test
    void testMultipleEnqueuesAndDequeues() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueFront("A").enqueBack("B").enqueFront("C");
        assertThat(deque.peekFront()).isEqualTo("C");
        assertThat(deque.peekBack()).isEqualTo("B");

        deque = deque.dequeFront();
        assertThat(deque.peekFront()).isEqualTo("A");

        deque = deque.dequeBack();
        assertThat(deque.peekBack()).isEqualTo("A");
    }

    @Test
    void testPeekFront() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueFront("A");
        deque = deque.enqueBack("B");
        assertThat(deque.peekFront()).isEqualTo("A");
    }

    @Test
    void testPeekBack() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueBack("B");
        deque = deque.enqueFront("A");
        assertThat(deque.peekBack()).isEqualTo("B");
    }

    @Test
    void testIsEmpty() {
        FDeque<String> deque = FDeque.create();
        assertThat(deque.isEmpty()).isTrue();
        deque = deque.enqueFront("A");
        assertThat(deque.isEmpty()).isFalse();
    }

    @Test
    void testPeekOnEmptyList() {
        FDeque<String> deque = FDeque.create();
        assertThat(deque.peekFront()).isNull();
        assertThat(deque.peekBack()).isNull();
    }

    @Test
    void testDequeueOnEmptyList() {
        FDeque<String> deque = FDeque.create();
        deque = deque.dequeFront();
        assertThat(deque.isEmpty()).isTrue();

        deque = deque.dequeBack();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    void testMultipleEnqueuesFront() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueFront("A").enqueFront("C");
        assertThat(deque.peekFront()).isEqualTo("C");
        assertThat(deque.peekBack()).isEqualTo("A");
    }

    @Test
    void testMultipleEnqueuesBack() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueBack("A").enqueBack("B");
        assertThat(deque.peekFront()).isEqualTo("A");
        assertThat(deque.peekBack()).isEqualTo("B");
    }

    @Test
    void testMixedOperations() {
        FDeque<String> deque = FDeque.create();
        deque = deque.enqueFront("A")
                .enqueBack("B")
                .dequeFront()
                .dequeBack();
        assertThat(deque.isEmpty()).isTrue();
    }
}
