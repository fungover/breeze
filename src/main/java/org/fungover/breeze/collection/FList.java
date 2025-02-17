package org.fungover.breeze.collection;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Abstract class representing an immutable functional list.
 *
 * @param <T> the type of elements in this list
 */

public abstract class FList<T> {
    /**
     * Returns the first element of the list.
     *
     * @return the head element of the list
     * @throws UnsupportedOperationException if the list is empty
     */
    public abstract T head();

    /**
     * Returns a new list that is the tail of this list (i.e., this list without the first element).
     *
     * @return the tail of the list
     * @throws UnsupportedOperationException if the list is empty
     */
    public abstract FList<T> tail();

    /**
     * Returns a new list with the specified element added at the front.
     *
     * @param element the element to add
     * @return a new list with the element added at the front
     */
    public abstract FList<T> prepend(T element);

    /**
     * Returns a new list with the specified element added at the end.
     *
     * @param element the element to add
     * @return a new list with the element added at the end
     */
    public abstract FList<T> append(T element);

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    public abstract boolean isEmpty();

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    public abstract int size();

    /**
     * Returns a new list with the function applied to each element.
     *
     * @param <R> the type of elements in the new list
     * @param f   the function to apply to each element
     * @return a new list with the function applied to each element
     */
    public abstract <R> FList<R> map(Function<T, R> f);

    /**
     * Returns a new list with only the elements that satisfy the predicate.
     *
     * @param p the predicate to test elements
     * @return a new list with only the elements that satisfy the predicate
     */
    public abstract FList<T> filter(Predicate<T> p);

    /**
     * Reverses the current list.
     *
     * This method should be implemented by subclasses to return a new list
     * with the elements in reverse order.
     *
     * @return a new FList with the elements in reverse order
     */
    public abstract FList<T> reverse();


    /**
     * Returns an empty list.
     *
     * @param <T> the type of elements in the list
     * @return an empty list
     */
    @SuppressWarnings("unchecked")
    public static <T> FList<T> empty() {
        return (FList<T>) Empty.SHARED_EMPTY;
    }

    /**
     * Private static class representing an empty list.
     *
     * @param <T> the type of elements in this list
     */

    private static class Empty<T> extends FList<T> {
        private static final FList<?> SHARED_EMPTY = new Empty<>();

        /**
         * Throws UnsupportedOperationException as the list is empty.
         * This method is not applicable for an empty list since there is no head element.
         *
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
         *
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
         *
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
         *
         * @param element the element to add
         * @return a new list with the element added at the end
         */
        @Override
        public FList<T> append(T element) {
            return new Cons<>(element, this);
        }

        /**
         * Checks if the list is empty.
         *
         * @return true as the list is empty
         */
        @Override
        public boolean isEmpty() {
            return true;
        }

        /**
         * Returns the number of elements in the list.
         *
         * @return 0 as the list is empty
         */
        @Override
        public int size() {
            return 0;
        }

        /**
         * Returns a new empty list with the function applied to each element.
         *
         * @param <R> the type of elements in the new list
         * @param f   the function to apply to each element
         * @return a new empty list
         */
        @Override
        public <R> FList<R> map(Function<T, R> f) {
            return (FList<R>) SHARED_EMPTY;
        }

        /**
         * Returns a new list with only the elements that satisfy the predicate.
         * Since this list is empty, the resulting list is also empty.
         *
         * @param p the predicate to test elements
         * @return this empty list, as there are no elements to filter
         */
        @Override
        public FList<T> filter(Predicate<T> p) {
            return this;
        }

        /**
         * Reverses the current list.
         *
         * Since this is an empty list, reversing it has no effect.
         * This method simply returns the current instance of the empty list.
         *
         * @return the current instance of the empty list
         */
        @Override
        public FList<T> reverse() {
            return this;
        }
    }


    /**
     * Private static class representing a non-empty list.
     *
     * @param <T> the type of elements in this list
     */
    private static class Cons<T> extends FList<T> {
        private final T head;
        private final FList<T> tail;
        private final int cachedSize;

        /**
         * Constructs a new Cons with the specified head and tail.
         *
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
         *
         * @return the head element of the list
         */
        @Override
        public T head() {
            return head;
        }

        /**
         * Returns a new list that is the tail of this list (i.e., this list without the first element).
         *
         * @return the tail of the list
         */
        @Override
        public FList<T> tail() {
            return tail;
        }

        /**
         * Returns a new list with the specified element added at the front.
         *
         * @param element the element to add
         * @return a new list with the element added at the front
         */
        @Override
        public FList<T> prepend(T element) {
            return new Cons<>(element, this);
        }

