package com.vassbassapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vassbassapp.dto.ApartmentDTO;
import com.vassbassapp.scrapper.Scrapper;
import com.vassbassapp.scrapper.ScrapperOlxApartments;

public class App 
{
    public static void main( String[] args ) {
        Scrapper<ApartmentDTO> scrapper = new ScrapperOlxApartments();
        try {
            scrapper.scrapp();
            List<ApartmentDTO>apartments = new ArrayList<>(scrapper.get());
            System.out.println("------------" + apartments.size() + "------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
