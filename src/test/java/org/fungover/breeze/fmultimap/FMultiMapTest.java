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
}
