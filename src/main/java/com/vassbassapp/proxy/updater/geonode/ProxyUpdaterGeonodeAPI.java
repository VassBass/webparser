package com.vassbassapp.proxy.updater.geonode;

import com.vassbassapp.json.JsonReader;
import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.updater.ProxyUpdater;
import com.vassbassapp.ui.console.ColoredPrinter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class ProxyUpdaterGeonodeAPI implements ProxyUpdater {
    private static final String URL =
            "https://proxylist.geonode.com/api/proxy-list?limit=500&page=1&sort_by=lastChecked&sort_type=desc&protocols=http&anonymityLevel=anonymous";

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
                    .map(e -> new ProxyEntity(e.get("ip").toString(), Integer.parseInt(e.get("port").toString())))
                    .collect(Collectors.toList()));
            ColoredPrinter.printlnGreen("Update proxies ... Success");
            return true;
        } catch (IOException e) {
            ColoredPrinter.printlnRed("Proxies update error ... " + e.getMessage());
            return false;
        }
    }
}
