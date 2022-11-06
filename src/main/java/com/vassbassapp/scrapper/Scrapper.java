package com.vassbassapp.scrapper;

import java.io.IOException;
import java.util.Collection;

public interface Scrapper<S> {
    Collection<S>get() throws IOException;
    void scrapp() throws IOException;
}
