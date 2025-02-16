package org.fungover.breeze.collection;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FQueueTest {

    @Test
    void testEmptyQueue() {
        FQueue<Integer> queue = FQueue.empty();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertEquals(Optional.empty(), queue.peek());
    }

    @Test
    void testEnqueue() {
        FQueue<Integer> queue = FQueue.empty();
        FQueue<Integer> newQueue = queue.enqueue(10);

        assertFalse(newQueue.isEmpty());
        assertEquals(1, newQueue.size());
        assertEquals(Optional.of(10), newQueue.peek());
    }

    @Test
    void testDequeue() {
        FQueue<Integer> queue = FQueue.empty();
        queue = queue.enqueue(10);
        queue = queue.enqueue(20);
        FQueue<Integer> dequeuedQueue = queue.dequeue();

        assertEquals(1, dequeuedQueue.size()); // ✅ Size should be 1
        assertEquals(20, dequeuedQueue.peek().orElse(null)); // ✅ Peek should return 20
    }


}