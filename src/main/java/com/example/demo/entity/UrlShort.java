package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "urlshort")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlShort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String urlShort;

    @ManyToOne
    private Url urlOriginal;

    public UrlShort(String shortUrl, Url url) {
        this.urlShort = shortUrl;
        this.urlOriginal = url;
    }

    public String getUrlShort() {
        return urlShort;
    }
}