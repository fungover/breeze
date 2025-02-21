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

class Tuple4Test {

    @Nested
    class Initialization {

        @Test
        @DisplayName("Call to .of() method with any null argument should throw IllegalArgumentException")
        void callToOfGivenAnyNullArgumentShouldThrowIllegalArgumentException() {

            assertAll(
                    () -> assertThatThrownBy(() -> Tuple4.of(null, 1, 1, 1))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Argument cannot be null"),
                    () -> assertThatThrownBy(() -> Tuple4.of(1, null, 1, 1))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Argument cannot be null"),
                    () -> assertThatThrownBy(() -> Tuple4.of(1, 1, null, 1))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Argument cannot be null"),
                    () -> assertThatThrownBy(() -> Tuple4.of(1, 1, 1, null))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Argument cannot be null")
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to .of() method with valid arguments should return new Tuple4 object with passed arguments.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToOfGivenValidArgumentsShouldReturnNewTuple4WithPassedArguments(T1 o1, T2 o2, T3 o3, T4 o4) {

            assertAll(
                    () -> assertThat(Tuple4.of(o1, o2, o3, o4).first()).isEqualTo(o1),
                    () -> assertThat(Tuple4.of(o1, o2, o3, o4).second()).isEqualTo(o2),
                    () -> assertThat(Tuple4.of(o1, o2, o3, o4).third()).isEqualTo(o3),
                    () -> assertThat(Tuple4.of(o1, o2, o3, o4).fourth()).isEqualTo(o4)
            );
        }
    }

    @Nested
    class Elements {

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to first should return value of first.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToFirstShouldReturnFirstElement(T1 o1, T2 o2, T3 o3, T4 o4) {

            assertThat(Tuple4.of(o1, o2, o3, o4).first()).isEqualTo(o1);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to _1 should return value of first.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callTo_1ShouldReturnFirstElement(T1 o1, T2 o2, T3 o3, T4 o4) {

            assertThat(Tuple4.of(o1, o2, o3, o4)._1()).isEqualTo(o1);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to second should return value of second.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToSecondShouldReturnSecondElement(T1 o1, T2 o2, T3 o3, T4 o4) {

            assertThat(Tuple4.of(o1, o2, o3, o4).second()).isEqualTo(o2);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to _2 should return value of second.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callTo_2ShouldReturnSecondElement(T1 o1, T2 o2, T3 o3, T4 o4) {

            assertThat(Tuple4.of(o1, o2, o3, o4)._2()).isEqualTo(o2);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to third should return value of third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToThirdShouldReturnThirdElement(T1 o1, T2 o2, T3 o3, T4 o4) {

            assertThat(Tuple4.of(o1, o2, o3, o4).third()).isEqualTo(o3);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to _3 should return value of third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callTo_3ShouldReturnThirdElement(T1 o1, T2 o2, T3 o3, T4 o4) {

            assertThat(Tuple4.of(o1, o2, o3, o4)._3()).isEqualTo(o3);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to fourth should return value of fourth.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToFourthShouldReturnFourthElement(T1 o1, T2 o2, T3 o3, T4 o4) {

            assertThat(Tuple4.of(o1, o2, o3, o4).fourth()).isEqualTo(o4);
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to _4 should return value of fourth.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callTo_4ShouldReturnFourthElement(T1 o1, T2 o2, T3 o3, T4 o4) {

            assertThat(Tuple4.of(o1, o2, o3, o4)._4()).isEqualTo(o4);
        }
    }

    @Nested
    class Transformation {

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to map1 should apply function to first and return a new Tuple4 with the function-result as first, o2 as second, o3 as third and o4 as fourth.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToMap1ShouldReturnNewTuple4WithFunctionResultAsFirst(T1 o1, T2 o2, T3 o3, T4 o4) {

            var map1 = Tuple4.of(o1, o2, o3, o4).map1(e -> 1);

            assertAll(
                    () -> assertThat(map1.first()).isEqualTo(1),
                    () -> assertThat(map1.second()).isEqualTo(o2),
                    () -> assertThat(map1.third()).isEqualTo(o3),
                    () -> assertThat(map1.fourth()).isEqualTo(o4)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to map2 should apply function to second and return a new Tuple4 with o1 as first, the function-result as second, o3 as third and o4 as fourth.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToMap2ShouldReturnNewTuple4WithFunctionResultAsSecond(T1 o1, T2 o2, T3 o3, T4 o4) {

            var map2 = Tuple4.of(o1, o2, o3, o4).map2(e -> 1);

            assertAll(
                    () -> assertThat(map2.first()).isEqualTo(o1),
                    () -> assertThat(map2.second()).isEqualTo(1),
                    () -> assertThat(map2.third()).isEqualTo(o3),
                    () -> assertThat(map2.fourth()).isEqualTo(o4)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to map3 should apply function to third and return a new Tuple4 with o1 as first, o2 as second, the function-result as third and o4 as fourth.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToMap3ShouldReturnNewTuple4WithFunctionResultAsThird(T1 o1, T2 o2, T3 o3, T4 o4) {

            var map3 = Tuple4.of(o1, o2, o3, o4).map3(e -> 1);

            assertAll(
                    () -> assertThat(map3.first()).isEqualTo(o1),
                    () -> assertThat(map3.second()).isEqualTo(o2),
                    () -> assertThat(map3.third()).isEqualTo(1),
                    () -> assertThat(map3.fourth()).isEqualTo(o4)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to map4 should apply function to fourth and return a new Tuple4 with o1 as first, o2 as second, o3 as third and the function-result as fourth.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToMap4ShouldReturnNewTuple4WithFunctionResultAsFourth(T1 o1, T2 o2, T3 o3, T4 o4) {

            var map3 = Tuple4.of(o1, o2, o3, o4).map4(e -> 1);

            assertAll(
                    () -> assertThat(map3.first()).isEqualTo(o1),
                    () -> assertThat(map3.second()).isEqualTo(o2),
                    () -> assertThat(map3.third()).isEqualTo(o3),
                    () -> assertThat(map3.fourth()).isEqualTo(1)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to mapAll should apply specified functions to first, second, third and fourth and return a new Tuple4 with the functions-result as element values.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToMapAllShouldReturnNewTuple4WithFunctionsResultAsElementValues(T1 o1, T2 o2, T3 o3, T4 o4) {

            var mapAll = Tuple4.of(o1, o2, o3, o4).mapAll(e -> 1, e2 -> 1, e3 -> 1, e4 -> 1);

            assertAll(
                    () -> assertThat(mapAll.first()).isEqualTo(1),
                    () -> assertThat(mapAll.second()).isEqualTo(1),
                    () -> assertThat(mapAll.third()).isEqualTo(1),
                    () -> assertThat(mapAll.fourth()).isEqualTo(1)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to any map method should not change calling object")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToAnyMapMethodShouldNotChangeCallingObject(T1 o1, T2 o2, T3 o3, T4 o4) {

            var tuple = Tuple4.of(o1, o2, o3, o4);

            tuple.map1(e -> 1);
            tuple.map2(e -> 1);
            tuple.map3(e -> 1);
            tuple.map4(e -> 1);
            tuple.mapAll(e1 -> 1, e2 -> 1, e3 -> 1, e4 -> 1);

            assertThat(tuple).isEqualTo(Tuple4.of(o1, o2, o3, o4));
        }

        @Test
        @DisplayName("Call to all map methods with null function should trigger IllegalArgumentException")
        void callToAnyMapMethodWithNullFunctionShouldTriggerIllegalArgumentException() {

            var tuple = Tuple4.of("s", "s", "s", "s");

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
                    () -> assertThatThrownBy(() -> tuple.map4(e -> null))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Function result cannot be null. Functions must return non-null values."),
                    () -> assertThatThrownBy(() -> tuple.mapAll(e1 -> null, e2 -> null, e3 -> null, e4 -> null))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("Function result cannot be null. Functions must return non-null values.")
            );
        }

        @Test
        @DisplayName("Call to map1 with string function should change first element")
        void callToMap1WithStringFunctionShouldChangeFirstElement() {

            var tuple = Tuple4.of("A", "A", "A", "A");
            var map1 = tuple.map1(e -> e.concat("BC"));

            assertThat(map1.first()).isEqualTo("ABC");
        }


        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to toArray should return Object[] with Tuple4 object elements")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToToArrayShouldReturnObjectArrayWithTuple4Elements(T1 o1, T2 o2, T3 o3, T4 o4) {

            var tuple = Tuple4.of(o1, o2, o3, o4);
            var array = tuple.toArray();

            assertAll(
                    () -> assertThat(array).hasSize(4),
                    () -> assertThat(array[0]).isEqualTo(o1),
                    () -> assertThat(array[1]).isEqualTo(o2),
                    () -> assertThat(array[2]).isEqualTo(o3),
                    () -> assertThat(array[3]).isEqualTo(o4)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to toList should return List<Object> with Tuple4 object elements")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToToListShouldReturnListOfObjectWithTuple4Elements(T1 o1, T2 o2, T3 o3, T4 o4) {

            var tuple = Tuple4.of(o1, o2, o3, o4);
            var list = tuple.toList();

            assertAll(
                    () -> assertThat(list).hasSize(4),
                    () -> assertThat(list.getFirst()).isEqualTo(o1),
                    () -> assertThat(list.get(1)).isEqualTo(o2),
                    () -> assertThat(list.get(2)).isEqualTo(o3),
                    () -> assertThat(list.get(3)).isEqualTo(o4)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to dropFirst should return a new Tuple3 with this.second as first, this.third as second and this.fourth as third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToDropFirstShouldReturnATuple3(T1 o1, T2 o2, T3 o3, T4 o4) {

            var tuple4 = Tuple4.of(o1, o2, o3, o4);
            var tuple3 = tuple4.dropFirst();

            assertAll(
                    () -> assertThat(tuple3.first()).isEqualTo(o2),
                    () -> assertThat(tuple3.second()).isEqualTo(o3),
                    () -> assertThat(tuple3.third()).isEqualTo(o4)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to dropSecond should return a new Tuple3 with this.first as first, this.third as second and this.fourth as third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToDropSecondShouldReturnATuple3(T1 o1, T2 o2, T3 o3, T4 o4) {

            var tuple4 = Tuple4.of(o1, o2, o3, o4);
            var tuple3 = tuple4.dropSecond();

            assertAll(
                    () -> assertThat(tuple3.first()).isEqualTo(o1),
                    () -> assertThat(tuple3.second()).isEqualTo(o3),
                    () -> assertThat(tuple3.third()).isEqualTo(o4)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to dropThird should return a new Tuple3 with this.first as first, this.second as second and this.fourth as third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToDropThirdShouldReturnATuple3(T1 o1, T2 o2, T3 o3, T4 o4) {

            var tuple4 = Tuple4.of(o1, o2, o3, o4);
            var tuple3 = tuple4.dropThird();

            assertAll(
                    () -> assertThat(tuple3.first()).isEqualTo(o1),
                    () -> assertThat(tuple3.second()).isEqualTo(o2),
                    () -> assertThat(tuple3.third()).isEqualTo(o4)
            );
        }

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to dropFourth should return a new Tuple3 with this.first as first, this.second as second and this.third as third.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToDropFourthShouldReturnATuple3(T1 o1, T2 o2, T3 o3, T4 o4) {

            var tuple4 = Tuple4.of(o1, o2, o3, o4);
            var tuple3 = tuple4.dropFourth();

            assertAll(
                    () -> assertThat(tuple3.first()).isEqualTo(o1),
                    () -> assertThat(tuple3.second()).isEqualTo(o2),
                    () -> assertThat(tuple3.third()).isEqualTo(o3)
            );
        }
    }

    @Nested
    class ComparisonAndToString {

        @ParameterizedTest
        @MethodSource("org.fungover.breeze.control.Tuple4Test#validArgumentsForCreationOfTuple4")
        @DisplayName("Call to compareTo should return 0 for equal Tuple objects (contains the same elements).")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToCompareToShouldReturnZeroForEqual(T1 o1, T2 o2, T3 o3, T4 o4) {

            var tupleA = Tuple4.of(o1, o2, o3, o4);
            var tupleB = Tuple4.of(o1, o2, o3, o4);

            assertThat(tupleA.compareTo(tupleB)).isEqualByComparingTo(0);
        }

        @ParameterizedTest
        @CsvSource({
                "1, 1, 1, 1, 1, 1, 1, 2",
                "1, 1, 1, 1, 1, 1, 2, 1",
                "1, 1, 1, 1, 1, 2, 1, 1",
                "1, 1, 1, 1, 2, 1, 1, 1",
                "A, A, A, A, A, A, A, B",
                "A, A, A, A, A, A, B, A",
                "A, A, A, A, A, B, A, A",
                "A, A, A, A, B, A, A, A"
        })
        @DisplayName("Call to compareTo should return negative integer for a Tuple object that is lesser than passed object.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToCompareToShouldReturnNegativeIntegerForLesser(T1 m1, T2 m2, T3 m3, T4 m4, T1 l1, T2 l2, T3 l3, T4 l4) {

            var tupleA = Tuple4.of(m1, m2, m3, m4);
            var tupleB = Tuple4.of(l1, l2, l3, l4);

            assertThat(tupleA.compareTo(tupleB)).isNegative();
        }

        @ParameterizedTest
        @CsvSource({
                "1, 1, 1, 2, 1, 1, 1, 1",
                "1, 1, 2, 1, 1, 1, 1, 1",
                "1, 2, 1, 1, 1, 1, 1, 1",
                "2, 1, 1, 1, 1, 1, 1, 1",
                "A, A, A, B, A, A, A, A",
                "A, A, B, A, A, A, A, A",
                "A, B, A, A, A, A, A, A",
                "B, A, A, A, A, A, A, A"
        })
        @DisplayName("Call to compareTo should return positive integer for a Tuple object that is greater than passed object.")
        <T1 extends Comparable<? super T1> & Serializable, T2 extends Comparable<? super T2> & Serializable, T3 extends Comparable<? super T3> & Serializable, T4 extends Comparable<? super T4> & Serializable>
        void callToCompareToShouldReturnPositiveIntegerForGreater(T1 m1, T2 m2, T3 m3, T4 m4, T1 l1, T2 l2, T3 l3, T4 l4) {

            var tupleA = Tuple4.of(m1, m2, m3, m4);
            var tupleB = Tuple4.of(l1, l2, l3, l4);

            assertThat(tupleA.compareTo(tupleB)).isPositive();
        }

        @Test
        @DisplayName("Call to compareTo should throw IllegalArgumentException for null argument.")
        void callToCompareToShouldThrowIllegalArgumentExceptionForNullArgument() {

            var tupleA = Tuple4.of(1, 1, 1, 1);

            assertThatThrownBy(() -> tupleA.compareTo(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Cannot compare with null");
        }

        @Test
        @DisplayName("Call to equals should return false for null argument")
        void callToEqualsShouldReturnFalseForNullArgument() {

            var tuple = Tuple4.of(1, 1, 1, 1);

            assertThat(tuple.equals(null)).isFalse();
        }

        @Test
        @DisplayName("Call to equals should return true for comparison with itself")
        void callToEqualsShouldReturnTrueForComparisonWithItself() {

            var tuple = Tuple4.of(1, 2, 3, 4);

            assertThat(tuple.equals(tuple)).isTrue();
        }

        @Test
        @DisplayName("Call to hashCode with equal Tuple objects should return equal hashcodes")
        void callToHashCodeWithEqualObjectsShouldReturnEqualHashCode() {

            var tupleA = Tuple4.of(1, "two", 3.0, 4);
            var tupleB = Tuple4.of(1, "two", 3.0, 4);

            assertThat(tupleA.hashCode()).hasSameHashCodeAs(tupleB.hashCode());
        }

        @Test
        @DisplayName("Call to hashCode with unequal Tuple objects should return not equal hashcodes")
        void callToHashCodeWithUnequalObjectsShouldReturnNotEqualHashCode() {

            var tupleA = Tuple4.of(1, "two", 3.0, 4);
            var tupleB = Tuple4.of(4, "two", 3.0, 1);

            assertThat(tupleA.hashCode()).doesNotHaveSameHashCodeAs(tupleB.hashCode());
        }

        @Test
        @DisplayName("Call to toString should return specified string with elements")
        void callToToStringShouldReturnSpecifiedStringWithElements() {

            var tupleInteger = Tuple4.of(1, 2, 3, 4).toString();
            var tupleString = Tuple4.of("A", "B", "C", "D").toString();

            assertAll(
                    () -> assertThat(tupleInteger).isEqualTo("Tuple4{first=1, second=2, third=3, fourth=4}"),
                    () -> assertThat(tupleString).isEqualTo("Tuple4{first=A, second=B, third=C, fourth=D}")
            );

        }
    }

    static Stream<Arguments> validArgumentsForCreationOfTuple4() {

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
                Arguments.of(myClass, myClass, myClass, myClass),
                Arguments.of(1, 2, 3, 4),
                Arguments.of(1.0, 2.0, 3.0, 4.0),
                Arguments.of(1.0f, 2.0f, 3.0f, 4.0f),
                Arguments.of("s", "s", "s", "s"),
                Arguments.of(1, "s", 3.0, 's'),
                Arguments.of(1.0, 'c', 3.0f, myClass),
                Arguments.of("s", "s", mySubClass, 2),
                Arguments.of(2.0, myClass, 'M', 'M'),
                Arguments.of(mySubClass, myClass, 3, myClass),
                Arguments.of("Hello\n\t", "World\r\n", "!\n", "\n"),
                Arguments.of(Integer.MAX_VALUE, Integer.MIN_VALUE, 1, 2)
        );
    }
}
