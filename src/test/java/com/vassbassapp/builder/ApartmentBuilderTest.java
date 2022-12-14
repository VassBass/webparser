package com.vassbassapp.builder;

import com.vassbassapp.dto.Apartment;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApartmentBuilderTest {

    @Test
    public void build() {
        Apartment expected = new Apartment();
        expected.setName("name");
        expected.setPrice("price");
        expected.setPlacement("placement");
        expected.setSquare("square");
        expected.setLink("link");

        ApartmentBuilder builder = new ApartmentBuilder();

        assertEquals(expected, builder
                .setName("name")
                .setPrice("price")
                .setPlacement("placement")
                .setSquare("square")
                .setLink("link")
                .build());
    }
}