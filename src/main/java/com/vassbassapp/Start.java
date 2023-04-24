package com.vassbassapp;

import com.vassbassapp.ui.console.listener.MainCommandListener;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        new MainCommandListener(new Scanner(System.in));
    }
}
