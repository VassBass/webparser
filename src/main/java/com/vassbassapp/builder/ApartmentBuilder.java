package com.vassbassapp.builder;

import com.vassbassapp.dto.ApartmentDTO;

public class ApartmentBuilder {
    private String name = "Unknown";
    private String price = "Unknown";
    private String placement = "Unknown";
    private String square = "Unknown";

    public ApartmentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ApartmentBuilder setPrice(String price) {
        this.price = price;
        return this;
    }

    public ApartmentBuilder setPlacement(String placement) {
        this.placement = placement;
        return this;
    }

    public ApartmentBuilder setSquare(String square) {
        this.square = square;
        return this;
    }

    public ApartmentDTO build(){
        ApartmentDTO apartment = new ApartmentDTO();
        apartment.setName(name);
        apartment.setPrice(price);
        apartment.setPlacement(placement);
        apartment.setSquare(square);
        return apartment;
    }
}
