package com.vassbassapp.scrapper.notebooks;

import com.vassbassapp.config.ApplicationConfigHolder;
import com.vassbassapp.logger.CustomLogger;
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
    protected final CustomLogger logger = CustomLogger.getInstance();

    private final List<String> urls;
    private final String baseUrl;

    public AbstractNotebookExtractor(String baseUrl, String urlSelector) {
        this.baseUrl = baseUrl;
        urls = extractItemsUrls(baseUrl, urlSelector);
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
        ApplicationConfigHolder configHolder = ApplicationConfigHolder.getInstance();

        List<Callable<Notebook>> callables = new ArrayList<>();
        for (String url : urls) {
            callables.add(() -> {
                try {
                    Document document = Jsoup.newSession().url(url).get();

                    String title = extractTitle(document);
                    String price = extractPrice(document);
                    String presence = extractPresence(document);
                    String modelCPU = extractModelCPU(document);
                    String sizeRAM = extractSizeRAM(document);
                    String mainStorage = extractMainStorage(document);
                    String mainOS = extractMainOS(document);

                    Notebook notebook = new Notebook(url)
                            .setTitle(Strings.notEmpty(title) ? title : UNKNOWN)
                            .setPrice(Strings.notEmpty(price) ? price : UNKNOWN)
                            .setPresence(Strings.notEmpty(presence) ? presence : UNKNOWN)
                            .setModelCPU(Strings.notEmpty(modelCPU) ? modelCPU : UNKNOWN)
                            .setSizeRAM(Strings.notEmpty(sizeRAM) ? sizeRAM : UNKNOWN)
                            .setMainStorage(Strings.notEmpty(mainStorage) ? mainStorage : UNKNOWN)
                            .setMainOS(Strings.notEmpty(mainOS) ? mainOS : UNKNOWN);
                    logger.scrapedSuccessful(url);
                    return notebook;
                } catch (Exception e) {
                    logger.errorWhileScrapping(url, e);
                    return null;
                }
            });
        }

        int threadPoolSize = configHolder.getThreadPoolSize();
        ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);
        List<Notebook> result = new ArrayList<>();
        try {
            List<Future<Notebook>> futures = service.invokeAll(callables);
            for (Future<Notebook> f : futures) {
                Notebook notebook = f.get();
                if (Objects.isNull(notebook)) continue;
                result.add(notebook);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.errorWhileScrapping(baseUrl, e);
        }
        service.shutdown();

        return result;
    }
}
