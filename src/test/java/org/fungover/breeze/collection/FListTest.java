package org.fungover.breeze.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FListTest {

    @Test
    @DisplayName("Test Empty List")
    void testEmptyList() {
        FList<Integer> list = FList.empty();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertThrows(UnsupportedOperationException.class, list::head);
        assertThrows(UnsupportedOperationException.class, list::tail);
    }


    @Test
    @DisplayName("Test Prepend")
    public void testPrepend() {
        FList<Integer> list = FList.empty();
        list = list.prepend(1);
        assertFalse(list.isEmpty());
        assertEquals(1, list.head());
        assertTrue(list.tail().isEmpty());
    }

    @Test
    @DisplayName("Test Append")
    public void testAppend() {
        FList<Integer> list = FList.empty();
        list = list.append(1);
        assertFalse(list.isEmpty());
        assertEquals(1, list.head());
        assertTrue(list.tail().isEmpty());
    }

    @Test
    @DisplayName("Test Prepend and Append")
    void testPrependAndAppend() {
        FList<Integer> list = FList.<Integer>empty().prepend(1).append(2);
        assertFalse(list.isEmpty());
        assertEquals(1, list.head());
        assertEquals(2, list.tail().head());
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Test Tail")
    void testTail() {
        FList<Integer> list = FList.<Integer>empty().prepend(1).prepend(2);
        FList<Integer> tail = list.tail();
        assertEquals(1, tail.head());
        assertEquals(1, tail.size());
    }


    @Test
    @DisplayName("Test Map")
    void testMap() {
        FList<Integer> list = FList.<Integer>empty().prepend(1).prepend(2).prepend(3);
        FList<String> mappedList = list.map(Object::toString);
        assertEquals("3", mappedList.head());
        assertEquals("2", mappedList.tail().head());
        assertEquals("1", mappedList.tail().tail().head());
    }

    @Test
    @DisplayName("Test Filter")
    void testFilter() {
        FList<Integer> list = FList.<Integer>empty().prepend(1).prepend(2).prepend(3);
        FList<Integer> filteredList = list.filter(x -> x % 2 == 1);
        assertEquals(3, filteredList.head());
        assertEquals(1, filteredList.tail().head());
        assertTrue(filteredList.tail().tail().isEmpty());
    }

    @Test
    @DisplayName("Test Is Empty")
    void testIsEmpty() {
        FList<Integer> emptyList = FList.empty();
        assertTrue(emptyList.isEmpty());

        FList<Integer> nonEmptyList = emptyList.prepend(1);
        assertFalse(nonEmptyList.isEmpty());
    }

    @Test
    @DisplayName("Test Size")
    public void testSize() {
        FList<Integer> list = FList.empty();
        list = list.prepend(1).prepend(2).prepend(3);
        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("Test Reverse")
    public void testReverse() {
        FList<Integer> list = FList.empty();
        list = list.prepend(1).prepend(2).prepend(3);
        FList<Integer> reversedList = list.reverse();
        assertEquals(1, reversedList.head());
        assertEquals(2, reversedList.tail().head());
        assertEquals(3, reversedList.tail().tail().head());
    }

    @Test
    @DisplayName("Test Cached FList")
    public void testCachedFList() {
        FList<Integer> list = FList.empty();
        list = list.prepend(1).prepend(2).prepend(3);
        FList<Integer> cachedList = new FList.CachedFList<>(list);
        assertEquals(3, cachedList.head());
        assertEquals(2, cachedList.tail().head());
        assertEquals(1, cachedList.tail().tail().head());
        assertEquals(3, cachedList.size());
        assertEquals(1, cachedList.reverse().head());
    }

}
