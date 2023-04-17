package com.vassbassapp.util;

import java.util.Objects;

public class Strings {
    public static boolean notEmpty(String str) {
        return Objects.nonNull(str) && !str.isBlank();
    }
}
