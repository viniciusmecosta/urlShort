package com.example.demo.repository;

import com.example.demo.entity.Url;
import com.example.demo.entity.UrlShort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlShortRepository extends JpaRepository<UrlShort, Long> {
    UrlShort findByUrlOriginal(Url urlOriginal);
}
