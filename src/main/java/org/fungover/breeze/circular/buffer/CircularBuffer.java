package org.fungover.breeze.circular.buffer;

import java.util.*;

public class CircularBuffer<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private final T[] buffer;
    private final int capacity;
    private final OverflowStrategy overflowStrategy;
    private final boolean threadSafe;
    private int count;
    private int head;
    private int tail;

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
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be greater than 0");
        if (overflowStrategy == null)
            throw new IllegalArgumentException("OverflowStrategy cannot be null");
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
            if (overflowStrategy == OverflowStrategy.REJECT) {
                throw new IllegalStateException("Buffer is full");
            } else {
                head = (head + 1) % capacity;
                count--;
            }
        }
        buffer[tail] = element;
        tail = (tail + 1) % capacity;
        count++;
    }

    /**
     * Adds multiple elements to the buffer, depending on the overflow strategy:
     * <br>OVERWRITE: overwrites oldest element(s) inside buffer if full
     * <br>REJECT: cancels write and throws exception if buffer is full
     *
     * @param elements New element(s) to be added to the buffer
     */
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

    /**
     * Adds multiple elements to the buffer, depending on the overflow strategy:
     * <br>OVERWRITE: overwrites oldest element(s) inside buffer if full
     * <br>REJECT: cancels write and throws exception if buffer is full
     *
     * @param elements New element(s) to be added to the buffer
     */
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
     * Returns the oldest element inside the buffer.
     *
     * @return the oldest element
     */
    public T peek() {
        if (threadSafe) {
            synchronized (this) {
                return peekInternal();
            }
        } else return peekInternal();
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
     * @throws IllegalArgumentException if element amount exceeds capacity
     */
    public Collection<T> removeBatch(int count) {
        if (count > capacity)
            throw new IllegalArgumentException("Count cannot be greater than capacity");

        Collection<T> removedElements = new ArrayList<>();

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
     * Clears the buffer and resets head, tail, and count.
     */
    public void clear() {
        if (threadSafe) {
            synchronized (this) {
                clearInternal();
            }
        } else clearInternal();
    }

    private void clearInternal() {
        for (int i = 0; i < capacity; i++) {
            buffer[i] = null;
        }
        head = 0;
        tail = 0;
        count = 0;
    }

    public boolean isThreadSafe() {
        return threadSafe;
    }

    public OverflowStrategy getOverflowStrategy() {
        return overflowStrategy;
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
        } else return capacity;
    }

    /**
     * Returns element at specified index.
     * @param index to access
     * @return element at specified index
     */
    protected T getAt(int index) {
        if (index < 0 || index >= count)
            throw new IndexOutOfBoundsException("Index does not exist");
        if (threadSafe) {
            synchronized (this) {
                return buffer[index];
            }
        } else return buffer[index];
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
        return new CircularBufferIterator<>(this);
    }

    private static class CircularBufferIterator<T> implements Iterator<T> {
        private final CircularBuffer<T> buffer;
        private int index, count;

        public CircularBufferIterator(CircularBuffer<T> buffer) {
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
            if (!hasNext()) throw new NoSuchElementException();
            T element = buffer.getAt(index);
            index = (index + 1) % buffer.capacity();
            count++;
            return element;
        }
    }
}