        /**
         * Appends the specified element to the end of the list.
         *
         * This method iterates through the current list, prepending each element to a new list.
         * After all elements are copied, the new element is prepended to the new list.
         * Finally, the new list is reversed to maintain the correct order.
         *
         * Time complexity: O(n), where n is the number of elements in the list.
         *
         * @param element the element to be appended to the list
         * @return a new FList with the specified element appended
         */
        @Override
        public FList<T> append(T element) {
            // Create a new list to hold the result
            FList<T> result = new Empty<>();
            FList<T> current = this;

            // Iterate through the list and copy elements to the new list
            while (!current.isEmpty()) {
                result = result.prepend(current.head());
                current = current.tail();
            }

            // Add the new element
            result = result.prepend(element);

            // Reverse the list to get the correct order
            return result.reverse();
        }


        /**
         * Checks if the list is empty.
         *
         * @return false as this list is not empty
         */
        @Override
        public boolean isEmpty() {
            return false;
        }

        /**
         * Returns the number of elements in the list.
         *
         * @return the size of the list
         */
        @Override
        public int size() {
            return cachedSize;
        }

        /**
         * Returns a new list with the function applied to each element.
         *
         * @param <R> the type of elements in the new list
         * @param f   the function to apply to each element
         * @return a new list with the function applied to each element
         */
        @Override
        public <R> FList<R> map(Function<T, R> f) {
            return new Cons<>(f.apply(head), tail.map(f));
        }

        /**
         * Returns a new list with only the elements that satisfy the predicate.
         *
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

        /**
         * Reverses the current list.
         *
         * This method iterates through the list, prepending each element to a new list,
         * effectively reversing the order of the elements.
         *
         * @return a new FList with the elements in reverse order
         */
        @Override
        public FList<T> reverse() {
            FList<T> reversed = new Empty<>();
            FList<T> current = this;
            while (!current.isEmpty()) {
                reversed = reversed.prepend(current.head());
                current = current.tail();
            }
            return reversed;
        }
    }

    /**
     * CachedFList is a wrapper around FList that caches the reversed version of the list.
     * This improves the performance of append operations by allowing elements to be prepended
     * to the reversed list.
     *
     * @param <T> the type of elements in this list
     */
    public static class CachedFList<T> extends FList<T> {
        private final FList<T> original;
        private FList<T> cachedReversed;

        /**
         * Constructs a CachedFList with the given original list.
         *
         * @param original the original list to be wrapped
         */
        public CachedFList(FList<T> original) {
            this.original = original;
            this.cachedReversed = null;
        }

        /**
         * Returns the head of the original list.
         *
         * @return the head element of the original list
         */
        @Override
        public T head() {
            return original.head();
        }

        /**
         * Returns the tail of the original list.
         *
         * @return the tail of the original list
         */
        @Override
        public FList<T> tail() {
            return original.tail();
        }

        /**
         * Prepends the specified element to the original list.
         *
         * @param element the element to be prepended
         * @return a new CachedFList with the element prepended
         */
        @Override
        public FList<T> prepend(T element) {
            return new CachedFList<>(original.prepend(element));
        }

        /**
         * Appends the specified element to the end of the list.
         * Uses the cached reversed list to improve performance.
         *
         * @param element the element to be appended
         * @return a new CachedFList with the element appended
         */
        @Override
        public FList<T> append(T element) {
            if (cachedReversed == null) {
                cachedReversed = original.reverse();
            }
            cachedReversed = cachedReversed.prepend(element);
            return new CachedFList<>(cachedReversed.reverse());
        }

        /**
         * Checks if the original list is empty.
         *
         * @return true if the original list is empty, false otherwise
         */
        @Override
        public boolean isEmpty() {
            return original.isEmpty();
        }

        /**
         * Returns the size of the original list.
         *
         * @return the number of elements in the original list
         */
        @Override
        public int size() {
            return original.size();
        }

        /**
         * Applies the given function to each element in the original list and returns a new list.
         *
         * @param <R> the type of elements in the new list
         * @param f the function to apply to each element
         * @return a new CachedFList with the mapped elements
         */
        @Override
        public <R> FList<R> map(Function<T, R> f) {
            return new CachedFList<>(original.map(f));
        }

        /**
         * Filters the elements of the original list based on the given predicate.
         *
         * @param p the predicate to apply to each element
         * @return a new CachedFList with the filtered elements
         */
        @Override
        public FList<T> filter(Predicate<T> p) {
            return new CachedFList<>(original.filter(p));
        }

        /**
         * Returns the reversed version of the original list.
         * Uses a cached version if available to improve performance.
         *
         * @return the reversed list
         */
        @Override
        public FList<T> reverse() {
            if (cachedReversed == null) {
                cachedReversed = original.reverse();
            }
            return cachedReversed;
        }
    }
}
