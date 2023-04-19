package com.vassbassapp.output.json.writer;

import com.vassbassapp.output.json.JsonWrap;
import com.vassbassapp.scrapper.notebooks.Notebook;

import java.util.Objects;

public class NotebooksJsonFileWriter extends JsonFileWriter<JsonWrap<Notebook>> {
    private static final String FILE_NAME = "notebooks.json";

    private static volatile NotebooksJsonFileWriter instance;

    private NotebooksJsonFileWriter(String fileName) {
        super(fileName);
    }

    public static NotebooksJsonFileWriter getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (NotebooksJsonFileWriter.class) {
                if (Objects.isNull(instance)) instance = new NotebooksJsonFileWriter(FILE_NAME);
            }
        }
        return instance;
    }
}
