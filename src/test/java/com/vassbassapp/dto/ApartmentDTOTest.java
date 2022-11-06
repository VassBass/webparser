package com.vassbassapp.dto;

import static org.junit.Assert.*;

import org.junit.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApartmentDTOTest {
    
    @Test
    public void testEqualsAndHashCodeApartmentsEquals(){
        ApartmentDTO apartment1 = new ApartmentDTO();
        apartment1.setName("name");
        apartment1.setPrice("price");
        apartment1.setPlacement("plasement");
        apartment1.setSquare("square");

        ApartmentDTO apartment2 = new ApartmentDTO();
        apartment2.setName("name");
        apartment2.setPrice("price");
        apartment2.setPlacement("plasement");
        apartment2.setSquare("square");

        assertEquals(apartment1, apartment2);
        assertEquals(apartment1.hashCode(), apartment2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeApartmentsNotEquals(){
        ApartmentDTO apartment1 = new ApartmentDTO();
        apartment1.setName("name");
        apartment1.setPrice("price");
        apartment1.setPlacement("plasement");
        apartment1.setSquare("square");

        ApartmentDTO apartment2 = new ApartmentDTO();

        assertNotEquals(apartment1, apartment2);
        assertNotEquals(apartment1.hashCode(), apartment2.hashCode());
    }

    @Test
    public void testToString() throws JsonProcessingException {
        ApartmentDTO apartment = new ApartmentDTO();
        apartment.setName("name");
        apartment.setPrice("price");
        apartment.setPlacement("plasement");
        apartment.setSquare("square");

        String expected = new ObjectMapper()
                            .writer().withDefaultPrettyPrinter().writeValueAsString(apartment);
        
        assertEquals(expected, apartment.toString());
    }
}
