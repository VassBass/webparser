package com.vassbassapp.proxy.updater.geonode;

import com.vassbassapp.proxy.ProxyEntity;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class ProxyUpdaterGeonodeAPITest {

    @Test
    public void testUpdate() {
        BlockingQueue<ProxyEntity> testQueue = new LinkedBlockingQueue<>();
        new ProxyUpdaterGeonodeAPI(testQueue).update();

        assertFalse(testQueue.isEmpty());
    }
}