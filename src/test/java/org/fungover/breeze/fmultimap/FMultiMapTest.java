package org.fungover.breeze.fmultimap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

class FMultiMapTest {

    @Test
    void shouldReturnTrueForEqualMaps() {
        FMultiMap<String, String> map1 = FMultiMap.empty();
        FMultiMap<String, String> map2 = FMultiMap.empty();

        map1 = map1.put("key1", "value1");
        map2 = map2.put("key1", "value1");

        assertEquals(map1, map2, "Maps with the same content should be equal");
        assertEquals(map1.hashCode(), map2.hashCode(), "Hash codes of equal maps should be the same");
    }

    @Test
    void shouldReturnFalseForNonEqualMaps() {
        FMultiMap<String, String> map1 = FMultiMap.empty();
        FMultiMap<String, String> map2 = FMultiMap.empty();

        map1 = map1.put("key1", "value1");
        map2 = map2.put("key2", "value2");

        assertNotEquals(map1.hashCode(), map2.hashCode());
    }


    @Test
    void shouldReturnStringRepresentationOfMap() {
        FMultiMap<String, String> map = FMultiMap.empty();
        map = map.put("key1", "value1");
        map = map.put("key2", "value2");

        String expectedString = "{key1=[value1], key2=[value2]}";
        assertEquals(expectedString, map.toString(), "The string representation of the map should match the expected format");
    }

    @Test
    void shouldCreateEmptySet() {
        FSet<String> set = FSet.empty();

        assertTrue(set.isEmpty(), "Newly created empty set should be empty");
        assertEquals(0, set.size(), "Size of the empty set should be 0");
    }

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

        Set<String> values = map.get("key1");

        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }

    @Test
    void shouldReturnEmptyForNonExistentKey() {
        FMultiMap<String, String> map = FMultiMap.empty();

        Set<String> values = map.get("nonExistentKey");

        assertTrue(values.isEmpty());
    }

    @Test
    void shouldReturnKeyCount() {
        FMultiMap<String, String> map = FMultiMap.empty();

        map = map.put("key1", "value1");
        map = map.put("key2", "value2");

        assertEquals(2, map.getKeyCount());
    }

    @Test
    void shouldNotAddDuplicateKeyValuePair() {
        FMultiMap<String, String> map = FMultiMap.empty();
        map = map.put("key1", "value1");
        map = map.put("key1", "value1");

        assertEquals(1, map.get("key1").size(), "Duplicate key-value pair should not increase the size of the set");
        assertTrue(map.containsEntry("key1", "value1"), "The map should still contain the value");
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

        Set<String> values = updatedMap.get("key1");

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

    @Test
    void shouldThrowExceptionWhenKeyIsNullPut() {
        FMultiMap<String, String> map = FMultiMap.empty();

        assertThrows(IllegalArgumentException.class, () -> map.put(null, "value1"), "Key cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenValueIsNullPut() {
        FMultiMap<String, String> map = FMultiMap.empty();

        assertThrows(IllegalArgumentException.class, () -> map.put("key1", null), "Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenKeyIsNullRemove() {
        FMultiMap<String, String> map = FMultiMap.empty();

        assertThrows(IllegalArgumentException.class, () -> map.remove(null, "value1"), "Key cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenValueIsNullRemove() {
        FMultiMap<String, String> map = FMultiMap.empty();

        assertThrows(IllegalArgumentException.class, () -> map.remove("key1", null), "Value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenAddingNullToSet() {
        FSet<String> set = FSet.empty();

        assertThrows(IllegalArgumentException.class, () -> set.add(null), "Adding null to the set should throw an exception");
    }

    @Test
    void shouldThrowExceptionWhenRemovingNullFromSet() {
        FSet<String> set = FSet.empty();

        assertThrows(IllegalArgumentException.class, () -> set.remove(null), "Removing null from the set should throw an exception");
    }

    @Test
    void shouldNotModifyOriginalMapAfterPut() {
        FMultiMap<String, String> map = FMultiMap.empty();
        FMultiMap<String, String> updatedMap = map.put("key1", "value1");

        assertFalse(map.containsKey("key1"), "The original map should not be modified");
        assertTrue(updatedMap.containsKey("key1"), "The updated map should contain the new key-value pair");
    }
}
