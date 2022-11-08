package com.vassbassapp.scrapper.houses;

import com.vassbassapp.dto.House;
import com.vassbassapp.mapper.HouseMapper;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

public class ScrapperOlxHousesPage implements Callable<Collection<House>> {
    private static final String MAIN_URL = "https://www.olx.ua/d/uk/nedvizhimost/doma/";
    private final String currentPageURL;
    private final Connection connection;

    private static final String HOLDER_CLASS = "css-rc5s2u";

    private final Collection<House> houses = new ArrayList<>();

    public ScrapperOlxHousesPage(Connection connection, int page) {
        this.connection = connection;
        currentPageURL = page > 1 ? MAIN_URL + "?page=" + page : MAIN_URL;
    }

    @Override
    public Collection<House> call() {
        try {
            Document currentPageDocument = connection.newRequest().url(currentPageURL).get();
            houses.clear();
            Elements holders = currentPageDocument.getElementsByClass(HOLDER_CLASS);
            HouseMapper mapper = new HouseMapper();
            for (Element holder : holders) {
                houses.add(mapper.map(holder));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return houses;
    }
}
