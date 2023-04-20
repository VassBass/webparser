package com.vassbassapp;

import com.vassbassapp.ui.console.listener.MainCommandListener;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
//        NotebookScrapersMap scrapersMap = NotebookScrapersMap.getInstance();
//        List<Notebook> result = new ArrayList<>();
//
//        System.out.println("Scrapping starts");
//        for (AbstractExtractor<Notebook> extractor : scrapersMap.values()) {
//            result.addAll(extractor.extract());
//        }
//
//        System.out.println("Saving result to json");
//        NotebooksJsonFileWriter.getInstance().write(new JsonWrap<>(result));
//
//        CustomLogger.getInstance().writeLogFile();
//        System.exit(0);

        new MainCommandListener(new Scanner(System.in));
    }
}
