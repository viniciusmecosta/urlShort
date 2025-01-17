package com.example.demo.service;

import com.example.demo.TO.*;
import com.example.demo.entity.Url;
import com.example.demo.entity.UrlShort;
import com.example.demo.entity.UrlView;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlShortRepository urlShortRepository;
    private final UrlViewRepository urlViewRepository;

    public UrlService(UrlRepository urlRepository, UrlShortRepository urlShortRepository, UrlViewRepository urlViewRepository) {
        this.urlRepository = urlRepository;
        this.urlShortRepository = urlShortRepository;
        this.urlViewRepository = urlViewRepository;
    }

    public UrlResponseTO shortenUrl(String urlRequestTO) {
        validateUrl(urlRequestTO);

        UrlShort existingShort = urlShortRepository.findByUrlOriginal(
                urlRepository.findByUrlOriginal(urlRequestTO)
        );

        if (existingShort != null) {
            return new UrlResponseTO(existingShort.getUrlOriginal().getUrlOriginal(), existingShort.getUrlShort());
        }

        String shortUrl = generateShortUrl(urlRequestTO);

        Url url = new Url(urlRequestTO);
        urlRepository.save(url);

        UrlShort urlShort = new UrlShort(shortUrl, url);
        urlShortRepository.save(urlShort);

        return new UrlResponseTO(url.getUrlOriginal(), shortUrl);
    }

    public List<UrlRankingTO> rankingUrl() {
        List<UrlView> urlViewList = urlViewRepository.findAll();

        Map<String, Long> urlCountMap = urlViewList.stream()
                .collect(Collectors.groupingBy(UrlView::getUrl, Collectors.counting()));

        List<UrlRankingTO> top10Urls = urlCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> new UrlRankingTO(entry.getKey(), entry.getValue().intValue()))
                .toList();

        return new ArrayList<>(top10Urls);
    }

    public String generateShortUrl(String originalUrl) {
        String urlClear = originalUrl.replaceFirst("https?://", "").toLowerCase();
        String shortUrl = generateHash(urlClear.toLowerCase()).substring(0, 6);
        return "https://" + shortUrl.toLowerCase();
    }

    private String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: ", e);
        }
    }

    private void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL null");
        }
        if (!url.matches("(https?://)?(www\\.)?.+\\..+")) {
            throw new IllegalArgumentException("Invalid URL : " + url);
        }
    }
    public UrlResponseTO findOriginalUrl(String urlShort) {
        if (urlShort == null || urlShort.isBlank()) {
            throw new IllegalArgumentException("URL null");
        }

        UrlShort urlShortEntity = urlShortRepository.findByUrlShort(urlShort);
        if (urlShortEntity == null) {
            throw new IllegalArgumentException("URL not found: " + urlShort);
        }

        UrlView urlView = new UrlView(urlShortEntity.getUrlShort(),new Date().toString());

        urlViewRepository.save(urlView);

        return new UrlResponseTO(
                urlShortEntity.getUrlOriginal().getUrlOriginal(),
                urlShortEntity.getUrlShort()
        );
    }

}
