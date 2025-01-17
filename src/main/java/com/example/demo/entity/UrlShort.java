package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "urlshort", uniqueConstraints = @UniqueConstraint(columnNames = "urlShort"))
public class UrlShort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String urlShort;

    @ManyToOne
    private Url urlOriginal;

    public UrlShort(String shortUrl, Url url) {
        this.urlShort = shortUrl;
        this.urlOriginal = url;
    }
    public UrlShort() {}
    public String getUrlShort() {
        return urlShort;
    }

    public Url getUrlOriginal() {
        return urlOriginal;
    }
}