package com.vassbassapp;

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
            System.out.println("Scrapping...please wait...");
            List<ApartmentDTO>apartments = new ArrayList<>(scrapper.call());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
