package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String url;

    private String date;

    public UrlView(String urlShort, String string) {
        this.url = urlShort;
        this.date = string;
    }

    public String getUrl() {
        return url;
    }
}
