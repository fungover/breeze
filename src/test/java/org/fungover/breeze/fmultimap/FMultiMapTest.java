package org.fungover.breeze.fmultimap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

class FMultiMapTest {

    @ParameterizedTest
    @CsvSource({
            "key1, value1",
            "key2, value2"
    })
    void shouldAddKeyValuePair(String key, String value) {
        FMultiMap<String, String> map = FMultiMap.empty();

        FMultiMap<String, String> updatedMap = map.put(key, value);

        assertTrue(updatedMap.containsKey(key));
        assertTrue(updatedMap.containsEntry(key, value));
    }

    @Test
    void shouldReturnValuesForKey() {
        FMultiMap<String, String> map = FMultiMap.empty();

        map = map.put("key1", "value1");
        map = map.put("key1", "value2");

        Optional<FSet<String>> result = map.get("key1");

        assertTrue(result.isPresent());
        FSet<String> values = result.get();

        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }

    @Test
    void shouldReturnEmptyForNonExistentKey() {
        FMultiMap<String, String> map = FMultiMap.empty();

        Optional<FSet<String>> result = map.get("nonExistentKey");

        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnKeyCount() {
        FMultiMap<String, String> map = FMultiMap.empty();

        map = map.put("key1", "value1");
        map = map.put("key2", "value2");

        assertEquals(2, map.getKeyCount());
    }

    @ParameterizedTest
    @CsvSource({
            "key1, value1",
            "key2, value2"
    })
    void shouldRemoveKeyAndValues(String key, String value) {
        FMultiMap<String, String> map = FMultiMap.empty();
        map = map.put(key, value);

        FMultiMap<String, String> updatedMap = map.remove(key, value);

        assertFalse(updatedMap.containsKey(key));
        assertFalse(updatedMap.containsEntry(key, value));
    }

    @Test
    void shouldRemoveOnlyOneValueForKey() {
        FMultiMap<String, String> map = FMultiMap.empty();

        map = map.put("key1", "value1");
        map = map.put("key1", "value2");

        FMultiMap<String, String> updatedMap = map.remove("key1", "value1");

        Optional<FSet<String>> result = updatedMap.get("key1");

        assertTrue(result.isPresent());
        FSet<String> values = result.get();

        assertFalse(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }

    @Test
    void shouldRemoveKeyWhenLastValueIsRemoved() {
        FMultiMap<String, String> map = FMultiMap.empty();

        map = map.put("key1", "value1");

        FMultiMap<String, String> updatedMap = map.remove("key1", "value1");

        assertFalse(updatedMap.containsKey("key1"));
    }

    @ParameterizedTest
    @CsvSource({
            "key2, value1",
            "key1, nonExistentValue"
    })
    void shouldNotRemoveIfKeyOrValueDoesNotExist(String key, String value) {
        FMultiMap<String, String> map = FMultiMap.empty();

        map = map.put("key1", "value1");
        map = map.put("key1", "value2");

        FMultiMap<String, String> updatedMap = map.remove(key, value);

        assertEquals(map, updatedMap);
        assertTrue(updatedMap.containsEntry("key1", "value1"));
        assertTrue(updatedMap.containsEntry("key1", "value2"));
    }

    @Test
    void shouldReturnSizeOfSet() {
        FSet<String> set = FSet.empty();

        assertEquals(0, set.size(), "Size of empty set should be 0");

        set = set.add("element1");
        assertEquals(1, set.size(), "Size should be 1 after adding one element");

        set = set.add("element2");
        assertEquals(2, set.size(), "Size should be 2 after adding a second element");

        set = set.remove("element1");
        assertEquals(1, set.size(), "Size should be 1 after removing one element");
    }

    @Test
    void shouldRemoveElementFromSet() {
        FSet<String> set = FSet.empty();

        set = set.add("element1");
        set = set.add("element2");

        FSet<String> updatedSet = set.remove("element1");

        assertFalse(updatedSet.contains("element1"), "Set should not contain 'element1' after removal");
        assertTrue(updatedSet.contains("element2"), "Set should still contain 'element2'");
        assertEquals(1, updatedSet.size(), "Size should be 1 after removing one element");
    }

    @Test
    void shouldNotChangeSetWhenRemovingNonExistentElement() {
        FSet<String> set = FSet.empty();

        set = set.add("element1");
        set = set.add("element2");

        FSet<String> updatedSet = set.remove("nonExistentElement");

        assertEquals(set, updatedSet, "Set should not change when removing non-existent element");
        assertEquals(2, updatedSet.size(), "Size should remain 2 after attempting to remove non-existent element");
    }

    @Test
    void shouldNotFailWhenRemovingFromEmptySet() {
        FSet<String> set = FSet.empty();

        FSet<String> updatedSet = set.remove("element1");

        assertEquals(set, updatedSet, "Removing from an empty set should return the same empty set");
        assertEquals(0, updatedSet.size(), "Size should still be 0 after removing from an empty set");
    }
}
