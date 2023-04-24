package com.vassbassapp.scrapper.notebook_stand.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class NotebookStand {
    private String link;
    private String title;
    private String price;
    private String presence;
    private String model;
    private String functions;
    private String diagonal;
    private String producerCountry;
    private String description;
    private String material;
}
