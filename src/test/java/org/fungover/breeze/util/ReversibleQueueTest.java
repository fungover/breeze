package org.fungover.breeze.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReversibleQueueTest {
    
    private ReversibleQueue<Integer> queue;
    
    @BeforeEach
    void setUp() {
        queue = new ReversibleQueue<>();
    }
    
    @Test
    void newQueueShouldBeEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }
    
    @Test
    void enqueueShouldAddElementToQueue() {
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
    }
    
    @Test
    void dequeueShouldRemoveAndReturnFirstElement() {
        queue.enqueue(1);
        queue.enqueue(2);
        
        assertEquals(1, queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals(2, queue.dequeue());
        assertTrue(queue.isEmpty());
    }
    
    @Test
    void dequeueOnEmptyQueueShouldReturnNull() {
        assertNull(queue.dequeue());
    }
    
    @Test
    void reverseShouldReverseOrderOfElements() {
        // Lägg till några element
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        // Vänd på kön
        queue.reverse();
        
        // Verifiera den omvända ordningen
        assertEquals(3, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(1, queue.dequeue());
        assertTrue(queue.isEmpty());
    }
    
    @Test
    void reverseOnEmptyQueueShouldNotThrowException() {
        assertDoesNotThrow(() -> queue.reverse());
        assertTrue(queue.isEmpty());
    }
    
    @Test
    void reverseOnSingleElementShouldMaintainElement() {
        queue.enqueue(1);
        queue.reverse();
        
        assertEquals(1, queue.size());
        assertEquals(1, queue.dequeue());
    }
    
    @Test
    void toStringShouldReturnQueueRepresentation() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        String expected = "[1, 2, 3]";
        assertEquals(expected, queue.toString());
    }
    
    @Test
    void toStringOnEmptyQueueShouldReturnEmptyBrackets() {
        assertEquals("[]", queue.toString());
    }
    
    @Test
    void sizeShouldReturnCorrectNumberOfElements() {
        assertEquals(0, queue.size());
        
        queue.enqueue(1);
        assertEquals(1, queue.size());
        
        queue.enqueue(2);
        assertEquals(2, queue.size());
        
        queue.dequeue();
        assertEquals(1, queue.size());
    }
    
    @Test
    void multipleOperationsTest() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.reverse(); // [2,1]
        queue.enqueue(3); // [2,1,3]
        queue.enqueue(4); // [2,1,3,4]
        queue.reverse(); // [4,3,1,2]
        
        assertEquals(4, queue.size());
        assertEquals(4, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertTrue(queue.isEmpty());
    }
} 