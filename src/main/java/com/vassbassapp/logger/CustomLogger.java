package com.vassbassapp.logger;

import com.vassbassapp.json.JsonWriter;
import com.vassbassapp.ui.console.ColoredPrinter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomLogger {
    private static volatile CustomLogger instance;
    private CustomLogger(){}
    public static CustomLogger getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (CustomLogger.class) {
                if (Objects.isNull(instance)) instance = new CustomLogger();
            }
        }
        return instance;
    }

    private final Map<String, String> errorPool = new HashMap<>();

    public void scrapedSuccessful(String url) {
        ColoredPrinter.printlnGreen(String.format("Scrapped %s ... Success", url));
    }

    public void errorWhileScrapping(String url, Exception error) {
        errorPool.put(url, error.getMessage());
        ColoredPrinter.printlnRed(String.format("Scrapped %s ... Error", url));
    }

    public void writeLogFile() {
        if (errorPool.isEmpty()) {
            ColoredPrinter.printlnPurple("Error pool is empty");
        } else {
            LocalDate currentDate = LocalDate.now();
            LocalDateTime currentTime = LocalDateTime.now();
            String fileName = String.format("%s_%s_%s_(%s_%s).log",
                    currentDate.getDayOfMonth(),
                    currentDate.getMonthValue(),
                    currentDate.getYear(),
                    currentTime.getHour(),
                    currentTime.getMinute());
            JsonWriter.getInstance().writeToFile(fileName, errorPool);
            ColoredPrinter.printlnRed("Error pool is not empty! See the log file!");
        }
    }
}
