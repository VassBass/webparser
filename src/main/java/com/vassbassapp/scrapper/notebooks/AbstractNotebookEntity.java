package com.vassbassapp.scrapper.notebooks;

import com.vassbassapp.scrapper.AbstractEntity;
import com.vassbassapp.util.Strings;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class AbstractNotebookEntity extends AbstractEntity<Notebook> {
    private final Notebook notebook;

    public AbstractNotebookEntity(String shop, String url) throws IOException {
        super(url);
        notebook = new Notebook(shop, url);
    }

    public abstract String extractTitle(Document document);
    public abstract String extractPrice(Document document);
    public abstract String extractPresence(Document document);
    public abstract String extractModelCPU(Document document);
    public abstract String extractSizeRAM(Document document);
    public abstract String extractMainStorage(Document document);
    public abstract String extractMainOS(Document document);

    @Override
    public Notebook create() {
        String title = extractTitle(document);
        String price = extractPrice(document);
        String presence = extractPresence(document);
        String modelCPU = extractModelCPU(document);
        String sizeRAM = extractSizeRAM(document);
        String mainStorage = extractMainStorage(document);
        String mainOS = extractMainOS(document);

        return notebook
                .setTitle(Strings.notEmpty(title) ? title : UNKNOWN)
                .setPrice(Strings.notEmpty(price) ? price : UNKNOWN)
                .setPresence(Strings.notEmpty(presence) ? presence : UNKNOWN)
                .setModelCPU(Strings.notEmpty(modelCPU) ? modelCPU : UNKNOWN)
                .setSizeRAM(Strings.notEmpty(sizeRAM) ? sizeRAM : UNKNOWN)
                .setMainStorage(Strings.notEmpty(mainStorage) ? mainStorage : UNKNOWN)
                .setMainOS(Strings.notEmpty(mainOS) ? mainOS : UNKNOWN);
    }
}
