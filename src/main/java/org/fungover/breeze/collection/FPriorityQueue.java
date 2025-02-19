package org.fungover.breeze.collection;

import java.util.*;

/**
 * Immutable, persistent priority queue implemented using a binary heap.
 * This class represents a priority queue where elements are dequeued based on their priority.
 *
 * @param <T> The type of elements in the priority queue.
 */
public final class FPriorityQueue<T>{
    /**
     * The list representing the binary heap structure.
     * It stores elements in a way that the highest priority element is at the root.
     */
    private final List<Node<T>> heap;
/**
 * Inner class representing an element in the priority queue with its associated priority.
 * Each node contains an element and its priority value.
 ** @param <T> The type of element stored in the node.
 *
 */


    /**
     * Constructs an empty priority queue.
     * The heap is initialized as an empty list.
     */
    public FPriorityQueue() {
        this.heap = new ArrayList<>();
    }

    /**
     * Constructs a new priority queue with a given heap.
     * This constructor is used internally to create new instances while maintaining immutability.
     *
     * @param heap The heap to be used for this priority queue.
     */
    private FPriorityQueue(List<Node<T>> heap) {
        this.heap = heap;
    }












    private static class Node<T> {
        /**
         * The element contained in the node.
         */
        final T element;
        /**
         * The priority of the element (lower value indicates higher priority).
         */
        final int priority;

        /**
         * Constructs a new node with the given element and priority.
         *
         * @param element  The element to be stored in the node.
         * @param priority The priority associated with the element.
         */
        Node(T element, int priority) {
            this.element = element;
            this.priority = priority;
        }
    }

}
