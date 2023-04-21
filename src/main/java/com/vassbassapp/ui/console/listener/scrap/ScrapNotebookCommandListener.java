package com.vassbassapp.ui.console.listener.scrap;

import com.vassbassapp.json.JsonWriter;
import com.vassbassapp.logger.CustomLogger;
import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.scrapper.notebooks.Notebook;
import com.vassbassapp.scrapper.notebooks.NotebookScrapersMap;
import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.ui.console.listener.CommandListener;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Queue;

public class ScrapNotebookCommandListener implements CommandListener {
    private static final String HELP_MESSAGE = """
                [scrap-notebook source-list]            -   shows available source for scraping notebooks
                [scrap-notebook start {source_name}]    -   start scraping of notebooks from source""";
    private static final String NOT_FIND_SOURCE_MESSAGE = """
            Scraper for this source is not exists
            Enter [scrap-notebook source-list] to see all available sources""";


    //Processed commands
    private static final String SOURCE_LIST = "source-list";
    private static final String START = "start";

    @Override
    public void process(Queue<String> commands) {
        if (commands.isEmpty()) {
            ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
            return;
        }

        String command = commands.poll();
        switch (command.toLowerCase(Locale.ROOT)) {
            case SOURCE_LIST -> showAllSources();
            case START -> scrapping(commands);

            default -> ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
        }
    }

    private void showAllSources() {
        ColoredPrinter.printlnSeparator();
        NotebookScrapersMap.getInstance().keySet().forEach(System.out::println);
        ColoredPrinter.printlnSeparator();
    }

    private void scrapping(Queue<String> commands) {
        if (commands.isEmpty()) {
            ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
            return;
        }

        String source = commands.poll();
        AbstractExtractor<Notebook> extractor = NotebookScrapersMap.getInstance().get(source);
        if (Objects.isNull(extractor)) {
            ColoredPrinter.printlnRed(NOT_FIND_SOURCE_MESSAGE);
        } else {
            ColoredPrinter.printlnPurple("Start scrapping!");
            List<Notebook> result = extractor.extract();
            String fileName = String.format("notebooks(%s).json", source);
            JsonWriter.getInstance().writeToFile(fileName, result);
            ColoredPrinter.printlnPurple("Scrapping ends");
            CustomLogger.getInstance().writeLogFile();
        }
    }

    @Override
    public void printHelp() {
        ColoredPrinter.println(HELP_MESSAGE);
    }
}
