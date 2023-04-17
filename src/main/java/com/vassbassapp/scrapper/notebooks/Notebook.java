package com.vassbassapp.scrapper.notebooks;

import java.util.Objects;

public class Notebook {
    private final String shop;
    private final String link;
    private String title;
    private String price;
    private String presence;
    private String modelCPU;
    private String sizeRAM;
    private String mainStorage;
    private String mainOS;

    public Notebook(String shop, String link) {
        this.shop = shop;
        this.link = link;
    }

    public String getShop() {
        return shop;
    }

    public String getTitle() {
        return title;
    }

    public Notebook setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLink() {
        return link;
    }

    public String getPrice() {
        return price;
    }

    public Notebook setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getPresence() {
        return presence;
    }

    public Notebook setPresence(String presence) {
        this.presence = presence;
        return this;
    }

    public String getModelCPU() {
        return modelCPU;
    }

    public Notebook setModelCPU(String modelCPU) {
        this.modelCPU = modelCPU;
        return this;
    }

    public String getSizeRAM() {
        return sizeRAM;
    }

    public Notebook setSizeRAM(String sizeRAM) {
        this.sizeRAM = sizeRAM;
        return this;
    }

    public String getMainStorage() {
        return mainStorage;
    }

    public Notebook setMainStorage(String mainStorage) {
        this.mainStorage = mainStorage;
        return this;
    }

    public String getMainOS() {
        return mainOS;
    }

    public Notebook setMainOS(String mainOS) {
        this.mainOS = mainOS;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notebook notebook = (Notebook) o;
        return link.equals(notebook.link) &&
                title.equals(notebook.title) &&
                Objects.equals(price, notebook.price) &&
                Objects.equals(presence, notebook.presence) &&
                Objects.equals(modelCPU, notebook.modelCPU) &&
                Objects.equals(sizeRAM, notebook.sizeRAM) &&
                Objects.equals(mainStorage, notebook.mainStorage) &&
                Objects.equals(mainOS, notebook.mainOS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, title, price, presence, modelCPU, sizeRAM, mainStorage, mainOS);
    }
}
