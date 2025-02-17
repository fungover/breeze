package org.fungover.breeze.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FTreeTest {

    @Test
    void shouldReturnFalseWhenTreeIsEmptyAndValueIsSearched() {
        FTree<Integer> tree = FTree.empty();

        assertFalse(tree.contains(10));
    }

    @Test
    void shouldThrowExceptionWhenAccessingValueInEmptyTree() {
        FTree<Integer> tree = FTree.empty();

        assertThrows(UnsupportedOperationException.class, tree::value);
    }

    @Test
    void shouldReturnEmptySubtreesForEmptyTree() {
        FTree<Integer> tree = FTree.empty();

        assertSame(tree.left(), tree);
        assertSame(tree.right(), tree);
    }

    @Test
    void shouldContainInsertedValue() {
        FTree<Integer> tree = FTree.empty();

        tree = tree.insert(10);

        assertTrue(tree.contains(10));
    }

    @Test
    void shouldContainAllInsertedValues() {
        FTree<Integer> tree = FTree.empty();

        tree = tree.insert(10);
        tree = tree.insert(5);
        tree = tree.insert(20);

        assertTrue(tree.contains(10));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(20));
    }

    @Test
    void shouldInsertValuesCorrectlyAndMaintainTreeStructure() {
        FTree<Integer> tree = FTree.empty();

        tree = tree.insert(10);
        tree = tree.insert(5);
        tree = tree.insert(20);

        assertTrue(tree.contains(10));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(20));
    }

}
