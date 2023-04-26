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

public class XlsxWriter {
    private static final String GET = "get";
    private static final String IS = "is";
    private static final String CLASS = "class";

    private final Class<?> clazz;
    protected Workbook book;

    public XlsxWriter(Class<?> clazz) {
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
                String toInsert = null;
                for (int x = 0; x < 4; x++) {
                    try {
                        if (x == 0){
                            toInsert = (String) clazz.getMethod(getters.get(c)).invoke(output.get(i));
                            break;
                        } else if (x == 1) {
                            toInsert = String.valueOf((int) clazz.getMethod(getters.get(c)).invoke(output.get(i)));
                            break;
                        } else if (x == 2) {
                            toInsert = String.valueOf((double) clazz.getMethod(getters.get(c)).invoke(output.get(i)));
                            break;
                        } else {
                            toInsert = String.valueOf((boolean) clazz.getMethod(getters.get(c)).invoke(output.get(i)));
                            break;
                        }
                    } catch (ClassCastException ignore) {}
                }

                cell.setCellValue(Objects.isNull(toInsert) ? Strings.EMPTY : toInsert);
            }
        }
    }

    public void saveToFile(String fileName) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            book.write(out);
            book.close();
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
                .filter(m -> m.startsWith(GET) || m.startsWith(IS))
                .filter(m -> !m.toLowerCase(Locale.ROOT).contains(CLASS))
                .collect(Collectors.toList());
    }
}
