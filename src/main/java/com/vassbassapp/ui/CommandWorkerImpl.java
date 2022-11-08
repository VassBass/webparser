package com.vassbassapp.ui;

import com.vassbassapp.dto.Apartment;
import com.vassbassapp.dto.House;
import com.vassbassapp.scrapper.apartments.ScrapperOlxApartments;
import com.vassbassapp.scrapper.houses.ScrapperOlxHouses;
import com.vassbassapp.writer.ApartmentFileWriter;
import com.vassbassapp.writer.HouseFileWriter;

import java.util.ArrayList;
import java.util.List;

public class CommandWorkerImpl implements CommandWorker {
    @Override
    public void searchApartments() {
        ScrapperOlxApartments scrapper = new ScrapperOlxApartments();
        ApartmentFileWriter writer = new ApartmentFileWriter();
        try {
            System.out.println("Scrapping...please wait...");
            List<Apartment> apartments = new ArrayList<>(scrapper.get());
            System.out.println("Write result to file \"apartments.json\"...");
            writer.write(apartments);
            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchHouses() {
        ScrapperOlxHouses scrapper = new ScrapperOlxHouses();
        HouseFileWriter writer = new HouseFileWriter();
        try {
            System.out.println("Scrapping...please wait...");
            List<House> houses = new ArrayList<>(scrapper.get());
            System.out.println("Write result to file \"houses.json\"...");
            writer.write(houses);
            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printHelp() {
        System.out.println("To search apartments in olx.ua enter \"apartments\" or \"/a\"");
        System.out.println("To search houses in olx.ua enter \"houses\" or \"/h\"");
        System.out.println("To show help enter \"help\" or \"/hlp\"");
        System.out.println("To close application enter \"exit\" or \"/e\"");
    }
}
