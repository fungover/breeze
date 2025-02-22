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

    /**
     * Inserts an element with a given priority into the priority queue.
     * This method maintains immutability by creating a new list,
     * adding the element, sorting it based on priority, and returning a new FPriorityQueue instance.
     *
     * @param element  The element to be added.
     * @param priority The priority of the element (lower value indicates higher priority).
     * @return A new FPriorityQueue instance with the element inserted in sorted order.
     */
    public FPriorityQueue<T> enqueue(T element, int priority ) {
        List<Node<T>> newHeap = new ArrayList<>(heap);
        newHeap.add(new Node<>(element, priority));
        heapifyUp(newHeap, newHeap.size() -1);
        return new FPriorityQueue<>(newHeap);
    }

    public FPriorityQueue<T> dequeue() {
        if(heap.isEmpty()) return
                new FPriorityQueue<>();

        List<Node<T>> newHeap = new ArrayList<>(heap);
        Collections.swap(newHeap, 0, heap.size() - 1);
        newHeap.remove(newHeap.size() - 1);

        return new FPriorityQueue<>(heapifyDown(newHeap, 0));
    }

    public T peek(){
       if(isEmpty())
        throw new NoSuchElementException("Priority queue is empty");
       return heap.get(0).element;
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public static <T> List<Node<T>> heapifyDown(List<Node<T>> originalHeap, int index) {
        if (index >= originalHeap.size()) return originalHeap;

        List<Node<T>> heap = new ArrayList<>(originalHeap);
        int size = heap.size();
        boolean swapped = false;

        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap.get(left).priority < heap.get(smallest).priority) {
                smallest = left;
            }
            if (right < size && heap.get(right).priority < heap.get(smallest).priority) {
                smallest = right;
            }
            if (smallest == index)
                break;

            Collections.swap(heap, index, smallest);
            swapped = true;
            index = smallest;
        }
        return swapped ? heap : originalHeap;
    }

    /**
     * Restores the heap property by moving the element at the given index up the heap.
     * This is done by repeatedly swapping the element with its parent until the heap property is satisfied.
     *
     * <p>Heap property: The parent node must have a priority lower than or equal to its children.
     *
     * @param heap  The list representing the heap.
     * @param index The index of the newly added element that may violate the heap property.
     */
    private void heapifyUp(List<Node<T>> heap, int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).priority >= heap.get(parentIndex).priority) {
                break;
            }
            Collections.swap(heap, index, parentIndex);
            index = parentIndex;
        }
    }

    /**
     * Inner class representing an element in the priority queue with its associated priority.
     * Each node contains an element and its priority value.
     ** @param <T> The type of element stored in the node.
     *
     */
    public static class Node<T> {
        /**
         * The element contained in the node.
         */
        public final T element;
        /**
         * The priority of the element (lower value indicates higher priority).
         */
        public final int priority;

        /**
         * Constructs a new node with the given element and priority.
         *
         * @param element  The element to be stored in the node.
         * @param priority The priority associated with the element.
         */
        public Node(T element, int priority) {
            this.element = element;
            this.priority = priority;
        }
    }


}
