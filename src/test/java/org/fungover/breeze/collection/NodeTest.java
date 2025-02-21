package org.fungover.breeze.collection;

import org.junit.jupiter.api.*;


import static java.util.Objects.hash;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTest {
    Node nullNode;
    Node nullNode2;

    Node node = new Node(1);
    Node node2 = new Node("a");

    @Nested
    class NodeValueTest {
        @Test
        @DisplayName("Node value test")
        void nodeValueTest() {
            Node node3 = new Node(1.0);
            Node node4 = new Node(true);
            Node node5 = new Node(false);
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
        void nodeColorisinitiallyFalseTest() {
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
            var expectedNode = new Node(1);
            var expected = node.equals(expectedNode);
            assertThat(expected).isTrue();

        }

        @Test
        @DisplayName("Node and not datatype node comparison is False Test")
        void nodeAndNotDatatypeNodeComparisonIsFalseTest() {
            var otherType = String.valueOf("1");
            assertThat(node.equals(otherType)).isFalse();

        }




    }

    @Nested
    class NullNodeValueTest {


        @BeforeEach
        void setUp() {

            nullNode = new Node();
            nullNode2 = new Node(null);
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
            Node edgeNode = new Node(1);
            Node edgeNode2 = new Node(1);
            assertThat(edgeNode.equals(edgeNode2)).isTrue();

        }


        @Test
        @DisplayName("Null typed node and Node with other data type is False Test")
        void nullTypedNodeAndNodeWithOtherDataTypeIsFalseTest() {
            Node edgeNode = new Node();
            Node edgeNode2 = new Node(1);
            assertThat(edgeNode).isNotEqualTo(edgeNode2);


        }

        @Test
        @DisplayName("Hashcode for node values is predictable test")
        void hashcodeForNodeValuesIsPredictableTest() {

            Node edgeNode = new Node(1);
            var expected = Integer.valueOf(1);
            assertThat(edgeNode.hashCode()).hasSameHashCodeAs(expected);

        }


    }
}
