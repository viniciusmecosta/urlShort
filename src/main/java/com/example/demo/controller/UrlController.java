package com.example.demo.controller;

import com.example.demo.TO.*;
import com.example.demo.entity.Url;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping(value = "create/{urlOriginal}")
    public ResponseEntity<UrlResponseTO> create(@RequestBody UrlRequestTO urlRequestTO, @PathVariable String urlOriginal) {

        return ResponseEntity.ok(urlService.shortenUrl(urlRequestTO));
    }

    @GetMapping(value = "find/{urlShort}")
    public ResponseEntity<Url> find(@PathVariable String urlShort) {

        return null;
    }

    @GetMapping(value = "ranking")
    public ResponseEntity<List<UrlRankingTO>> ranking() {
        return ResponseEntity.ok(urlService.rankingUrl());

    }

}
