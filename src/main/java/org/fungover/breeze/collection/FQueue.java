package org.fungover.breeze.collection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FQueue<T> {

    private final List<T> front;
    private final List<T> back;

    public FQueue(List<T> front, List<T> back) {
        this.front = front;
        this.back = back;
    }

    public static <T> FQueue<T> empty() {
        return new FQueue<>(Collections.emptyList(), Collections.emptyList());
    }

    public FQueue<T> enqueue(T element) {
        return new FQueue<>(front, Stream.concat(Stream.of(element), back.stream())
                .collect(Collectors.toList()));
    }


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

    public Optional<T> peek() {
        if (front.isEmpty() && back.isEmpty()) {
            return Optional.empty();
        }
        if (!front.isEmpty()) {
            return Optional.of(front.get(0)); // Correct order
        }
        return Optional.of(back.get(back.size() - 1)); // Oldest element in back
    }

    public boolean isEmpty() {
        return front.isEmpty() && back.isEmpty();
    }

    public int size() {
        return front.size() + back.size();
    }
}
