package org.fungover.breeze.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTest {

    Node node = new Node(1);


    @Nested
    class NodeValueTest {
        @Test
        @DisplayName("Node value test")
        void nodeValueTest() {
            Node node2 = new Node("a");
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
        @DisplayName("Node stores null value test")
        void nodeStoresNullValueTest() {
            var node = new Node(null);
            assertThat(node.getValue()).isNull();

        }

    }

}
