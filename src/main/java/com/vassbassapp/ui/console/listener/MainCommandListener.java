package com.vassbassapp.ui.console.listener;

import com.vassbassapp.ui.console.ColoredPrinter;

import java.util.*;

public class MainCommandListener {
    public static final String INVALID_INPUT_MESSAGE = "Invalid input! Enter [help] to see all the commands.";
    public static final String ERROR_MESSAGE = "An error has occurred";
    private static final String WELCOME_MESSAGE = """
            Welcome! This is demo of vasscraper!
            Enter [help] to see all the commands.
            To close program enter [close]""";
    public static final String HELP_MESSAGE = """
                [proxy list]    -   shows list of available proxy
                [proxy update]  -   update list of available proxy
                [proxy count]   -   shows count of available proxy""";

    private static final String SPACE_SPLITTER = "\\s";

    //Processed commands
    private static final String CLOSE = "close";
    private static final String HELP = "help";
    private static final String PROXY = "proxy";

    private ProxyCommandListener proxyCommandListener;

    @SuppressWarnings("InfiniteLoopStatement")
    public MainCommandListener(Scanner listener) {
        ColoredPrinter.printlnPurple(WELCOME_MESSAGE);
        while (true) {
            delegate(new ArrayList<>(Arrays.asList(listener.nextLine().split(SPACE_SPLITTER))));
        }
    }

    private void delegate(List<String> commands) {
        if (commands.isEmpty()) {
            ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
            return;
        }

        String command = commands.get(0);
        switch (command.toLowerCase(Locale.ROOT)) {
            case CLOSE -> System.exit(0);
            case HELP -> ColoredPrinter.printlnPurple(HELP_MESSAGE);

            case PROXY -> {
                if (Objects.isNull(proxyCommandListener)) proxyCommandListener = new ProxyCommandListener();
                commands.remove(0);
                proxyCommandListener.process(commands);
            }

            default -> ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
        }
    }
}
