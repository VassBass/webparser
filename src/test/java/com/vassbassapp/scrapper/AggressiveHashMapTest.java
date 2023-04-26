package com.vassbassapp.scrapper;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertThrows;

public class AggressiveHashMapTest {

    @Test
    public void testPut() {
        AbstractExtractor<String> abstractExtractor = new AbstractExtractor<>() {
            @Override
            public Collection<String> extract() {
                return Collections.emptyList();
            }
        };

        AggressiveHashMap<String> aggressiveHashMap = new AggressiveHashMap<>();

        aggressiveHashMap.put("first", abstractExtractor);
        aggressiveHashMap.put("second", abstractExtractor);

        assertThrows(IllegalArgumentException.class, () -> aggressiveHashMap.put("first", abstractExtractor));
    }
}