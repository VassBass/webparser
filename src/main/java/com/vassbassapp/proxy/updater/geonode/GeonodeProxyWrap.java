package com.vassbassapp.proxy.updater.geonode;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GeonodeProxyWrap {
    private List<Map<String, Object>> data;
}
