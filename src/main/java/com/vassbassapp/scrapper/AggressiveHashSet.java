package com.vassbassapp.scrapper;

import java.util.HashSet;

public class AggressiveHashSet<E> extends HashSet<AbstractExtractor<E>> {
    @Override
    public boolean add(AbstractExtractor<E> e) {
        if (super.add(e)) {
            return true;
        } else throw new IllegalArgumentException("Can't hold two implementations of one extractor!");
    }
}
