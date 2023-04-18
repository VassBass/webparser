package com.vassbassapp.scrapper.notebooks;

import com.vassbassapp.scrapper.AbstractExtractor;
import com.vassbassapp.scrapper.ConnectionHolder;
import com.vassbassapp.util.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNotebookExtractor extends AbstractExtractor<Notebook> {
    private final List<Notebook> notebooks;

    public AbstractNotebookExtractor(String baseUrl, String urlSelector) throws IOException {
        Document document = ConnectionHolder.getConnection().url(baseUrl).get();
        notebooks = new ArrayList<>();
        Elements elements = document.select(urlSelector);
        for (Element element : elements) {
            String url = element.absUrl("href");
            if (Strings.notEmpty(url)) {
                notebooks.add(new Notebook(url));
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
    public List<Notebook> extract() throws IOException {
        for (Notebook notebook : notebooks) {
            String url = notebook.getLink();
            System.out.printf("Read notebook from \"%s\"\n", url);
            Document document = ConnectionHolder.getConnection().url(url).get();

            String title = extractTitle(document);
            String price = extractPrice(document);
            String presence = extractPresence(document);
            String modelCPU = extractModelCPU(document);
            String sizeRAM = extractSizeRAM(document);
            String mainStorage = extractMainStorage(document);
            String mainOS = extractMainOS(document);

            notebook
                    .setTitle(Strings.notEmpty(title) ? title : UNKNOWN)
                    .setPrice(Strings.notEmpty(price) ? price : UNKNOWN)
                    .setPresence(Strings.notEmpty(presence) ? presence : UNKNOWN)
                    .setModelCPU(Strings.notEmpty(modelCPU) ? modelCPU : UNKNOWN)
                    .setSizeRAM(Strings.notEmpty(sizeRAM) ? sizeRAM : UNKNOWN)
                    .setMainStorage(Strings.notEmpty(mainStorage) ? mainStorage : UNKNOWN)
                    .setMainOS(Strings.notEmpty(mainOS) ? mainOS : UNKNOWN);
        }
        return notebooks;
    }
}
