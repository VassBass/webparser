package com.vassbassapp.scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vassbassapp.builder.ApartmentBuilder;
import com.vassbassapp.dto.ApartmentDTO;

public class ScrapperOlxApartmentsPage implements Scrapper<ApartmentDTO> {
    private static final String MAIN_URL = "https://www.olx.ua/d/uk/nedvizhimost/kvartiry/";
    private final int currentPage;
    private final Document currentPageDocument;

    private static final String HOLDER_CLASS = "css-rc5s2u";

    private static final String NAME_CLASS = "css-1pvd0aj-Text eu5v0x0";
    private static final String PRICE_CLASS = "css-1q7gvpp-Text eu5v0x0";
    private static final String PLACEMENT_CLASS = "css-p6wsjo-Text eu5v0x0";
    private static final String SQUARE_CLASS = "css-1kfqt7f";

    private final Collection<ApartmentDTO> apartments = new ArrayList<>();

    public ScrapperOlxApartmentsPage(Connection connection, int page) throws IOException {
        currentPage = page;
        String currentPage_URL = page > 1 ? MAIN_URL + "?page=" + page : MAIN_URL;
        currentPageDocument = connection.newRequest().url(currentPage_URL).get();
    }

    public boolean isPageExists(){
        return getUrlPage() == currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    private int getUrlPage() {
        String title = currentPageDocument.title();
        int index = title.lastIndexOf(" ") + 1;
        try {
            return Integer.parseInt(title.substring(index));
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    @Override
    public Collection<ApartmentDTO> get() {
        return apartments;
    }

    @Override
    public void scrapp(){
        apartments.clear();
        Elements holders = currentPageDocument.getElementsByClass(HOLDER_CLASS);
        for (Element holder : holders) {
            apartments.add(createApartment(holder));
        }
    }

    private ApartmentDTO createApartment(Element holder) {
        ApartmentBuilder builder = new ApartmentBuilder();

        Elements name = holder.getElementsByClass(NAME_CLASS);
        if (!name.isEmpty()) builder.setName(name.get(0).text());

        Elements price = holder.getElementsByClass(PRICE_CLASS);
        if (!price.isEmpty()) builder.setPrice(price.get(0).text());

        Elements placement = holder.getElementsByClass(PLACEMENT_CLASS);
        if (!placement.isEmpty()) builder.setPlacement(placement.get(0).text());

        Elements square = holder.getElementsByClass(SQUARE_CLASS);
        if (!square.isEmpty()) builder.setSquare(square.get(0).text());

        return builder.build();
    }
}
