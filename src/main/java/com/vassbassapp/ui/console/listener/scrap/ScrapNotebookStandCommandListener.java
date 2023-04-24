package com.vassbassapp.ui.console.listener.scrap;

import com.vassbassapp.json.JsonWriter;
import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.scrapper.notebook_stand.NotebookStandScrapersMap;
import com.vassbassapp.scrapper.notebook_stand.dto.NotebookStand;
import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.ui.console.listener.CommandListener;

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
            ColoredPrinter.printlnPurple("Start scrapping!");
            List<NotebookStand> result = new ArrayList<>(extractor.extract());

            ColoredPrinter.print("Writing output to json ... ");
            String fileName = String.format("notebook_stands(%s).json", source);
            if (JsonWriter.getInstance().writeToFile(fileName, result)) {
                ColoredPrinter.printlnGreen("Success " + fileName);
            } else ColoredPrinter.printlnRed(ERROR_MESSAGE);

            ColoredPrinter.printlnPurple("Scrapping ends");
        }
    }

    @Override
    public void printHelp() {
        ColoredPrinter.println(HELP_MESSAGE);
    }
}
