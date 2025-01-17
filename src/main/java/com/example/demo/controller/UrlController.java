package com.example.demo.controller;

import com.example.demo.to.*;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UrlController {
    @Autowired
    private UrlService urlService;

	@PostMapping(value = "create")
    public ResponseEntity<UrlResponseTO> create(@RequestBody String urlReceived) {
        return ResponseEntity.ok(urlService.shortenUrl(urlReceived));
    }

    @GetMapping(value = "find")
    public ResponseEntity<UrlResponseTO> find(@RequestBody String urlShort) {
        return ResponseEntity.ok(urlService.findOriginalUrl(urlShort));
    }

    @GetMapping(value = "ranking")
    public ResponseEntity<List<UrlRankingTO>> ranking() {
        return ResponseEntity.ok(urlService.rankingUrl());

    }

    @ControllerAdvice
    public static class HandlerErrorController {

        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ResponseBody
        public String handleException(Exception ex) {
            return ex.getMessage();
        }
    }
}
