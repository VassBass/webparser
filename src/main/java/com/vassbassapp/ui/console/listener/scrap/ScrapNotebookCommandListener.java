package com.vassbassapp.ui.console.listener.scrap;

import com.vassbassapp.json.JsonWriter;
import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.scrapper.notebooks.dto.Notebook;
import com.vassbassapp.scrapper.notebooks.NotebookScrapersMap;
import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.ui.console.listener.CommandListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
            LocalDate date = LocalDate.now();
            LocalDateTime time = LocalDateTime.now();
            String message = String.format("Start scraping at - %s.%s.%s/%s:%s",
                    date.getDayOfMonth(),
                    date.getMonthValue(),
                    date.getYear(),
                    time.getHour(),
                    time.getMinute());
            ColoredPrinter.printlnPurple(message);
            List<Notebook> result = new ArrayList<>(extractor.extract());

            ColoredPrinter.print("Writing output to json ... ");
            String fileName = String.format("notebooks(%s).json", source);
            String folderName = String.format("output/%s/notebook", source);
            if (JsonWriter.getInstance().writeToFile(folderName, fileName, result)) {
                ColoredPrinter.printlnGreen("Success " + fileName);
            } else ColoredPrinter.printlnRed(ERROR_MESSAGE);

            date = LocalDate.now();
            time = LocalDateTime.now();
            message = String.format("Scrapping ends at - %s.%s.%s/%s:%s",
                    date.getDayOfMonth(),
                    date.getMonthValue(),
                    date.getYear(),
                    time.getHour(),
                    time.getMinute());
            ColoredPrinter.printlnPurple(message);
        }
    }

    @Override
    public void printHelp() {
        ColoredPrinter.println(HELP_MESSAGE);
    }
}
