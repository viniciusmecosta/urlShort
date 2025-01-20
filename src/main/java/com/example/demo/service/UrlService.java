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
import java.util.stream.Collectors;

@Service
public class UrlService {

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
        List<UrlView> urlViewList = urlViewRepository.findAll();

        Map<String, Long> urlCountMap = urlViewList.stream()
                .collect(Collectors.groupingBy(UrlView::getUrl, Collectors.counting()));

        List<UrlRankingTO> top10Urls = urlCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> new UrlRankingTO(entry.getKey(), entry.getValue().intValue()))
                .toList();

		if (top10Urls.isEmpty()) {
            throw new NoUrlViewException("No url fetched");
        }
        return new ArrayList<>(top10Urls);
    }

    public String generateShortUrl(String originalUrl) {
        String shortUrl;
        do {
            shortUrl = generateHash(originalUrl + UUID.randomUUID()).substring(0, 6);
        } while (urlRepository.findByUrlShort(shortUrl) != null);
        return "https://" + shortUrl.toLowerCase() + ".com";
    }

    private String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new HashException("Hash Error");
        }
    }

    private void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new UrlNullException("URL null");
        }
        if (!url.matches(".+\\..+")) {
            throw new UrlInvalidException("Invalid URL (Example: 'example.com' or 'https://example.com' or 'http://example.com')");
        }
    }

    private String addHttps(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }
}