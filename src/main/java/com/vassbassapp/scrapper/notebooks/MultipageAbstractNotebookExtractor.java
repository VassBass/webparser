package com.vassbassapp.scrapper.notebooks;

import com.vassbassapp.config.ApplicationConfigHolder;
import com.vassbassapp.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public abstract class MultipageAbstractNotebookExtractor extends AbstractNotebookExtractor {
    private static final String PAGE_URL_SELECTOR = "meta[property*=url]";
    private static final String PAGE_URL_ATTR = "content";

    public MultipageAbstractNotebookExtractor(String baseUrl, String urlSelector) {
        super(baseUrl, urlSelector);
        System.out.println();
    }

    @Override
    protected List<String> extractItemsUrls(String baseUrl, String urlSelector) {
        ApplicationConfigHolder configHolder = ApplicationConfigHolder.getInstance();
        int threadPoolSize = configHolder.getThreadPoolSize();

        int firstPage = 1;
        List<String> result = new ArrayList<>();

        boolean hasNext = true;
        while (hasNext) {
                try {
                    List<Callable<List<String>>> callables = new ArrayList<>(threadPoolSize);
                    for (int i = 0; i < threadPoolSize; i++) {
                        final int currentPageNum = firstPage + i;
                        callables.add(getUrlsFromPage(baseUrl, currentPageNum, urlSelector));
                    }
                    ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);
                    List<Future<List<String>>> futures = service.invokeAll(callables);

                    for (Future<List<String>> f : futures) {
                        List<String> urls = f.get();
                        if (urls.isEmpty()) hasNext = false;
                        else result.addAll(urls);
                    }

                    firstPage += threadPoolSize;
                } catch (ExecutionException | InterruptedException e) {
                    int lastPage = firstPage + (threadPoolSize-1);
                    String url = String.format("%s - pages %s-%s", baseUrl, firstPage, lastPage);
                    logger.errorWhileScrapping(url, e);
                    hasNext = false;
                }
            }

        return result;
    }

    private Callable<List<String>> getUrlsFromPage(String baseUrl, int pageNum, String urlSelector) {
        return () -> {
            StringBuilder pageUrlBuilder = new StringBuilder(baseUrl);
            if (baseUrl.charAt(baseUrl.length() - 1) != '/') pageUrlBuilder.append("/");
            pageUrlBuilder.append("?page=").append(pageNum);
            final String pageUrl = pageUrlBuilder.toString();
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(1,6));
                    Document document = Jsoup.newSession().url(pageUrl).get();
                    String current = document.select(PAGE_URL_SELECTOR).attr(PAGE_URL_ATTR);
                    if ((pageNum == 1 && current.equals(baseUrl)) || current.equals(pageUrl)) {
                        List<String> threadResult = new ArrayList<>();
                        Elements elements = document.select(urlSelector);
                        for (Element element : elements) {
                            String url = element.absUrl("href");
                            if (Strings.notEmpty(url)) {
                                threadResult.add(url);
                            }
                        }
                        logger.scrapedSuccessful(current);
                        return threadResult;
                    } else return Collections.emptyList();
                } catch (Exception e) {
                    if (i == 4) logger.errorWhileScrapping(pageUrl, e);
                }
            }
            return Collections.emptyList();
        };
    }


}
