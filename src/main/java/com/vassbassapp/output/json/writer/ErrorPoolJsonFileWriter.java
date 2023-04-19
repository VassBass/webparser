package com.vassbassapp.output.json.writer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class ErrorPoolJsonFileWriter extends JsonFileWriter<Map<String, Exception>> {
    private static volatile ErrorPoolJsonFileWriter instance;

    private ErrorPoolJsonFileWriter(String fileName) {
        super(fileName);
    }

    public static ErrorPoolJsonFileWriter getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (ErrorPoolJsonFileWriter.class) {
                if (Objects.isNull(instance)){
                    LocalDate currentDate = LocalDate.now();
                    LocalDateTime currentTime = LocalDateTime.now();
                    String fileName = String.format("%s_%s_%s_(%s_%s).log",
                            currentDate.getDayOfMonth(),
                            currentDate.getMonthValue(),
                            currentDate.getYear(),
                            currentTime.getHour(),
                            currentTime.getMinute());
                    instance = new ErrorPoolJsonFileWriter(fileName);
                }
            }
        }
        return instance;
    }
}
