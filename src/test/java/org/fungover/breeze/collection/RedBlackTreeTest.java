package org.fungover.breeze.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

class RedBlackTreeTest {
    int [] values;

    RedBlackTree<Integer> tree = new RedBlackTree<>();

    @Nested
    class TreeIsEmptyTests{
        RedBlackTree<Integer> treeEmpty = new RedBlackTree<>();

        @Test
        @DisplayName("Tree without values has value null and color Red test")
        void treeWithoutValuesHasValueNullAndColorRedTest() {

           var emptyNode = treeEmpty.findValue(null);
            assertThat(emptyNode).isNull();

        }


    }

    @Test
    @DisplayName("Adding a single value to tree Test")
    void addingAValueToTreeTest() {
        tree.insert(10);
        var expectedly = tree.findValue(10);
        assertThat(expectedly.getValue()).isEqualTo(10);
        assertThat(expectedly.color).isTrue();

    }

    @Test
    @DisplayName("Insert a single node into an empty tree (root should be black)")
    void insertSingleNodeInEmptyTree() {
        tree.insert(10);
        var rootNode = tree.findValue(10);
        assertThat(rootNode).isNotNull();
        assertThat(rootNode.color).isTrue(); // Root node should be black
    }



    @Test
    @DisplayName("Adding a second node to tree with node a node child node is Red Test ")
    void addingASecondNodeToTreeWithNodeANodeChildNodeIsRedTest() {
        tree.insert(10);
        tree.insert(20);
        var expectedly = tree.findValue(20);
        assertThat(expectedly.getValue()).isEqualTo(20);
        assertThat(expectedly.color).isFalse();

    }

    @Test
    @DisplayName("Adding three nodes to tree trigger coloring Test")
    void addingThreeNodesToTreeTriggerColoringTest() {

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        var expectedly = tree.findValue(10);
        var expectedly2 = tree.findValue(20);
        var expectedly3 = tree.findValue(30);
        assertThat(expectedly.color).isFalse();
        assertThat(expectedly2.color).isTrue();
        assertThat(expectedly3.color).isFalse();
        tree.printRedBlackTree();

    }

    @Test
    @DisplayName("Adding consecutive smaller node values to tree trigger Right rotation Test")
    void addingConsecutiveSmallerNodeValuesToTreeTriggerRightRotationTest() {
        tree.insert(10);
        tree.insert(5);
        tree.insert(2);
        var expectedly = tree.findValue(10);
        var expectedly2 = tree.findValue(5);
        var expectedly3 = tree.findValue(2);

        assertThat(expectedly.color).isFalse();
        assertThat(expectedly2.color).isTrue();
        assertThat(expectedly3.color).isFalse();

    }

    @Test
    @DisplayName("Adding forth node to tree that is in between 10 and 20 Test")
    void addingForthNodeToTreeThatIsInBetween10And20Test() {
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);
        var expectedly = tree.findValue(10);
        var expectedly2 = tree.findValue(20);
        var expectedly3 = tree.findValue(30);
        var expectedly4 = tree.findValue(15);
        assertThat(expectedly.color).isTrue();
        assertThat(expectedly2.color).isTrue();
        assertThat(expectedly3.color).isTrue();
        assertThat(expectedly4.color).isFalse();

    }



    @Test
    @DisplayName("Test for specific tree structure after insertions")
    void testSpecificTreeStructure() {
        // Insert values into the tree to get the desired structure
        values = new int[]{10, 20, 30, 15, 25, 5, 1, 12};
        for (int value : values) {
            tree.insert(value);
        }


        // Capture the output of printRedBlackTree
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream); // Redirect System.out to capture printed output

        // Call the method to print the tree
        tree.printRedBlackTree();

        // Restore the original System.out
        System.setOut(System.out);

        // Expected tree structure
        var expectedTreeStructure = """
                RedBlack Tree with Nodes and Values

                 /-- 20B
                      /-- 10R
                           /-- 5B
                                /-- 1R
                           \\-- 15B
                                /-- 12R
                      \\-- 30B
                           /-- 25R

                Total size: 8
                """;

        // Compare the captured output to the expected output
        assertThat(outputStream.toString().trim()).isEqualTo(expectedTreeStructure.trim());
    }

    @Test
    @DisplayName("Size increases if nodes are added to RedBlackTree Test")
    void sizeIncreasesIfNodesAreAddedToRedBlackTreeTest() {
        assertThat(tree.getSize()).isZero();
        tree.insert(10);
        assertThat(tree.getSize()).isEqualTo(1);

    }

    @Test
    @DisplayName("Tree traversing in natural order Test")
    void treeTraversingInNaturalOrderTest() {
        // Insert values into the tree to get the desired structure
        values = new int[]{10, 20, 30, 15, 25, 5, 1, 12};
        for (int value : values) {
            tree.insert(value);
        }

        // Capture the output of printRedBlackTree
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream); // Redirect System.out to capture printed output

        tree.inorder();

        System.setOut(System.out);

        var expected = "1 \n5 \n10 \n12 \n15 \n20 \n25 \n30";
        assertThat(outputStream.toString().trim()).isEqualTo(expected.trim());

    }

    @Test
    @DisplayName("Tree node value not found is False Test")
    void treeNodeValueNotFoundIsFalseTest() {
        tree.insert(10);
        assertThat(tree.isNodeWithValueFound(20)).isFalse();
        assertThat(tree.isNodeWithValueFound(10)).isTrue();

    }

    @Test
    @DisplayName("Insert Null as value to  a tree node causes exception Test")
    void insertNullAsValueToTreeNodeCausesExceptionTest() {
        assertThatThrownBy( () -> tree.insert(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot insert null value");

    }

    @Test
    @DisplayName("Tree does not add duplicated values test")
    void treeDoesNotAddDuplicatedValuesTest() {
        tree.insert(10);
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(20);
        tree.insert(30);
        assertThat(tree.getSize()).isEqualTo(3);


    }

    @Test
    @DisplayName("Right insertion test when inserting larger value")
    void rightInsertionTestWhenInsertingLargerValue() {
        tree.insert(10);
        tree.insert(20);


        var node10 = tree.findValue(10);
        var node20 = tree.findValue(20);

        assertThat(node20).isNotNull();
        assertThat(node10.right).isEqualTo(node20);
    }

    @Test
    @DisplayName("Insert a large number of nodes into the Red-Black Tree")
    void insertLargeNumberOfNodes() {
        for (int i = 1; i <= 10000; i++) {
            tree.insert(i);
        }
        assertThat(tree.getSize()).isEqualTo(10000);
    }




    }
