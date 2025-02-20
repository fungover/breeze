package org.fungover.breeze.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A generic queue implementation that supports reversing the order of elements.
 *
 * @param <T> the type of elements held in this queue
 */
public class ReversibleQueue<T> {
    private final Queue<T> queue;

    /**
     * Constructs an empty reversible queue.
     */
    public ReversibleQueue() {
        this.queue = new LinkedList<>();
    }

    /**
     * Adds an element to the end of the queue.
     *
     * @param item the element to add
     */
    public void enqueue(T item) {
        queue.add(item);
    }

    /**
     * Removes and returns the head of the queue.
     *
     * @return the head of the queue, or null if the queue is empty
     */
    public T dequeue() {
        return queue.poll();
    }

    /**
     * Reverses the order of elements in the queue.
     * Uses a deque as temporary storage to achieve the reversal.
     */
    public void reverse() {
        Deque<T> deque = new ArrayDeque<>();
        
        while (!queue.isEmpty()) {
            deque.push(queue.poll());
        }
        
        while (!deque.isEmpty()) {
            queue.add(deque.pop());
        }
    }

    /**
     * Returns whether the queue is empty.
     *
     * @return true if the queue contains no elements
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number of elements in the queue
     */
    public int size() {
        return queue.size();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
} 