package com.vassbassapp.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vassbassapp.ui.console.ColoredPrinter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Objects;

public class JsonWriter {
    private static volatile JsonWriter instance;
    private final ObjectWriter writer;

    private JsonWriter() {
        writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public static JsonWriter getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (JsonWriter.class) {
                if (Objects.isNull(instance)) instance = new JsonWriter();
            }
        }
        return instance;
    }

    public <T> boolean writeToFile(String fileName, T content) {
        try {
            writer.writeValue(Paths.get(fileName).toFile(), content);
            return true;
        } catch (IOException e) {
            ColoredPrinter.printlnRed("Error writing content to json file!");
            e.printStackTrace();
            return false;
        }
    }
}
