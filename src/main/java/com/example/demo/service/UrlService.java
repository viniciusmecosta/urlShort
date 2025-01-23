package com.example.demo.service;

import com.example.demo.exception.*;
import com.example.demo.to.*;
import com.example.demo.entity.Url;
import com.example.demo.entity.UrlView;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UrlService {
    private final String https = "https://";
    private final String http = "http://";
    private final UrlRepository urlRepository;
    private final UrlViewRepository urlViewRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository, UrlViewRepository urlViewRepository) {
        this.urlRepository = urlRepository;
        this.urlViewRepository = urlViewRepository;
    }

    public Url shortenUrl(String urlReceived) {
        validateUrl(urlReceived);
        urlReceived = addHttps(urlReceived);
        Url existingUrl = urlRepository.findByUrlOriginal(urlReceived);
        if (existingUrl != null) {
            return existingUrl;
        }

        String urlGenerated = generateShortUrl(urlReceived);
        Url url = new Url(urlReceived,urlGenerated);
        urlRepository.save(url);

        return url;
    }

    public String find(String urlShort) {
        validateUrl(urlShort);
        urlShort = addHttps(urlShort);
        Url url = urlRepository.findByUrlShort(urlShort);
        if (url == null) {
            throw new UrlNotFoundException("Url not found");
        }
        UrlView urlView = new UrlView(url.getUrlShort(),new Date().toString());
        urlViewRepository.save(urlView);
        return url.getUrlOriginal();
    }

    public List<UrlRankingTO> ranking() {
        List<UrlRankingTO> urlRankingTOs = urlViewRepository.findRankingUrlView();

		if (urlRankingTOs.isEmpty()) {
            throw new NoUrlViewException("No url fetched");
        }
        return urlRankingTOs;
    }

    public String generateShortUrl(final String originalUrl) {
        String shortUrl;
        do {
            shortUrl = generateHash(originalUrl + UUID.randomUUID()).substring(0, 6);
        } while (urlRepository.findByUrlShort(shortUrl) != null);
        return https + shortUrl.toLowerCase() + ".com";
    }

    private String generateHash(final String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new HashException("Hash Error");
        }
    }

    public void validateUrl(final String url) {
        if (url == null || url.isBlank()) {
            throw new UrlNullException("URL null");
        }
        if (!url.matches(".+\\..+")) {
            throw new UrlInvalidException("Invalid URL (Example: 'example.com' or 'https://example.com' or 'http://example.com')");
        }
    }

    public String addHttps(final String url) {
        if (!url.startsWith(http) && !url.startsWith(https)) {
            return https + url;
        }
        return url;
    }
}