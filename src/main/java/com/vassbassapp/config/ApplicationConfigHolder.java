package com.vassbassapp.config;

import com.vassbassapp.util.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ApplicationConfigHolder {
    private static final String CONFIG_FILE = "application.properties";

    private static final String KEY_THREAD_POOL_SIZE = "thread.pool.size";

    private int threadPoolSize = 5;

    private static volatile ApplicationConfigHolder instance;
    private ApplicationConfigHolder(){ init(); }
    public static ApplicationConfigHolder getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (ApplicationConfigHolder.class) {
                if (Objects.isNull(instance)) instance = new ApplicationConfigHolder();
            }
        }
        return instance;
    }

    private void init() {
        try (InputStream in = ApplicationConfigHolder.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            Properties properties = new Properties();
            properties.load(in);

            String threadPoolSize = properties.getProperty(KEY_THREAD_POOL_SIZE);
            if (Strings.isInt(threadPoolSize)) this.threadPoolSize = Integer.parseInt(threadPoolSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }
}
