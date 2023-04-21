package com.vassbassapp.proxy.manager;

import com.vassbassapp.json.JsonReader;
import com.vassbassapp.proxy.ProxyEntity;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProxyManager {
    private static final File PROXY_FILE = new File("proxy.json");
    private static final String KEY_IP = "ip";
    private static final String KEY_PORT = "port";

    @SuppressWarnings("unchecked")
    public static List<ProxyEntity> getAllProxy() throws IOException {
        if (PROXY_FILE.exists()) {
            List<Map<String, Object>> proxyEntries = JsonReader.getInstance().readFromFile(PROXY_FILE, List.class);
            if (Objects.nonNull(proxyEntries)) return proxyEntries.stream()
                    .map(e -> new ProxyEntity(e.get(KEY_IP).toString(), Integer.parseInt(e.get(KEY_PORT).toString())))
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}
