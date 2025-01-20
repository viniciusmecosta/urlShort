package com.example.demo.repository;

import com.example.demo.entity.UrlView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UrlViewRepositoryTest {

    @Autowired
    private UrlViewRepository urlViewRepository;

    @Test
    void save_shouldPersistUrlView() {
        UrlView urlView = new UrlView("https://abc123.com", "2025-01-20");
        UrlView savedUrlView = urlViewRepository.save(urlView);

        assertNotNull(savedUrlView);
        assertEquals("https://abc123.com", savedUrlView.getUrl());
        assertEquals("2025-01-20", savedUrlView.getDate());
    }
}
