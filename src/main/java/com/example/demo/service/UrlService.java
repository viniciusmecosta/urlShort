package com.example.demo.service;

import com.example.demo.TO.*;
import com.example.demo.entity.Url;
import com.example.demo.entity.UrlShort;
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

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlShortRepository urlShortRepository;

    @Autowired
    private UrlViewRepository urlViewRepository;

    public UrlResponseTO shortenUrl(String urlRequestTO) {

        Url existingUrl = urlRepository.findByUrlOriginal(urlRequestTO);

        if (existingUrl != null) {
            UrlShort existingShort = urlShortRepository.findByUrlOriginal(existingUrl);
            return new UrlResponseTO(existingShort.getUrlShort(), existingUrl.getUrlOriginal());
        }

        String shortUrl = generateShortUrl(urlRequestTO);

        Url url = new Url(urlRequestTO);
        urlRepository.save(url);

        UrlShort urlShort = new UrlShort(shortUrl, url);
        urlShortRepository.save(urlShort);

        return new UrlResponseTO(urlRequestTO, shortUrl);

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
        String urlClear = originalUrl.replaceFirst("https?://", "");
        String shortUrl = generateHash(urlClear).substring(0, 6);
        return "https://" + shortUrl;
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
}
