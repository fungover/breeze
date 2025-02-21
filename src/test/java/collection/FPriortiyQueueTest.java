package collection;

import org.fungover.breeze.collection.FPriorityQueue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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
    void testHeapifyDownWithMultipleElements(){
        List<FPriorityQueue.Node<String>> heap = new ArrayList<>();

        heap.add(new FPriorityQueue.Node<>("C", 3)); //Root
        heap.add(new FPriorityQueue.Node<>("A", 1)); //Left child
        heap.add(new FPriorityQueue.Node<>("B", 2)); // Right child

        List<FPriorityQueue.Node<String>> newHeap = FPriorityQueue.heapifyDown(heap,0);

        assertEquals("A", newHeap.get(0).element);
        assertEquals(1, newHeap.get(0).priority);
        assertEquals("C", newHeap.get(1).element);
        assertEquals(3, newHeap.get(1).priority );
        assertEquals("B", newHeap.get(2).element);
        assertEquals(2, newHeap.get(2).priority );
    }
}