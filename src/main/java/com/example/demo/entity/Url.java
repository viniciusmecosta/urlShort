package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String urlOriginal;

    public Url(String originalUrl) {
        this.urlOriginal = originalUrl;
    }


    public String getUrlOriginal() {
        return urlOriginal;
    }
}
