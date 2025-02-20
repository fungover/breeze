package org.fungover.breeze.control;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Final class that defines a comparable Tuple with an element size of 4.
 * The class implements Comparable and Serializable.
 * A tuple is useful for storing objects of equal or different class types.
 * <p>
 * Initialize: Tuple4.of(1, "two", 3.0, 'f');
 * <p>
 * All fields are set as private and final.
 * Factory method of() to initialize new instance.
 * Methods that produce a change in any element returns a new instance.
 * Any call to methods in this class with a null object will result in a NullPointerException.
 *
 * @param <T1> class type of first element where class must implement serializable and class (or superclass) must implement comparable.
 * @param <T2> class type of second element where class must implement serializable and class (or superclass) must implement comparable.
 * @param <T3> class type of third element where class must implement serializable and class (or superclass) must implement comparable.
 * @param <T4> class type of fourth element where class must implement serializable and class (or superclass) must implement comparable.
 */
public final class Tuple4<T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable> implements Serializable, Comparable<Tuple4<T1, T2, T3, T4>> {

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
     * This value represents the fourth element of this object.
     */
    private final T4 fourth;

    /**
     * Private constructor that initialize a new Tuple4 object.
     *
     * @param first  first element of the tuple
     * @param second second element of the tuple
     * @param third  third element of the tuple
     * @param fourth fourth element of the tuple
     */
    private Tuple4(T1 first, T2 second, T3 third, T4 fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    /**
     * Constructs a Tuple4 with 4 elements.
     *
     * @param first  first not null element in the tuple
     * @param second second not null element in the tuple
     * @param third  third not null element in the tuple
     * @param fourth fourth not null element in the tuple
     * @param <T1>   class type of first element where class must implement serializable and class (or superclass) must implement comparable.
     * @param <T2>   class type of second element where class must implement serializable and class (or superclass) must implement comparable.
     * @param <T3>   class type of third element where class must implement serializable and class (or superclass) must implement comparable.
     * @param <T4>   class type of fourth element where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple4 with 4 elements
     * @throws IllegalArgumentException if any argument is null
     */
    public static <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
    Tuple4<T1, T2, T3, T4> of(T1 first, T2 second, T3 third, T4 fourth) {

        if (first == null || second == null || third == null || fourth == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        return new Tuple4<>(first, second, third, fourth);
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
     * Returns the fourth element of this tuple
     *
     * @return fourth
     */
    public T4 _4() {
        return fourth;
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
     * Returns the fourth element of this tuple
     *
     * @return second
     */
    public T4 fourth() {
        return fourth;
    }

    /**
     * Applies specified function to the first element of this tuple object and returns
     * a new Tuple4 object with the result of the function as its first element, this.second as its second element,
     * this.third as its third element and this.fourth as its fourth element.
     *
     * @param function the function that should be applied on this objects first element
     * @param <R>      class type of the element that results from the function where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple4 with R first, T2 second, T3 third and T4 fourth as values.
     * @throws IllegalArgumentException if result of the function (R) is null
     */
    public <R extends Comparable<? super R> & Serializable> Tuple4<R, T2, T3, T4> map1(Function<T1, R> function) {

        R functionResult = checkForNullFunctionResult(function.apply(first));

        return Tuple4.of(functionResult, second, third, fourth);
    }

    /**
     * Applies the specified function to the second element of this tuple object and returns
     * a new Tuple4 object with this.first as its first element, the result of the function as its second element,
     * this.third as its third element and this.fourth as its fourth element.
     *
     * @param function the function that should be applied on this objects second element
     * @param <R>      class type of the element that results from the function where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple4 with T1 first, R second, T3 third and T4 fourth as values.
     * @throws IllegalArgumentException if result of the function (R) is null
     */
    public <R extends Comparable<? super R> & Serializable> Tuple4<T1, R, T3, T4> map2(Function<T2, R> function) {

        R functionResult = checkForNullFunctionResult(function.apply(second));

        return Tuple4.of(first, functionResult, third, fourth);
    }

    /**
     * Applies the specified function to the third element of this tuple object and returns
     * a new Tuple4 object with this.first as its first element, this.second as its second element,
     * the result of the function as its third element and this.fourth as its fourth element.
     *
     * @param function the function that should be applied on this objects third element
     * @param <R>      class type of the element that results from the function where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple4 with T1 first, T2 second, R third and T4 fourth as values.
     * @throws IllegalArgumentException if result of the function (R) is null
     */
    public <R extends Comparable<? super R> & Serializable> Tuple4<T1, T2, R, T4> map3(Function<T3, R> function) {

        R functionResult = checkForNullFunctionResult(function.apply(third));

        return Tuple4.of(first, second, functionResult, fourth);
    }

    /**
     * Applies the specified function to the fourth element of this tuple object and returns
     * a new Tuple4 object with this.first as its first element, this.second as its second element,
     * this.third as its third element and the result of the function as its fourth element.
     *
     * @param function the function that should be applied on this objects fourth element
     * @param <R>      class type of the element that results from the function where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple4 with T1 first, T2 second, T3 third and R fourth as values.
     * @throws IllegalArgumentException if result of the function (R) is null
     */
    public <R extends Comparable<? super R> & Serializable> Tuple4<T1, T2, T3, R> map4(Function<T4, R> function) {

        R functionResult = checkForNullFunctionResult(function.apply(fourth));

        return Tuple4.of(first, second, third, functionResult);
    }


    /**
     * Applies the specified functions to this tuple objects elements and returns
     * a new Tuple4 object with the results of the functions its elements.
     *
     * @param function1 the function that should be applied on this objects first element
     * @param function2 the function that should be applied on this objects second element
     * @param function3 the function that should be applied on this objects third element
     * @param function4 the function that should be applied on this objects fourth element
     * @param <R1>      class type of the element that results from function1 where class must implement serializable and class (or superclass) must implement comparable.
     * @param <R2>      class type of the element that results from function2 where class must implement serializable and class (or superclass) must implement comparable.
     * @param <R3>      class type of the element that results from function3 where class must implement serializable and class (or superclass) must implement comparable.
     * @param <R4>      class type of the element that results from function4 where class must implement serializable and class (or superclass) must implement comparable.
     * @return a new Tuple4 with R1 first, R2 second, R3 third and R4 fourth as values.
     * @throws IllegalArgumentException if result of any function (R1, R2, R3 or R4) is null
     */
    public <R1 extends Comparable<? super R1> & Serializable, R2 extends Comparable<? super R2> & Serializable, R3 extends Comparable<? super R3> & Serializable, R4 extends Comparable<? super R4> & Serializable>
    Tuple4<R1, R2, R3, R4> mapAll(Function<T1, R1> function1, Function<T2, R2> function2, Function<T3, R3> function3, Function<T4, R4> function4) {

        R1 firstFunctionResult = checkForNullFunctionResult(function1.apply(first));
        R2 secondFunctionResult = checkForNullFunctionResult(function2.apply(second));
        R3 thirdFunctionResult = checkForNullFunctionResult(function3.apply(third));
        R4 fourthFunctionResult = checkForNullFunctionResult(function4.apply(fourth));

        return Tuple4.of(firstFunctionResult, secondFunctionResult, thirdFunctionResult, fourthFunctionResult);
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
        return new Object[]{first, second, third, fourth};
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
        return List.of(first, second, third, fourth);
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
    public int compareTo(Tuple4<T1, T2, T3, T4> o) {

        if (o == null) {
            throw new IllegalArgumentException("Cannot compare with null");
        }

        int isFirstElementsEqual = first.compareTo(o.first);

        if (isFirstElementsEqual != 0) {
            return isFirstElementsEqual;
        }

        int isSecondElementsEqual = second.compareTo(o.second);

        if (isSecondElementsEqual != 0) {
            return isSecondElementsEqual;
        }

        int isThirdElementsEqual = third.compareTo(o.third);

        return isThirdElementsEqual != 0 ? isThirdElementsEqual : fourth.compareTo(o.fourth);
    }

    /**
     * Checks if this Tuple object is equal to the specified other object.
     * The objects are only equal if the order of their elements are the same.
     * This means that a tuple with elements (1, "two", 3.0, 4) is not the same as one with elements (4, "two", 3.0, 1).
     *
     * @param o other object to which this object will be compared with
     * @return true if o is equal to this or if o contains the same elements in the same order as this, else false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple4<?, ?, ?, ?> tuple4)) return false;
        return Objects.equals(first, tuple4.first) && Objects.equals(second, tuple4.second) && Objects.equals(third, tuple4.third) && Objects.equals(fourth, tuple4.fourth);
    }

    /**
     * @return the hash code for this tuple
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second, third, fourth);
    }

    /**
     * @return a string representation of this tuple
     */
    @Override
    public String toString() {
        return "Tuple4{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                ", fourth=" + fourth +
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
