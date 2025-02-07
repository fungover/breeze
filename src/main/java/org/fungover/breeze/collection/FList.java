package org.fungover.breeze.collection;

import java.util.function.Function;
import java.util.function.Predicate;


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
    public static <T> FList<T> empty() {
        return new Empty<>();
    }

    private static class Empty<T> extends FList<T> {

        @Override
        public T head() {
            return null;
        }

        @Override
        public FList<T> tail() {
            return null;
        }

        @Override
        public FList<T> prepend(T element) {
            return null;
        }

        @Override
        public FList<T> append(T element) {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public <R> FList<R> map(Function<T, R> f) {
            return null;
        }

        @Override
        public FList<T> filter(Predicate<T> p) {
            return null;
        }
    }
}
