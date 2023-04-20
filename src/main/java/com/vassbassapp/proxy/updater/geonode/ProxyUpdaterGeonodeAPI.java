package com.vassbassapp.proxy.updater.geonode;

import com.vassbassapp.json.JsonReader;
import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.updater.JsonProxyUpdater;
import com.vassbassapp.ui.console.ColoredPrinter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProxyUpdaterGeonodeAPI extends JsonProxyUpdater {
    private static final String URL =
            "https://proxylist.geonode.com/api/proxy-list?limit=500&page=1&sort_by=lastChecked&sort_type=desc&protocols=http";
    private static final String KEY_IP = "ip";
    private static final String KEY_PORT = "port";

    private static volatile ProxyUpdaterGeonodeAPI instance;

    private ProxyUpdaterGeonodeAPI(){}

    public static ProxyUpdaterGeonodeAPI getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (ProxyUpdaterGeonodeAPI.class) {
                if (Objects.isNull(instance)) instance = new ProxyUpdaterGeonodeAPI();
            }
        }
        return instance;
    }

    @Override
    public boolean update() {
        ColoredPrinter.print("Sending request to API ... ");
        try (InputStream in = new URL(URL).openConnection().getInputStream()) {
            GeonodeProxyWrap wrap = JsonReader.getInstance().readFromStream(in, GeonodeProxyWrap.class);
            ColoredPrinter.printlnGreen("Success");
            ColoredPrinter.print("Mapping of API response ... ");
            List<ProxyEntity> proxyEntities = wrap.getData().stream()
                    .map(e -> new ProxyEntity(e.get(KEY_IP).toString(), e.get(KEY_PORT).toString()))
                    .collect(Collectors.toList());
            ColoredPrinter.printlnGreen("Success");
            return super.saveProxyList(proxyEntities);
        } catch (IOException e) {
            ColoredPrinter.printlnRed("An error has occurred");
            e.printStackTrace();
            return false;
        }
    }
}
