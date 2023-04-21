package com.vassbassapp.ui.console.listener;

import java.util.Queue;

public interface CommandListener {
    String INVALID_INPUT_MESSAGE = "Invalid input! Enter [help] to see all the commands.";
    String ERROR_MESSAGE = "An error has occurred";

    void process(Queue<String> commands);
    void printHelp();
}
