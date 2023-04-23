package com.vassbassapp.ui.console.listener;

import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.ui.console.listener.scrap.ScrapNotebookCommandListener;

import java.util.*;

public class MainCommandListener implements CommandListener {
    private static final String WELCOME_MESSAGE = """
            Welcome! This is demo of vasscraper!
            Enter [help] to see all the commands.
            To close program enter [close]""";
    private static final String HELP_MESSAGE = """
            [help]  -   shows all available commands
            [close] -   closes the application""";

    private static final String SPACE_SPLITTER = "\\s";

    //Processed commands
    private static final String CLOSE = "close";
    private static final String HELP = "help";
    private static final String SCRAP_NOTEBOOK = "scrap-notebook";

    private final CommandListener scrapCommandListener;

    @SuppressWarnings("InfiniteLoopStatement")
    public MainCommandListener(Scanner listener) {
        scrapCommandListener = new ScrapNotebookCommandListener();
        ColoredPrinter.printlnPurple(WELCOME_MESSAGE);
        while (true) {
            process(new LinkedList<>(Arrays.asList(listener.nextLine().split(SPACE_SPLITTER))));
        }
    }

    @Override
    public void process(Queue<String> commands) {
        if (commands.isEmpty()) {
            ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
            return;
        }

        String command = commands.poll();
        switch (command.toLowerCase(Locale.ROOT)) {
            case CLOSE -> System.exit(0);
            case HELP -> printHelp();

            case SCRAP_NOTEBOOK -> scrapCommandListener.process(commands);

            default -> ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
        }
    }

    @Override
    public void printHelp() {
        scrapCommandListener.printHelp();
        ColoredPrinter.printlnSeparator();
        ColoredPrinter.println(HELP_MESSAGE);
    }
}
