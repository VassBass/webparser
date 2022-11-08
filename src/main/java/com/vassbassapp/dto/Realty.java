package com.vassbassapp.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public class Realty {
    private String name;
    private String price;
    private String placement;
    private String square;
    private String link;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPlacement() {
        return placement;
    }

    public String getSquare() {
        return square;
    }

    public String getLink(){
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        if (this == o) return true;

        Realty realty = (Realty) o;
        return name.equals(realty.getName()) &&
                price.equals(realty.getPrice()) &&
                placement.equals(realty.getPlacement()) &&
                square.equals(realty.getSquare()) &&
                link.equals(realty.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, placement, square, link);
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper()
                    .writer().withDefaultPrettyPrinter()
                    .writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
