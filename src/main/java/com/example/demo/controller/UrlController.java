package com.example.demo.controller;

import com.example.demo.exception.UrlInvalidException;
import com.example.demo.exception.UrlNullException;
import com.example.demo.to.*;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping(value = "create")
    public ResponseEntity<UrlResponseTO> create(@RequestBody(required = false) String urlReceived) {
        return ResponseEntity.ok(urlService.shortenUrl(urlReceived));
    }

    @GetMapping(value = "find")
    public ResponseEntity<UrlResponseTO> find(@RequestBody(required = false) String urlShort) {
        return ResponseEntity.ok(urlService.findOriginalUrl(urlShort));
    }

    @GetMapping(value = "ranking")
    public ResponseEntity<List<UrlRankingTO>> ranking() {
        return ResponseEntity.ok(urlService.rankingUrl());

    }
}
