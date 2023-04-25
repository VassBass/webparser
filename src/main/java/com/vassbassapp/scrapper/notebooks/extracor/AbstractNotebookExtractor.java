package com.vassbassapp.scrapper.notebooks.extracor;

import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.scrapper.notebooks.dto.Notebook;
import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public abstract class AbstractNotebookExtractor extends AbstractExtractor<Notebook> {
    private static final String USER_AGENT =
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";

    protected final String baseUrl;
    protected final String urlSelector;

    public AbstractNotebookExtractor(String baseUrl, String urlSelector) {
        super();
        this.baseUrl = baseUrl;
        this.urlSelector = urlSelector;
    }

    protected Collection<String> extractItemsUrls() {
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
            ColoredPrinter.printlnGreen(String.format("Scrapped %s ... Success", baseUrl));
        } catch (IOException e) {
            ColoredPrinter.printlnRed(String.format("Scrapped %s ... %s", baseUrl, e.getMessage()));
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
    public Collection<Notebook> extract() {
        BlockingQueue<String> urls = new LinkedBlockingQueue<>(extractItemsUrls());
        BlockingQueue<Notebook> notebooks = new LinkedBlockingQueue<>();

        Thread proxyUpdate = new Thread(updateProxy());
        proxyUpdate.start();

        ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);

        List<Callable<Void>> callables = new ArrayList<>();
        try {
            for (int i = 0; i < threadPoolSize; i++) callables.add(tryToScrap(urls, proxies, notebooks));
            service.invokeAll(callables);
        } catch (InterruptedException e) {
            ColoredPrinter.printlnRed(String.format("Scrapped %s ... %s", baseUrl, e.getMessage()));
        }
        service.shutdown();
        proxyUpdate.interrupt();

        return notebooks;
    }

    private Callable<Void> tryToScrap(BlockingQueue<String> urls,
                                          BlockingQueue<ProxyEntity> proxies,
                                          BlockingQueue<Notebook> notebooks) {
        return () -> {
            while (!urls.isEmpty()) {
                ProxyEntity proxy = proxies.take();
                String url = urls.take();
                try {
                    Document document = Jsoup.newSession()
                            .userAgent(USER_AGENT)
                            .referrer("http://www.google.com")
                            .proxy(proxy.getIp(), proxy.getPort())
                            .url(url)
                            .timeout(60_000)
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
                    ColoredPrinter.printlnGreen(String.format("Scrapped %s ... Success", url));
                    proxies.put(proxy);
                    notebooks.put(notebook);
                } catch (Exception e) {
                    urls.put(url);
                    ColoredPrinter.printlnRed(String.format("Scrapped %s ... %s", url, e.getMessage()));
                }
            }
            return null;
        };
    }
}
