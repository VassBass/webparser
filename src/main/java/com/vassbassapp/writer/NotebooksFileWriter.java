package com.vassbassapp.writer;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vassbassapp.scrapper.notebooks.Notebook;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

public class NotebooksFileWriter implements FileWriter<Notebook> {
    @Override
    public void write(Collection<Notebook> content) {
        try {
            new ObjectMapper()
                    .writer(new DefaultPrettyPrinter())
                    .writeValue(Paths.get("notebooks.json").toFile(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
