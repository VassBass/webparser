package com.vassbassapp.scrapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.Objects;

public class ConnectionHolder {
    private ConnectionHolder(){}

    private static volatile Connection connection;
    public static Connection getConnection() {
        if (Objects.isNull(connection)) {
            synchronized (ConnectionHolder.class) {
                if (Objects.isNull(connection)) {
                    connection = Jsoup.newSession();
                }
            }
        }
        return connection;
    }
}
