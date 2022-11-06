package com.vassbassapp.builder;

import com.vassbassapp.dto.ApartmentDTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApartmentBuilderTest {

    @Test
    public void build() {
        ApartmentDTO expected = new ApartmentDTO();
        expected.setName("name");
        expected.setPrice("price");
        expected.setPlacement("placement");
        expected.setSquare("square");

        ApartmentBuilder builder = new ApartmentBuilder();

        assertEquals(expected, builder
                .setName("name")
                .setPrice("price")
                .setPlacement("placement")
                .setSquare("square")
                .build());
    }
}