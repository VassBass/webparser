package com.vassbassapp.builder;

import com.vassbassapp.dto.House;

public class HouseBuilder implements RealtyBuilder {
    private String name = "Unknown";
    private String price = "Unknown";
    private String placement = "Unknown";
    private String square = "Unknown";
    private String link = "https://www.olx.ua/d/uk/nedvizhimost/doma/";


    @Override
    public HouseBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public HouseBuilder setPrice(String price) {
        this.price = price;
        return this;
    }

    @Override
    public HouseBuilder setPlacement(String placement) {
        this.placement = placement;
        return this;
    }

    @Override
    public HouseBuilder setSquare(String square) {
        this.square = square;
        return this;
    }

    @Override
    public HouseBuilder setLink(String link) {
        this.link = link;
        return this;
    }

    @Override
    public House build() {
        House h = new House();
        h.setName(name);
        h.setPrice(price);
        h.setPlacement(placement);
        h.setSquare(square);
        h.setLink(link);
        return h;
    }
}
