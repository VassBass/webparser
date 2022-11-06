package com.vassbassapp.writer;

import java.util.Collection;

public interface FileWriter<S> {
    void write(Collection<S>content);
}
