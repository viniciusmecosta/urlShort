package com.example.demo.TO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
