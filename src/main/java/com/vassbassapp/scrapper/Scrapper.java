package com.vassbassapp.scrapper;

import java.util.Collection;

public interface Scrapper<S> {
    Collection<S> get();
}
