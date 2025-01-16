package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlShort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String urlShort;

    @ManyToOne
    private Url urlOriginal;

    public String getUrlShort() {
        return urlShort;
    }
}

