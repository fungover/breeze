package org.fungover.breeze.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.util.Objects.hash;
import static org.assertj.core.api.Assertions.*;

class HashCodeWrapperTest {
    Node.HashCodeWrapper smallValueNode;
    Node.HashCodeWrapper bigValueNode;
    Node.HashCodeWrapper nullValueNode;
    Node.HashCodeWrapper nullValueNode2;

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
            var expectedValue = hash(20);
            assertThat(bigValueNode.hashCode()).isEqualTo(expectedValue);

        }


        @Test
        @DisplayName("Node with the same value returns is zero when node are compared Test")
        void nodeWithTheSameValueReturnsIsZeroWhenNodeAreComparedTest() {
            var intValue = smallValueNode.compareTo(smallValueNode);
            assertThat(intValue).isZero();

        }


        @Test
        @DisplayName("Nodes with the same values are equal return is always True Test")
        void nodesWithTheSameValuesAreEqualReturnIsAlwaysTrueTest() {
            var expectedValue = new Node.HashCodeWrapper<>(10);
            var actualBoolean = smallValueNode.equals(expectedValue);

            var actualBoolean2 = bigValueNode.equals(bigValueNode);
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
            assertThat(isFalseBoolean).isFalse();

        }

        @Test
        @DisplayName("Node comparsion between null and node values is False Test")
        void nodeComparsionBetweenNullAndNodeValuesIsFalseTest() {
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




    }




}
