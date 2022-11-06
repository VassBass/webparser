package com.vassbassapp.writer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vassbassapp.dto.ApartmentDTO;

public class ApartmentFileWriter implements FileWriter<ApartmentDTO> {

    @Override
    public void write(Collection<ApartmentDTO> content) {
        try {
            new ObjectMapper()
                .writer(new DefaultPrettyPrinter())
                .writeValue(Paths.get("apartments.json").toFile(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
