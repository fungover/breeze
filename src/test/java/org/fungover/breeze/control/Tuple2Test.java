package org.fungover.breeze.control;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serializable;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class Tuple2Test {

    @Nested
    class Initialization {

        @Test
        @DisplayName("Call to .of() method with any null argument should throw NullPointerException")
        void callToOfGivenAnyNullArgumentShouldThrowNullPointerException() {

            assertAll (
                    () -> assertThatThrownBy(() -> Tuple2.of(null, 1))
                            .isInstanceOf(NullPointerException.class)
                            .hasMessage("Argument cannot be null"),
                    () -> assertThatThrownBy(() -> Tuple2.of(1, null))
                            .isInstanceOf(NullPointerException.class)
                            .hasMessage("Argument cannot be null")
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to .of() method with valid arguments should return new Tuple2 object with passed arguments.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToOfGivenValidArgumentsShouldReturnNewTuple2WithPassedArguments(T1 o1, T2 o2) {

            assertAll(
                    () -> assertThat(Tuple2.of(o1, o2).first()).isEqualTo(o1),
                    () -> assertThat(Tuple2.of(o1,o2).second()).isEqualTo(o2)
            );
        }
    }

    @Nested
    class Elements {

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to first should return value of first.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToFirstShouldReturnFirstElement(T1 o1, T2 o2) {
            assertThat(Tuple2.of(o1, o2).first()).isEqualTo(o1);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to _1 should return value of first.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callTo_1ShouldReturnFirstElement(T1 o1, T2 o2) {
            assertThat(Tuple2.of(o1, o2)._1()).isEqualTo(o1);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to second should return value of second.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToSecondShouldReturnFirstElement(T1 o1, T2 o2) {
            assertThat(Tuple2.of(o1, o2).second()).isEqualTo(o2);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to _2 should return value of second.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callTo_2ShouldReturnFirstElement(T1 o1, T2 o2) {
            assertThat(Tuple2.of(o1, o2)._2()).isEqualTo(o2);
        }
    }

    @Nested
    class Transformation {

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to swap should switch element order and return a new Tuple2")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToSwapShouldSwitchElementOrder(T1 o1, T2 o2) {

            var swap = Tuple2.of(o1, o2).swap();

            assertAll(
                    () -> assertThat(swap.first()).isEqualTo(o2),
                    () -> assertThat(swap.second()).isEqualTo(o1)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to map1 should apply function to first and return a new Tuple2 with the function-result as first and passed o2 as second.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToMap1ShouldReturnNewTuple2WithFunctionResultAsFirst(T1 o1, T2 o2) {

            var map1 = Tuple2.of(o1, o2).map1(e -> 1);

            assertAll(
                    () -> assertThat(map1.first()).isEqualTo(1),
                    () -> assertThat(map1.second()).isEqualTo(o2)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to map2 should apply function to second and return a new Tuple2 with the function-result as second and passed o1 as first.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToMap2ShouldReturnNewTuple2WithFunctionResultAsSecond(T1 o1, T2 o2) {

            var map2 = Tuple2.of(o1, o2).map2(e -> 1);

            assertAll(
                    () -> assertThat(map2.first()).isEqualTo(o1),
                    () -> assertThat(map2.second()).isEqualTo(1)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to mapAll should apply specified functions to first and second and return a new Tuple2 with the functions-result as element values.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToMapAllShouldReturnNewTuple2WithFunctionsResultAsElementValues(T1 o1, T2 o2) {

            var mapAll = Tuple2.of(o1, o2).mapAll(e -> 1, e2 -> 1);

            assertAll(
                    () -> assertThat(mapAll.first()).isEqualTo(1),
                    () -> assertThat(mapAll.second()).isEqualTo(1)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to any map method should not change calling object")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToAnyMapMethodShouldNotChangeCallingObject(T1 o1, T2 o2) {
            var tuple2 = Tuple2.of(o1, o2);
            var map1 = tuple2.map1(e -> 1);
            var map2 = tuple2.map2(e -> 1);
            var mapAll = tuple2.mapAll(e1 -> 1, e2 -> 1);

            assertThat(tuple2).isEqualTo(Tuple2.of(o1, o2));
        }

        @Test
        @DisplayName("Call to all map methods with null function should trigger NullPointerException")
        void callToAnyMapMethodWithNullFunctionShouldTriggerNullPointerException() {

            var tuple2 = Tuple2.of("s", "s");

            assertAll(
                    () -> assertThatThrownBy(() -> tuple2.mapAll(e1 -> null, e2 -> null))
                            .isInstanceOf(NullPointerException.class)
                            .hasMessage("Function result is null"),
                    () -> assertThatThrownBy(() -> tuple2.map1(e -> null))
                            .isInstanceOf(NullPointerException.class)
                            .hasMessage("Function result is null"),
                    () -> assertThatThrownBy(() -> tuple2.map2(e -> null))
                            .isInstanceOf(NullPointerException.class)
                            .hasMessage("Function result is null")
            );
        }

        @Test
        @DisplayName("Call to map1 with string function should change first element")
        void callToMap1WithStringFunctionShouldChangeFirstElement(){

            var tuple2 = Tuple2.of("A", "A");
            var map1 = tuple2.map1(e -> e.concat("BC"));

            assertThat(map1.first()).isEqualTo("ABC");
        }


        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to toArray should return Object[] with Tuple2 object elements")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToToArrayShouldReturnObjectArrayWithTuple2Elements(T1 o1, T2 o2) {
            var tuple2 = Tuple2.of(o1, o2);
            var array = tuple2.toArray();

            assertAll(
                    () -> assertThat(array).hasSize(2),
                    () -> assertThat(array[0]).isEqualTo(o1),
                    () -> assertThat(array[1]).isEqualTo(o2)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to toList should return List<Object> with Tuple2 object elements")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToToListShouldReturnListOfObjectWithTuple2Elements(T1 o1, T2 o2) {
            var tuple2 = Tuple2.of(o1, o2);
            var list = tuple2.toList();

            assertAll(
                    () -> assertThat(list).hasSize(2),
                    () -> assertThat(list.getFirst()).isEqualTo(o1),
                    () -> assertThat(list.get(1)).isEqualTo(o2)
            );
        }
    }

    @Nested
    class ComparisonAndToString {

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple2Test#validArgumentsForCreationOfTuple2")
        @DisplayName("Call to compareTo should return 0 for equal Tuple2 objects (contains the same elements).")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void callToCompareToShouldReturnZeroForEqual(T1 o1, T2 o2) {

            var tuple1 = Tuple2.of(o1, o2);
            var tuple2 = Tuple2.of(o1, o2);

            assertThat(tuple1.compareTo(tuple2)).isZero();
        }

        @ParameterizedTest
        @CsvSource({
                "1, 1, 1, 2",
                "1, 1, 2, 1",
                "A, A, A, B",
                "A, A, B, A"
        })
        @DisplayName("Call to compareTo should return negative integer for a Tuple2 object that is lesser than passed compare to object.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void  callToCompareToShouldReturnNegativeIntegerForLesser(T1 m1, T2 m2, T1 l1, T2 l2) {

            var tuple1 = Tuple2.of(m1, m2);
            var tuple2 = Tuple2.of(l1, l2);

            assertThat(tuple1.compareTo(tuple2)).isNegative();
        }

        @ParameterizedTest
        @CsvSource({
                "1, 2, 1, 1",
                "2, 1, 1, 1",
                "A, B, A, A",
                "B, A, A, A"
        })
        @DisplayName("Call to compareTo should return positive integer for a Tuple2 object that is greater than passed compare to object.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable> void  callToCompareToShouldReturnPositiveIntegerForGreater(T1 m1, T2 m2, T1 l1, T2 l2) {

            var tuple1 = Tuple2.of(m1, m2);
            var tuple2 = Tuple2.of(l1, l2);

            assertThat(tuple1.compareTo(tuple2)).isPositive();
        }

        @Test
        @DisplayName("Call to equals should return false for null argument")
        void callToEqualsShouldReturnFalseForNullArgument(){

            var tuple2 = Tuple2.of(1,2);

            assertThat(tuple2.equals(null)).isEqualTo(false);
        }

        @Test
        @DisplayName("Call to hashCode with equal Tuple2 objects should return equal hashcodes")
        void callToHashCodeWithEqualTuple2ObjectsShouldReturnEqualHashCode(){

            var firstTuple2 = Tuple2.of("A", 1);
            var secondTuple2 = Tuple2.of("A", 1);

            assertThat(firstTuple2.hashCode()).hasSameHashCodeAs(secondTuple2.hashCode());
        }

        @Test
        @DisplayName("Call to hashCode with unequal Tuple2 objects should return unequal hashcodes")
        void callToHashCodeWithUnequalTuple2ObjectsShouldReturnEqualHashCode(){

            var firstTuple2 = Tuple2.of("A", 1);
            var secondTuple2 = Tuple2.of("B", 2);

            assertThat(firstTuple2.hashCode()).doesNotHaveSameHashCodeAs(secondTuple2.hashCode());
        }

        @Test
        @DisplayName("Call to toString should return specified string with elements")
        void callToToStringShouldReturnSpecifiedStringWithElements(){

            var intTuple2 = Tuple2.of(1, 2).toString();
            var stringTuple2 = Tuple2.of("A", "B").toString();

            assertAll(
                    () -> assertThat(intTuple2).isEqualTo("Tuple2{first=1, second=2}"),
                    () -> assertThat(stringTuple2).isEqualTo("Tuple2{first=A, second=B}")
            );

        }
    }

    static Stream<Arguments> validArgumentsForCreationOfTuple2() {

        class MyClass implements Comparable<MyClass>, Serializable {

            /**
             * @param o the object to be compared.
             * @return
             */
            @Override
            public int compareTo(MyClass o) {
                return 0;
            }
        }

        class MySubClass extends MyClass {
            @Override public int compareTo(MyClass o) {
                if(o instanceof MySubClass) {
                    return 0;
                }
                return 1;
            }
        }

        var myClass = new MyClass();
        var mySubClass = new MySubClass();

        return Stream.of(
                Arguments.of(myClass, myClass),
                Arguments.of(1, 2),
                Arguments.of(1.0, 2.0),
                Arguments.of(1.0f, 2.0f),
                Arguments.of("s", "s"),
                Arguments.of(1, "s"),
                Arguments.of(1.0, 'c'),
                Arguments.of(myClass, "s"),
                Arguments.of(2.0, myClass),
                Arguments.of(mySubClass, myClass)
        );
    }
}
