package com.vassbassapp.scrapper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public abstract class AbstractExtractor<E> {
    public static final String UNKNOWN = "Unknown";

    protected String getText(Elements elements) {
        if (elements.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();
        for (Element element : elements) {
            builder.append(element.text());
        }

        return builder.toString();
    }

    public abstract List<E> extract() throws IOException;
}
