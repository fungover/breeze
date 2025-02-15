package org.fungover.breeze.collection;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FQueueTest {

    @Test
    void testEmptyQueue() {
        FQueue<Integer> queue = FQueue.empty();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertEquals(Optional.empty(), queue.peek());
    }
}