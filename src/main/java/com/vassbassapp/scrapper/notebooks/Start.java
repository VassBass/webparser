package com.vassbassapp.scrapper.notebooks;

import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.writer.NotebooksJsonFileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args) throws IOException, InterruptedException {
        NotebookScrapersSet scrapersSet = NotebookScrapersSet.getInstance();
        List<Notebook> result = new ArrayList<>();

        System.out.println("Start");
        for (AbstractExtractor<Notebook> extractor : scrapersSet) {
            result.addAll(extractor.extract());
        }

        NotebooksJsonFileWriter.getInstance().write(result);
        System.out.println("End");
    }
}
