package com.vassbassapp.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vassbassapp.dto.Apartment;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

public class ApartmentFileWriter implements FileWriter<Apartment> {
    @Override
    public void write(Collection<Apartment> content) {
        try {
            new ObjectMapper()
                    .writer().withDefaultPrettyPrinter()
                    .writeValue(Paths.get("apartments.json").toFile(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
