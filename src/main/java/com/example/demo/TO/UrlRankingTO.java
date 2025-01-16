package com.example.demo.TO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UrlRankingTO {
    private String url;
    private int count;

    public UrlRankingTO(String url, int count) {
        this.url = url;
        this.count = count;
    }
}
