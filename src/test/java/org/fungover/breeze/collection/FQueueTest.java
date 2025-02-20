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

        assertEquals(1, dequeuedQueue.size());
        assertEquals(20, dequeuedQueue.peek().orElse(null));
    }

    @Test
    void testDequeueUntilEmpty() {
        FQueue<Integer> queue = FQueue.empty();
        queue = queue.enqueue(10);
        queue = queue.enqueue(20);
        FQueue<Integer> q1 = queue.dequeue();
        FQueue<Integer> q2 = q1.dequeue();

        assertTrue(q2.isEmpty());
        assertEquals(0, q2.size());
        assertEquals(Optional.empty(), q2.peek());
    }

    @Test
    void testPeek() {
        FQueue<Integer> queue = FQueue.empty();
        queue = queue.enqueue(10);
        queue = queue.enqueue(20);
        assertEquals(Optional.of(10), queue.peek());
    }

    @Test
    void testSize() {
        FQueue<Integer> queue = FQueue.empty();
        assertEquals(0, queue.size());

        queue = queue.enqueue(10).enqueue(20).enqueue(30);
        assertEquals(3, queue.size());
    }

}