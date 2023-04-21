package com.vassbassapp.ui.console.listener;

import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.manager.ProxyManager;
import com.vassbassapp.proxy.updater.geonode.ProxyUpdaterGeonodeAPI;
import com.vassbassapp.ui.console.ColoredPrinter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.vassbassapp.ui.console.listener.MainCommandListener.ERROR_MESSAGE;
import static com.vassbassapp.ui.console.listener.MainCommandListener.INVALID_INPUT_MESSAGE;

public class ProxyCommandListener {
    private static final String EMPTY_LIST_MESSAGE = """
            Proxy list is empty
            Enter [proxy update] to update proxy list""";

    //Processed commands
    private static final String LIST = "list";
    private static final String UPDATE = "update";
    private static final String COUNT = "count";

    public void process(List<String> commands) {
        if (commands.isEmpty()) {
            ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
        }

        String command = commands.get(0);
        switch (command.toLowerCase(Locale.ROOT)) {
            case LIST -> showList();
            case UPDATE -> updateList();
            case COUNT -> showCount();

            default -> ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
        }
    }

    private void showList() {
        try {
            List<ProxyEntity> proxyEntities = ProxyManager.getAllProxy();
            if (proxyEntities.isEmpty()) {
                ColoredPrinter.println(EMPTY_LIST_MESSAGE);
            } else {
                ColoredPrinter.printlnSeparator();
                proxyEntities.forEach(System.out::println);
                ColoredPrinter.printlnSeparator();
            }
        } catch (IOException e) {
            ColoredPrinter.printlnRed(ERROR_MESSAGE);
        }
    }

    private void updateList() {
        if (ProxyUpdaterGeonodeAPI.getInstance().update()) {
            ColoredPrinter.printlnGreen("Proxy list was update successfully");
        }
    }

    private void showCount() {
        try {
            ColoredPrinter.println(String.format("Proxy count = %s", ProxyManager.getAllProxy().size()));
        } catch (IOException e) {
            ColoredPrinter.printlnRed(ERROR_MESSAGE);
        }
    }
}
