package com.vassbassapp.dto;

import static org.junit.Assert.*;

import org.junit.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApartmentTest {
    
    @Test
    public void testEqualsAndHashCodeApartmentsEquals(){
        Realty apartment1 = new Apartment();
        apartment1.setName("name");
        apartment1.setPrice("price");
        apartment1.setPlacement("placement");
        apartment1.setSquare("square");
        apartment1.setLink("link");

        Realty apartment2 = new Apartment();
        apartment2.setName("name");
        apartment2.setPrice("price");
        apartment2.setPlacement("placement");
        apartment2.setSquare("square");
        apartment2.setLink("link");

        assertEquals(apartment1, apartment2);
        assertEquals(apartment1.hashCode(), apartment2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeApartmentsNotEquals(){
        Realty apartment1 = new Apartment();
        apartment1.setName("name");
        apartment1.setPrice("price");
        apartment1.setPlacement("placement");
        apartment1.setSquare("square");
        apartment1.setLink("link");

        Realty apartment2 = new Apartment();

        assertNotEquals(apartment1, apartment2);
        assertNotEquals(apartment1.hashCode(), apartment2.hashCode());
    }

    @Test
    public void testToString() throws JsonProcessingException {
        Realty apartment = new Apartment();
        apartment.setName("name");
        apartment.setPrice("price");
        apartment.setPlacement("placement");
        apartment.setSquare("square");
        apartment.setLink("link");

        String expected = new ObjectMapper()
                            .writer().withDefaultPrettyPrinter().writeValueAsString(apartment);
        
        assertEquals(expected, apartment.toString());
    }
}
