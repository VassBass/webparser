package com.vassbassapp.scrapper.notebooks;

import com.vassbassapp.config.ApplicationConfigHolder;
import com.vassbassapp.logger.CustomLogger;
import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.proxy.manager.ProxyManager;
import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public abstract class AbstractNotebookExtractor extends AbstractExtractor<Notebook> {
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";
    protected final CustomLogger logger = CustomLogger.getInstance();

    private final String baseUrl;
    private final String urlSelector;

    public AbstractNotebookExtractor(String baseUrl, String urlSelector) {
        this.baseUrl = baseUrl;
        this.urlSelector = urlSelector;
    }

    protected List<String> extractItemsUrls(String baseUrl, String urlSelector) {
        List<String> result = new ArrayList<>();

        try {
            Document document = Jsoup.newSession().url(baseUrl).get();
            Elements elements = document.select(urlSelector);
            for (Element element : elements) {
                String url = element.absUrl("href");
                if (Strings.notEmpty(url)) {
                    result.add(url);
                }
            }
            logger.scrapedSuccessful(baseUrl);
        } catch (IOException e) {
            logger.errorWhileScrapping(baseUrl, e);
        }

        return result;
    }

    public abstract String extractTitle(Document document);
    public abstract String extractPrice(Document document);
    public abstract String extractPresence(Document document);
    public abstract String extractModelCPU(Document document);
    public abstract String extractSizeRAM(Document document);
    public abstract String extractMainStorage(Document document);
    public abstract String extractMainOS(Document document);

    @Override
    public List<Notebook> extract() {
        List<String> urls = extractItemsUrls(baseUrl, urlSelector);

        ApplicationConfigHolder configHolder = ApplicationConfigHolder.getInstance();
        List<Notebook> result = new ArrayList<>();
        int threadPoolSize = configHolder.getThreadPoolSize();
        ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);

        List<Callable<Notebook>> callables = new ArrayList<>();
        try {
            BlockingQueue<ProxyEntity> proxies = new LinkedBlockingQueue<>(ProxyManager.getAllProxy());
            for (String url : urls) callables.add(tryToScrap(url, proxies));

            List<Future<Notebook>> futures = service.invokeAll(callables);
            for (Future<Notebook> f : futures) {
                Notebook notebook = f.get();
                if (Objects.isNull(notebook)) continue;
                result.add(notebook);
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            logger.errorWhileScrapping(baseUrl, e);
        }
        service.shutdown();

        return result;
    }

    private Callable<Notebook> tryToScrap(String url, BlockingQueue<ProxyEntity> proxies) {
        return () -> {
            while (!proxies.isEmpty()) {
                ProxyEntity proxy = proxies.take();
                try {
                    Document document = Jsoup.newSession()
                            .userAgent(USER_AGENT)
                            .referrer("http://www.google.com")
                            .proxy(proxy.getIp(), proxy.getPort())
                            .url(url)
                            .get();

                    String title = extractTitle(document);
                    String price = extractPrice(document);
                    String presence = extractPresence(document);
                    String modelCPU = extractModelCPU(document);
                    String sizeRAM = extractSizeRAM(document);
                    String mainStorage = extractMainStorage(document);
                    String mainOS = extractMainOS(document);

                    Notebook notebook = Notebook.builder()
                            .link(url)
                            .title(Strings.notEmpty(title) ? title : UNKNOWN)
                            .price(Strings.notEmpty(price) ? price : UNKNOWN)
                            .presence(Strings.notEmpty(presence) ? presence : UNKNOWN)
                            .modelCPU(Strings.notEmpty(modelCPU) ? modelCPU : UNKNOWN)
                            .sizeRAM(Strings.notEmpty(sizeRAM) ? sizeRAM : UNKNOWN)
                            .mainStorage(Strings.notEmpty(mainStorage) ? mainStorage : UNKNOWN)
                            .mainOS(Strings.notEmpty(mainOS) ? mainOS : UNKNOWN)
                            .build();
                    logger.scrapedSuccessful(url);
                    proxies.put(proxy);
                    return notebook;
                } catch (Exception e) {
                    logger.errorWhileScrapping(url, e);
                }
            }
            logger.errorWhileScrapping("Proxy list is empty!", null);
            return null;
        };
    }
}
