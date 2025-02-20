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

class Tuple3Test {

    @Nested
    class Initialization {

        @Test
        @DisplayName("Call to .of() method with any null argument should throw IllegalArgumentException")
        void callToOfGivenAnyNullArgumentShouldThrowIllegalArgumentException() {

            assertAll(
                    () -> assertThatThrownBy(() -> Tuple3.of(null, 1, 1))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Argument cannot be null"),
                    () -> assertThatThrownBy(() -> Tuple3.of(1, null, 1))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Argument cannot be null"),
                    () -> assertThatThrownBy(() -> Tuple3.of(1, 1, null))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Argument cannot be null")
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to .of() method with valid arguments should return new Tuple3 object with passed arguments.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToOfGivenValidArgumentsShouldReturnNewTuple3WithPassedArguments(T1 o1, T2 o2, T3 o3) {

            assertAll(
                    () -> assertThat(Tuple3.of(o1, o2, o3).first()).isEqualTo(o1),
                    () -> assertThat(Tuple3.of(o1, o2, o3).second()).isEqualTo(o2),
                    () -> assertThat(Tuple3.of(o1, o2, o3).third()).isEqualTo(o3)
            );
        }
    }

    @Nested
    class Elements {

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to first should return value of first.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToFirstShouldReturnFirstElement(T1 o1, T2 o2, T3 o3) {

            assertThat(Tuple3.of(o1, o2, o3).first()).isEqualTo(o1);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to _1 should return value of first.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callTo_1ShouldReturnFirstElement(T1 o1, T2 o2, T3 o3) {

            assertThat(Tuple3.of(o1, o2, o3)._1()).isEqualTo(o1);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to second should return value of second.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToSecondShouldReturnSecondElement(T1 o1, T2 o2, T3 o3) {

            assertThat(Tuple3.of(o1, o2, o3).second()).isEqualTo(o2);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to _2 should return value of second.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callTo_2ShouldReturnSecondElement(T1 o1, T2 o2, T3 o3) {

            assertThat(Tuple3.of(o1, o2, o3)._2()).isEqualTo(o2);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to third should return value of third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToThirdShouldReturnThirdElement(T1 o1, T2 o2, T3 o3) {

            assertThat(Tuple3.of(o1, o2, o3).third()).isEqualTo(o3);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to _3 should return value of third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callTo_3ShouldReturnThirdElement(T1 o1, T2 o2, T3 o3) {

            assertThat(Tuple3.of(o1, o2, o3)._3()).isEqualTo(o3);
        }
    }

    @Nested
    class Transformation {

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to map1 should apply function to first and return a new Tuple3 with the function-result as first, o2 as second and o3 as third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToMap1ShouldReturnNewTuple3WithFunctionResultAsFirst(T1 o1, T2 o2, T3 o3) {

            var map1 = Tuple3.of(o1, o2, o3).map1(e -> 1);

            assertAll(
                    () -> assertThat(map1.first()).isEqualTo(1),
                    () -> assertThat(map1.second()).isEqualTo(o2),
                    () -> assertThat(map1.third()).isEqualTo(o3)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to map2 should apply function to second and return a new Tuple3 with o1 as first, the function-result as second and o3 as third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToMap2ShouldReturnNewTuple3WithFunctionResultAsSecond(T1 o1, T2 o2, T3 o3) {

            var map2 = Tuple3.of(o1, o2, o3).map2(e -> 1);

            assertAll(
                    () -> assertThat(map2.first()).isEqualTo(o1),
                    () -> assertThat(map2.second()).isEqualTo(1),
                    () -> assertThat(map2.third()).isEqualTo(o3)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to map3 should apply function to third and return a new Tuple3 with o1 as first, o2 as second and the function-result as third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToMap3ShouldReturnNewTuple3WithFunctionResultAsThird(T1 o1, T2 o2, T3 o3) {

            var map3 = Tuple3.of(o1, o2, o3).map3(e -> 1);

            assertAll(
                    () -> assertThat(map3.first()).isEqualTo(o1),
                    () -> assertThat(map3.second()).isEqualTo(o2),
                    () -> assertThat(map3.third()).isEqualTo(1)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to mapAll should apply specified functions to first, second and third and return a new Tuple3 with the functions-result as element values.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToMapAllShouldReturnNewTuple3WithFunctionsResultAsElementValues(T1 o1, T2 o2, T3 o3) {

            var mapAll = Tuple3.of(o1, o2, o3).mapAll(e -> 1, e2 -> 1, e3 -> 1);

            assertAll(
                    () -> assertThat(mapAll.first()).isEqualTo(1),
                    () -> assertThat(mapAll.second()).isEqualTo(1),
                    () -> assertThat(mapAll.third()).isEqualTo(1)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to any map method should not change calling object")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToAnyMapMethodShouldNotChangeCallingObject(T1 o1, T2 o2, T3 o3) {

            var tuple = Tuple3.of(o1, o2, o3);

            tuple.map1(e -> 1);
            tuple.map2(e -> 1);
            tuple.map3(e -> 1);
            tuple.mapAll(e1 -> 1, e2 -> 1, e3 -> 1);

            assertThat(tuple).isEqualTo(Tuple3.of(o1, o2, o3));
        }

        @Test
        @DisplayName("Call to all map methods with null function should trigger IllegalArgumentException")
        void callToAnyMapMethodWithNullFunctionShouldTriggerIllegalArgumentException() {

            var tuple = Tuple3.of("s", "s", "s");

            assertAll(
                    () -> assertThatThrownBy(() -> tuple.map1(e -> null))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Function result cannot be null. Functions must return non-null values."),
                    () -> assertThatThrownBy(() -> tuple.map2(e -> null))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Function result cannot be null. Functions must return non-null values."),
                    () -> assertThatThrownBy(() -> tuple.map3(e -> null))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Function result cannot be null. Functions must return non-null values."),
                    () -> assertThatThrownBy(() -> tuple.mapAll(e1 -> null, e2 -> null, e3 -> null))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Function result cannot be null. Functions must return non-null values.")
            );
        }

        @Test
        @DisplayName("Call to map1 with string function should change first element")
        void callToMap1WithStringFunctionShouldChangeFirstElement() {

            var tuple = Tuple3.of("A", "A", "A");
            var map1 = tuple.map1(e -> e.concat("BC"));

            assertThat(map1.first()).isEqualTo("ABC");
        }


        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to toArray should return Object[] with Tuple3 object elements")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToToArrayShouldReturnObjectArrayWithTuple3Elements(T1 o1, T2 o2, T3 o3) {

            var tuple = Tuple3.of(o1, o2, o3);
            var array = tuple.toArray();

            assertAll(
                    () -> assertThat(array).hasSize(3),
                    () -> assertThat(array[0]).isEqualTo(o1),
                    () -> assertThat(array[1]).isEqualTo(o2),
                    () -> assertThat(array[2]).isEqualTo(o3)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to toList should return List<Object> with Tuple3 object elements")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToToListShouldReturnListOfObjectWithTuple3Elements(T1 o1, T2 o2, T3 o3) {

            var tuple = Tuple3.of(o1, o2, o3);
            var list = tuple.toList();

            assertAll(
                    () -> assertThat(list).hasSize(3),
                    () -> assertThat(list.getFirst()).isEqualTo(o1),
                    () -> assertThat(list.get(1)).isEqualTo(o2),
                    () -> assertThat(list.get(2)).isEqualTo(o3)
            );
        }
    }

    @Nested
    class ComparisonAndToString {

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple3Test#validArgumentsForCreationOfTuple3")
        @DisplayName("Call to compareTo should return 0 for equal Tuple objects (contains the same elements).")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToCompareToShouldReturnZeroForEqual(T1 o1, T2 o2, T3 o3) {

            var tupleA = Tuple3.of(o1, o2, o3);
            var tupleB = Tuple3.of(o1, o2, o3);

            assertThat(tupleA.compareTo(tupleB)).isEqualByComparingTo(0);
        }

        @ParameterizedTest
        @CsvSource({
                "1, 1, 1, 1, 1, 2",
                "1, 1, 1, 1, 2, 1",
                "1, 1, 1, 2, 1, 1",
                "A, A, A, A, A, B",
                "A, A, A, A, B, A",
                "A, A, A, B, A, A"
        })
        @DisplayName("Call to compareTo should return negative integer for a Tuple object that is lesser than passed object.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToCompareToShouldReturnNegativeIntegerForLesser(T1 m1, T2 m2, T3 m3, T1 l1, T2 l2, T3 l3) {

            var tupleA = Tuple3.of(m1, m2, m3);
            var tupleB = Tuple3.of(l1, l2, l3);

            assertThat(tupleA.compareTo(tupleB)).isNegative();
        }

        @ParameterizedTest
        @CsvSource({
                "1, 1, 2, 1, 1, 1",
                "1, 2, 1, 1, 1, 1",
                "2, 1, 1, 1, 1, 1",
                "A, A, B, A, A, A",
                "A, B, A, A, A, A",
                "B, A, A, A, A, A"
        })
        @DisplayName("Call to compareTo should return positive integer for a Tuple object that is greater than passed object.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable>
        void callToCompareToShouldReturnPositiveIntegerForGreater(T1 m1, T2 m2, T3 m3, T1 l1, T2 l2, T3 l3) {

            var tupleA = Tuple3.of(m1, m2, m3);
            var tupleB = Tuple3.of(l1, l2, l3);

            assertThat(tupleA.compareTo(tupleB)).isPositive();
        }

        @Test
        @DisplayName("Call to compareTo should throw IllegalArgumentException for null argument.")
        void callToCompareToShouldThrowIllegalArgumentExceptionForNullArgument() {

            var tupleA = Tuple3.of(1, 1, 1);

            assertThatThrownBy(() -> tupleA.compareTo(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Cannot compare with null");
        }

        @Test
        @DisplayName("Call to equals should return false for null argument")
        void callToEqualsShouldReturnFalseForNullArgument() {

            var tuple = Tuple3.of(1, 2, 3);

            assertThat(tuple.equals(null)).isFalse();
        }

        @Test
        @DisplayName("Call to hashCode with equal Tuple objects should return equal hashcodes")
        void callToHashCodeWithEqualObjectsShouldReturnEqualHashCode() {

            var tupleA = Tuple3.of(1, "two", 3);
            var tupleB = Tuple3.of(1, "two", 3);

            assertThat(tupleA.hashCode()).hasSameHashCodeAs(tupleB.hashCode());
        }

        @Test
        @DisplayName("Call to hashCode with unequal Tuple objects should return not equal hashcodes")
        void callToHashCodeWithUnequalObjectsShouldReturnNotEqualHashCode() {

            var tupleA = Tuple3.of(1, "two", 3);
            var tupleB = Tuple3.of(3, "two", 1);

            assertThat(tupleA.hashCode()).doesNotHaveSameHashCodeAs(tupleB.hashCode());
        }

        @Test
        @DisplayName("Call to toString should return specified string with elements")
        void callToToStringShouldReturnSpecifiedStringWithElements() {

            var tupleInteger = Tuple3.of(1, 2, 3).toString();
            var tupleString = Tuple3.of("A", "B", "C").toString();

            assertAll(
                    () -> assertThat(tupleInteger).isEqualTo("Tuple3{first=1, second=2, third=3}"),
                    () -> assertThat(tupleString).isEqualTo("Tuple3{first=A, second=B, third=C}")
            );

        }
    }

    static Stream<Arguments> validArgumentsForCreationOfTuple3() {

        class MyClass implements Comparable<MyClass>, Serializable {

            /**
             * Only for test.
             * @param o the object to be compared.
             * @return 0 as all objects should be considered equal
             */
            @Override
            public int compareTo(MyClass o) {
                return 0;
            }
        }

        class MySubClass extends MyClass {
            /**
             * Only for test.
             * @param o the object to be compared.
             * @return 0 if instance of MySubClass, else 1.
             */
            @Override
            public int compareTo(MyClass o) {
                if (o instanceof MySubClass) {
                    return 0;
                }
                return 1;
            }
        }

        var myClass = new MyClass();
        var mySubClass = new MySubClass();

        return Stream.of(
                Arguments.of(myClass, myClass, myClass),
                Arguments.of(1, 2, 3),
                Arguments.of(1.0, 2.0, 3.0),
                Arguments.of(1.0f, 2.0f, 3.0f),
                Arguments.of("s", "s", "s"),
                Arguments.of(1, "s", 3.0),
                Arguments.of(1.0, 'c', 3.0f),
                Arguments.of(myClass, "s", mySubClass),
                Arguments.of(2.0, myClass, 'M'),
                Arguments.of(mySubClass, myClass, 3),
                Arguments.of("Hello\n\t", "World\r\n", "!\n"),
                Arguments.of(Integer.MAX_VALUE, Integer.MIN_VALUE, 1)
        );
    }
}
