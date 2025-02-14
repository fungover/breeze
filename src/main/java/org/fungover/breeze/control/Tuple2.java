package org.fungover.breeze.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Final class that defines a comparable Tuple with an element size of 2.
 * A tuple is useful for storing objects of equal or different class types.
 *
 * Initialize:
 *                  Tuple2<Integer, String> tuple2 = Tuple2.of(1,"A");
 * Use:
 *                  Integer firstElement = tuple2.first();       // alt. tuple2._1()
 *                  String secondElement = tuple2.second();      // alt. tuple2._2()
 *                  Tuple2<String, Integer> swapped = tuple2.swap();
 *                  Tuple2<Integer, String> map1 = tuple2.map1(firstElement -> firstElement + 10);
 *                  Tuple2<Integer, String> map2 = tuple2.map2(secondElement -> secondElement.concat("BC");
 *                  Tuple2<Integer, String> mapAll = tuple2.mapAll( (firstElement -> firstElement + 10), (secondElement -> secondElement.concat("BC")) );
 *                  Object[] toArray = tuple2.toArray();
 *                  List<Object> toList = tuple2.toList();
 *
 * All fields are set as private and final.
 * Factory method of() to initialize new instance.
 * Methods that produce a change in any element returns a new instance.
 * Any call to methods in this class with a null object will result in a NullPointerException.
 * @param <T1> class type of first element where class must implement serializable and class (or superclass) must implement comparable.
 * @param <T2> class type of second element where class must implement serializable and class (or superclass) must implement comparable.
 */
public final class Tuple2<T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> implements Serializable, Comparable<Tuple2<T1, T2>> {

    /**
     * This value represents the first element of this object.
     */
    private final T1 first;

    /**
     * This value represents the second element of this object.
     */
    private final T2 second;

    /**
     * Private constructor that initialize a new Tuple2 object.
     * @param first first element of the tuple
     * @param second second element of the tuple
     */
    private Tuple2(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Constructs a Tuple2 with two elements.
     * @param first first not null element in the tuple
     * @param second second not null element in the tuple
     * @return a new Tuple2 with two elements
     * @param <T1> class type of first element (class or super must implement comparable)
     * @param <T2> class type of second element (class or super must implement comparable)
     * @throws NullPointerException if any argument is null
     */
    public static <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> Tuple2<T1, T2> of(T1 first, T2 second) throws IllegalArgumentException {
        if(first == null || second == null) {
            throw new NullPointerException("Argument cannot be null");
        }
        return new Tuple2<>(first, second);
    }

    /**
     * @return the first element in this tuple
     */
    public T1 _1() {
        return first;
    }

    /**
     * @return the second element in this tuple
     */
    public T2 _2() {
        return second;
    }

    /**
     * @return the first element in this tuple
     */
    public T1 first(){
        return first;
    }

    /**
     * @return the second element in this tuple
     */
    public T2 second(){
        return second;
    }

    /**
     * Swaps the elements of this tuple.
     * @return a new Tuple2
     */
    public Tuple2<T2, T1> swap() {
        return Tuple2.of(second, first);
    }

    /**
     * Applies specified function to the first element of this Tuple2 object and returns
     * a new Tuple2 object with the result of the function as its first element and this.second as its second element.
     * @param function the function that should be applied on this objects first element
     * @return a new Tuple2 with R first and T2 second as values.
     * @param <R> class type of the element that results from the function (class or super must implement Comparable)
     * @throws NullPointerException if result of the function (R) is null
     */
    public <R extends Comparable<? super R> & Serializable> Tuple2<R,T2> map1(Function<T1, R> function) {

        R functionResult = checkForNullFunctionResult(function.apply(first));

        return Tuple2.of(functionResult, second);
    }

    /**
     * Applies the specified function to the second element of this Tuple2 object and returns
     * a new Tuple2 object with this.first as its first element and the result of the function as its second element.
     * @param function the function that should be applied on this objects second element
     * @return a new Tuple2 with T1 first and R second as values.
     * @param <R> class type of the element that results from the function (class or super must implement Comparable)
     * @throws NullPointerException if result of the function (R) is null
     */
    public <R extends Comparable<? super R> & Serializable> Tuple2<T1,R> map2(Function<T2, R> function) {

        R functionResult = checkForNullFunctionResult(function.apply(second));

        return Tuple2.of(first, functionResult);
    }


    /**
     * Applies the specified functions to this Tuple2 objects elements and returns
     * a new Tuple2 object with the results of the functions its elements.
     * @param function1 the function that should be applied on this objects first element
     * @param function2 the function that should be applied on this objects second element
     * @return a new Tuple2 with R1 first and R2 second as values.
     * @param <R1> class type of the element that results from function1 (class or super must implement Comparable)
     * @param <R2> class type of the element that results from function2 (class or super must implement Comparable)
     * @throws NullPointerException if result of any function (R1 or R2) is null
     */
    public <R1 extends Comparable<? super R1> & Serializable, R2 extends Comparable<? super R2> & Serializable> Tuple2<R1,R2> mapAll(Function<T1, R1> function1, Function<T2, R2> function2) {

        R1 firstFunctionResult = checkForNullFunctionResult(function1.apply(first));
        R2 secondFunctionResult = checkForNullFunctionResult(function2.apply(second));

        return Tuple2.of(firstFunctionResult, secondFunctionResult);
    }

    /**
     * Converts this Tuple2 object into an Object[] array.
     * The order of the elements in this object will transpose to the Object[] array order meaning that
     * this.first will be at index 0 of the array, this.second will be at index 1, and so forth if this object contains more elements.
     * @return a new Object[] array containing the elements of this Tuple2 object.
     */
    public Object[] toArray(){
        return new Object[]{first, second};
    }

    /**
     * Converts this Tuple2 object into a ArrayList of type Object.
     * The order of the elements in this object will transpose to list order meaning that
     * this.first will be at index 0 of the list, this.second will be at index 1, and so forth if this object contains more elements.
     * @return a new ArrayList containing the elements of this Tuple2 object.
     */
    public List<Object> toList(){
        return new ArrayList<>(Arrays.asList(first, second));
    }

    /**
     * Compares this object with the specified object for order.
     * The order of one Tuple compared with another Tuple is dependent on
     * their elements order where the first element is prioritized,
     * and the rest are sequentially following.
     * This means that element T1 first is initially compared between the Tuple objects and
     * only if they are equal will T2 second be compared (and so forth if the Tuple contains more elements).
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Tuple2<T1, T2> o){
        int isFirstEqual = first.compareTo(o.first);
        return isFirstEqual == 0 ? this.second.compareTo(o.second) : isFirstEqual;
    }

    /**
     * Checks if this Tuple2 object is equal to the other object.
     * The objects are only equal if the order of their elements are the same.
     * This means that a tuple with arguments (1, "one") is not the same as one with arguments ("one",1).
     * @param o other object to which this object will be compared with
     * @return true if o is equal to this or if o contains the same elements in the same order as this, else false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple2<?, ?> tuple2)) return false;
        return Objects.equals(first, tuple2.first) && Objects.equals(second, tuple2.second);
    }


    /**
     * @return the hash code for this tuple
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    /**
     * @return a string representation of this tuple
     */
    @Override
    public String toString() {
        return "Tuple2{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    /**
     * Checks if passed functionResult is null and throws a NullPointerException if true.
     * @param functionResult the result of the function
     * @param <R> class type for the result of the function
     * @throws NullPointerException if functionResult is null
     */
    private static <R> R checkForNullFunctionResult(R functionResult) {
        if(functionResult == null) {
            throw new NullPointerException("Function result is null");
        }
        return functionResult;
    }
}
