package org.fungover.breeze.control;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Final class that defines a comparable Tuple with an element size of 3.
 * The class implements Comparable and Serializable.
 * A tuple is useful for storing objects of equal or different class types.
 * <p>
 * Initialize: Tuple3.of(1, "two", 3.0);
 * <p>
 * All fields are set as private and final.
 * Factory method of() to initialize new instance.
 * Methods that produce a change in any element returns a new instance.
 * Any call to methods in this class with a null object will result in a NullPointerException.
 *
 * @param <T1> class type of first element where class must implement serializable and class (or superclass) must implement comparable.
 * @param <T2> class type of second element where class must implement serializable and class (or superclass) must implement comparable.
 * @param <T3> class type of third element where class must implement serializable and class (or superclass) must implement comparable.
 */
public final class Tuple3<T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable> implements Serializable, Comparable<Tuple3<T1, T2, T3>> {

    /**
     * This value represents the first element of this object.
     */
    private final T1 first;

    /**
     * This value represents the second element of this object.
     */
    private final T2 second;

    /**
     * This value represents the third element of this object.
     */
    private final T3 third;

    /**
     * Private constructor that initialize a new Tuple3 object.
     *
     * @param first  first element of the tuple
     * @param second second element of the tuple
     * @param third  third element of the tuple
     */
    private Tuple3(T1 first, T2 second, T3 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Constructs a Tuple3 with 3 elements.
     *
     * @param first  first not null element in the tuple
     * @param second second not null element in the tuple
     * @param third  third not null element in the tuple
     * @param <T1>   class type of first element where class must implement serializable and class (or superclass) must implement comparable.
     * @param <T2>   class type of second element where class must implement serializable and class (or superclass) must implement comparable.
     * @param <T3>   class type of third element where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple3 with 3 elements
     * @throws IllegalArgumentException if any argument is null
     */
    public static <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
    Tuple3<T1, T2, T3> of(T1 first, T2 second, T3 third) {

        if (first == null || second == null || third == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        return new Tuple3<>(first, second, third);
    }

    /**
     * Returns the first element of this tuple
     *
     * @return first
     */
    public T1 _1() {
        return first;
    }

    /**
     * Returns the second element of this tuple
     *
     * @return second
     */
    public T2 _2() {
        return second;
    }

    /**
     * Returns the third element of this tuple
     *
     * @return third
     */
    public T3 _3() {
        return third;
    }

    /**
     * Returns the first element of this tuple
     *
     * @return first
     */
    public T1 first() {
        return first;
    }

    /**
     * Returns the second element of this tuple
     *
     * @return second
     */
    public T2 second() {
        return second;
    }

    /**
     * Returns the third element of this tuple
     *
     * @return second
     */
    public T3 third() {
        return third;
    }

    /**
     * Applies specified function to the first element of this tuple object and returns
     * a new Tuple3 object with the result of the function as its first element, this.second as its second element and
     * this.third as its third element.
     *
     * @param function the function that should be applied on this objects first element
     * @param <R>      class type of the element that results from the function where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple3 with R first, T2 second and T3 third as values.
     * @throws IllegalArgumentException if result of the function (R) is null
     */
    public <R extends Comparable<? super R> & Serializable> Tuple3<R, T2, T3> map1(Function<T1, R> function) {

        R functionResult = checkForNullFunctionResult(function.apply(first));

        return Tuple3.of(functionResult, second, third);
    }

    /**
     * Applies the specified function to the second element of this tuple object and returns
     * a new Tuple3 object with this.first as its first element, the result of the function as its second element and
     * this.third as its third element.
     *
     * @param function the function that should be applied on this objects second element
     * @param <R>      class type of the element that results from the function where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple3 with T1 first, R second and T3 third as values.
     * @throws IllegalArgumentException if result of the function (R) is null
     */
    public <R extends Comparable<? super R> & Serializable> Tuple3<T1, R, T3> map2(Function<T2, R> function) {

        R functionResult = checkForNullFunctionResult(function.apply(second));

        return Tuple3.of(first, functionResult, third);
    }

    /**
     * Applies the specified function to the third element of this tuple object and returns
     * a new Tuple3 object with this.first as its first element, this.second as its second element and
     * the result of the function as its third element.
     *
     * @param function the function that should be applied on this objects third element
     * @param <R>      class type of the element that results from the function where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple3 with T1 first, T2 second and R third as values.
     * @throws IllegalArgumentException if result of the function (R) is null
     */
    public <R extends Comparable<? super R> & Serializable> Tuple3<T1, T2, R> map3(Function<T3, R> function) {

        R functionResult = checkForNullFunctionResult(function.apply(third));

        return Tuple3.of(first, second, functionResult);
    }


    /**
     * Applies the specified functions to this tuple objects elements and returns
     * a new Tuple3 object with the results of the functions its elements.
     *
     * @param function1 the function that should be applied on this objects first element
     * @param function2 the function that should be applied on this objects second element
     * @param function3 the function that should be applied on this objects third element
     * @param <R1>      class type of the element that results from function1 where class must implement serializable and class (or superclass) must implement comparable.
     * @param <R2>      class type of the element that results from function2 where class must implement serializable and class (or superclass) must implement comparable.
     * @param <R3>      class type of the element that results from function3 where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple3 with R1 first, R2 second and R3 third as values.
     * @throws IllegalArgumentException if result of any function (R1, R2 or R3) is null
     */
    public <R1 extends Comparable<? super R1> & Serializable, R2 extends Comparable<? super R2> & Serializable, R3 extends Comparable<? super R3> & Serializable>
    Tuple3<R1, R2, R3> mapAll(Function<T1, R1> function1, Function<T2, R2> function2, Function<T3, R3> function3) {

        R1 firstFunctionResult = checkForNullFunctionResult(function1.apply(first));
        R2 secondFunctionResult = checkForNullFunctionResult(function2.apply(second));
        R3 thirdFunctionResult = checkForNullFunctionResult(function3.apply(third));

        return Tuple3.of(firstFunctionResult, secondFunctionResult, thirdFunctionResult);
    }

    /**
     * Converts this tuple objects elements into an Object[] array.
     * The order of the elements in this object will transpose to the Object[] array order,
     * meaning that this.first will be at index 0 of the array, this.second will be at index 1,
     * and so forth if this object contains more elements.
     *
     * @return a new Object[] array containing the elements of this tuple object.
     */
    public Object[] toArray() {
        return new Object[]{first, second, third};
    }

    /**
     * Converts this tuple objects elements into an immutable List of type Object.
     * The order of the elements in this object will transpose to list order,
     * meaning that this.first will be at index 0 of the list, this.second will be at index 1,
     * and so forth if this object contains more elements.
     *
     * @return an immutable List of type Object containing the elements of this tuple object.
     */
    public List<Object> toList() {
        return List.of(first, second, third);
    }

    /**
     * Compares this object with the specified object for order.
     * The order of one Tuple compared with another Tuple is dependent on
     * their elements order where the first element is prioritized,
     * and the rest are sequentially following.
     * This means that element T1 first is initially compared between the Tuple objects and
     * only if they are equal will T2 second be compared, and so forth if the Tuple contains more elements.
     *
     * @param o the specified object to compare this object with.
     * @return a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     * @throws IllegalArgumentException if o is null
     */
    @Override
    public int compareTo(Tuple3<T1, T2, T3> o) {

        if (o == null) {
            throw new IllegalArgumentException("Cannot compare with null");
        }

        int isFirstElementsEqual = first.compareTo(o.first);

        if (isFirstElementsEqual != 0) {
            return isFirstElementsEqual;
        }

        int isSecondElementsEqual = second.compareTo(o.second);

        return isSecondElementsEqual != 0 ? isSecondElementsEqual : third.compareTo(o.third);
    }

    /**
     * Checks if this Tuple object is equal to the specified other object.
     * The objects are only equal if the order of their elements are the same.
     * This means that a tuple with elements (1, "two", 3.0) is not the same as one with elements (3.0, "two", 1).
     *
     * @param o other object to which this object will be compared with
     * @return true if o is equal to this or if o contains the same elements in the same order as this, else false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple3<?, ?, ?> tuple3)) return false;
        return Objects.equals(first, tuple3.first) && Objects.equals(second, tuple3.second) && Objects.equals(third, tuple3.third);
    }

    /**
     * @return the hash code for this tuple
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }

    /**
     * @return a string representation of this tuple
     */
    @Override
    public String toString() {
        return "Tuple3{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }

    /**
     * Checks if passed functionResult is null and throws a IllegalArgumentException if true.
     *
     * @param functionResult the result of the function
     * @param <R>            class type for the result of the function
     * @throws IllegalArgumentException if functionResult is null
     */
    private <R> R checkForNullFunctionResult(R functionResult) {
        if (functionResult == null) {
            throw new IllegalArgumentException("Function result cannot be null. Functions must return non-null values.");
        }
        return functionResult;
    }
}
