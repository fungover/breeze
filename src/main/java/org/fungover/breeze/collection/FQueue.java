package org.fungover.breeze.collection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FQueue<T> {

    private final List<T> front;
    private final List<T> back;

    //Constructor: Creates a queue with two lists, front and back.
    public FQueue(List<T> front, List<T> back) {
        this.front = front;
        this.back = back;
    }

    //Creates and returns an empty queue
    public static <T> FQueue<T> empty() {
        return new FQueue<>(Collections.emptyList(), Collections.emptyList());
    }

    // Adds and element to the end of the queue.
    public FQueue<T> enqueue(T element) {
        return new FQueue<>(front, Stream.concat(Stream.of(element), back.stream())
                .collect(Collectors.toList()));
    }

    //Removes the first element from the queue and returns the new queue.
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

    //Returns the first element in the queue without removing it.
    public Optional<T> peek() {
        if (front.isEmpty() && back.isEmpty()) {
            return Optional.empty();
        }
        if (!front.isEmpty()) {
            return Optional.of(front.get(0));
        }
        return Optional.of(back.get(back.size() - 1));
    }

    //Checks if the queue is empty
    public boolean isEmpty() {
        return front.isEmpty() && back.isEmpty();
    }

    //Returns the number of elements in the queue
    public int size() {
        return front.size() + back.size();
    }
}
