package com.vassbassapp.proxy.updater.geonode;

import com.vassbassapp.json.JsonReader;
import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.updater.ProxyUpdater;
import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.util.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class ProxyUpdaterGeonodeAPI implements ProxyUpdater {
    private static final String URL =
            "https://proxylist.geonode.com/api/proxy-list?limit=500&page=1&sort_by=lastChecked&sort_type=desc&protocols=http&anonymityLevel=anonymous";
    private static final String IP_KEY = "ip";
    private static final String PORT_KEY = "port";

    private final BlockingQueue<ProxyEntity> queue;

    public ProxyUpdaterGeonodeAPI(BlockingQueue<ProxyEntity> queue) {
        this.queue = queue;
    }

    @Override
    public boolean update() {
        ColoredPrinter.printlnPurple("Start update proxies");

        try (InputStream in = new URL(URL).openConnection().getInputStream()) {
            GeonodeProxyWrap wrap = JsonReader.getInstance().readFromStream(in, GeonodeProxyWrap.class);
            queue.addAll(wrap.getData().stream()
                    .map(this::parseProxy)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));

            ColoredPrinter.printlnGreen("Update proxies ... Success");
            return true;
        } catch (IOException e) {
            ColoredPrinter.printlnRed("Proxies update error ... " + e.getMessage());
            return false;
        }
    }

    private ProxyEntity parseProxy(Map<String, Object> entry) {
        String ip = entry.get(IP_KEY).toString();
        String port = entry.get(PORT_KEY).toString();
        if (Strings.notEmpty(ip, port) && Strings.isInt(port)) {
            return new ProxyEntity(ip, Integer.parseInt(port));
        } else return null;
    }
}
