package com.vassbassapp;

import java.util.ArrayList;
import java.util.List;

import com.vassbassapp.dto.ApartmentDTO;
import com.vassbassapp.scrapper.Scrapper;
import com.vassbassapp.scrapper.ScrapperOlxApartments;
import com.vassbassapp.writer.ApartmentFileWriter;
import com.vassbassapp.writer.FileWriter;

public class App 
{
    public static void main( String[] args ) {
        Scrapper<ApartmentDTO> scrapper = new ScrapperOlxApartments();
        FileWriter<ApartmentDTO> writer = new ApartmentFileWriter();
        try {
            System.out.println("Scrapping...please wait...");
            List<ApartmentDTO>apartments = new ArrayList<>(scrapper.get());
            System.out.println("Write result to file \"apartments.json\"...");
            writer.write(apartments);
            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
