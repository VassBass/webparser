package com.vassbassapp.scrapper.notebook_stand;

import com.vassbassapp.scrapper.AggressiveHashMap;
import com.vassbassapp.scrapper.notebook_stand.dto.NotebookStand;
import com.vassbassapp.scrapper.notebook_stand.scrapers.moyo.MoyoNotebookStandExtractor;
import com.vassbassapp.scrapper.notebooks.NotebookScrapersMap;

import java.util.Objects;

public class NotebookStandScrapersMap extends AggressiveHashMap<NotebookStand> {
    private static volatile NotebookStandScrapersMap instance;
    public static NotebookStandScrapersMap getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (NotebookScrapersMap.class) {
                if (Objects.isNull(instance)) instance = new NotebookStandScrapersMap();
            }
        }
        return instance;
    }

    private NotebookStandScrapersMap() {
        this.put("www.moyo.ua", new MoyoNotebookStandExtractor());
    }
}
