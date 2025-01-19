package com.example.demo.controller;

import com.example.demo.to.*;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("create/{url}")
    public ResponseEntity<UrlResponseTO> create(@PathVariable String url) {
        return ResponseEntity.status(HttpStatus.CREATED).body(urlService.shortenUrl(url));
    }

    @GetMapping("find/{shortUrl}")
    public ResponseEntity<Void> find(@PathVariable String shortUrl) {
        String urlOriginal = urlService.find(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlOriginal)) .build();
    }

    @GetMapping(value = "ranking")
    public ResponseEntity<List<UrlRankingTO>> ranking() {
        return ResponseEntity.ok(urlService.rankingUrl());
    }
}
