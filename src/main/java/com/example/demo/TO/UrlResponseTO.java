package com.example.demo.TO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UrlResponseTO {
    private String urlOriginal;
    private String urlShort;

    public UrlResponseTO(String urlOriginal, String urlShort) {
        this.urlOriginal = urlOriginal;
        this.urlShort = urlShort;
    }
}
