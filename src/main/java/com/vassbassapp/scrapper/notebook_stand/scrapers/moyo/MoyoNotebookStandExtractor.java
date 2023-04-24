package com.vassbassapp.scrapper.notebook_stand.scrapers.moyo;

import com.vassbassapp.scrapper.notebook_stand.extractor.MultipageAbstractNotebookStandExtractor;
import org.jsoup.nodes.Document;

public class MoyoNotebookStandExtractor extends MultipageAbstractNotebookStandExtractor {
    private static final String BASE_URL = "https://www.moyo.ua/ua/acsessor/acsessor_for_comp/stands/";
    private static final String URL_SELECTOR = ".product-item_name";

    private static final String TITLE_SELECTOR = ".product_name";
    private static final String PRICE_SELECTOR = ".product_price_current";
    private static final String PRESENCE_SELECTOR = ".product_availability_status";
    private static final String MODEL_SELECTOR = ".product_characteristics_list_item:has(.key:contains(Модель)) .value";
    private static final String FUNCTIONS_SELECTOR = ".product_characteristics_list_item:has(.key:contains(Функції)) .value";
    private static final String DIAGONAL_SELECTOR = ".product_characteristics_list_item:has(.key:contains(діагональ)) .value";
    private static final String PRODUCER_COUNTRY_SELECTOR = ".product_characteristics_list_item:has(.key:contains(Країна-виробник)) .value";
    private static final String DESCRIPTION_SELECTOR = ".product_description_content";
    private static final String MATERIAL_SELECTOR = ".product_characteristics_list_item:has(.key:contains(Матеріал)) .value";

    public MoyoNotebookStandExtractor() {
        super(BASE_URL, URL_SELECTOR);
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
    public String extractModel(Document document) {
        return getText(document.select(MODEL_SELECTOR));
    }

    @Override
    public String extractFunctions(Document document) {
        return getText(document.select(FUNCTIONS_SELECTOR));
    }

    @Override
    public String extractDiagonal(Document document) {
        return getText(document.select(DIAGONAL_SELECTOR));
    }

    @Override
    public String extractProducerCountry(Document document) {
        return getText(document.select(PRODUCER_COUNTRY_SELECTOR));
    }

    @Override
    public String extractDescription(Document document) {
        return getText(document.select(DESCRIPTION_SELECTOR));
    }

    @Override
    public String extractMaterial(Document document) {
        return getText(document.select(MATERIAL_SELECTOR));
    }
}
