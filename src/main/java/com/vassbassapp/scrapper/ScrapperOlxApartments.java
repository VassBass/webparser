package com.vassbassapp.scrapper;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.vassbassapp.dto.ApartmentDTO;

public class ScrapperOlxApartments implements Scrapper<ApartmentDTO> {
    private static final String URL = "https://www.olx.ua/d/uk/nedvizhimost/kvartiry/";

    private static final String PAGES_NUM_CLASS = "css-1mi714g";

    @Override
    public Collection<ApartmentDTO> get() {
        Collection<ApartmentDTO> apartments = new ArrayList<>();
        try {
            Connection connection = Jsoup.newSession();
            Document doc = connection.newRequest().url(URL).get();
            LinkedList<Element> numberOfPages = new LinkedList<>(doc.getElementsByClass(PAGES_NUM_CLASS));
            int numOfPages = numberOfPages.isEmpty() ? 0 : Integer.parseInt(numberOfPages.getLast().text());

            Collection<ScrapperOlxApartmentsPage> scrappers = new ArrayList<>();

            int page = 1;
            ExecutorService es = Executors.newFixedThreadPool(5);
            while (page <= numOfPages) {
                scrappers.add(new ScrapperOlxApartmentsPage(connection, page++));
            }
            List<Future<Collection<ApartmentDTO>>> f = es.invokeAll(scrappers);
            f.forEach(fu -> {
                try {
                    apartments.addAll(fu.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
            es.shutdown();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

        return apartments;
    }
}
