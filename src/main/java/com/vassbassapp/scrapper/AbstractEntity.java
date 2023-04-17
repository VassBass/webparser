package com.vassbassapp.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public abstract class AbstractEntity<E> {
    public static final String UNKNOWN = "Unknown";

    protected final Document document;

    public AbstractEntity(String url) throws IOException {
        this.document = ConnectionHolder.getConnection().newRequest().url(url).get();
    }

    protected String getText(Elements elements) {
        if (elements.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();
        for (Element element : elements) {
            builder.append(element.text());
        }

        return builder.toString();
    }

    public abstract E create();
}
