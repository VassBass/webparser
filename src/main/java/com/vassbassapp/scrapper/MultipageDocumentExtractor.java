package com.vassbassapp.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

public class MultipageDocumentExtractor {
    private static final String PAGE_PARAM = "?page=";

    private static final String URL_SELECTOR = "meta[property*=url]";
    private static final String URL_ATTR = "content";

    private final String baseUrl;
    private int currentPageNum = 0;

    public MultipageDocumentExtractor(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Document getNext() throws IOException {
        if (baseUrl.isBlank()) return null;

        StringBuilder pageUrlBuilder = new StringBuilder(baseUrl);
        if (baseUrl.charAt(baseUrl.length() - 1) != '/') pageUrlBuilder.append("/");
        pageUrlBuilder.append(PAGE_PARAM).append(++currentPageNum);
        String pageUrl = pageUrlBuilder.toString();

        Document document = Jsoup.newSession().url(pageUrl).get();
        String current = document.select(URL_SELECTOR).attr(URL_ATTR);
        if (currentPageNum == 1 || current.equals(pageUrl)) {
            return document;
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        String baseUrl = "https://www.moyo.ua/ua/comp-and-periphery/notebooks/";
        MultipageDocumentExtractor extractor = new MultipageDocumentExtractor(baseUrl);
        Document document = extractor.getNext();
        while (Objects.nonNull(document)) {
            System.out.println(document.head().select(URL_SELECTOR).attr(URL_ATTR));
            document = extractor.getNext();
        }
    }
}
