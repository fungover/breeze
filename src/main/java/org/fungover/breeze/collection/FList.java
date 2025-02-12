package org.fungover.breeze.collection;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Abstract class representing an immutable functional list.
 * @param <T> the type of elements in this list
 */

public abstract class FList<T> {
    /**
     * Returns the first element of the list.
     * @return the head element of the list
     * @throws UnsupportedOperationException if the list is empty
     */
    public abstract T head();

    /**
     * Returns a new list that is the tail of this list (i.e., this list without the first element).
     * @return the tail of the list
     * @throws UnsupportedOperationException if the list is empty
     */
    public abstract FList<T> tail();

    /**
     * Returns a new list with the specified element added at the front.
     * @param element the element to add
     * @return a new list with the element added at the front
     */
    public abstract FList<T> prepend(T element);

    /**
     * Returns a new list with the specified element added at the end.
     * @param element the element to add
     * @return a new list with the element added at the end
     */
    public abstract FList<T> append(T element);

    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise
     */
    public abstract boolean isEmpty();

    /**
     * Returns the number of elements in the list.
     * @return the size of the list
     */
    public abstract int size();

    /**
     * Returns a new list with the function applied to each element.
     * @param <R> the type of elements in the new list
     * @param f the function to apply to each element
     * @return a new list with the function applied to each element
     */
    public abstract <R> FList<R> map(Function<T, R> f);

    /**
     * Returns a new list with only the elements that satisfy the predicate.
     * @param p the predicate to test elements
     * @return a new list with only the elements that satisfy the predicate
     */
    public abstract FList<T> filter(Predicate<T> p);

    /**
     * Returns an empty list.
     * @param <T> the type of elements in the list
     * @return an empty list
     */
    @SuppressWarnings("unchecked")
    public static <T> FList<T> empty() {
        return (FList<T>) Empty.SHARED_EMPTY;
    }

    /**
     * Private static class representing an empty list.
     * @param <T> the type of elements in this list
     */

    private static class Empty<T> extends FList<T> {
        private static final FList<?> SHARED_EMPTY = new Empty<>();
        /**
         * Throws UnsupportedOperationException as the list is empty.
         * This method is not applicable for an empty list since there is no head element.
         * @return nothing
         * @throws UnsupportedOperationException always, because an empty list has no head element
         */
        @Override
        public T head() {
            throw new UnsupportedOperationException("Empty list has no head");
        }

        /**
         * Throws UnsupportedOperationException as the list is empty.
         * This method is not applicable for an empty list since there is no tail element.
         * @return nothing
         * @throws UnsupportedOperationException always, because an empty list has no tail element
         */
        @Override
        public FList<T> tail() {
            throw new UnsupportedOperationException("Empty list has no tail");
        }

        /**
         * Returns a new list with the specified element added at the front.
         * Since this list is empty, the new list will contain only the specified element.
         * @param element the element to add
         * @return a new list with the element added at the front
         */
        @Override
        public FList<T> prepend(T element) {
            return new Cons<>(element, this);
        }

        /**
         * Returns a new list with the specified element added at the end.
         * Since this list is empty, the new list will contain only the specified element.
         * @param element the element to add
         * @return a new list with the element added at the end
         */
        @Override
        public FList<T> append(T element) {
            return new Cons<>(element, this);
        }

        /**
         * Checks if the list is empty.
         * @return true as the list is empty
         */
        @Override
        public boolean isEmpty() {
            return true;
        }

        /**
         * Returns the number of elements in the list.
         * @return 0 as the list is empty
         */
        @Override
        public int size() {
            return 0;
        }

        /**
         * Returns a new empty list with the function applied to each element.
         * @param <R> the type of elements in the new list
         * @param f the function to apply to each element
         * @return a new empty list
         */
        @Override
        public <R> FList<R> map(Function<T, R> f) {
            return (FList<R>) SHARED_EMPTY;
        }

        /**
         * Returns a new list with only the elements that satisfy the predicate.
         * Since this list is empty, the resulting list is also empty.
         * @param p the predicate to test elements
         * @return this empty list, as there are no elements to filter
         */
        @Override
        public FList<T> filter(Predicate<T> p) {
            return this;
        }
    }

    /**
     * Private static class representing a non-empty list.
     * @param <T> the type of elements in this list
     */
    private static class Cons<T> extends FList<T> {
        private final T head;
        private final FList<T> tail;
        private final int cachedSize;

        /**
         * Constructs a new Cons with the specified head and tail.
         * @param head the first element of the list
         * @param tail the rest of the list
         */
        public Cons(T head, FList<T> tail) {
            this.head = head;
            this.tail = tail;
            this.cachedSize = 1 + tail.size();
        }

        /**
         * Returns the first element of the list.
         * @return the head element of the list
         */
        @Override
        public T head() {
            return head;
        }

        /**
         * Returns a new list that is the tail of this list (i.e., this list without the first element).
         * @return the tail of the list
         */
        @Override
        public FList<T> tail() {
            return tail;
        }

        /**
         * Returns a new list with the specified element added at the front.
         * @param element the element to add
         * @return a new list with the element added at the front
         */
        @Override
        public FList<T> prepend(T element) {
            return new Cons<>(element, this);
        }

        /**
         * Returns a new list with the specified element added at the end.
         * @param element the element to add
         * @return a new list with the element added at the end
         */
        @Override
        public FList<T> append(T element) {
            return new Cons<>(head, tail.append(element));
        }

        /**
         * Checks if the list is empty.
         * @return false as this list is not empty
         */
        @Override
        public boolean isEmpty() {
            return false;
        }

        /**
         * Returns the number of elements in the list.
         * @return the size of the list
         */
        @Override
        public int size() {
            return cachedSize;
        }

        /**
         * Returns a new list with the function applied to each element.
         * @param <R> the type of elements in the new list
         * @param f the function to apply to each element
         * @return a new list with the function applied to each element
         */
        @Override
        public <R> FList<R> map(Function<T, R> f) {
            return new Cons<>(f.apply(head), tail.map(f));
        }

        /**
         * Returns a new list with only the elements that satisfy the predicate.
         * @param p the predicate to test elements
         * @return a new list with only the elements that satisfy the predicate
         */
        @Override
        public FList<T> filter(Predicate<T> p) {
            if (p.test(head)) {
                return new Cons<>(head, tail.filter(p));
            } else {
                return tail.filter(p);
            }
        }
    }
}
