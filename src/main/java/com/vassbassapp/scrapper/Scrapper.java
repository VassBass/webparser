package com.vassbassapp.scrapper;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import com.vassbassapp.builder.ApartmentBuilder;
import com.vassbassapp.dto.ApartmentDTO;

public class Scrapper {
    private static final String URL = "https://www.olx.ua/d/uk/nedvizhimost/kvartiry/";

    private static final String HOLDER_CLASS = "css-rc5s2u";

    private static final String NAME_CLASS = "css-1pvd0aj-Text eu5v0x0";
    private static final String PRICE_CLASS = "css-1q7gvpp-Text eu5v0x0";
    private static final String PLACEMENT_CLASS = "css-p6wsjo-Text eu5v0x0";
    private static final String SQUARE_CLASS = "css-1kfqt7f";

    public List<ApartmentDTO>getApartments() throws IOException {
        List<ApartmentDTO> apartments = new ArrayList<>();

        Document document = Jsoup.connect(URL).get();

        Elements holders = document.getElementsByClass(HOLDER_CLASS);
        for (Element holder : holders) {
            apartments.add(createApartmentDTO(holder));
        }

        return apartments;
    }

    private ApartmentDTO createApartmentDTO(Element holder) {
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
