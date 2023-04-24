package com.vassbassapp.proxy.updater;

import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.updater.geonode.ProxyUpdaterGeonodeAPI;
import com.vassbassapp.proxy.updater.proxyscrape.ProxyUpdaterProxyscrapeAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ProxyUpdaterManager implements ProxyUpdater {
    private List<ProxyUpdater> buffer = new ArrayList<>();

    private final BlockingQueue<ProxyEntity> proxyEntityQueue;
    int currentUpdaterIndex = 0;

    public ProxyUpdaterManager(BlockingQueue<ProxyEntity> proxyEntityQueue) {
        this.proxyEntityQueue = proxyEntityQueue;
        registerUpdaters();
    }

    @Override
    public boolean update() {
        if (currentUpdaterIndex >= buffer.size()) currentUpdaterIndex = 0;
        return buffer.get(currentUpdaterIndex++).update();
    }

    private void registerUpdaters() {
        buffer.add(new ProxyUpdaterProxyscrapeAPI(proxyEntityQueue));
        buffer.add(new ProxyUpdaterGeonodeAPI(proxyEntityQueue));
    }
}
