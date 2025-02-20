package org.fungover.breeze.collection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A functional queue implementation that maintains two lists: front and back.
 * This queue is immutable, meaning every operation returns a new instance of the queue.
 *
 * @param <T> the type of elements in the queue
 */
public class FQueue<T> {

    private final List<T> front;
    private final List<T> back;


    /**
     * Constructs a queue with two lists: front and back.
     *
     * @param front the front list of the queue
     * @param back  the back list of the queue
     */
    public FQueue(List<T> front, List<T> back) {
        this.front = front;
        this.back = back;
    }


    /**
     * Creates and returns an empty queue.
     *
     * @param <T> the type of elements the queue will hold
     * @return an empty {@code FQueue}
     */
    public static <T> FQueue<T> empty() {
        return new FQueue<>(Collections.emptyList(), Collections.emptyList());
    }


    /**
     * Adds an element to the end of the queue.
     *
     * @param element the element to be added
     * @return a new {@code FQueue} with the element added
     */
    public FQueue<T> enqueue(T element) {
        return new FQueue<>(front, Stream.concat(Stream.of(element), back.stream())
                .collect(Collectors.toList()));
    }

    /**
     * Removes the first element from the queue and returns the new queue.
     * If the queue is empty, it returns an empty queue.
     *
     * @return a new {@code FQueue} with the first element removed
     */
    public FQueue<T> dequeue() {
        if (front.isEmpty() && back.isEmpty()) {
            return new FQueue<>(Collections.emptyList(), Collections.emptyList());
        }

        if (front.isEmpty()) {
            // Reverse 'back' and move it to 'front'
            List<T> newFront = new ArrayList<>(back);
            Collections.reverse(newFront);
            return new FQueue<>(newFront.subList(1, newFront.size()), Collections.emptyList());
        }

        return new FQueue<>(front.subList(1, front.size()), back);
    }

    /**
     * Returns the first element in the queue without removing it.
     *
     * @return an {@code Optional} containing the first element if present, otherwise an empty {@code Optional}
     */
    public Optional<T> peek() {
        if (front.isEmpty() && back.isEmpty()) {
            return Optional.empty();
        }
        if (!front.isEmpty()) {
            return Optional.of(front.get(0));
        }
        return Optional.of(back.get(back.size() - 1));
    }

    /**
     * Checks if the queue is empty.
     *
     * @return {@code true} if the queue is empty, otherwise {@code false}
     */
    public boolean isEmpty() {
        return front.isEmpty() && back.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return front.size() + back.size();
    }

    /**
     * Returns a new queue with the elements in reverse order.
     *
     * @return a new {@code FQueue} with elements in reverse order
     */
    public FQueue<T> reverse() {
        List<T> reversedFront = new ArrayList<>(front);
        List<T> reversedBack = new ArrayList<>(back);
        Collections.reverse(reversedFront);
        Collections.reverse(reversedBack);
        return new FQueue<>(reversedBack, reversedFront);
    }
}