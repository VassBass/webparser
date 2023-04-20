package com.vassbassapp.scrapper;

import java.util.HashMap;

public class AggressiveHashMap<E> extends HashMap<String, AbstractExtractor<E>> {
    @Override
    public AbstractExtractor<E> put(String key, AbstractExtractor<E> value) {
        if (super.containsKey(key)) {
            throw new IllegalArgumentException("Can't hold two implementations of one extractor!");
        } else {
            return super.put(key, value);
        }
    }
}
