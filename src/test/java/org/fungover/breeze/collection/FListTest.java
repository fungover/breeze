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
        FList<Object> list = FList.empty().prepend(1);
        assertFalse(list.isEmpty());
        assertEquals(1, list.head());
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Test Append")
    void testAppend() {
        FList<Object> list = FList.empty().append(1);
        assertFalse(list.isEmpty());
        assertEquals(1, list.head());
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Test Prepend and Append")
    void testPrependAndAppend() {
        FList<Object> list = FList.empty().prepend(1).append(2);
        assertFalse(list.isEmpty());
        assertEquals(1, list.head());
        assertEquals(2, list.tail().head());
        assertEquals(2, list.size());
    }

}
