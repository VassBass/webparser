package com.vassbassapp;

import java.io.IOException;
import java.util.List;

import com.vassbassapp.dto.ApartmentDTO;
import com.vassbassapp.scrapper.Scrapper;

public class App 
{
    public static void main( String[] args ) {
        Scrapper scrapper = new Scrapper();
        try {
            List<ApartmentDTO>apartments = scrapper.getApartments();
            System.out.println("------------" + apartments.size() + "------------------");
            apartments.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
