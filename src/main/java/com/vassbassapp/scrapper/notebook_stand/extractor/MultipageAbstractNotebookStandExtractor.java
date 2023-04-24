package com.vassbassapp.scrapper.notebook_stand.extractor;

import com.vassbassapp.proxy.ProxyEntity;
import com.vassbassapp.ui.console.ColoredPrinter;
import com.vassbassapp.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public abstract class MultipageAbstractNotebookStandExtractor extends AbstractNotebookStandExtractor {
    private static final String PAGE_URL_SELECTOR = "meta[property*=url]";
    private static final String PAGE_URL_ATTR = "content";
    private static final String USER_AGENT =
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";
    private static final String PAGE_PREFIX = "?page=";

    public MultipageAbstractNotebookStandExtractor(String baseUrl, String urlSelector) {
        super(baseUrl, urlSelector);
    }

    @Override
    protected Collection<String> extractItemsUrls() {
        BlockingQueue<String> urls = new LinkedBlockingQueue<>();
        BlockingQueue<ProxyEntity> proxies = new LinkedBlockingQueue<>();

        Thread proxyUpdate = new Thread(updateProxy(proxies));
        proxyUpdate.start();

        ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);
        try {
            List<Callable<Void>> callables = new ArrayList<>(threadPoolSize);
            for (int i = 0; i < threadPoolSize;) {
                callables.add(getUrlsFromPage(++i, proxies, urls));
            }
            service.invokeAll(callables);
        } catch (InterruptedException e) {
            ColoredPrinter.printlnRed(String.format("Scrapped %s ... %s", baseUrl, e.getMessage()));
        }
        service.shutdown();
        proxyUpdate.interrupt();

        return urls;
    }

    private Callable<Void> getUrlsFromPage(int pageNum,
                                           BlockingQueue<ProxyEntity> proxies,
                                           BlockingQueue<String> urls) {
        return () -> {
            int currentPage = pageNum;
            while (true) {
                StringBuilder pageUrlBuilder = new StringBuilder(baseUrl);
                if (baseUrl.charAt(baseUrl.length() - 1) != '/') pageUrlBuilder.append("/");
                pageUrlBuilder.append(PAGE_PREFIX).append(currentPage);
                final String pageUrl = pageUrlBuilder.toString();

                ProxyEntity proxy = proxies.take();
                try {
                    Document document = Jsoup.newSession()
                            .userAgent(USER_AGENT)
                            .referrer("http://www.google.com")
                            .proxy(proxy.getIp(), proxy.getPort())
                            .url(pageUrl)
                            .get();
                    String current = document.select(PAGE_URL_SELECTOR).attr(PAGE_URL_ATTR);
                    if ((currentPage == 1 && current.equals(baseUrl)) || current.equals(pageUrl)) {
                        Elements elements = document.select(urlSelector);
                        for (Element element : elements) {
                            String url = element.absUrl("href");
                            if (Strings.notEmpty(url)) {
                                urls.put(url);
                            }
                        }
                        ColoredPrinter.printlnGreen(String.format("Scrapped %s ... Success", current));
                        proxies.put(proxy);
                        currentPage += threadPoolSize;
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    ColoredPrinter.printlnRed(String.format("Scrapped %s ... %s", pageUrl, e.getMessage()));
                }
            }
            return null;
        };
    }
}
