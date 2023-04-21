package com.vassbassapp.proxy.updater.geonode;

import com.vassbassapp.json.JsonReader;
import com.vassbassapp.proxy.updater.JsonProxyUpdater;
import com.vassbassapp.ui.console.ColoredPrinter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProxyUpdaterGeonodeAPI extends JsonProxyUpdater {
    private static final String URL =
            "https://proxylist.geonode.com/api/proxy-list?limit=500&page=1&sort_by=lastChecked&sort_type=desc&protocols=http&anonymityLevel=anonymous";

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
            List<Map<String, Object>> proxyEntities = wrap.getData();
            ColoredPrinter.printlnGreen("Success");
            return super.saveProxyList(proxyEntities);
        } catch (IOException e) {
            ColoredPrinter.printlnRed("An error has occurred");
            e.printStackTrace();
            return false;
        }
    }
}
