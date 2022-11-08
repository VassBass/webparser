package com.vassbassapp.ui;

import java.util.Scanner;

public class FirstCommandListener implements CommandListener {
    private static final String apartmentsFull = "apartments";
    private static final String apartments = "/a";

    private static final String housesFull = "houses";
    private static final String houses = "/h";

    private static final String helpFull = "help";
    private static final String help = "/hlp";

    private static final String exitFull = "exit";
    private static final String exit = "/e";

    private final CommandWorker worker = new CommandWorkerImpl();

    @Override
    public void startListen(Scanner scanner) {
        String command = scanner.next();
        switch (command) {
            case apartments, apartmentsFull -> {
                worker.searchApartments();
                startListen(scanner);
            }
            case houses, housesFull -> {
                worker.searchHouses();
                startListen(scanner);
            }
            case help, helpFull -> {
                worker.printHelp();
                startListen(scanner);
            }
            case exit, exitFull -> System.out.println("Goodbye!");
            default -> {
                System.out.println("Invalid input!");
                System.out.println("To show help enter \"help\" or \"/h\"");
                startListen(scanner);
            }
        }
    }
}
