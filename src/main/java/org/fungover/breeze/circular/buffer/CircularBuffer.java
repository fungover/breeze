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

    public void add(T element) {
        if (threadSafe) {
            //Thread safe add
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
    }

    //Access oldest element without removing content
    public T peek() {
        if (threadSafe) {
            //Thread safe peek
        }
        return peekInternal();
    }

    private T peekInternal() {
        return (isEmpty()) ? null : buffer[head];
    }

    public T remove() {
        if (threadSafe) {
            //Thread safe remove
        } return internalRemove();
    }

    private T internalRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Buffer is empty");
        } return null;
    }

    private boolean isFull() {
        return count == capacity;
    }

    private boolean isEmpty() {
        return count == 0;
    }

    private void clear(T[] buffer) {
        buffer = null;
    }

    //Iterable
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
