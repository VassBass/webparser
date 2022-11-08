package com.vassbassapp.builder;

import com.vassbassapp.dto.Apartment;

public class ApartmentBuilder implements RealtyBuilder {
    private String name = "Unknown";
    private String price = "Unknown";
    private String placement = "Unknown";
    private String square = "Unknown";
    private String link = "https://www.olx.ua/d/uk/nedvizhimost/kvartiry/";


    @Override
    public ApartmentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ApartmentBuilder setPrice(String price) {
        this.price = price;
        return this;
    }

    @Override
    public ApartmentBuilder setPlacement(String placement) {
        this.placement = placement;
        return this;
    }

    @Override
    public ApartmentBuilder setSquare(String square) {
        this.square = square;
        return this;
    }

    @Override
    public ApartmentBuilder setLink(String link) {
        this.link = link;
        return this;
    }

    @Override
    public Apartment build() {
        Apartment a = new Apartment();
        a.setName(name);
        a.setPrice(price);
        a.setPlacement(placement);
        a.setSquare(square);
        a.setLink(link);
        return a;
    }
}
