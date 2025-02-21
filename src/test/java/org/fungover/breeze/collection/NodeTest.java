package org.fungover.breeze.collection;

import org.junit.jupiter.api.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTest {
    Node<?> nullNode;
    Node<?> nullNode2;

    Node<Integer> node = new Node<>(1);
    Node<String> node2 = new Node<>("a");

    @Nested
    class NodeValueTest {
        @Test
        @DisplayName("Node value test")
        void nodeValueTest() {
            Node<Double> node3 = new Node<>(1.0);
            Node<Boolean> node4 = new Node<>(true);
            Node<Boolean> node5 = new Node<>(false);
            assertAll(
                    () -> assertEquals(1, node.getValue()),
                    () -> assertEquals("a", node2.getValue()),
                    () -> assertEquals(1.0, node3.getValue()),
                    () -> assertEquals(true, node4.getValue()),
                    () -> assertEquals(false, node5.getValue())
            );
        }

        @Test
        @DisplayName("Node Color is initially False test")
        void nodeColorIsInitiallyFalseTest() {
            assertThat(node.color).isFalse();

        }


        @Test
        @DisplayName("Nodes with different values are not equal test")
        void nodesWithDifferentValuesAreNotEqualTest() {

            var expected = node.equals(node2);
            assertThat(expected).isFalse();

        }

        @Test
        @DisplayName("Nodes with the same values are equal Test")
        void nodesWithTheSameValuesAreEqualTest() {
            var expectedNode = new Node<>(1);
            var expected = node.equals(expectedNode);
            assertThat(expected).isTrue();

        }

        @Test
        @DisplayName("Node and not datatype node comparison is False Test")
        void nodeAndNotDatatypeNodeComparisonIsFalseTest() {
            var otherType = "1";
            var expected = node.equals(otherType);
            assertThat(expected).isFalse();

        }




    }

    @Nested
    class NullNodeValueTest {


        @BeforeEach
        void setUp() {

            nullNode = new Node<>();
            nullNode2 = new Node<>(null);
        }


        @Test
        @DisplayName("Node stores null value test")
        void nodeStoresNullValueTest() {

            assertThat(nullNode.getValue()).isNull();
            assertThat(nullNode2.getValue()).isNull();

        }

        @Test
        @DisplayName("Null nodes comparison throws null pointer exception test")
        void nullNodesComparisonThrowsNullPointerExceptionTest() {
            assertThatThrownBy(() -> nullNode.getValue().equals(nullNode2.getValue())).isInstanceOf(NullPointerException.class);

        }

        @Test
        @DisplayName("Null object comparison is True")
        void nullObjectComparisonIsTrue() {
            assertThat(nullNode).isEqualTo(nullNode2);


        }


    }

    @Nested
    class EdgeCasesWithNodeValueTest {


        @Test
        @DisplayName("Nodes that have equal value is True Test")
        void nodesThatHaveEqualValueIsTrueTest() {
            Node<Integer> edgeNode = new Node<>(1);
            Node<Integer> edgeNode2 = new Node<>(1);
            assertThat(edgeNode.equals(edgeNode2)).isTrue();

        }


        @Test
        @DisplayName("Null typed node and Node with other data type is False Test")
        void nullTypedNodeAndNodeWithOtherDataTypeIsFalseTest() {
            Node<?> edgeNode = new Node<>();
            Node<Integer> edgeNode2 = new Node<>(1);
            assertThat(edgeNode).isNotEqualTo(edgeNode2);


        }

        @Test
        @DisplayName("Hashcode for node values is predictable test")
        void hashcodeForNodeValuesIsPredictableTest() {

            Node<Integer> edgeNode = new Node<>(1);
            var expected = Integer.valueOf(1);
            assertThat(edgeNode.hashCode()).hasSameHashCodeAs(expected);

        }


    }
}
