package com.vassbassapp.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vassbassapp.ui.console.ColoredPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public class JsonWriter {
    private static volatile JsonWriter instance;
    private final Gson writer;

    private JsonWriter() {
        writer = new GsonBuilder().setPrettyPrinting().create();
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
        try (Writer fileWriter = new FileWriter(fileName)) {
            writer.toJson(content, fileWriter);
            return true;
        } catch (IOException e) {
            ColoredPrinter.printlnRed(String.format("Error while writing to file: %s", fileName));
            return false;
        }
    }
}
