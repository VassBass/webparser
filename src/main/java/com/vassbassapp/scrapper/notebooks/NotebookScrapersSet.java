package com.vassbassapp.scrapper.notebooks;

import com.vassbassapp.scrapper.AggressiveHashSet;
import com.vassbassapp.scrapper.notebooks.moyo.MoyoNotebookExtractor;

import java.io.IOException;
import java.util.Objects;

public class NotebookScrapersSet extends AggressiveHashSet<Notebook> {
    private static volatile NotebookScrapersSet instance;
    public static NotebookScrapersSet getInstance() throws IOException {
        if (Objects.isNull(instance)) {
            synchronized (NotebookScrapersSet.class) {
                if (Objects.isNull(instance)) instance = new NotebookScrapersSet();
            }
        }
        return instance;
    }

    private NotebookScrapersSet() throws IOException {
        this.add(new MoyoNotebookExtractor());
    }
}
