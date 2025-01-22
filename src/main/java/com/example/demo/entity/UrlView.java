package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "urlview")
public class UrlView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String url;

    private String date;

    public UrlView() {
    }

    public UrlView(String urlShort, String string) {
        this.url = urlShort;
        this.date = string;
    }

    public String getUrl() {
        return url;
    }

    public String getDate(){
        return date;
    }
}
