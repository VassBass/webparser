package com.vassbassapp.ui.console.listener;

import com.vassbassapp.json.JsonReader;
import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.updater.geonode.ProxyUpdaterGeonodeAPI;
import com.vassbassapp.ui.console.ColoredPrinter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.vassbassapp.ui.console.listener.MainCommandListener.ERROR_MESSAGE;
import static com.vassbassapp.ui.console.listener.MainCommandListener.INVALID_INPUT_MESSAGE;

public class ProxyCommandListener {
    private static final String PROXY_FILE_NAME = "proxy.json";

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
        File proxyFile = new File(PROXY_FILE_NAME);
        if (proxyFile.exists()) {
            try {
                List<Map<String, Object>> proxyEntities = getProxyList(proxyFile);
                if (Objects.isNull(proxyEntities) || proxyEntities.isEmpty()) {
                    ColoredPrinter.println(EMPTY_LIST_MESSAGE);
                } else {
                    ColoredPrinter.printlnSeparator();
                    proxyEntities.stream()
                            .map(m -> new ProxyEntity(m.get("ip").toString(), m.get("port").toString()))
                            .forEach(System.out::println);
                    ColoredPrinter.printlnSeparator();
                }
            } catch (IOException e) {
                ColoredPrinter.printlnRed(ERROR_MESSAGE);
            }
        } else {
            ColoredPrinter.println(EMPTY_LIST_MESSAGE);
        }
    }

    private void updateList() {
        if (ProxyUpdaterGeonodeAPI.getInstance().update()) {
            ColoredPrinter.printlnGreen("Proxy list was update successfully");
        }
    }

    private void showCount() {
        int count = 0;
        File proxyFile = new File(PROXY_FILE_NAME);
        if (proxyFile.exists()) {
            try {
                List<Map<String, Object>> proxyEntities = getProxyList(proxyFile);
                if (Objects.nonNull(proxyEntities)) count = proxyEntities.size();
            } catch (IOException e) {
                ColoredPrinter.println(ERROR_MESSAGE);
            }
        }
        ColoredPrinter.println(String.format("Proxy count = %s", count));
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getProxyList(File proxyFile) throws IOException {
        return JsonReader.getInstance().readFromFile(proxyFile, List.class);
    }
}
