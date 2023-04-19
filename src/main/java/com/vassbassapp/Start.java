package com.vassbassapp;

import com.vassbassapp.logger.CustomLogger;
import com.vassbassapp.output.json.JsonWrap;
import com.vassbassapp.output.json.writer.NotebooksJsonFileWriter;
import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.scrapper.notebooks.Notebook;
import com.vassbassapp.scrapper.notebooks.NotebookScrapersSet;

import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args) {
        NotebookScrapersSet scrapersSet = NotebookScrapersSet.getInstance();
        List<Notebook> result = new ArrayList<>();

        System.out.println("Scrapping starts");
        for (AbstractExtractor<Notebook> extractor : scrapersSet) {
            result.addAll(extractor.extract());
        }

        System.out.println("Saving result to json");
        NotebooksJsonFileWriter.getInstance().write(new JsonWrap<>(result));

        CustomLogger.getInstance().writeLogFile();
        System.exit(0);
    }
}
