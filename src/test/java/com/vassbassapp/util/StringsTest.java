package com.vassbassapp.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringsTest {

    @Test
    public void testNotEmpty() {
        assertTrue(Strings.notEmpty("empty"));
        assertTrue(Strings.notEmpty("empty", "null", "."));

        assertFalse(Strings.notEmpty("empty", "", "null"));
        assertFalse(Strings.notEmpty(""));
        assertFalse(Strings.notEmpty("empty", null, "null"));
    }

    @Test
    public void testContainsAllIgnoreCase() {
        assertTrue(Strings.containsAllIgnoreCase("This test string for testing", "this", "For", "TeSt"));
        assertTrue(Strings.containsAllIgnoreCase("This test string for testing", "STRING", " ", "", "s test s"));

        assertFalse(Strings.containsAllIgnoreCase("This test string for testing", "stringsfor"));
        assertFalse(Strings.containsAllIgnoreCase("This test string for testing", null, "for"));
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(Strings.containsIgnoreCase("This test string for testing", "TEST strIng"));
        assertTrue(Strings.containsIgnoreCase("This test string for testing", "g fOr T"));
        assertTrue(Strings.containsIgnoreCase("This test string for testing", ""));
        assertTrue(Strings.containsIgnoreCase("This test string for testing", " "));

        assertFalse(Strings.containsIgnoreCase("This test string for testing", "g fOr Tt"));
        assertFalse(Strings.containsIgnoreCase("This test string for testing", "a"));
    }

    @Test
    public void testIsInt() {
        assertTrue(Strings.isInt("8"));
        assertTrue(Strings.isInt("888"));
        assertTrue(Strings.isInt("8_888"));

        assertFalse(Strings.isInt("8.88"));
        assertFalse(Strings.isInt("8-88"));
        assertFalse(Strings.isInt("8,88"));
        assertFalse(Strings.isInt("88888888888888888888888888888888888888888"));
        assertFalse(Strings.isInt("int"));
        assertFalse(Strings.isInt(null));
    }
}