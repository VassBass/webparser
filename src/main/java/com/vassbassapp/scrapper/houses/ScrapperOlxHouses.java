package com.vassbassapp.scrapper.houses;

import com.vassbassapp.dto.House;
import com.vassbassapp.scrapper.Scrapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ScrapperOlxHouses implements Scrapper<House> {
    private static final String URL = "https://www.olx.ua/d/uk/nedvizhimost/doma/";

    private static final String PAGES_NUM_CLASS = "css-1mi714g";

    @Override
    public Collection<House> get() {
        Collection<House> houses = new ArrayList<>();
        try {
            Connection connection = Jsoup.newSession();
            Document doc = connection.newRequest().url(URL).get();
            LinkedList<Element> numberOfPages = new LinkedList<>(doc.getElementsByClass(PAGES_NUM_CLASS));
            int numOfPages = numberOfPages.isEmpty() ? 0 : Integer.parseInt(numberOfPages.getLast().text());

            Collection<ScrapperOlxHousesPage> scrappers = new ArrayList<>();

            int page = 1;
            ExecutorService es = Executors.newFixedThreadPool(5);
            while (page <= numOfPages) {
                scrappers.add(new ScrapperOlxHousesPage(connection, page++));
            }
            List<Future<Collection<House>>> f = es.invokeAll(scrappers);
            f.forEach(fu -> {
                try {
                    houses.addAll(fu.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
            es.shutdown();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

        return houses;
    }
}
