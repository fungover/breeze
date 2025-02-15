package org.fungover.breeze.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FQueue<T> {
    private final List<T> front;
    private final List<T> back;

    public FQueue(List<T> front, List<T> back) {
        this.front = front;
        this.back = back;
    }

    public static <T> FQueue<T> empty(){
        return new FQueue<>(Collections.emptyList(),Collections.emptyList());

    }

    public FQueue<T> enqueue(T value) {
        List<T> newBack = new ArrayList<>(back);
        newBack.add(value);
        return new FQueue<>(front, newBack);
    }

    public FQueue<T> dequeue() {
        if (front.isEmpty() && back.isEmpty()) {
            return this;
        }

        if (front.isEmpty()) {
            List<T> newFront = new ArrayList<>(back);
            Collections.reverse(newFront);
            return new FQueue<>(newFront.subList(1, newFront.size()), Collections.emptyList());

        }
        return new FQueue<>(front.subList(1, front.size()), back);
    }

    public Optional<T> peek(){
        if (front.isEmpty() && back.isEmpty()) {
            return Optional.empty();
        }
        if (!front.isEmpty()) {
            return Optional.of(front.get(0));
        }
        return Optional.of(back.get(0));
    }

    public boolean isEmpty(){
        return front.isEmpty() && back.isEmpty();
    }

    public int size(){
        return front.size() + back.size();
    }


}
