package com.vassbassapp.ui.console.listener.proxy;

import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.manager.ProxyManager;
import com.vassbassapp.proxy.updater.geonode.ProxyUpdaterGeonodeAPI;
import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.ui.console.listener.CommandListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

public class ProxyCommandListener implements CommandListener {
    private static final String EMPTY_LIST_MESSAGE = """
            Proxy list is empty
            Enter [proxy update] to update proxy list""";
    private static final String HELP_MESSAGE = """
                [proxy list]    -   shows list of available proxy
                [proxy update]  -   update list of available proxy
                [proxy count]   -   shows count of available proxy""";

    //Processed commands
    private static final String LIST = "list";
    private static final String UPDATE = "update";
    private static final String COUNT = "count";

    @Override
    public void process(Queue<String> commands) {
        if (commands.isEmpty()) {
            ColoredPrinter.printlnRed(INVALID_INPUT_MESSAGE);
            return;
        }

        String command = commands.poll();
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

    @Override
    public void printHelp() {
        ColoredPrinter.println(HELP_MESSAGE);
    }
}
