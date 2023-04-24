package com.vassbassapp.scrapper;

import com.vassbassapp.util.Strings;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class AbstractExtractor<E> {
    public static final String UNKNOWN = "Unknown";

    protected String getText(Elements elements) {
        if (elements.isEmpty()) return Strings.EMPTY;
        return elements.stream()
                .map(Element::text)
                .collect(Collectors.joining(" "));
    }

    public abstract Collection<E> extract();
}
