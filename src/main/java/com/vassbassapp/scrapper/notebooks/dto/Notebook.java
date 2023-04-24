package com.vassbassapp.scrapper.notebooks.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class Notebook {
    private final String link;
    private String title;
    private String price;
    private String presence;
    private String modelCPU;
    private String sizeRAM;
    private String mainStorage;
    private String mainOS;
}
