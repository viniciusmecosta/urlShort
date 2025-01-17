package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "url", uniqueConstraints = @UniqueConstraint(columnNames = "urlOriginal"))
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String urlOriginal;

    public Url() {
    }

    public Url(String urlOriginal) {
        this.urlOriginal = urlOriginal;
    }

    public String getUrlOriginal() {
        return urlOriginal;
    }
    public void setUrlOriginal(String urlOriginal) {
        this.urlOriginal = urlOriginal;
    }
}