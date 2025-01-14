package com.example.demo.TO;

import com.example.demo.entity.Url;

public record UrlShortTO(
        Long id,
        String urlShort,
        Url url
) {
}
