package org.fungover.breeze.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FListTest {

    @Test
    void testEmptyList() {
        FList<Integer> list = FList.empty();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertThrows(UnsupportedOperationException.class, list::head);
        assertThrows(UnsupportedOperationException.class, list::tail);
    }

}
