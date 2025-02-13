import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class FDeque<T> {

    private final List<T> front;
    private final List<T> back;

    private FDeque(List<T> front, List<T> back) {
        this.front = Collections.unmodifiableList(front);
        this.back = Collections.unmodifiableList(back);
    }

    public static <T> FDeque<T> create() {
        return new FDeque<T>(new LinkedList<>(), new LinkedList<>());
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
                return create(); }
             else if (front.isEmpty()) {
            List<T> newBack = new LinkedList<>(back);
            newBack.removeFirst();
            return new FDeque<>(Collections.emptyList(), newBack);
        }
             else {
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
            }
            else {
            List<T> newBack = new LinkedList<>(back);
            newBack.removeLast();
            return new FDeque<>(front, newBack);
        }
    }

    /**
     * Check which element is in front
     * @return The element in front
     */
    public T peekFront() {
        if (!front.isEmpty()) {
            return front.getFirst();
        } else if (!back.isEmpty()) {
            return back.getFirst();
        }
        return null;
    }

    /**
     * Check which element is in the back
     * @return The element in the back
     */
    public T peekBack() {
        if (!back.isEmpty()) {
            return back.getLast();
        } else if (!front.isEmpty()) {
            return front.getLast();
        }
        return null;
    }

    /**
     * Check if the list is empty
     * @return True if the list is empty, otherwise false
     */
    public boolean isEmpty() {
        return front.isEmpty() && back.isEmpty();
    }
}