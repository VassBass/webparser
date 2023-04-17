package com.vassbassapp.util;

import java.util.Locale;
import java.util.Objects;

public class Strings {
    public static boolean notEmpty(String str) {
        return Objects.nonNull(str) && !str.isBlank();
    }

    public static boolean containsAllIgnoreCase(String source, String ... contains) {
        for (String c : contains) {
            boolean con = source.toLowerCase(Locale.ROOT).contains(c.toLowerCase(Locale.ROOT)) &&
                    source.toUpperCase(Locale.ROOT).contains(c.toUpperCase(Locale.ROOT));
            if (!con) return false;
        }
        return true;
    }

    public static boolean containsIgnoreCase(String source, String contains) {
        return source.toLowerCase(Locale.ROOT).contains(contains.toLowerCase(Locale.ROOT)) &&
                source.toUpperCase(Locale.ROOT).contains(contains.toUpperCase(Locale.ROOT));
    }
}
