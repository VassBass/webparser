package com.vassbassapp.proxy.updater.proxyscrape;

import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.updater.ProxyUpdater;
import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.util.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class ProxyUpdaterProxyscrapeAPI implements ProxyUpdater {
    private static final String URL =
            "https://api.proxyscrape.com/v2/?request=displayproxies&protocol=http&timeout=30000&country=all&ssl=all&anonymity=anonymous";
    private static final String PROXY_ENTITY_SPLITTER = "\r\n";

    private final BlockingQueue<ProxyEntity> queue;

    public ProxyUpdaterProxyscrapeAPI(BlockingQueue<ProxyEntity> queue) {
        this.queue = queue;
    }

    
    /**
     * The update function is responsible for updating the proxy list.
     * It does so by downloading a new list of proxies from the URL, parsing it and adding them to the queue.
     
     *
     *
     * @return A boolean value, which is true if the update was successful and false otherwise
     */
    @Override
    public boolean update() {
        ColoredPrinter.printlnPurple("Start update proxies");

        try (InputStream in = new URL(URL).openConnection().getInputStream()) {
            String response = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            queue.addAll(parseProxy(response));
            ColoredPrinter.printlnGreen("Update proxies ... Success");
            return true;
        } catch (IOException e) {
            ColoredPrinter.printlnRed("Proxies update error ... " + e.getMessage());
            return false;
        }
    }

    
    /**
     * The parseProxy function takes a String as an argument and returns a Collection of ProxyEntity objects.
     * The function splits the input string by the PROXY_ENTITY_SPLITTER constant, which is \r\n.
     * It then maps each element in the resulting array to a new ProxyEntity object if it contains at least one colon (\r\n) character,
     * and if that colon separates two non-empty strings where the second string can be parsed into an integer.  If these conditions are not met, null is returned instead of a new ProxyEntity object.  
     * Finally, all null values are filtered out from this collection before returning
     *
     * @param entry Split the string into an array of strings
     *
     * @return A collection of proxyentity objects
     */
    private Collection<ProxyEntity> parseProxy(String entry) {
        String[] proxies = entry.split(PROXY_ENTITY_SPLITTER);
        return Arrays.stream(proxies)
                .map(e -> {
                    int colonIndex = e.indexOf(':');
                    if (colonIndex >= 0) {
                        String ip = e.substring(0, colonIndex);
                        String port = e.substring(++colonIndex);
                        if (Strings.notEmpty(ip, port) && Strings.isInt(port)) {
                            return new ProxyEntity(ip, Integer.parseInt(port));
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
