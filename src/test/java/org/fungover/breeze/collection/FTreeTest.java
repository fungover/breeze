package org.fungover.breeze.collection;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FTreeTest {

    @Test
    void testEmptyTree() {
        FTree<Integer> tree = FTree.empty();

        assertFalse(tree.contains(10));
        assertThrows(UnsupportedOperationException.class, tree::value);
        assertSame(tree.left(), tree);
        assertSame(tree.right(), tree);
    }

    @Test
    void testInsertAndContains() {
        FTree<Integer> tree = FTree.empty();

        tree = tree.insert(10);

        assertTrue(tree.contains(10));

        tree = tree.insert(5);
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(10));
    }

    @Test
    void testInsertMultipleValues() {
        FTree<Integer> tree = FTree.empty();

        tree = tree.insert(10);
        tree = tree.insert(5);
        tree = tree.insert(20);

        assertTrue(tree.contains(10));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(20));
    }

    @Test
    void testMapFunction() {
        FTree<Integer> tree = FTree.<Integer>empty().insert(10).insert(5).insert(20);


        FTree<String> mappedTree = tree.mapAndRebuild(Object::toString);

        assertTrue(mappedTree.contains("10"));
        assertTrue(mappedTree.contains("5"));
        assertTrue(mappedTree.contains("20"));
        assertFalse(mappedTree.contains("15"));
    }

    @Test
    void testEmptyTreeMap() {
        FTree<Integer> tree = FTree.empty();

        FTree<String> mappedTree = tree.map(Object::toString);

        assertInstanceOf(EmptyTree.class, mappedTree);
    }

    @Test
    void testInsertAndLeftRightSubtrees() {
        FTree<Integer> tree = FTree.<Integer>empty().insert(10).insert(5).insert(20);


        assertTrue(tree.left().contains(5));
        assertTrue(tree.right().contains(20));
    }

    @Test
    void testNonEmptyTreeMapWithOrderPreservingFunction() {

        FTree<Integer> tree = FTree.<Integer>empty().insert(10).insert(5).insert(20);

        FTree<Integer> mappedTree = tree.map(x -> x + 1);

        assertTrue(mappedTree.contains(11));
        assertTrue(mappedTree.contains(6));
        assertTrue(mappedTree.contains(21));

        assertEquals(11, mappedTree.value());
        assertEquals(6, mappedTree.left().value());
        assertEquals(21, mappedTree.right().value());
    }

    @Test
    void testMapAndRebuildPreservesNaturalOrderInTree() {

        FTree<Integer> tree = FTree.<Integer>empty().insert(10).insert(5).insert(20);

        FTree<Integer> mappedTree = tree.mapAndRebuild(i -> i);

        assertEquals(5, mappedTree.value());
        assertInstanceOf(EmptyTree.class, mappedTree.left());

        assertEquals(10, mappedTree.right().value());
        assertInstanceOf(EmptyTree.class, mappedTree.right().left());

        assertEquals(20, mappedTree.right().right().value());
        assertInstanceOf(EmptyTree.class, mappedTree.right().right().left());
        assertInstanceOf(EmptyTree.class, mappedTree.right().right().right());
    }
}
