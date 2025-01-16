package com.example.demo.repository;

import com.example.demo.entity.Url;
import com.example.demo.entity.UrlShort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortRepository extends JpaRepository<UrlShort, Long> {
    UrlShort findByUrlOriginal(Url urlOriginal);
    boolean existsByUrlShort(Url urlShort);
}