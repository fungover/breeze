package org.fungover.breeze.circular.buffer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CircularBuffer<T> implements Iterable<T> {
    private final OverflowStrategy overflowStrategy;

    private T[] buffer;
    private static final int DEFAULT_CAPACITY = 10;
    private final int capacity;
    private int count;
    private int head; // next element to read
    private int tail; // next element to write
    private final boolean threadSafe;

    public CircularBuffer() {
        this.capacity = DEFAULT_CAPACITY;
        this.overflowStrategy = OverflowStrategy.OVERWRITE;
        this.threadSafe = false;
    }
    public CircularBuffer(int capacity, OverflowStrategy overflowStrategy, boolean threadSafe) {
        this.capacity = capacity;
        this.overflowStrategy = overflowStrategy;
        this.threadSafe = threadSafe;
        this.buffer = (T[]) new Object[capacity];
        this.count = 0;
        this.head = 0;
        this.tail = 0;
    }

    /**
     * Adds an element to the buffer, depending on the overflow strategy:
     * <br>OVERWRITE: overwrites oldest element inside buffer if full
     * <br>REJECT: cancels write and throws exception if buffer is full
     * @param element
     * New element to be added to the buffer.
     */
    public void add(T element) {
        if (threadSafe) {
            synchronized (this) {
                addInternal(element);
            }
        } else {
            addInternal(element);
        }
    }

    private void addInternal(T element) {
        if (isFull()) {
            if (overflowStrategy == OverflowStrategy.REJECT)
                throw new IllegalStateException("Buffer is full");
            else {
                head = (head + 1) % capacity;
                count--;
            }
        }
        buffer[tail] = element;
        tail = (tail + 1) % capacity;
        count++;
    }

    /**
     * Retrieves the oldest element inside the buffer, without removing it.
     */
    public T peek() {
        if (threadSafe) {
            synchronized (this) {
                return peekInternal();
            }
        }
        return peekInternal();
    }

    private T peekInternal() {
        return (isEmpty()) ? null : buffer[head];
    }

    /**
     * Removes and returns oldest element inside the buffer.
     * If buffer is empty, throws exception.
     */
    public T remove() {
        if (threadSafe) {
            synchronized (this) {
                removeInternal();
            }
        } return removeInternal();
    }

    private T removeInternal() {
        if (isEmpty())
            throw new NoSuchElementException("Buffer is empty");
        T element = buffer[head];
        buffer[head] = null;
        head = (head + 1) % capacity;
        count--;
        return element;
    }

    /**
     * Clears the buffer and resets head, tail and count.
     */
    public void clear() {
        if (threadSafe) {
            synchronized (this) {
                clearInternal();
            }
        } else clearInternal();
    }

    private void clearInternal() {
        for (int i = 0; i < count; i++) {
            buffer[i] = null;
        }
        head = 0;
        tail = 0;
        count = 0;
    }

    /**
     * Returns the number of elements inside the buffer.
     */
    public int count() {
        if (threadSafe) {
            synchronized (this) {
                return count;
            }
        } else return count;
    }

    /**
     * Returns the capacity of the buffer.
     */
    public int capacity() {
        if (threadSafe) {
            synchronized (this) {
                return capacity;
            }
        } return capacity;
    }

    /**
     * Returns true if the buffer is full.
     */
    private boolean isFull() {
        return count == capacity;
    }

    /**
     * Returns true if the buffer is empty.
     */
    private boolean isEmpty() {
        return count == 0;
    }


    //Todo: Iterable
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }
}
