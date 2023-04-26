package com.vassbassapp.json;

import com.vassbassapp.TestClass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JsonWriterTest {

    private JsonWriter writer;
    List<TestClass> testClassList;

    @Before
    public void setUp() {
        writer = JsonWriter.getInstance();

        testClassList = List.of(
                new TestClass("first", 1, 1.11, true),
                new TestClass("second", 2, 2.22, true),
                new TestClass("third", 3, 3.33, false),
                new TestClass("fourth", 4, 4.44, false)
        );
    }

    @Test
    public void testGetInstance() {
        assertSame(writer, JsonWriter.getInstance());
    }

    @Test
    public void testWriteToFile() {
        writer.writeToFile("temp.json", testClassList);
    }

    @Test
    public void testWriteToFileInFolder() {
        writer.writeToFile("output", "temp.json", testClassList);
    }
}