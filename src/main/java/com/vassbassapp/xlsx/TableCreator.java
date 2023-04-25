package com.vassbassapp.xlsx;

import com.vassbassapp.util.Strings;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class TableCreator {
    private static final String GET = "get";
    private static final String CLASS = "class";

    private final Class<?> clazz;
    protected Workbook book;

    public TableCreator(Class<?> clazz) {
        this.clazz = clazz;
        createHeader();
    }

    public void fillTable(List<?> output) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < output.size(); i++) {
            List<String> getters = getGettersNames();
            Sheet sheet = book.getSheet(clazz.getSimpleName());
            Row row = sheet.createRow(i + 1);

            for (int c = 0; c < getters.size(); c++) {
                Cell cell = row.createCell(c);
                String value = (String) clazz.getMethod(getters.get(c)).invoke(output.get(i));
                cell.setCellValue(Objects.isNull(value) ? Strings.EMPTY : value);
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveToFile(String folderName, String fileName) throws IOException {
        new File(folderName).mkdirs();
        try (FileOutputStream out = new FileOutputStream(new File(folderName, fileName))) {
            book.write(out);
            book.close();
        }
    }

    private void createHeader() {
        if (Objects.isNull(book)) book = new XSSFWorkbook();

        List<String> headerStrings = getGettersNames().stream()
                .map(m -> m.replaceAll(GET, Strings.EMPTY))
                .collect(Collectors.toList());
        Sheet sheet = book.createSheet(clazz.getSimpleName());

        Row header = sheet.createRow(0);

        CellStyle headerStyle = book.createCellStyle();
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) book).createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < headerStrings.size(); i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headerStrings.get(i));
            cell.setCellStyle(headerStyle);
        }
    }

    private List<String> getGettersNames() {
        return Arrays.stream(clazz.getMethods())
                .map(Method::getName)
                .filter(m -> m.contains(GET))
                .filter(m -> !m.toLowerCase(Locale.ROOT).contains(CLASS))
                .collect(Collectors.toList());
    }
}
