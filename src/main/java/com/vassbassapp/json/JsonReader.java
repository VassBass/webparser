package com.vassbassapp.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JsonReader {
    private static volatile JsonReader instance;
    private final Gson reader;

    private JsonReader() {
        reader = new GsonBuilder().setPrettyPrinting().create();
    }

    public static JsonReader getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (JsonWriter.class) {
                if (Objects.isNull(instance)) instance = new JsonReader();
            }
        }
        return instance;
    }

    public <T> T read(String json, Class<T> clazz) {
        return reader.fromJson(json, clazz);
    }

    public <T> T readFromStream(InputStream stream, Class<T> clazz) throws IOException {
        String json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        return read(json, clazz);
    }

    @Nullable
    public <T> T readFromFile(File file, Class<T> clazz) throws IOException {
        try (InputStream in = new FileInputStream(file)) {
            return readFromStream(in, clazz);
        }
    }
}
