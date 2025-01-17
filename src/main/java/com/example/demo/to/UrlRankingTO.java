package com.example.demo.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlRankingTO {
    @JsonProperty("url")
    private String url;

    @JsonProperty("count")
    private int count;

    public UrlRankingTO(String url, int count) {
        this.url = url;
        this.count = count;
    }
}
