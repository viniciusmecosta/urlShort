package com.example.demo.TO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UrlRequestTO {
    private String urlOriginal;

    public String getUrlOriginal() {
        return urlOriginal;
    }
}
