package com.example.demo.repository;

import com.example.demo.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByUrlOriginal(String urlOriginal);
}
