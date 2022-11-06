package com.vassbassapp.scrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.vassbassapp.dto.ApartmentDTO;

public class ScrapperOlxApartments implements Scrapper<ApartmentDTO> {
    private final Collection<ApartmentDTO> apartments = new ArrayList<>();

    @Override
    public Collection<ApartmentDTO> get() throws IOException {
        return apartments;
    }

    @Override
    public void scrapp() throws IOException {
        LinkedList<ScrapperOlxApartmentsPage> scrappers = new LinkedList<>();

        int page = 1;
        Connection connection = Jsoup.newSession();
        scrappers.add(new ScrapperOlxApartmentsPage(connection, page));
        ExecutorService es = Executors.newCachedThreadPool();
        do {
            System.out.println(">>>>>>>>>" + page);
            es.execute(() -> scrappers.getLast().scrapp());
            scrappers.add(new ScrapperOlxApartmentsPage(connection, ++page));
        } while (scrappers.getLast().isPageExists());
        es.shutdown();
        try {
            if (es.awaitTermination(10, TimeUnit.MINUTES)) {
                scrappers.forEach(s -> apartments.addAll(s.get()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
