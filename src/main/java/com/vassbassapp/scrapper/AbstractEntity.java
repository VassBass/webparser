package com.vassbassapp.scrapper;

import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class AbstractEntity<E> {
    public static final String UNKNOWN = "Unknown";

    protected final Document document;

    public AbstractEntity(String url) throws IOException {
        this.document = ConnectionHolder.getConnection().newRequest().url(url).get();
    }

    public abstract E create();
}
