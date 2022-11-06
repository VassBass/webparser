package com.vassbassapp.scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vassbassapp.dto.ApartmentDTO;
import com.vassbassapp.mapper.ApartmentMapper;

public class ScrapperOlxApartmentsPage implements Callable<Collection<ApartmentDTO>> {
    private static final String MAIN_URL = "https://www.olx.ua/d/uk/nedvizhimost/kvartiry/";
    private final String currentPageURL;
    private final Connection connection;
    private Document currentPageDocument;

    private static final String HOLDER_CLASS = "css-1apmciz";

    private final Collection<ApartmentDTO> apartments = new ArrayList<>();

    public ScrapperOlxApartmentsPage(Connection connection, int page) {
        this.connection = connection;
        currentPageURL = page > 1 ? MAIN_URL + "?page=" + page : MAIN_URL;
    }

    @Override
    public Collection<ApartmentDTO> call() {
        try {
            currentPageDocument = connection.newRequest().url(currentPageURL).get();
            apartments.clear();
            Elements holders = currentPageDocument.getElementsByClass(HOLDER_CLASS);
            ApartmentMapper mapper = new ApartmentMapper();
            for (Element holder : holders) {
                apartments.add(mapper.map(holder));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apartments;
    }
}
