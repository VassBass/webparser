package com.vassbassapp.builder;

import com.vassbassapp.dto.Realty;

public interface RealtyBuilder {
    RealtyBuilder setName(String name);
    RealtyBuilder setPrice(String price);
    RealtyBuilder setPlacement(String placement);
    RealtyBuilder setSquare(String square);
    RealtyBuilder setLink(String link);
    Realty build();
}
