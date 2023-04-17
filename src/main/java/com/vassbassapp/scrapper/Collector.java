package com.vassbassapp.scrapper;

import java.util.Collection;

public interface Collector<S> {
    Collection<S> collect();
}
