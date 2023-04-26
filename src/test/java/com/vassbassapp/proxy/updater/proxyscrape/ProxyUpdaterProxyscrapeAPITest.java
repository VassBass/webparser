package com.vassbassapp.proxy.updater.proxyscrape;

import com.vassbassapp.proxy.ProxyEntity;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class ProxyUpdaterProxyscrapeAPITest {

    @Test
    public void testUpdate() {
        BlockingQueue<ProxyEntity> testQueue = new LinkedBlockingQueue<>();
        new ProxyUpdaterProxyscrapeAPI(testQueue).update();

        assertFalse(testQueue.isEmpty());
    }
}