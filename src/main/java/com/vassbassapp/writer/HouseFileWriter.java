package com.vassbassapp.writer;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vassbassapp.dto.House;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

public class HouseFileWriter implements FileWriter<House> {
    @Override
    public void write(Collection<House> content) {
        try {
            new ObjectMapper()
                    .writer(new DefaultPrettyPrinter())
                    .writeValue(Paths.get("houses.json").toFile(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
