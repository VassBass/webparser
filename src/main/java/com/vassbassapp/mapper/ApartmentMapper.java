package com.vassbassapp.mapper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vassbassapp.builder.ApartmentBuilder;
import com.vassbassapp.dto.ApartmentDTO;

public class ApartmentMapper implements Mapper<ApartmentDTO> {
    private static final String NAME_CLASS = "css-1pvd0aj-Text eu5v0x0";
    private static final String PRICE_CLASS = "css-1q7gvpp-Text eu5v0x0";
    private static final String PLACEMENT_CLASS = "css-p6wsjo-Text eu5v0x0";
    private static final String SQUARE_CLASS = "css-1kfqt7f";

    @Override
    public ApartmentDTO map(Element element) {
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

        return builder.build();
    }
}
