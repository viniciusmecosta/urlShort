package com.example.demo.repository;

import com.example.demo.entity.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Test
    void findByUrlShort_whenUrlExists() {
        Url url = new Url("https://example.com", "https://abc123.com");
        urlRepository.save(url);

        Url result = urlRepository.findByUrlShort("https://abc123.com");

        assertNotNull(result);
        assertEquals("https://example.com", result.getUrlOriginal());
    }

    @Test
    void findByUrlShort_whenUrlNoExists() {
        String urlShort = "https://example.com";

        Url result = urlRepository.findByUrlShort(urlShort);

        assertNull(result);
    }
}
