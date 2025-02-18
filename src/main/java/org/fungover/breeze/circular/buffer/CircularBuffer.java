package org.fungover.breeze.circular.buffer;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CircularBuffer<T> implements Iterable<T> {
    private final OverflowStrategy overflowStrategy;

    private final T[] buffer;
    private static final int DEFAULT_CAPACITY = 10;
    private final int capacity;
    private int count;
    private int head;
    private int tail;
    private final boolean threadSafe;

    @SuppressWarnings("unchecked")
    public CircularBuffer() {
        this.capacity = DEFAULT_CAPACITY;
        this.overflowStrategy = OverflowStrategy.OVERWRITE;
        this.threadSafe = true;
        this.buffer = (T[]) new Object[this.capacity];
        this.count = 0;
        this.head = 0;
        this.tail = 0;
    }

    @SuppressWarnings("unchecked")
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
     *
     * @param element New element to be added to the buffer.
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

    public void addAll(Collection<? extends T> elements) {
        if (threadSafe) {
            synchronized (this) {
                for (T element : elements) {
                    addInternal(element);
                }
            }
        } else {
            for (T element : elements) {
                addInternal(element);
            }
        }
    }

    @SafeVarargs
    public final void addAll(T... elements) {
        if (threadSafe) {
            synchronized (this) {
                for (T element : elements) {
                    add(element);
                }
            }
        } else {
            for (T element : elements) {
                add(element);
            }
        }
    }

    /**
     * Returns the oldest element inside the buffer, <b>without</b> removing it.
     *
     * @return the oldest element
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
     * Removes and returns the oldest element in this buffer.
     *
     * @return the oldest element
     * @throws NoSuchElementException if the buffer is empty
     */
    public T remove() {
        if (threadSafe) {
            synchronized (this) {
                return removeInternal();
            }
        } else return removeInternal();
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
     * Removes and returns the specified number of oldest elements from the buffer.
     *
     * @param count the number of elements to remove
     * @return a collection containing the removed elements
     */
    public Collection<T> removeBatch(int count) {
        Collection<T> removedElements = new LinkedList<>();
        if (threadSafe) {
            synchronized (this) {
                for (int i = 0; i < count; i++) {
                    removedElements.add(removeInternal());
                }
            }
        } else {
            for (int i = 0; i < count; i++) {
                removedElements.add(removeInternal());
            }
        }
        return removedElements;
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
        }
        return capacity;
    }

    protected T getAt(int index) {
        if (threadSafe) {
            synchronized (this) {
                return buffer[index];
            }
        }
        return buffer[index];
    }

    protected int getHead() {
        if (threadSafe) {
            synchronized (this) {
                return head;
            }
        } else return head;
    }

    protected int getTail() {
        if (threadSafe) {
            synchronized (this) {
                return tail;
            }
        } else return tail;
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

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator<>(this);
    }
}

class CustomIterator<T> implements Iterator<T> {
    private final CircularBuffer<T> buffer;
    private int index, count;

    public CustomIterator(CircularBuffer<T> buffer) {
        this.buffer = buffer;
        this.index = buffer.getHead();
        count = 0;
    }

    @Override
    public boolean hasNext() {
        return count < buffer.count();
    }

    @Override
    public T next() {
        T element = buffer.getAt(index);
        index = (index + 1) % buffer.capacity();
        count++;
        return element;
    }
}
