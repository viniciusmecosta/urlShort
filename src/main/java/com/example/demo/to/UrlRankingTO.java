package com.example.demo.to;

public class UrlRankingTO {
    private String url;

    private int count;

    public UrlRankingTO(String url, int count) {
        this.url = url;
        this.count = count;
    }

    public String getUrl() {
        return this.url;
    }
    public int getCount() {
        return this.count;
    }
}
