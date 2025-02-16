package org.fungover.breeze.util;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public final class FDeque<T> {

    private final List<T> front;
    private final List<T> back;

    private FDeque(List<T> front, List<T> back) {
        if (front == null || back == null) {
            throw new IllegalArgumentException("Lists cannot be null");
        }
        this.front = Collections.unmodifiableList(front);
        this.back = Collections.unmodifiableList(back);
    }

    public static <T> FDeque<T> create() {
        return new FDeque<>(new LinkedList<>(), new LinkedList<>());
    }

    /**
     * Add an element to the front of the list
     * @param element element
     * @return A new list with the new element in front
     */
    public FDeque<T> enqueFront(T element) {
        List<T> newFront = new LinkedList<>(front);
        newFront.addFirst(element);
        return new FDeque<>(newFront, back);
    }

    /**
     * Add an element to the back of the list
     * @param element element
     * @return A new list with the new element in the back
     */
    public FDeque<T> enqueBack(T element) {
        List<T> newBack = new LinkedList<>(back);
        newBack.addLast(element);
        return new FDeque<>(front, newBack);
    }

    /**
     * Remove the front element
     * @return A list without the front element
     */
    public FDeque<T> dequeFront() {
        if (front.isEmpty() && back.isEmpty()) {
            return create();
        } else if (front.isEmpty()) {
            List<T> newBack = new LinkedList<>(back);
            newBack.removeFirst();
            return new FDeque<>(Collections.emptyList(), newBack);

        } else {
            List<T> newFront = new LinkedList<>(front);
            newFront.removeFirst();
            return new FDeque<>(newFront, back);
        }
    }

    /**
     * Remove the back element
     * @return A new list without the back element
     */
    public FDeque<T> dequeBack() {
        if (back.isEmpty() && front.isEmpty()) {
                return create();
            } else if (back.isEmpty()) {
                List<T> newFront = new LinkedList<>(front);
                newFront.removeLast();
                return new FDeque<>(Collections.emptyList(), newFront);

            } else {
            List<T> newBack = new LinkedList<>(back);
            newBack.removeLast();
            return new FDeque<>(front, newBack);
        }
    }

    /**
     * Check which element is in front
     * @return The element in front
     */
    public Optional<T> peekFront() {
        if (!front.isEmpty()) {
            return Optional.of(front.getFirst());
        } else if (!back.isEmpty()) {
            return Optional.of(back.getFirst());
        }
        return Optional.empty();
    }

    /**
     * Check which element is in the back
     * @return The element in the back
     */
    public Optional<T> peekBack() {
        if (!back.isEmpty()) {
            return Optional.of(back.getLast());
        } else if (!front.isEmpty()) {
            return Optional.of(front.getLast());
        }
        return Optional.empty();
    }

    /**
     * Check if the list is empty
     * @return True if the list is empty, otherwise false
     */
    public boolean isEmpty() {
        return front.isEmpty() && back.isEmpty();
    }
}