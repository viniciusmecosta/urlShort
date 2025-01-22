package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String urlOriginal;

    @Column(unique = true, nullable = false)
    private String urlShort;

    public Url() {
    }

    public Url(String urlOriginal, String urlShort) {
        this.urlOriginal = urlOriginal;
        this.urlShort = urlShort;
    }

    public String getUrlOriginal() {
        return urlOriginal;
    }

    public String getUrlShort() {
        return urlShort;
    }
}