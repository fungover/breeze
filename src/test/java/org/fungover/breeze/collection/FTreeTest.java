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

}
