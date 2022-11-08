package com.vassbassapp.builder;

import com.vassbassapp.dto.House;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HouseBuilderTest {

    @Test
    public void build() {
        House expected = new House();
        expected.setName("name");
        expected.setPrice("price");
        expected.setPlacement("placement");
        expected.setSquare("square");
        expected.setLink("link");

        HouseBuilder builder = new HouseBuilder();

        assertEquals(expected, builder
                .setName("name")
                .setPrice("price")
                .setPlacement("placement")
                .setSquare("square")
                .setLink("link")
                .build());
    }
}