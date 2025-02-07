package org.fungover.breeze.fmultimap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FMultiMapTest {

    @Test
    void shouldAddKeyValuePairToMap() {
        FMultiMap<String, String> map = FMultiMap.empty();

        map = map.put("key1", "value1");

        assertTrue(map.get("key1").contains("value1"));
    }
}
