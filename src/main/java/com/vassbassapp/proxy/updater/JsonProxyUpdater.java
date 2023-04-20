package com.vassbassapp.proxy.updater;

import com.vassbassapp.json.JsonWriter;
import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.ui.console.ColoredPrinter;

import java.util.List;

public abstract class JsonProxyUpdater implements ProxyUpdater {
    private static final String FILE_NAME = "proxy.json";

    public boolean saveProxyList(List<ProxyEntity> proxyList) {
        ColoredPrinter.print("Saving result to json file ... ");
        if (JsonWriter.getInstance().writeToFile(FILE_NAME, proxyList)) {
            ColoredPrinter.printlnGreen("Success");
            return true;
        } else return false;
    }
}
