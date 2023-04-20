package com.vassbassapp.proxy;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ProxyEntity {
    private String ip;
    private String port;

    @Override
    public String toString() {
        return String.format("%s : %s", ip, port);
    }
}
