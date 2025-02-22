package org.fungover.breeze.collection;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class FPriorityQueueTest {

    @Test
    void testEnqueueAndPeek() {
        FPriorityQueue<String> queue = new FPriorityQueue<>();
        queue = queue.enqueue("Low", 3);
        queue = queue.enqueue("High", 1);
        queue = queue.enqueue("Medium", 2);

        assertEquals("High", queue.peek(), "Peek should return the highest priority element.");
    }

    @Test
    void testDequeueAndPeek() {
        FPriorityQueue<String> queue = new FPriorityQueue<>();
        queue = queue.enqueue("Low", 3);
        queue = queue.enqueue("High", 1);
        queue = queue.enqueue("Medium", 2);

        queue = queue.dequeue();
        assertEquals("Medium", queue.peek(), "After dequeueing the highest priority element, the next one should be the second highest priority");
    }

    @Test
    void testDequeueEmptyQueue() {
        FPriorityQueue<String> queue = new FPriorityQueue<>();
        assertThrows(NoSuchElementException.class, queue::peek, "Priority queue is empty");

        queue = queue.dequeue();
        assertTrue(queue.isEmpty(), "Queue should be empty after dequeueing the last element");
    }

    @Test
    void testIsEmpty() {
        FPriorityQueue<String> queue = new FPriorityQueue<>();
        assertTrue(queue.isEmpty(), "Queue should be empty");

        queue = queue.enqueue("Item", 1);
        assertFalse(queue.isEmpty(), "Queue should not be empty after adding an element");

        queue = queue.dequeue();
        assertTrue(queue.isEmpty(), "Queue should be empty after dequeueing the last element");
    }

    @Test
    void testMultipleEnqueues() {
        FPriorityQueue<Integer> queue = new FPriorityQueue<>();
        queue = queue.enqueue(10, 2);
        queue = queue.enqueue(5, 1);
        queue = queue.enqueue(20, 3);

        assertEquals(5, queue.peek(), "The element with the highest priority (lowest value) should be at the top");
    }


    @Test
    void testPriorityOrdering() {
        FPriorityQueue<String> queue = new FPriorityQueue<>();
        queue = queue.enqueue("Item1", 5);
        queue = queue.enqueue("Item2", 3);
        queue = queue.enqueue("Item3", 1);

        queue = queue.dequeue();
        assertEquals("Item2", queue.peek(), "After removing the highest priority element, the second highest priority should be at the top");

    }

    @Test
    void testHeapifyDownWithMultipleElements() {
        FPriorityQueue<String> queue = new FPriorityQueue<>();

        queue = queue.enqueue("C", 3)
                .enqueue("A", 1)
                .enqueue("B", 2);

        assertEquals("A", queue.peek(), "Highest priority element should be 'A'");
        queue = queue.dequeue();
        assertEquals("B", queue.peek(), "Highest priority element should be 'B'");
        queue = queue.dequeue();
        assertEquals("C", queue.peek(), "Highest priority element should be 'C'");
    }
}