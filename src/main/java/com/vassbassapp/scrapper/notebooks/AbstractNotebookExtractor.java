package com.vassbassapp.scrapper.notebooks;

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
import java.util.stream.Collectors;

public abstract class AbstractNotebookExtractor extends AbstractExtractor<Notebook> {
    private final List<String> urls;

    public AbstractNotebookExtractor(String baseUrl, String urlSelector) throws IOException {
        Document document = Jsoup.newSession().url(baseUrl).get();
        urls = new ArrayList<>();
        Elements elements = document.select(urlSelector);
        for (Element element : elements) {
            String url = element.absUrl("href");
            if (Strings.notEmpty(url)) {
                urls.add(url);
            }
        }
    }

    public abstract String extractTitle(Document document);
    public abstract String extractPrice(Document document);
    public abstract String extractPresence(Document document);
    public abstract String extractModelCPU(Document document);
    public abstract String extractSizeRAM(Document document);
    public abstract String extractMainStorage(Document document);
    public abstract String extractMainOS(Document document);

    @Override
    public List<Notebook> extract() throws InterruptedException {
        List<Callable<Notebook>> callables = new ArrayList<>();
        for (String url : urls) {
            callables.add(() -> {
                System.out.printf("Read notebook from \"%s\"\n", url);
                Document document = Jsoup.newSession().url(url).get();

                String title = extractTitle(document);
                String price = extractPrice(document);
                String presence = extractPresence(document);
                String modelCPU = extractModelCPU(document);
                String sizeRAM = extractSizeRAM(document);
                String mainStorage = extractMainStorage(document);
                String mainOS = extractMainOS(document);

                return new Notebook(url)
                        .setTitle(Strings.notEmpty(title) ? title : UNKNOWN)
                        .setPrice(Strings.notEmpty(price) ? price : UNKNOWN)
                        .setPresence(Strings.notEmpty(presence) ? presence : UNKNOWN)
                        .setModelCPU(Strings.notEmpty(modelCPU) ? modelCPU : UNKNOWN)
                        .setSizeRAM(Strings.notEmpty(sizeRAM) ? sizeRAM : UNKNOWN)
                        .setMainStorage(Strings.notEmpty(mainStorage) ? mainStorage : UNKNOWN)
                        .setMainOS(Strings.notEmpty(mainOS) ? mainOS : UNKNOWN);
            });
        }

        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Future<Notebook>> futures = service.invokeAll(callables);

        List<Notebook> result = futures.stream()
                .map(f -> {
                    try {
                        return f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        service.shutdown();

        return result;
    }
}
