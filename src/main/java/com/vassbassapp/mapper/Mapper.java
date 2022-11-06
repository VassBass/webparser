package com.vassbassapp.mapper;

import org.jsoup.nodes.Element;

public interface Mapper<S> {
    S map(Element element);
}
