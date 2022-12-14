package com.vassbassapp.mapper;

import com.vassbassapp.builder.ApartmentBuilder;
import com.vassbassapp.dto.Apartment;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ApartmentMapper implements Mapper<Apartment> {
    private static final String NAME_CLASS = "css-1pvd0aj-Text eu5v0x0";
    private static final String PRICE_CLASS = "css-1q7gvpp-Text eu5v0x0";
    private static final String PLACEMENT_CLASS = "css-p6wsjo-Text eu5v0x0";
    private static final String SQUARE_CLASS = "css-1kfqt7f";

    @Override
    public Apartment map(Element element) {
        ApartmentBuilder builder = new ApartmentBuilder();

        Elements name = element.getElementsByClass(NAME_CLASS);
        if (!name.isEmpty()) builder.setName(name.get(0).text());

        Elements price = element.getElementsByClass(PRICE_CLASS);
        if (!price.isEmpty()) builder.setPrice(price.get(0).text());

        Elements placement = element.getElementsByClass(PLACEMENT_CLASS);
        if (!placement.isEmpty()) {
            String p = placement.get(0).text();
            int index = p.indexOf(" - ");
            builder.setPlacement(p.substring(0, index));
        }
        
        Elements square = element.getElementsByClass(SQUARE_CLASS);
        if (!square.isEmpty()) builder.setSquare(square.get(0).text());

        builder.setLink("https://www.olx.ua" + element.attr("href"));

        return builder.build();
    }
}
