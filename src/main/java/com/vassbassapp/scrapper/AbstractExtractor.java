package com.vassbassapp.scrapper;

import com.vassbassapp.config.ApplicationConfigHolder;
import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.updater.ProxyUpdater;
import com.vassbassapp.proxy.updater.geonode.ProxyUpdaterGeonodeAPI;
import com.vassbassapp.util.Strings;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class AbstractExtractor<E> {
    public static final String UNKNOWN = "Unknown";

    protected final int threadPoolSize;

    public AbstractExtractor() {
        threadPoolSize = ApplicationConfigHolder.getInstance().getThreadPoolSize();
    }

    protected String getText(Elements elements) {
        if (elements.isEmpty()) return Strings.EMPTY;
        return elements.stream()
                .map(Element::text)
                .collect(Collectors.joining(" "));
    }

    public abstract Collection<E> extract();

    protected Runnable updateProxy(BlockingQueue<ProxyEntity> proxyEntities) {
        return () -> {
            ProxyUpdater updater = new ProxyUpdaterGeonodeAPI(proxyEntities);
            while (true) {
                if (proxyEntities.size() < threadPoolSize) {
                    if (!updater.update()) System.exit(1);
                }

                try { TimeUnit.SECONDS.sleep(2); }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1); }
            }
        };
    }
}
