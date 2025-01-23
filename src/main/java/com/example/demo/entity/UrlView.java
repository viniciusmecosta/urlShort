package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "urlview")
@Getter
@Setter
@NoArgsConstructor

public class UrlView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String url;

    private String date;

    public UrlView(String url, String date) {
        this.url = url;
        this.date = date;
    }
}
