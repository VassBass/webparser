package com.vassbassapp.ui.console;

public class ColoredPrinter {
    private static final String RESET = "\u001B[0m";

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String PURPLE = "\u001B[35m";

    public static void printlnRed(String message){
        System.out.println(RED + message + RESET);
    }
    public static void printlnGreen(String message){
        System.out.println(GREEN + message + RESET);
    }
    public static void printlnPurple(String message){
        System.out.println(PURPLE + message + RESET);
    }
    public static void println(String message) {
        System.out.println(message);
    }
    public static void printlnSeparator(){
        System.out.println("----------");
    }

    public static void printRed(String message){
        System.out.print(RED + message + RESET);
    }
    public static void printGreen(String message){
        System.out.print(GREEN + message + RESET);
    }
    public static void printPurple(String message){
        System.out.print(PURPLE + message + RESET);
    }
    public static void print(String message) {
        System.out.print(message);
    }
    public static void printSeparator(){
        System.out.print("----------");
    }
}
