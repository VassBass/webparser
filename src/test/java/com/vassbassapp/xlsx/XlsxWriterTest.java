package com.vassbassapp.xlsx;

import com.vassbassapp.TestClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class XlsxWriterTest {

    private XlsxWriter writer;
    private List<TestClass> testClassList;

    @Before
    public void setUp() {
        writer = new XlsxWriter(TestClass.class);

        testClassList = List.of(
                new TestClass("first", 1, 1.11, true),
                new TestClass("second", 2, 2.22, true),
                new TestClass("third", 3, 3.33, false),
                new TestClass("fourth", 4, 4.44, false)
        );
    }

    @Test
    public void testFillTable() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        writer.fillTable(testClassList);
    }

    @Test
    public void testSaveToFile() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        writer.fillTable(testClassList);
        writer.saveToFile("temp.xlsx");
    }

    @Test
    public void testSaveToFileInFolder() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        writer.fillTable(testClassList);
        writer.saveToFile("output", "temp.xlsx");
    }
}