package com.vassbassapp.writer;

import java.util.Collection;

public interface JsonFileWriter<S> {
    void write(Collection<S>content);
}
