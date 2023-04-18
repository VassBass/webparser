package com.vassbassapp.scrapper.notebooks.moyo;

import com.vassbassapp.scrapper.notebooks.AbstractNotebookExtractor;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MoyoNotebookExtractor extends AbstractNotebookExtractor {
    private static final String URL = "https://www.moyo.ua/ua/comp-and-periphery/notebooks/";
    private static final String URL_SELECTOR = ".product-item_name";

    private static final String TITLE_SELECTOR = ".product_name";
    private static final String PRICE_SELECTOR = ".product_price_current";
    private static final String PRESENCE_SELECTOR = ".product_availability_status";
    private static final String MODEL_CPU_SELECTOR = ".product_characteristics_list_item:contains(Процесор (модель)) .value";
    private static final String SIZE_RAM_SELECTOR = ".product_characteristics_list_item:contains(Оперативна пам'ять (об'єм) .value";
    private static final String MAIN_STORAGE_SELECTOR = ".product_characteristics_list_item:contains(Вбудований накопичувач):not(:contains(швид)) .value";
    private static final String MAIN_OS_SELECTOR = ".product_characteristics_list_item:contains(Передвстановлена ОС) .value";

    public MoyoNotebookExtractor() throws IOException {
        super(URL, URL_SELECTOR);
    }

    @Override
    public String extractTitle(Document document) {
        return getText(document.select(TITLE_SELECTOR));
    }

    @Override
    public String extractPrice(Document document) {
        return getText(document.select(PRICE_SELECTOR));
    }

    @Override
    public String extractPresence(Document document) {
        return getText(document.select(PRESENCE_SELECTOR));
    }

    @Override
    public String extractModelCPU(Document document) {
        return getText(document.select(MODEL_CPU_SELECTOR));
    }

    @Override
    public String extractSizeRAM(Document document) {
        return getText(document.select(SIZE_RAM_SELECTOR));
    }

    @Override
    public String extractMainStorage(Document document) {
        return getText(document.select(MAIN_STORAGE_SELECTOR));
    }

    @Override
    public String extractMainOS(Document document) {
        return getText(document.select(MAIN_OS_SELECTOR));
    }
}
