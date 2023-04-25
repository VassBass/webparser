package com.vassbassapp.ui.console.listener.scrap;

import com.vassbassapp.json.JsonWriter;
import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.scrapper.notebook_stand.NotebookStandScrapersMap;
import com.vassbassapp.scrapper.notebook_stand.dto.NotebookStand;
import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.ui.console.listener.CommandListener;
import com.vassbassapp.xlsx.TableCreator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ScrapNotebookStandCommandListener implements CommandListener {
    private static final String HELP_MESSAGE = """
                [scrap-notebook-stand source-list]            -   shows available source for scraping notebook-stands
                [scrap-notebook-stand start {source_name}]    -   start scraping of notebook-stands from source""";
    private static final String NOT_FIND_SOURCE_MESSAGE = """
            Scraper for this source is not exists
            Enter [scrap-notebook-stand source-list] to see all available sources""";


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
        NotebookStandScrapersMap.getInstance().keySet().forEach(System.out::println);
        ColoredPrinter.printlnSeparator();
    }

    private void scrapping(Queue<String> commands) {
        if (commands.isEmpty()) {
            ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
            return;
        }

        String source = commands.poll();
        AbstractExtractor<NotebookStand> extractor = NotebookStandScrapersMap.getInstance().get(source);
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

            List<NotebookStand> result = new ArrayList<>(extractor.extract());

            String fileName = String.format("notebook_stands(%s)", source);
            String folderName = String.format("output/%s/notebook_stand", source);

            ColoredPrinter.print("Writing output to json ... ");
            String jsonFileName = fileName + ".json";
            if (JsonWriter.getInstance().writeToFile(folderName, jsonFileName, result)) {
                ColoredPrinter.printlnGreen("Success : " + jsonFileName);
            } else ColoredPrinter.printlnRed(ERROR_MESSAGE);

            ColoredPrinter.print("Writing output to xlsx ... ");
            String xlsxFileName = fileName + ".xlsx";
            try {
                TableCreator creator = new TableCreator(NotebookStand.class);
                creator.fillTable(result);
                creator.saveToFile(folderName, xlsxFileName);
                ColoredPrinter.printlnGreen("Success : " + xlsxFileName);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | IOException e) {
                ColoredPrinter.printlnRed(String.format("%s : %s", ERROR_MESSAGE, e.getMessage()));
            }


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
