package com.vassbassapp.output.json;

import java.util.List;

public class JsonWrap<I> {
    private final int item_count;
    private final List<I> items;

    public JsonWrap(List<I> items) {
        item_count = items.size();
        this.items = items;
    }

    public int getItem_count() {
        return item_count;
    }

    public List<I> getItems() {
        return items;
    }
}
