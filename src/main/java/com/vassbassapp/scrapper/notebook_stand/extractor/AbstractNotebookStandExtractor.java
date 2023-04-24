package com.vassbassapp.scrapper.notebook_stand.extractor;

import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.scrapper.notebook_stand.dto.NotebookStand;
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

public abstract class AbstractNotebookStandExtractor extends AbstractExtractor<NotebookStand> {
    private static final String USER_AGENT =
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";

    protected final String baseUrl;
    protected final String urlSelector;

    public AbstractNotebookStandExtractor(String baseUrl, String urlSelector) {
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
    public abstract String extractModel(Document document);
    public abstract String extractFunctions(Document document);
    public abstract String extractDiagonal(Document document);
    public abstract String extractProducerCountry(Document document);
    public abstract String extractDescription(Document document);
    public abstract String extractMaterial(Document document);

    @Override
    public Collection<NotebookStand> extract() {
        BlockingQueue<String> urls = new LinkedBlockingQueue<>(extractItemsUrls());
        BlockingQueue<ProxyEntity> proxies = new LinkedBlockingQueue<>();
        BlockingQueue<NotebookStand> stands = new LinkedBlockingQueue<>();

        Thread proxyUpdate = new Thread(updateProxy(proxies));
        proxyUpdate.start();

        ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);

        List<Callable<Void>> callables = new ArrayList<>();
        try {
            for (int i = 0; i < threadPoolSize; i++) callables.add(tryToScrap(urls, proxies, stands));
            service.invokeAll(callables);
        } catch (InterruptedException e) {
            ColoredPrinter.printlnRed(String.format("Scrapped %s ... %s", baseUrl, e.getMessage()));
        }
        service.shutdown();
        proxyUpdate.interrupt();

        return stands;
    }

    private Callable<Void> tryToScrap(BlockingQueue<String> urls,
                                      BlockingQueue<ProxyEntity> proxies,
                                      BlockingQueue<NotebookStand> stands) {
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
                            .get();

                    String title = extractTitle(document);
                    String price = extractPrice(document);
                    String presence = extractPresence(document);
                    String model = extractModel(document);
                    String functions = extractFunctions(document);
                    String diagonal = extractDiagonal(document);
                    String producerCountry = extractProducerCountry(document);
                    String description = extractDescription(document);
                    String material = extractMaterial(document);

                    NotebookStand stand = NotebookStand.builder()
                            .link(url)
                            .title(Strings.notEmpty(title) ? title : UNKNOWN)
                            .price(Strings.notEmpty(price) ? price : UNKNOWN)
                            .presence(Strings.notEmpty(presence) ? presence : UNKNOWN)
                            .model(Strings.notEmpty(model) ? model : UNKNOWN)
                            .functions(Strings.notEmpty(functions) ? functions : UNKNOWN)
                            .diagonal(Strings.notEmpty(diagonal) ? diagonal : UNKNOWN)
                            .producerCountry(Strings.notEmpty(producerCountry) ? producerCountry : UNKNOWN)
                            .description(Strings.notEmpty(description) ? description : UNKNOWN)
                            .material(Strings.notEmpty(material) ? material : UNKNOWN)
                            .build();
                    ColoredPrinter.printlnGreen(String.format("Scrapped %s ... Success", url));
                    proxies.put(proxy);
                    stands.put(stand);
                } catch (Exception e) {
                    ColoredPrinter.printlnRed(String.format("Scrapped %s ... %s", url, e.getMessage()));
                }
            }
            return null;
        };
    }
}
