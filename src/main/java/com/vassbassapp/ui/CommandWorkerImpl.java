package com.vassbassapp.ui;

import com.vassbassapp.dto.ApartmentDTO;
import com.vassbassapp.scrapper.Scrapper;
import com.vassbassapp.scrapper.ScrapperOlxApartments;
import com.vassbassapp.writer.ApartmentFileWriter;
import com.vassbassapp.writer.FileWriter;

import java.util.ArrayList;
import java.util.List;

public class CommandWorkerImpl implements CommandWorker {
    @Override
    public void searchApartments() {
        Scrapper<ApartmentDTO> scrapper = new ScrapperOlxApartments();
        FileWriter<ApartmentDTO> writer = new ApartmentFileWriter();
        try {
            System.out.println("Scrapping...please wait...");
            List<ApartmentDTO> apartments = new ArrayList<>(scrapper.get());
            System.out.println("Write result to file \"apartments.json\"...");
            writer.write(apartments);
            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printHelp() {
        System.out.println("To search apartments in olx.ua enter \"apartments\" or \"/a\"");
        System.out.println("To show help enter \"help\" or \"/h\"");
        System.out.println("To close application enter \"exit\" or \"/e\"");
    }
}
