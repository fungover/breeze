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
    void testPrepend() {
        FList<Integer> list = FList.<Integer>empty().prepend(1);
        assertFalse(list.isEmpty());
        assertEquals(1, list.head());
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Test Append")
    void testAppend() {
        FList<Integer> list = FList.<Integer>empty().append(1);
        assertFalse(list.isEmpty());
        assertEquals(1, list.head());
        assertEquals(1, list.size());
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

}
