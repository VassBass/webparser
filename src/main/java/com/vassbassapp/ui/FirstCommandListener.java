package com.vassbassapp.ui;

import java.util.Scanner;

public class FirstCommandListener implements CommandListener {
    private static final String apartmentsFull = "apartments";
    private static final String apartments = "/a";
    private static final String exitFull = "exit";
    private static final String exit = "/e";

    private final CommandWorker worker = new CommandWorkerImpl();

    @Override
    public void startListen(Scanner scanner) {
        while (true) {
            String command = scanner.next();
            if (command.equals(apartments) || command.equals(apartmentsFull)) {
                worker.searchApartments();
            } else if (command.equals(exit) || command.equals(exitFull)) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid input!");
                System.out.println("To show help enter \"help\" or \"/h\"");
            }
        }
    }
}
