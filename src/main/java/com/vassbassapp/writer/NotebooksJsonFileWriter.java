package com.vassbassapp.writer;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vassbassapp.scrapper.notebooks.Notebook;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;

public class NotebooksJsonFileWriter implements JsonFileWriter<Notebook> {
    private static volatile NotebooksJsonFileWriter instance;
    public static NotebooksJsonFileWriter getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (NotebooksJsonFileWriter.class) {
                if (Objects.isNull(instance)) instance = new NotebooksJsonFileWriter();
            }
        }
        return instance;
    }

    private final ObjectWriter writer;
    private NotebooksJsonFileWriter() {
        this.writer = new ObjectMapper().writer(new DefaultPrettyPrinter());
    }

    @Override
    public void write(Collection<Notebook> content) {
        try {
            writer.writeValue(Paths.get("notebooks.json").toFile(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
