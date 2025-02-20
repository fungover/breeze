package org.fungover.breeze.collection;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FQueueTest {

    @Test
    void testEmptyQueue() {
        FQueue<Integer> queue = FQueue.<Integer>empty();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertEquals(Optional.empty(), queue.peek());
    }

    @Test
    void testEnqueue() {
        FQueue<Integer> queue = FQueue.<Integer>empty();
        FQueue<Integer> newQueue = queue.enqueue(10);

        assertFalse(newQueue.isEmpty());
        assertEquals(1, newQueue.size());
        assertEquals(Optional.of(10), newQueue.peek());
    }

    @Test
    void testDequeue() {
        FQueue<Integer> queue = FQueue.<Integer>empty();
        queue = queue.enqueue(10);
        queue = queue.enqueue(20);
        FQueue<Integer> dequeuedQueue = queue.dequeue();

        assertEquals(1, dequeuedQueue.size());
        assertEquals(20, dequeuedQueue.peek().orElse(null));
    }

    @Test
    void testDequeueUntilEmpty() {
        FQueue<Integer> queue = FQueue.<Integer>empty();
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
        FQueue<Integer> queue = FQueue.<Integer>empty();
        queue = queue.enqueue(10);
        queue = queue.enqueue(20);
        assertEquals(Optional.of(10), queue.peek());
    }

    @Test
    void testSize() {
        FQueue<Integer> queue = FQueue.<Integer>empty();
        assertEquals(0, queue.size());

        queue = queue.enqueue(10).enqueue(20).enqueue(30);
        assertEquals(3, queue.size());
    }

    @Test
    void testReverseEmptyQueue() {
        FQueue<Integer> queue = FQueue.<Integer>empty();
        FQueue<Integer> reversed = queue.reverse();
        
        assertTrue(reversed.isEmpty());
        assertEquals(0, reversed.size());
    }

    @Test
    void testReverseSingleElement() {
        FQueue<Integer> queue = FQueue.<Integer>empty().enqueue(1);
        FQueue<Integer> reversed = queue.reverse();
        
        assertEquals(1, reversed.size());
        assertEquals(Optional.of(1), reversed.peek());
    }

    @Test
    void testReverseMultipleElements() {
        FQueue<Integer> queue = FQueue.<Integer>empty()
                .enqueue(1)
                .enqueue(2)
                .enqueue(3);
        
        FQueue<Integer> reversed = queue.reverse();
        
        assertEquals(3, reversed.size());
        assertEquals(Optional.of(3), reversed.peek());
        
        // Verify complete reversed order
        assertEquals(Optional.of(3), reversed.peek());
        reversed = reversed.dequeue();
        assertEquals(Optional.of(2), reversed.peek());
        reversed = reversed.dequeue();
        assertEquals(Optional.of(1), reversed.peek());
    }

    @Test
    void testReverseAndEnqueue() {
        FQueue<Integer> queue = FQueue.<Integer>empty()
                .enqueue(1)
                .enqueue(2)
                .reverse()    // [2,1]
                .enqueue(3)   // [2,1,3]
                .enqueue(4)   // [2,1,3,4]
                .reverse();   // [4,3,1,2]
        
        assertEquals(4, queue.size());
        
        // Verify the final order
        assertEquals(Optional.of(4), queue.peek());
        queue = queue.dequeue();
        assertEquals(Optional.of(3), queue.peek());
        queue = queue.dequeue();
        assertEquals(Optional.of(1), queue.peek());
        queue = queue.dequeue();
        assertEquals(Optional.of(2), queue.peek());
    }

}