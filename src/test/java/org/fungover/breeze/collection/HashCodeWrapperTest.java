package org.fungover.breeze.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HashCodeWrapperTest {
    Node.HashCodeWrapper<Integer> smallValueNode;
    Node.HashCodeWrapper<Integer> bigValueNode;
    Node.HashCodeWrapper<Integer> nullValueNode;
    Node.HashCodeWrapper<Integer> nullValueNode2;

    @Nested
    class HashCodeWrapperNodeAllocationWithValueTests {
        @BeforeEach
        void setUp() {

            smallValueNode = new Node.HashCodeWrapper<>(10);
            bigValueNode = new Node.HashCodeWrapper<>(20);

        }


        @Test
        @DisplayName("Node has predictable value Test")
        void nodeHasPredictableValueTest() {
            var expectedValue = 10;
            assertThat(smallValueNode.getValue()).isEqualTo(expectedValue);

        }

        @Test
        @DisplayName("Small node value is always negative compared to greater value test")
        void smallNodeValueIsAlwaysNegativeComparedToGreaterValueTest() {
            var intValue = smallValueNode.compareTo(bigValueNode);
            assertThat(intValue).isNegative();
        }

        @Test
        @DisplayName("Big node value is always positive compared to small value test")
        void bigNodeValueIsAlwaysPositiveComparedToSmallValueTest() {
            var intValue = bigValueNode.compareTo(smallValueNode);
            assertThat(intValue).isPositive();

        }

        @Test
        @DisplayName("Node has predictable hash value test")
        void nodeHasPredictableHashValueTest() {
            var expectedValue = 20;
            assertThat(bigValueNode.hashCode()).hasSameHashCodeAs(expectedValue);

        }


        @Test
        @DisplayName("Node with the same value returns is zero when node are compared Test")
        void nodeWithTheSameValueReturnsIsZeroWhenNodeAreComparedTest() {
            Node.HashCodeWrapper<Integer> small = new Node.HashCodeWrapper<>(10);
            var intValue = smallValueNode.compareTo(small);
            assertThat(intValue).isZero();

        }


        @Test
        @DisplayName("Nodes with the same values are equal return is always True Test")
        void nodesWithTheSameValuesAreEqualReturnIsAlwaysTrueTest() {
            var expectedValue = new Node.HashCodeWrapper<>(10);
            var actualBoolean = smallValueNode.equals(expectedValue);
            var expectedValue2 = new Node.HashCodeWrapper<>(20);

            var actualBoolean2 = bigValueNode.equals(expectedValue2);
            assertThat(actualBoolean).isTrue();
            assertThat(actualBoolean2).isTrue();

        }


        @Test
        @DisplayName("Nodes with different values is False Test")
        void nodesWithDifferentValuesIsFalseTest() {
            var otherValue = new Node.HashCodeWrapper<>(20);
            var isFalseBoolean = smallValueNode.equals(otherValue);
            assertThat(isFalseBoolean).isFalse();

        }

        @Test
        @DisplayName("Node comparison against other datatypes is always false Test")
        void nodeComparisonAgainstOtherDatatypesIsAlwaysFalseTest() {
            var otherDataType =  Integer.parseInt("10");
            var isFalseBoolean = smallValueNode.equals(otherDataType);
            var isFalseBoolean2 = bigValueNode.equals(10);
            assertThat(isFalseBoolean).isFalse();
            assertThat(isFalseBoolean2).isFalse();

        }

        @Test
        @DisplayName("Node comparison between null and node values is False Test")
        void nodeComparisonBetweenNullAndNodeValuesIsFalseTest() {
            var nullLocalValueNode = new Node.HashCodeWrapper<>(null);
            var isFalseBoolean = smallValueNode.equals(nullLocalValueNode);
            var isFalseBoolean2 = bigValueNode == null;
            assertThat(isFalseBoolean).isFalse();
            assertThat(isFalseBoolean2).isFalse();

        }


    }

    @Nested
    class HashCodeWrapperNodeAllocationWithNullValueTests {

        @BeforeEach
        void setUp() {
            nullValueNode = new Node.HashCodeWrapper<>(null);
            nullValueNode2 = new Node.HashCodeWrapper<>(null);
            smallValueNode = new Node.HashCodeWrapper<>(10);
        }

        @Test
        @DisplayName("Comparison of 2 null node values is Zero test")
        void comparisonOf2NullNodeValuesIsZeroTest() {
            var actual  = nullValueNode2.compareTo(nullValueNode);
            assertThat(actual).isZero();

        }

        @Test
        @DisplayName("Comparison between null node value and node value is always negative test")
        void comparisonBetweenNullNodeValueAndNodeValueIsAlwaysNegativeTest() {
            var actual  = nullValueNode.compareTo(smallValueNode);
            assertThat(actual).isNegative();

        }

        @Test
        @DisplayName("Comparison between value and null is always positive test")
        void comparisonBetweenValueAndNullIsAlwaysPositiveTest() {
            var actual = smallValueNode.compareTo(nullValueNode);
            assertThat(actual).isPositive();

        }

        @Test
        @DisplayName("Nodes with null values is True test")
        void nodesWithNullValuesIsTrueTest() {
            var expectedNullNode = new Node.HashCodeWrapper<>(null);
            var isBoolean2 = nullValueNode.equals(expectedNullNode);
            assertThat(isBoolean2).isTrue();

        }

        @Test
        @DisplayName("Comparison between null and non-null value returns false")
        void comparisonBetweenNullAndNonNullValueReturnsFalseTest() {
            var actual = smallValueNode.equals(nullValueNode);
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("Comparing two null wrapped nodes returns true")
        void comparingTwoNullWrappedNodesReturnsTrueTest() {
            var actual = nullValueNode.equals(nullValueNode2);
            assertThat(actual).isTrue();
        }




    }




}
