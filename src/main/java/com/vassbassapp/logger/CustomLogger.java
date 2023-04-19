package com.vassbassapp.logger;

import com.vassbassapp.output.json.writer.ErrorPoolJsonFileWriter;

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

    private final Map<String, Exception> errorPool = new HashMap<>();

    public void scrapedSuccessful(String url) {
        System.out.printf("Scrapped %s ... Success\n", url);
    }

    public void errorWhileScrapping(String url, Exception error) {
        errorPool.put(url, error);
        System.err.printf("Scrapped %s ... Error\n", url);
    }

    public void writeLogFile() {
        if (errorPool.isEmpty()) {
            System.out.println("Error pool is empty");
        } else {
            ErrorPoolJsonFileWriter jsonFileWriter = ErrorPoolJsonFileWriter.getInstance();
            jsonFileWriter.write(errorPool);
            System.err.println("Error pool is not empty! See the log file!");
        }
    }
}
