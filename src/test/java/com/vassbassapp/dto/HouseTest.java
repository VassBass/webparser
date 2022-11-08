package com.vassbassapp.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class HouseTest {

    @Test
    public void testEqualsAndHashCodeApartmentsEquals(){
        Realty house1 = new House();
        house1.setName("name");
        house1.setPrice("price");
        house1.setPlacement("placement");
        house1.setSquare("square");
        house1.setLink("link");

        Realty house2 = new House();
        house2.setName("name");
        house2.setPrice("price");
        house2.setPlacement("placement");
        house2.setSquare("square");
        house2.setLink("link");

        assertEquals(house1, house2);
        assertEquals(house1.hashCode(), house2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeApartmentsNotEquals(){
        Realty house1 = new House();
        house1.setName("name");
        house1.setPrice("price");
        house1.setPlacement("placement");
        house1.setSquare("square");
        house1.setLink("link");

        Realty house2 = new House();

        assertNotEquals(house1, house2);
        assertNotEquals(house1.hashCode(), house2.hashCode());
    }

    @Test
    public void testToString() throws JsonProcessingException {
        Realty house = new House();
        house.setName("name");
        house.setPrice("price");
        house.setPlacement("placement");
        house.setSquare("square");
        house.setLink("link");

        String expected = new ObjectMapper()
                .writer().withDefaultPrettyPrinter().writeValueAsString(house);

        assertEquals(expected, house.toString());
    }
}