package com.vassbassapp.json;

import com.vassbassapp.TestClass;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class JsonReaderTest {

    private JsonReader reader;

    @Before
    public void setUp() {
        reader = JsonReader.getInstance();
    }

    @Test
    public void testGetInstance() {
        assertSame(reader, JsonReader.getInstance());
    }

    @Test
    public void testRead() {
        String json = """
                {
                    "string" : "string",
                    "intNumber" : 8,
                    "doubleNumber" : 8.88,
                    "booleanValue" : true
                }""";
        TestClass expected = new TestClass("string", 8, 8.88, true);

        assertEquals(expected, reader.read(json, TestClass.class));
    }

    @Test
    public void testReadFromStream() throws IOException {
        String json = """
                {
                    "string" : "string",
                    "intNumber" : 8,
                    "doubleNumber" : 8.88,
                    "booleanValue" : true
                }""";
        TestClass expected = new TestClass("string", 8, 8.88, true);

        assertEquals(expected, reader.readFromStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)), TestClass.class));
    }

    @Test
    public void testReadFromFile() throws IOException {
        String testFile = "src/test/resources/test.json";
        TestClass expected = new TestClass("string", 8, 8.88, true);

        assertEquals(expected, reader.readFromFile(new File(testFile), TestClass.class));
    }
}