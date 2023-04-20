package com.vassbassapp.scrapper.notebooks;

import com.vassbassapp.scrapper.AggressiveHashMap;
import com.vassbassapp.scrapper.notebooks.moyo.MoyoNotebookExtractor;

import java.util.Objects;

public class NotebookScrapersMap extends AggressiveHashMap<Notebook> {
    private static volatile NotebookScrapersMap instance;
    public static NotebookScrapersMap getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (NotebookScrapersMap.class) {
                if (Objects.isNull(instance)) instance = new NotebookScrapersMap();
            }
        }
        return instance;
    }

    private NotebookScrapersMap() {
        this.put("www.moyo.ua", new MoyoNotebookExtractor());
    }
}
