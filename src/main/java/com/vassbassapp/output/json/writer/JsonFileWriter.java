package com.vassbassapp.output.json.writer;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.nio.file.Paths;

public abstract class JsonFileWriter<S> {
    private final String fileName;
    private final ObjectWriter writer;

    protected JsonFileWriter(String fileName) {
        this.fileName = fileName;
        this.writer = new ObjectMapper().writer(new DefaultPrettyPrinter());
    }

    public void write(S source) {
        try {
            writer.writeValue(Paths.get(fileName).toFile(), source);
        } catch (IOException e) {
            System.err.println("Error writing result to json file!");
            e.printStackTrace();
        }
    }
}
